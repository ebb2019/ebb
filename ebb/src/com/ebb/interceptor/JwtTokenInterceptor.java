package com.ebb.interceptor;

import com.ebb.common.BaseResponse;
import com.ebb.common.Code;
import com.ebb.utils.StringUtils;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;

/**
 * JwtToken拦截器
 * 
 * @author dyzeng
 * @date 2018-05-29
 */
public class JwtTokenInterceptor implements Interceptor {

	public void intercept(Invocation inv) {
		Controller controller = inv.getController();
		// 从reques中拿出token
		String token = controller.getPara("token");
		// token报错
		if (StringUtils.isEmpty(token)) {
			controller.renderJson(new BaseResponse(Code.ARGUMENT_ERROR,
					"token不能为空"));
			return;
		}
		// 通过token获取值
		Cache bbsCache = Redis.use("bbs");
		String account = bbsCache.get(token);
		if (account.isEmpty()) { // 为空 说明token失效
			controller.renderJson(new BaseResponse(Code.TOKEN_INVALID,
					"token已经失效,请重新登录"));
			return;
		}
		//
		// //校验jwt的token合法性
		// if(!TokenManager.validateToken(user.getLong("userId").toString(),token)){
		// controller.renderJson(new
		// BaseResponse(Code.TOKEN_INVALID,"token已经失效,请重新登录"));
		// return;
		// }
		String mobile = controller.get("mobile");
		if(!mobile.equals(account)){
			controller.renderJson(new BaseResponse(Code.TOKEN_INVALID,
					"token有误！"));
			return;
		}
		controller.setAttr("account", account);
		inv.invoke();
	}
}
