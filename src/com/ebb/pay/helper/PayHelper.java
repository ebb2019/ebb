package com.ebb.pay.helper;

/**
 * 支付工具类
 * 
 * @author Administrator
 *
 */
public class PayHelper {

	public static Object createPayObject(String type, String payType, String orderNum, String fee, String subject,
			String returnUrl, String showurl, String ip, String openid) throws Exception {
		if (type.equals("alipay")) {
			if(payType.equals("web")){
				return AlipayHelper.bulidHtml(orderNum, subject, fee);
			}else if(payType.equals("wap")){
				return AlipayHelper.bulidMobileHtml(orderNum, subject, fee, showurl);
			}else if(payType.equals("app")){
				return AlipayHelper.bulidAppForm(orderNum, fee);
			}
		}

		if (type.equals("weixinpay")) {
			if(payType.equals("web")){
				return WeiXinHelper.bulidWebPay(orderNum, fee, ip, "wap", subject, orderNum);
			}else if(payType.equals("wap")){
				return WeiXinHelper.bulidWebPay(orderNum, fee, ip, "wap", subject, orderNum);
			}else if(payType.equals("js")){
				return WeiXinHelper.bulidJSPay(orderNum, fee, ip, "js", openid, subject);
			}else if(payType.equals("app")){
				return WeiXinHelper.bulidAppPay(orderNum, fee, ip, "app", subject);
			}
		}
		return null;
	}
}
