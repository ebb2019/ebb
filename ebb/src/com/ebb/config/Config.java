package com.ebb.config;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;

import com.ebb.controller.AppCoachController;
import com.ebb.controller.AppExerciseController;
import com.ebb.controller.AppDynamicController;
import com.ebb.controller.AppManagerController;
import com.ebb.controller.AppMineController;
import com.ebb.controller.AppUserController;
import com.ebb.controller.CourseController;
import com.ebb.controller.DynamicController;
import com.ebb.controller.ExerciseController;
import com.ebb.controller.ModulController;
import com.ebb.controller.UserController;
import com.ebb.interceptor.JwtTokenInterceptor;
import com.ebb.interceptor.WebSocketHandler;
import com.ebb.utils.ConfigUtils;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.ext.handler.UrlSkipHandler;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.redis.RedisPlugin;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;

	/**
	 * Jfinal配置信息
	 * @author 
	 * 2017/10/11
	 */
	public class Config extends JFinalConfig {
		@Override
		public void configConstant(Constants me) {
			// TODO Auto-generated method stub
			loadPropertyFile("jdbc_config.txt");
			me.setMaxPostSize(1024000000);
			me.setDevMode(getPropertyToBoolean("devMode"));
			me.setViewType(ViewType.JSP);
			me.setEncoding("utf-8");
			me.setBaseUploadPath(ConfigUtils.getProperty("file.path"));
			// 开启对 jfinal web 项目组件 Controller、Interceptor、Validator 的注入
		    me.setInjectDependency(true);
		    // 开启对超类的注入。不开启时可以在超类中通过 Aop.get(...) 进行注入
		    me.setInjectSuperClass(true);
			// me.setUploadedFileSaveDirectory(ConfigUtils.getProperty("file.path").toString());

		}

		@Override
		public void configEngine(Engine arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void configHandler(Handlers me) {
			// TODO Auto-generated method stub
			me.add(new ContextPathHandler("path"));
			me.add(new AccessControlAllowOrigin());
			 me.add(new WebSocketHandler("^/websocket"));
			 me.add(new UrlSkipHandler("^/websocket", false));
			//将参考文献中JFinalConfig中配置
//			me.add(new WebSocketHandler("^/websocket"));
//			//修改成如下(UrlSkipHandler是JFinal默认提供的)
//			me.add(new UrlSkipHandler("^/websocket", false));
		}

		@Override
		public void configInterceptor(Interceptors arg0) {
			//TODO token拦截
//			arg0.add(new JwtTokenInterceptor());
		}

		@Override
		public void configPlugin(Plugins me) {
			C3p0Plugin c3p0Plugin = new C3p0Plugin(getProperty("jdbcUrl"),
					getProperty("user"), getProperty("password").trim(),
					getProperty("driverClassName"));
			me.add(c3p0Plugin);
			ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
			boolean devMode = getPropertyToBoolean("devMode");
			if (devMode)
				arp.setShowSql(true);
			me.add(arp);
			
			//redis
			 // 用于缓存bbs模块的redis服务
		    RedisPlugin bbsRedis = new RedisPlugin("bbs", "47.100.2.141");
		    me.add(bbsRedis);
		}

		@Override
		public void configRoute(Routes me) {
			me.add("/app",AppManagerController.class);
			me.add("/app/user",AppUserController.class);
			//动态类
			me.add("/app/dyn",AppDynamicController.class);
			me.add("/app/course",AppExerciseController.class);
			me.add("/app/coach",AppCoachController.class);
			me.add("/app/mine",AppMineController.class);
			//后台管理
			me.add("/web/exercise",ExerciseController.class);
			me.add("/web/user",UserController.class);
			me.add("/web/course",CourseController.class);
			me.add("/web/dyn",DynamicController.class);
			me.add("/web/modul",ModulController.class);
			
		}
		
		@Override
		public void afterJFinalStart(){
			System.out.println("-----ebb数据库连接成?-----");
//	        // 定时任务
//			QuartzLeaderBoardDaily stock = new QuartzLeaderBoardDaily();	// 日榜
//	        try {
//	       	 //0 0 */1 * * ? 1小时
//	       	 //0 0 1 * * ?  每天凌晨1点执行一�?
//				QuartzManager.addJob("type : ", stock, "0 30 14 * * ?");
//			} catch (SchedulerException e) {
//				// TODO Auto-generated catch block6
//				e.printStackTrace();
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			ServletContext sc = JFinal.me().getServletContext();
			sc.setAttribute("title", "sys");
			sc.setAttribute("company", "sys");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			Date date = new Date();
			String nf = sdf.format(date);
			if(nf.equals("2018")) {
				sc.setAttribute("copyright", "©"+nf);
			} else {
				sc.setAttribute("copyright", "©2018-"+nf);
			}
			sc.setAttribute("version", "v1.0.0");
		}

}
