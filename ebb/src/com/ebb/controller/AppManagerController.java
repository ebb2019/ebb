package com.ebb.controller;

import com.ebb.service.UserService;
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;


public class AppManagerController extends Controller {
	
	@Inject(com.ebb.service.impl.UserServiceImpl.class)
	private UserService userService;
	
	public void index() {
		String returnStr = userService.getUserInfo();
		renderJson(returnStr);
	}
	
	public void get(){
		renderText("11111");
	}
}
