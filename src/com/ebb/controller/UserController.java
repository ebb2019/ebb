package com.ebb.controller;


import java.util.Date;

import java.util.List;

import com.ebb.common.Code;
import com.ebb.common.Inits;
import com.ebb.model.dto.ReturnJson;
import com.ebb.model.dto.ToPage;
import com.ebb.utils.DateUtils;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class UserController extends Controller{
	
	/**
	 * 后台账号登陆
	 */
	@Clear
	public void login(){
		
		String userName = get("name");
		String password = get("password");
		
		ReturnJson rj = new ReturnJson();
		List<Record> list = Db.find("select * from admin_info where user_name='"+userName+"' and password = '"+password+"'");
		
		if(list.size() > 0){ //说明有此账号
			rj.setCode(1);
			rj.setBody(list.get(0));
		}else{
			rj.setCode(0);
		}
		renderJson(rj);
	}
	
	@Clear
	public void getMenu(){
		ReturnJson rj = new ReturnJson();
		List<Record> PList = Db.find("select * from menu_info where parent = 0");
		
		for(Record rd : PList){
			int id = rd.getInt("id");
			List<Record> list = Db.find("select * from menu_info where parent = "+id+"");
			rd.set("children", list);
		}
		rj.setCode(1);
		rj.setBody(PList);
		renderJson(rj);
		//
	}

	
	/**
	 * 获得会员信息
	 */
	@Clear
	public void getUserInfo(){
		ReturnJson rj = new ReturnJson();
		//获得分页数据
		Integer currentPage = getInt("currentPage");
 		Integer pagesize = getInt("pagesize");
 		String where = "";
 		where += " 1=1";
 		String nickName = get("nickName");
 		String mobile = get("mobile");
 		Long beginTime = getLong("beginTime");
 		Long endTime = getLong("endTime");
 		if(beginTime != null){
 			where += " and create_time > '"+DateUtils.stampToStr(beginTime, "yyyy-MM-dd 00:00:00")+"'";
 		}
 		if(endTime != null){
 			//转成时间
 			where += " and create_time < '"+DateUtils.stampToStr(endTime, "yyyy-MM-dd 23:59:59")+"'";
 		}
 		if(!nickName.isEmpty()){
 			where += " and nick_name like '%"+nickName+"%'";
 		}
 		if(!mobile.isEmpty()){
 			where += " and mobile like '%"+mobile+"%'";
 		}
 		
 		where += " order by create_time desc";
		//分页如果为空，设置初始参数
		ToPage page = Inits.initPage(currentPage, pagesize);
		
		Page<Record> pageList = Db.paginate(page.getCurrentPage(), page.getPagesize(), "select * ","from user_info where"+where);
	
		rj.setBody(pageList);
		rj.setCode(Code.SUCCESS);
	
		renderJson(rj);
	}
	
	
	/**
	 * 获取用户列表
	 */
	public void getAllAdmin(){
		List<Record> find = Db.find("select * from admin_info admin left join role_info role on admin.role_id = role.id");
		renderJson(find);
	}
	
	/**
	 * 添加用户
	 * 若添加成功则返回true，否则返回false
	 */
	public void insertAdmin(){
		Record record = new Record();
		boolean save = false;
		
		List<Record> find = Db.find("select user_name from admin_info where account = '" + get("account") + "'");
		if(find.isEmpty()){
			record.set("user_name", get("user_name"));
			record.set("account",get("account"));
			record.set("password",get("password"));
			record.set("role_id",1);
			record.set("is_abled","1");
			record.set("create_time", new Date());
			record.set("update_time", new Date());
			
			save = Db.save("admin_info", record);
			renderJson(save);
		}else{
			renderJson(save);
		}
	}
	
	/**
	 * 更改用户信息
	 */
	public void updateAdmin(){
		Record record = new Record();
		boolean update = false;
		
		List<Record> find = Db.find("select account from admin_info where account = '"+get("account")+"'");
		if(find.isEmpty()){
			record.set("id", getInt("id"));
			record.set("user_name", get("user_name"));
			record.set("account",get("account"));
			record.set("password", get("password"));
			record.set("role_id",getInt("role_id"));
			record.set("role_name", get("role_name"));
			record.set("is_abled", getInt("is_abled"));
			record.set("update_time",new Date());
			
			update = Db.update("admin_info", "id", record);
			renderJson(update);
		}else{
			renderJson(update);
		}	
	}
	
	/**
	 * 删除用户信息
	 */
	public void deleteAdmin(){
		
		Record record = new Record();
		record.set("id", get("id"));
		record.set("is_abled",getInt("is_abled"));
		record.set("update_time", new Date());
		
		boolean update = Db.update("admin_info","id",record);
		renderJson(update);
		
	}
	
	/**
	 * 获取角色列表
	 */
	public void getAllRole(){
		List<Record> find = Db.find("select * from role_info");
		renderJson(find);
	}
	
	/**
	 * 添加角色
	 */
	public void insertRole(){
		
		
		Record record = new Record();
		record.set("name", get("name"));
		record.set("operator", get("operator"));
		record.set("menu_list", get("menu_list"));
		record.set("is_abled",1);
		record.set("create_time", new Date());
		record.set("update_time", new Date());
		
		
		List<Record> find = Db.find("select * from role_info where name = '"+ record.getStr("name") + "'");
		boolean save = false;
		if(find.isEmpty()){
			save = Db.save("role_info", record);
			renderJson(save);
		}else{
			renderJson(save);
		}
	}
	/**
	 * 更改角色信息
	 */
	public void updateRole(){
		
		Record record = new Record();
		boolean update = false;
		
		List<Record> find = Db.find("select name from role_info where name = '"+get("name")+"'");
		if(find.isEmpty()){
			record.set("id", getInt("id"));
			record.set("name", get("name"));
			record.set("operator",get("operator"));
			record.set("menu_list", get("menu_list"));
			record.set("update_time", new Date());
			record.set("is_abled", getInt("is_abled"));
			
			
			update = Db.update("role_info", "id", record);
			renderJson(update);
		}else{
			renderJson(update);
		}		
	}
	
	/**
	 * 删除角色信息
	 * 需要数据:id,is_abled
	 */
	public void deleteRole(){
		Record record = new Record();
		record.set("id", get("id"));
		record.set("is_abled",getInt("is_abled"));
		record.set("update_time", new Date());
		
		boolean update = Db.update("role_info","id",record);
		renderJson(update);
	}
	
	/**
	 * 获取所有的菜单
	 */
	public void getAllMenu(){
		List<Record> find = Db.find("select * from menu_info");
		renderJson(find);
	}
	
	/**
	 * 获取层级列表
	 */
	public void getLevelMenu(){
		
		List<Record> result = Db.find("select * from menu_info where parent = 0");
		for (Record record : result) {
			int id = record.getInt("id");
			List<Record> child = Db.find("select * from menu_info where parent = '"+id+"'");
			record.set("child", child);
		}
		renderJson(result);
	}
	
	/**
	 * test分页
	 */
	public void getPageAdmin(){

		int pageNum = Integer.parseInt(get("pageNum"));
		int pageSize = Integer.parseInt(get("pageSize"));
		List<Record> find = Db.find("select * from admin_info limit "+pageNum*pageSize+","+pageSize);
		renderJson(find);

	}
	
}
