package com.ebb.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ebb.common.Code;
import com.ebb.model.dto.ReturnJson;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;

public class CourseController extends Controller{
	
	private static Logger log = Logger.getLogger(CourseController.class);
	
	/**
	 * 获得课程分类
	 */
	@Clear
	public void getSortList(){
		ReturnJson rj = new ReturnJson();
		
		try {
			List<Record> list =Db.find("select * from course_sort where is_able = 1 and pid= 0");
			//获得数量
			for(Record rd : list){
				Record temp = Db.findFirst("select count(id) num from course_sort where pid = "+rd.getInt("id")+"");
				rd.set("num", temp.getInt("num"));
			}
			rj.setBody(list);
			rj.setCode(Code.SUCCESS);
			rj.setMessage("查询成功！");
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e+"");
			rj.setCode(Code.FAIL);
			rj.setMessage("查询失败！");
		}
		renderJson(rj);
	}
	
	/**
	 * 通过sortId获得子分类
	 */
	@Clear
	public void getCourseBySortId(){
		Integer id = getInt("id");
		ReturnJson rj = new ReturnJson();
		try {
			List<Record> list = Db.find("select * from course_sort where is_able = 1 and pid= "+id+"");
			List<Record> rList = new ArrayList<>();
			for(Record rd : list){
				Record temp = new Record();
				temp.set("subName", rd.get("name"));
				temp.set("subImg", rd.get("img_path"));
				rList.add(temp);
			}
			rj.setBody(rList);
			rj.setCode(Code.SUCCESS);
			rj.setMessage("查询成功！");
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e+"");
			rj.setCode(Code.FAIL);
			rj.setMessage("查询失败！");
		}
		renderJson(rj);
	}
	
	/**
	 * 删除大分类，执行更新
	 */
	@Clear
	public void deleteSort(){
		Integer id = getInt("id");
		ReturnJson rj = new ReturnJson();
		Record temp = new Record();
		temp.set("id", id);
		temp.set("is_able", 0);
		
		try {
			Db.update("course_sort", temp);
			rj.setCode(Code.SUCCESS);
			rj.setMessage("查询成功！");
			
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e+"");
			rj.setCode(Code.FAIL);
			rj.setMessage("查询失败！");
		}
		renderJson(rj);
	}
}
