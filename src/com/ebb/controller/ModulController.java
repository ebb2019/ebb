package com.ebb.controller;

import java.util.List;

import org.apache.log4j.Logger;

import com.ebb.common.Code;
import com.ebb.model.dto.ReturnJson;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class ModulController extends Controller{
	
	private static Logger log = Logger.getLogger(ModulController.class);
	
	public void getModulList(){
		ReturnJson rj = new ReturnJson();
		
		try {
			List<Record> rdList = Db.find("select * from menu_info where is_able =1");
			rj.setBody(rdList);
			rj.setCode(Code.SUCCESS);
			rj.setMessage("查询成功");
		} catch (Exception e) {
			log.error(e);
			// TODO: handle exception
			rj.setCode(Code.FAIL);
			rj.setMessage("查询失败");
		}finally{
			renderJson(rj);
		}
	}
}
