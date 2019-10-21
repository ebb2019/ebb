package com.ebb.utils;

import java.util.HashMap;
import java.util.Map;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

public class JPushUtils {
	private static String APP_KEY = "XXXXXXXXXXXXXXXXXX";
	private static String MASTER_SECRET = "XXXXXXXXXXXXXXXX";
	
	/**
	 * IOS推送
	 * @param parm
	 */
	public static void jpushAndroid(Map<String, String> parm) {
		 
		//创建JPushClient(极光推送的实例)
		JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
		//推送的关键,构造一个payload 
		PushPayload payload = PushPayload.newBuilder()
				.setPlatform(Platform.android())//指定android平台的用户
				.setAudience(Audience.all())//你项目中的所有用户
				//.setAudience(Audience.registrationId(parm.get("id")))//registrationId指定用户
				.setNotification(Notification.android(parm.get("msg"), "这是title", parm))
				//发送内容
				.setOptions(Options.newBuilder().setApnsProduction(false).build())
				//这里是指定开发环境,不用设置也没关系
				.setMessage(Message.content(parm.get("msg")))//自定义信息
				.build();
 
		try {
			PushResult pu = jpushClient.sendPush(payload);  
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (APIRequestException e) {
			e.printStackTrace();
		}    
	}
	
	/**
	 * 苹果推送
	 * @param parm
	 */
	public static  void jpushIOS(Map<String, String> parm) {
		 
		//创建JPushClient
		JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
		PushPayload payload = PushPayload.newBuilder()
				.setPlatform(Platform.ios())//ios平台的用户
				.setAudience(Audience.all())//所有用户
				//.setAudience(Audience.registrationId(parm.get("id")))//registrationId指定用户
				.setNotification(Notification.newBuilder()
						.addPlatformNotification(IosNotification.newBuilder()
								.setAlert(parm.get("msg"))
								.setBadge(+1)
								.setSound("happy")//这里是设置提示音(更多可以去官网看看)
								.addExtras(parm)
								.build())
						.build())
				.setOptions(Options.newBuilder().setApnsProduction(false).build())
				.setMessage(Message.newBuilder().setMsgContent(parm.get("msg")).addExtras(parm).build())//自定义信息
				.build();
 
		try {
			PushResult pu = jpushClient.sendPush(payload);
 
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (APIRequestException e) {
			e.printStackTrace();
		}    

	}
	
	/**
	 * 安卓 IOS全推
	 * @param parm
	 */
	public static void jpushAll(Map<String, String> parm) {
		 
		//创建JPushClient
		JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
		//创建option
		PushPayload payload = PushPayload.newBuilder()
				.setPlatform(Platform.all())  //所有平台的用户
				.setAudience(Audience.registrationId(parm.get("id")))//registrationId指定用户
				.setNotification(Notification.newBuilder()
						.addPlatformNotification(IosNotification.newBuilder() //发送ios
								.setAlert(parm.get("msg")) //消息体
								.setBadge(+1)
								.setSound("happy") //ios提示音
								.addExtras(parm) //附加参数
								.build())
						.addPlatformNotification(AndroidNotification.newBuilder() //发送android
								.addExtras(parm) //附加参数
								.setAlert(parm.get("msg")) //消息体
								.build())
						.build())
				.setOptions(Options.newBuilder().setApnsProduction(true).build())//指定开发环境 true为生产模式 false 为测试模式 (android不区分模式,ios区分模式)
				.setMessage(Message.newBuilder().setMsgContent(parm.get("msg")).addExtras(parm).build())//自定义信息
				.build();
		try {
			PushResult pu = jpushClient.sendPush(payload);
			System.out.println(pu.toString());
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (APIRequestException e) {
			e.printStackTrace();
		}    

	}
	

	public static void main(String[] args) {
 
		//设置推送参数
		//这里同学们就可以自定义推送参数了
		Map<String, String> parm = new HashMap<String, String>();
		//这里的id是,移动端集成极光并登陆后,极光用户的rid
		parm.put("id", "XXXXXXXXXXXXXXXX");                                                                                      
		//设置提示信息,内容是文章标题
		parm.put("msg","测试测试,收到请联系发送人");
		jpushAll(parm);
 
	}
}
