package com.ebb.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;

/**
 * 解决跨域访问
 * date time : 2017�?�?2�?13:47:12
 */
public class AccessControlAllowOrigin extends Handler {

	@SuppressWarnings("deprecation")
	@Override
	public void handle(String target, HttpServletRequest request,
			HttpServletResponse response, boolean[] isHandled) {
		response.setHeader("Access-Control-Allow-Origin", "*");// cors解决跨域
		nextHandler.handle(target, request, response, isHandled);
	}
}
