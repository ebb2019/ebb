package com.ebb.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.hutool.core.date.DateUtil;

import com.ebb.common.Code;
import com.ebb.model.dto.ReturnJson;
import com.ebb.utils.AliyunVODUtiles;
import com.ebb.utils.DateUtils;
import com.ebb.utils.RandomNameUtils;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

public class AppCoachController extends Controller{

	private static Logger log = Logger.getLogger(AppCoachController.class);
	/**
	 * 获取约教课程列表
	 */
	@Clear
	public void getCoachCourseList(){  
		ReturnJson returnJson = new ReturnJson();
		
		try {
			List<Record> FirstList = Db.find("select * from course_sort where is_able = 1 and pid = 0");
			for (Record record : FirstList) {
				List<Record> secondList = Db.find("select * from course_sort where is_able = 1 and pid = "+ record.getInt("id"));
				record.set("child",secondList);
			}
			List<UploadFile> a = getFiles();
			
			returnJson.setBody(FirstList);
			returnJson.setCode(Code.SUCCESS);
			returnJson.setMessage("获取成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnJson.setCode(Code.FAIL);
			returnJson.setMessage("获取异常");
		}finally{
			renderJson(returnJson);
		}
		
	}
	
	/**
	 * 获取约教课程详细信息
	 */
	public void getCoachCourseDetail(){
		//获取课程id
		Integer id = getInt("id");
		
		ReturnJson returnJson = new ReturnJson();
		
		try {
			Record rd = Db.findFirst("select * from course_info where sort_id = "+id);
			
			String vid = AliyunVODUtiles.getPlayInfo(rd.getStr("vid"));
			rd.set("vurl", vid);
			
			returnJson.setBody(rd);
			returnJson.setCode(Code.SUCCESS);
			returnJson.setMessage("获取成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnJson.setCode(Code.FAIL);
			returnJson.setMessage("获取异常");
		}finally{
			renderJson(returnJson);
		}
	}
	
	/**
	 * 获取约教课程对应的课时及教练信息
	 */
	@Clear
	public void getCoachAndPeriod(){
		Integer courseId = getInt("courseId");
		ReturnJson returnJson = new ReturnJson();
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		try {
			Record findFirst = Db.findFirst("select * from course_info where id = "+courseId);
			
			Integer periodId = findFirst.getInt("period_id");
			
			//查询当前所选课程的课时及价格
			List<Record> find = Db.find("select ci.pic_path pic_path, pd.id periodId,pd.coast coast,pd.period from period_config pc left join course_info ci on pc.course_id = ci.id left join period_dic pd on pc.period_id = pd.id where ci.period_id = "+periodId+" order by coast asc");
			
			//获得价格区间
			Integer beginCoast = find.get(0).getInt("coast");
			Integer endCoast = find.get(find.size()-1).getInt("coast");
			
			//获得月销量
			Record rd = Db.findFirst("select count(id) as num from order_info where status = 9 or status = 10 and type = 1");
			
			
			//查询当前所选课程对应的教练
			List<Record> find2 = Db.find("select  ui.id,ui.nick_name,ui.quotes quotes, ui.mobile,ui.nick_id,ui.sex,pic_path from coach_config cc left join user_info ui on cc.coach_id = ui.id where ui.type = 1 and cc.status = 1 and cc.is_able = 1 and  cc.course_id = "+courseId);
			map.put("pic_path", find.get(0).getStr("pic_path"));
			map.put("period", find);
			map.put("coach", find2);
			map.put("beginCoast", beginCoast);
			map.put("endCoast", endCoast);
			map.put("num", rd.getInt("num"));
			
			returnJson.setBody(map);
			returnJson.setCode(Code.SUCCESS);
			returnJson.setMessage("获取成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnJson.setCode(Code.FAIL);
			returnJson.setMessage("获取异常");
		}finally{
			renderJson(returnJson);
		}
		
	}
	/**
	 * 保存约教订单(伪)
	 */
	@Clear
	public void saveFakeCoachOrder(){
		Integer userId = getInt("userId");
		Integer courseId = getInt("courseId");
		String lon = get("lon");
		String lat = get("lat");
		String address = get("address");
		String day = get("day");
		String beginTime = get("beginTime");
		String endTime = get("endTime");
		String province = get("province");
		String city = get("city");
		String region = get("region");
		
		Record record = new Record();
		ReturnJson returnJson = new ReturnJson();
		
		record.set("user_id",userId);
		record.set("course_id", courseId);
		record.set("order_num",RandomNameUtils.createOrderNum("YJ"));
		record.set("lon",lon);
		record.set("lat", lat);
		record.set("address",address);
		record.set("day", day);
		record.set("begin_time",day+" "+beginTime);
		record.set("end_time",day+" "+endTime);
		record.set("province",province);
		record.set("city",city);
		record.set("region", region);
		record.set("create_time",new Date());
		record.set("update_time", new Date());
		record.set("type", 1);
		record.set("status", 99);
		record.set("is_able",1);
		
		try {
			Db.save("order_info", record);
			
			returnJson.setBody(record);
			returnJson.setCode(Code.SUCCESS);
			returnJson.setMessage("保存成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnJson.setCode(Code.FAIL);
			returnJson.setMessage("保存异常");
		}finally{
			renderJson(returnJson);
		}
	}
	
	/**
	 * 在伪订单中保存用户所选课时及教练
	 */
	public void updateCoachOrder(){
	
		//获取当前用户所选择课时id
		Integer periodId = getInt("periodId");
		//获取当前用户所选择教练id
		Integer coachId = getInt("coachId");
		//获取之前伪订单id
		Integer orderId = getInt("orderId");
		
		ReturnJson returnJson = new ReturnJson();
		Record record = new Record();
		
		record.set("period_id", periodId);
		record.set("coach_id", coachId);
		record.set("id", orderId);
		record.set("status", 0);
		
		try {
			Db.update("order_info", "id", record);
//			Db.update("update order_info set period_id = "+periodId+" ,coach_id = "+coachId+" where  id ="+orderId);
			returnJson.setBody(record);
			returnJson.setCode(Code.SUCCESS);
			returnJson.setMessage("保存成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnJson.setCode(Code.FAIL);
			returnJson.setMessage("保存异常");
		}finally{
			renderJson(returnJson);
		}
		
	}
	
	/**
	 * 订单逻辑 --获得所有订单
	 * 0:初步发起；1：带接单；2：达成订单，会员待支付；5：会员已经支付，待上课；9：完成订单，待评价；10：评价完成
	 */
	public void getOrderListByStatus(){
		
		Integer status = getInt("status");
		Integer id = getInt("id");
		String where = "";
		ReturnJson rj = new ReturnJson();
		if(status != null ){
			where += " and status = "+status+"";
		}
		if(id != null ){
			where += " and id="+where+"";
		}
		try {
			List<Record> rd = Db.find("select * from order_info where is_able = 1 and type = 1 "+where);
			rj.setBody(rd);
			rj.setCode(Code.SUCCESS);
			rj.setMessage("查询成功");
		} catch (Exception e) {
			// TODO: handle exception
			rj.setCode(Code.FAIL);
			rj.setMessage("查询失败");
		}finally{
			renderJson(rj);
		}
	}
	
	/**
	 * 取消订单
	 */
	public void cancelOrder(){
		
		Integer id = getInt("id");
		ReturnJson rj = new ReturnJson();
		
		//根据Id取消订单
		Record rd = new Record();
		rd.set("id", id);
		rd.set("status", 99);
		try {
			Db.update("oreder_info", "id", rd);
			rj.setBody(rd);
			rj.setCode(Code.SUCCESS);
			rj.setMessage("取消成功！");
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e);
			rj.setCode(Code.FAIL);
			rj.setMessage("取消失败！");
		}finally{
			renderJson("rj");
		}
	}
	
	/**
	 * 获得订单详情
	 */
	public void getOrderById(){
		
		Integer id = getInt("id");
		ReturnJson rj = new ReturnJson();
		
		try {
			//通过Id获得订单详情
			Record rd = Db.findFirst("select * from order_info where id = "+id+"");
			rj.setBody(rd);
			rj.setCode(Code.SUCCESS);
			rj.setMessage("成功！");
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e);
			rj.setCode(Code.FAIL);
			rj.setMessage("失败！");
		}
	}
	
}
