package com.ebb.controller;

import java.util.List;

import com.ebb.common.Code;
import com.ebb.common.Inits;
import com.ebb.model.dto.ReturnJson;
import com.ebb.model.dto.ToPage;
import com.jfinal.aop.Clear;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class ExerciseController extends CourseController{
	
	/**
	 * 获得所有约练订单
	 */
	@Clear
	public void getAllExerciseOrder(){
		Integer pageNum = getInt("pageNum");
		Integer pageSize = getInt("pageSize");
		ReturnJson returnJson = new ReturnJson();
		
		ToPage toPage = Inits.initPage(pageNum,pageSize);
		
		String where = "";
		
		try {
			String from = "from order_info o LEFT JOIN exercise_sort e ON o.course_id = e.id LEFT JOIN user_info u ON o.user_id = u.id where o.type = 2"+ where;
			
			Page<Record> find = Db.paginate(toPage.getCurrentPage(), toPage.getPagesize(), "select o.*, u.nick_id,u.mobile,u.sex,u.nick_name,e.name course_name ",from);
			
			
//			List<Record> find = Db.find("select oi.*,oc.*,ui.*,oi.id from order_info oi left join order_config oc on oi.id = oc.order_id "
//					                                       + "left join user_info ui on oi.user_id = ui.id" 
//			         +" where oi.is_able = 1 and oi.type = 2 and oi.status = 0 or oi.status = 10 order by oi.update_time desc limit "+(pageNum-1)*pageSize+","+pageSize);
			
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
	 * 获取约练订单的详细信息
	 */
	@Clear
	public void getExerciseOrderDetail(){
		//获取订单id
		Integer orderId = getInt("id");
		
		ReturnJson returnJson = new ReturnJson();
		
		try {
			List<Record> find = Db.find("select oi.*,oc.*,ui.* from order_config oc left join order_info oi on oc.order_id = oi.id"
															 +" left join user_info ui on oc.user_id = ui.id"
					 +" where oc.order_id = "+orderId);
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
	 * 删除该约练订单
	 */
	@Clear
	public void deleteExerciseOrder(){
		Integer orderId = getInt("id");
		
		ReturnJson returnJson = new ReturnJson();
		
		try {
			int update = Db.update("update order_info set is_able = 0 where id = "+orderId);
			returnJson.setBody(update);
			returnJson.setCode(Code.SUCCESS);
			returnJson.setMessage("修改成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnJson.setCode(Code.FAIL);
			returnJson.setMessage("修改异常");
		}finally{
			renderJson(returnJson);
		}
	}
}
