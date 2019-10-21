package com.ebb.utils;

import org.apache.log4j.Logger;

import com.aliyun.oss.ClientException;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.ebb.common.Code;

public class AliyunSMS {
	
	private static Logger log = Logger.getLogger(AliyunSMS.class);
	
	//产品名称:云通信短信API产品,开发者无需替换
	static final String product = "Dysmsapi";
	//产品域名,开发者无需替换
	static final String domain = "dysmsapi.aliyuncs.com";
	static final String accessKeyId = "LTAIdJ7LIQPylNxj";
	static final String accessKeySecret = "XqozcTYm5wKxY4itcM58EMggKLxkK6";
	static final String signName = "易健身";//签名
//	static final String template = "SMS_173160618";//模板code
	
	public static void sendSms(String mobile,String code,String template) throws com.aliyuncs.exceptions.ClientException {
		//可自助调整超时时间
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");
		
		//初始化acsClient,暂不支持region化
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		IAcsClient acsClient = new DefaultAcsClient(profile);
		
		//组装请求对象-具体描述见控制台-文档部分内容
		SendSmsRequest request = new SendSmsRequest();
		
		//必填:待发送手机号
		request.setPhoneNumbers(mobile);
		//必填:短信签名-可在短信控制台中找到
		request.setSignName(signName);
		//必填:短信模板-可在短信控制台中找到
		request.setTemplateCode(template);
		//可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		request.setTemplateParam( "{\"code\":\""+code+"\"}");
		
		SendSmsResponse httpResponse = acsClient.getAcsResponse(request);
		if("OK".equals(httpResponse.getCode())){
			System.out.println("success_消息发送成功");
		}else{
			if(log.isInfoEnabled()){
				log.error(httpResponse.getCode()+"  "+httpResponse.getMessage());
			}
			System.out.println("error_消息发送失败");
		}
	}
	public static void main(String[] args) throws ClientException, InterruptedException {
		try {
			sendSms("17621588862","1234",Code.SmsRegist);
		} catch (com.aliyuncs.exceptions.ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
