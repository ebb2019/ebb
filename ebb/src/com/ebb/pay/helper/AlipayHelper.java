package com.ebb.pay.helper;

import java.net.URLEncoder;
import java.util.Map;

import com.ebb.pay.config.AlipayConfig;
import com.ebb.pay.form.AlipayAppForm;
import com.ebb.pay.form.AlipayAppPay;
import com.ebb.pay.form.AlipayDirectPay;
import com.ebb.pay.form.AlipayMobilePay;
import com.ebb.pay.sign.RSA;
import com.ebb.pay.util.MD5Util;
import com.ebb.pay.util.MapUtil;
import com.ebb.pay.util.StringUtil;


/**
 * 支付宝支付 工具类
 * 
 * @author Administrator
 *
 */
public class AlipayHelper {

	/**
	 * 生成wap支付
	 * 
	 * @param orderNum
	 * @param subject
	 * @param amount
	 * @param showurl
	 *            显示产品url
	 * @return
	 */
	public static String bulidMobileHtml(String orderNum, String subject, String amount, String showurl) {
		AlipayMobilePay mobilePay = new AlipayMobilePay();
		mobilePay.setOut_trade_no(orderNum);
		double fee = (Double.parseDouble(amount)) / 100;
		mobilePay.setTotal_fee(StringUtil.dou2(fee));
		mobilePay.setSubject(subject);
		mobilePay.setShow_url(showurl);
		return bulidMobileHtml(mobilePay);
	}
	public static String bulidMobileHtml(AlipayMobilePay pay) {
		StringBuffer html = new StringBuffer();
		String sign = sign(pay);
		pay.setSign(sign);
		html.append("<form style=\"display:none\" action=\"" + AlipayConfig.WEB_URL
				+ "\" name=\"payform\" id=\"payform\" method=\"get\">");
		Map<String, Object> payMap = MapUtil.form2Map(pay, "");
		for (Map.Entry<String, Object> entry : payMap.entrySet()) {
			html.append("<input name = \"" + entry.getKey() + "\" value=\"" + entry.getValue() + "\"/>");
		}
		html.append("</form>");
		return html.toString();
	}

	/**
	 * 生成app支付返回
	 * 
	 * @param orderNum
	 *            订单号
	 * @param subject
	 *            内容
	 * @param amount金额
	 *            (fen)
	 * @return
	 */
	public static AlipayAppPay bulidApp(String orderNum, String subject, int amount) {
		AlipayAppPay appPay = new AlipayAppPay();
		appPay.setOut_trade_no(orderNum);
		double fee = ((double) amount) / 100;
		appPay.setTotal_fee(StringUtil.dou2(fee));
		appPay.setSubject(subject);
		appPay.setBody(subject);
		return bulidApp(appPay);
	}
	/**
	 * MD5 加签
	 * @param pay
	 * @return
	 */
	public static AlipayAppPay bulidApp(AlipayAppPay pay) {
		String sign = sign(pay);
		pay.setSign(sign);
		return pay;
	}

	/**
	 * 生成 支付html直接提交
	 * 
	 * @param orderNum
	 * @param subject
	 * @param amount
	 * @return
	 */
	public static String bulidHtml(String orderNum, String subject, String amount) {
		AlipayDirectPay pay = new AlipayDirectPay();
		pay.setOut_trade_no(orderNum);
		double fee = (Double.parseDouble(amount)) / 100;
		pay.setTotal_fee(StringUtil.dou2(fee));
		pay.setSubject(subject);
		return bulidHtml(pay);
	}

	public static String bulidHtml(AlipayDirectPay pay) {
		StringBuffer html = new StringBuffer();
		String sign = sign(pay);
		pay.setSign(sign);
		html.append("<form style=\"display:none\" action=\"" + AlipayConfig.WEB_URL
				+ "\" name=\"payform\" id=\"payform\" method=\"get\">");
		Map<String, Object> payMap = MapUtil.form2Map(pay, "");
		for (Map.Entry<String, Object> entry : payMap.entrySet()) {
			html.append("<input name = \"" + entry.getKey() + "\" value=\"" + entry.getValue() + "\"/>");
		}
		html.append("</form>");
		return html.toString();
	}

	public static String sign(Object pay) {
		Map<String, Object> payMap = MapUtil.form2Map(pay, "sign_type", "sign");
		String sortParam = MapUtil.sortMap(payMap);
		String sign = MD5Util.encode(sortParam + AlipayConfig.key, "UTF-8");
		return sign;
	}
	/**
	 * RSA 加签
	 * @param orderNum
	 * @param amount
	 * @return
	 * @throws Exception
	 */
	public static AlipayAppForm bulidAppForm(String orderNum,String amount ) throws Exception {
		AlipayAppForm form = new AlipayAppForm();
		double fee = (Double.parseDouble(amount)) / 100;
		form.setBiz_content(orderNum, StringUtil.dou2(fee));
		Map<String, Object> payMap = MapUtil.form2Map(form, "sign");
		String sortParam = MapUtil.sortMap(payMap);
		String sign = RSA.sign(sortParam, AlipayConfig.PIRVATE_KEY, "utf-8");
		form.setSign(URLEncoder.encode(sign,"utf-8"));
		return form;
	}
	
	public static AlipayAppForm bulidAppForm(String orderNum,String amount,String notifyurl) throws Exception{
		System.out.println("---------------------支付宝回调地址:"+notifyurl);
		AlipayAppForm form = new AlipayAppForm();
		double fee = (Double.parseDouble(amount)) / 100;
		form.setNotify_url(notifyurl);
		form.setBiz_content(orderNum, StringUtil.dou2(fee));
		Map<String, Object> payMap = MapUtil.form2Map(form, "sign");
		System.out.println(payMap);
		String sortParam = MapUtil.sortMap(payMap);
		String sign = RSA.sign(sortParam, AlipayConfig.PIRVATE_KEY, "utf-8");
		form.setSign(URLEncoder.encode(sign,"utf-8"));
		return form;
	}

}

