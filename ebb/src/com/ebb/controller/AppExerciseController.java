package com.ebb.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ebb.common.Code;
import com.ebb.common.TimeFormat;
import com.ebb.model.dto.ReturnJson;
import com.ebb.utils.RandomNameUtils;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class AppExerciseController extends Controller{

	private static Logger log = Logger.getLogger(AppExerciseController.class);
	/**
	 * @author xwyf
	 * 
	 * @describe 获取约练分类信息列表
	 */
	@Clear
	public void getExercise(){
		ReturnJson returnJson = new ReturnJson();
		List<Record> result = null;
		
		try {
			result = Db.find("select * from exercise_sort where is_able = 1 and pid = 0 and type =1");
			for (Record record : result) {
				int id = record.getInt("id");

				List<Record> child = Db.find("select * from exercise_sort where is_able = 1 and pid = "+id);

				record.set("child", child);
			}
			//获取约练信息列表
			returnJson.setBody(result);
			returnJson.setCode(Code.SUCCESS);
			returnJson.setMessage("获取成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnJson.setCode(Code.FAIL);
			returnJson.setMessage("获取异常");
		}finally{
			renderJson(returnJson);
		}
	}
	
	/**
	 * @author xwyf
	 * 
	 * @describe 获取约练信息详情
	 */
	@Clear
	public void getExerciseDetail(){
		//接收所选择的约练内容id
		Integer id = getInt("id");
		ReturnJson returnJson = new ReturnJson();
		
		try {
			Record find = Db.findFirst("select * from exercise_sort where id = "+id+"");
			Map<String, Record> timeMap = TimeFormat.timeTocharact();
			returnJson.setExample(timeMap);
			returnJson.setBody(find);
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
	
	public void toExercise(){
		
	}
	
	/**
	 * 保存约练订单
	 */
	public void saveExercise(){
		String lon = get("lon"); //经度
		String lat = get("lat"); //纬度
		String day = get("day"); //天
		String night = get("night"); //时辰
		Integer courseId = getInt("courseId"); //课程id
		Integer userId = getInt("userId"); //发起人Id
		String address = get("address"); //地点
		String clubName = get("clubName"); //会所名称
		String province = get("province");
		String city = get("city");
		String region = get("region");
		ReturnJson rj = new ReturnJson();
		//生成点单号
		String orderNum = RandomNameUtils.createOrderNum("YL");
		//数据存入订单表
		Record rd = new Record();
		rd.set("province", province);
		rd.set("city", city);
		rd.set("region", region);
		rd.set("order_num", orderNum);
		rd.set("course_id", courseId);
		rd.set("status", 0);
		rd.set("lon", lon);
		rd.set("lat", lat);
		rd.set("address", address);
		rd.set("club_name", clubName);
		rd.set("is_able", 1);
		rd.set("create_time", new Date());
		rd.set("type", 2);
		rd.set("day", day);
		rd.set("night", night);
		rd.set("user_id", userId);
		rd.set("update_time", new Date());
		try {
			Db.save("order_info", rd);
			//数据保存关联表
//			Record configRd = new Record();
//			configRd.set("order_id", rd.getInt("id"));
//			configRd.set("user_id", userId);
//			Db.save("order_config", configRd);
			rj.setBody(rd);
			rj.setCode(Code.SUCCESS);
			rj.setMessage("保存成功");
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e);
			rj.setCode(Code.FAIL);
			rj.setMessage("保存失败");
		}finally{
			renderJson(rj);
		}
		
		
	}
	
	/**
	 * 获取所有约练订单列表
	 * 筛选条件:相同健身会所，约练类型订单
	 * 
	 */
	@Clear
	public void getExerciseOrderList(){
		ReturnJson returnJson = new ReturnJson();
		
		//获取当前用户id
		Integer userId = getInt("userId");
		String userLon = get("lon");
		String userLat = get("lat");
		
		Integer pageNum = getInt("pageNum");
		Integer pageSize = getInt("pageSize");
		//根据用户id查询当前用户属于哪个健身会所
		try {
//			Record findFirst = Db.findFirst("select * from survey_table where user_id = "+userId);
//			
//			String clubName = findFirst.getStr("club_name");
			
			//根据健身会所查询所有相同会所的未禁用的约练订单信息(默认2公里内)
//			List<Record> find = Db.find("select * from (select *,lat_lng_distance(order_info.lon,order_info.lat,"+userLon+","
//			                     +userLat+") as dis from order_info where is_able = 1 and club_name = '"+clubName+"') a where a.dis <= 2 order by a.dis desc");
			if(pageNum == null){
				pageNum = 1;
			}
			if(pageSize == null){
				pageSize = 20;
			}
			//where a.dis <= 2
			List<Record> find = Db.find("select * from (select o.*,u.sex,u.nick_id,u.nick_name,u.pic_path,e.name,lat_lng_distance(o.lon,o.lat,"+userLon+","
			                     +userLat+") as dis from order_info o LEFT JOIN user_info u on o.user_id=u.id left join exercise_sort e on o.course_id = e.id  where o.is_able = 1 and o.type = 2 and u.id != "+userId+" limit "+(pageNum-1)*pageSize+","+pageSize+") a  order by a.dis,update_time desc");

			//若当前位置没有约练订单
			if(find.isEmpty()){
				returnJson.setBody(Code.SUCCESS);
				returnJson.setMessage("附近暂无约练订单");
				return;
			}
			
			returnJson.setBody(find);
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
	 * 获取约练订单详情
	 */
	@Clear
	public void getExerciseOrder(){
		//根据约练订单id获取订单详细信息
		Integer id = getInt("id");
		ReturnJson returnJson = new ReturnJson();
		
		try {
			List<Record> find = Db.find("select o.*,e.name from order_info o left join exercise_sort e on o.caurse_id = e.id  where o.is_able = 1 and o.type = 2 and o.id = "+id);
			
			returnJson.setBody(find);
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
	 * 通过用户id获得未完成订单
	 */
	public void getExerciseOrderByUserId(){
		Integer id = getInt("userId");
		ReturnJson returnJson = new ReturnJson();
		
		try {
			Record user =Db.findFirst("select * from user_info where id = "+id+"");
			Record exercise =Db.findFirst("select * from order_info where is_able = 1 and type = 2 and status =0 and user_id="+id+"");
//			Record find = Db.findFirst("select o.*,u.* from user_info u left join order_info o  on o.user_id = u.id  where o.is_able = 1 and o.type = 2 and o.status =0  and u.id = "+id);
			
			Map<String, Record> map = new HashMap<String, Record>();
			map.put("user", user);
			map.put("exercise", exercise);
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
	
	public void reachOrder(){
		Integer userId = getInt("userId");
		Integer orderId = getInt("orderId");
		ReturnJson rj = new ReturnJson();
		//完成订单，执行更新
		Record rd = new Record();
		rd.set("user_id", userId);
		rd.set("status", 10);
		try {
			Db.update("order_info", "user_id", rd);
			//保存订单关联
			Record tempRd = new Record();
			tempRd.set("order_id", orderId);
			tempRd.set("user_id", userId);
			tempRd.set("status", 10);
			rd.set("update_time", new Date());
			Db.save("order_config", tempRd);
			
			rj.setCode(Code.SUCCESS);
			rj.setMessage("获取成功");
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e);
			rj.setCode(Code.FAIL);
			rj.setMessage("保存失败");
		}finally{
			renderJson(rj);
		}
	}
	
	/**
	 * 更新订单状态
	 */
	public void updateOrderStatus(){
		//TODO:所有订单 包含约教和约练
		Integer orderId = getInt("orderId");
		Integer status = getInt("status");
		
		ReturnJson returnJson = new ReturnJson();
		Record record = new Record();
		record.set("order_id", orderId);
		record.set("status", status);
		
		try {
			boolean update = Db.update("order_info", record);
			returnJson.setBody(update);
			returnJson.setCode(Code.SUCCESS);
			returnJson.setMessage("更新成功");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnJson.setCode(Code.FAIL);
			returnJson.setMessage("更新异常");
		}finally{
			renderJson(returnJson);
		}
	}
}
