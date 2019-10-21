package com.ebb.pay.helper;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ebb.pay.config.WeixinConfig;
import com.ebb.pay.form.WeXinAppPay;
import com.ebb.pay.form.WeXinJSPay;
import com.ebb.pay.form.WeiXinUnifiedOrder;
import com.ebb.pay.util.HttpHelper;
import com.ebb.pay.util.MD5Util;
import com.ebb.pay.util.MapUtil;
import com.ebb.pay.util.StringUtil;
import com.ebb.pay.util.XMLparse;


/**
 * 微信支付
 * 
 * @author Administrator
 *
 */
public class WeiXinHelper {

	private static final Logger LOG = LoggerFactory.getLogger(WeiXinHelper.class);

	/**
	 * app 支付 app 支付的appid,mchid与 公众号或者是网页支付不相同
	 * 
	 * @param out_trade_no
	 * @param total_fee
	 * @param spbill_create_ip
	 * @param trade_type
	 * @param openid
	 * @param body
	 * @param product_id
	 * @return
	 * @throws Exception
	 */
	public static WeiXinUnifiedOrder createUnifiedOrder(String out_trade_no, String total_fee, String spbill_create_ip,
			String trade_type, String openid, String body, String product_id, String appid, String mchid, String key)
	throws Exception {
		StringUtil.isNull(trade_type);
		String nonce_str = StringUtil.getUUID();
		WeiXinUnifiedOrder order = new WeiXinUnifiedOrder();
		order.setAppid(appid);
		order.setMch_id(mchid);
		order.setNonce_str(nonce_str);
		order.setBody(body);
		order.setOut_trade_no(out_trade_no);
		order.setTotal_fee(total_fee);
		order.setSpbill_create_ip(spbill_create_ip);
		if (trade_type.toLowerCase().equals("js")) {
			order.setTrade_type("JSAPI");
			StringUtil.isNull(openid, "openid");
			order.setOpenid(openid);
		}
		if (trade_type.toLowerCase().equals("app")) {
			order.setTrade_type("APP");
		}
		if (trade_type.toLowerCase().equals("wap")) {
			order.setTrade_type("NATIVE");
		}
		order.setProduct_id(product_id);
		order.setSign(sign(order, key));
		return order;
	}

	/**
	 * app 支付 app 支付的appid,mchid与 公众号或者是网页支付不相同
	 * 
	 * @param out_trade_no商户订单号
	 * @param total_fee总金额
	 * @param spbill_create_ip终端Ip
	 * @param trade_type交易类型
	 * @param openid
	 * @param body商品描述
	 * @param product_id
	 * @param notify_url 支付回调地址
	 * @return
	 * @throws Exception
	 */
	public static WeiXinUnifiedOrder createUnifiedOrder(String out_trade_no, String total_fee, String spbill_create_ip,
			String trade_type, String openid, String body, String product_id, String appid, String mchid, String key,String notify_url,String attach)
	throws Exception {
		StringUtil.isNull(trade_type);
		String nonce_str = StringUtil.getUUID();
		WeiXinUnifiedOrder order = new WeiXinUnifiedOrder();
		order.setAppid(appid);
		order.setMch_id(mchid);
		order.setAttach(attach);
		order.setNonce_str(nonce_str);//随机字符串
		order.setBody(body);
		order.setOut_trade_no(out_trade_no);
		order.setTotal_fee(total_fee);
		order.setSpbill_create_ip(spbill_create_ip);
		if (trade_type.toLowerCase().equals("js")) {
			order.setTrade_type("JSAPI");
			StringUtil.isNull(openid, "openid");
			order.setOpenid(openid);
		}
		if (trade_type.toLowerCase().equals("app")) {
			order.setTrade_type("APP");
		}
		if (trade_type.toLowerCase().equals("wap")) {
			order.setTrade_type("NATIVE");
		}
		order.setNotify_url(notify_url);
		order.setProduct_id(product_id);
		order.setSign(sign(order, key));//签名
		return order;
	}

	public static String getWXPrepay(WeiXinUnifiedOrder order) throws Exception {
		System.out.println("--------------微信支付XML:"+MapUtil.map2XMLStr(MapUtil.form2Map(order)));
		String resp = HttpHelper.doPostWithBody(WeixinConfig.UNIFIEDORDER, MapUtil.map2XMLStr(MapUtil.form2Map(order)));
		LOG.info("微信支付订单号:{},返回:{}", order.getOut_trade_no(), resp);
		if (resp.equals("")) {
			throw new Exception("统一下单失败 返回为空 ");
		}
		Map<String, String> respMap = XMLparse.xml2map(resp);
		if (respMap.containsKey("prepay_id")) {
			return respMap.get("prepay_id");
		} else {
			throw new Exception("统一下单失败 获取perpay_id 失败 :" + resp);
		}
	}

	public static String getErCodeUrl(WeiXinUnifiedOrder order) throws Exception {
//		String resp = HttpHelper.doPostWithBody(WeixinConfig.UNIFIEDORDER, MapUtil.map2XMLStr(MapUtil.form2Map(order)));
//		LOG.info("微信支付订单号:{},返回:{}", order.getOut_trade_no(), resp);
//		if (resp.equals("")) {
//			throw new Exception("统一下单失败 返回为空 ");
//		}
//		Map<String, String> respMap = XMLparse.xml2map(resp);
//		if (respMap.containsKey("code_url")) {
//			return respMap.get("code_url");
//		} else {
//			throw new Exception("统一下单失败 获取code_url 失败 :" + resp);
//		}
		return "";
	}

	/**
	 * 微信支付 JS
	 * 
	 * @param out_trade_no
	 *            订单
	 * @param total_fee
	 *            总价
	 * @param spbill_create_ip
	 *            ip
	 * @param trade_type
	 *            支付类型
	 * @param openid
	 *            JS Openid
	 * @param body
	 *            支付内容
	 * @return
	 * @throws Exception
	 */
	public static WeXinJSPay bulidJSPay(String out_trade_no, String total_fee, String spbill_create_ip, String trade_type,
			String openid, String body) throws Exception {
		WeXinJSPay pay = new WeXinJSPay();
		pay.setNonceStr(StringUtil.getUUID());
		pay.setTimeStamp(System.currentTimeMillis() / 1000 + "");
		String prepay_id = getWXPrepay(createUnifiedOrder(out_trade_no, total_fee, spbill_create_ip, trade_type, openid,
				body, "", WeixinConfig.APPID, WeixinConfig.MCHID, WeixinConfig.KEY));
		pay.set_package("prepay_id=" + prepay_id);
		pay.setPaySign(sign(pay, WeixinConfig.KEY));
		return pay;
	}

	
	/**
	 * 微信支付APP
	 * 
	 * @param out_trade_no
	 *            订单
	 * @param total_fee
	 *            总价
	 * @param spbill_create_ip
	 *            ip
	 * @param trade_type
	 *            支付类型
	 * @param openid
	 *            JS Openid
	 * @param body
	 *            支付内容
	 * @return
	 * @throws Exception
	 */
	public static WeXinAppPay bulidAppPay(String out_trade_no, String total_fee, String spbill_create_ip,
			String trade_type, String body) throws Exception {
		WeXinAppPay pay = new WeXinAppPay();
		String prepayId = getWXPrepay(createUnifiedOrder(out_trade_no, total_fee, spbill_create_ip, trade_type, null,
				body, "", WeixinConfig.APP_APPID, WeixinConfig.PARTNERID, WeixinConfig.KEY));
		pay.setPrepayid(prepayId);
		pay.setTimestamp(System.currentTimeMillis() / 1000 + "");
		pay.setNoncestr(StringUtil.getUUID());
		pay.setSign(sign(pay, WeixinConfig.APP_SECRET));
		return pay;
	}
	
	/**
	 * 微信支付APP
	 * 
	 * @param out_trade_no
	 *            订单
	 * @param total_fee
	 *            总价
	 * @param spbill_create_ip
	 *            ip
	 * @param trade_type
	 *            支付类型
	 * @param openid
	 *            JS Openid
	 * @param body
	 *            支付内容
	 * @return
	 * @throws Exception
	 */
	public static WeXinAppPay bulidAppPay(String out_trade_no, String total_fee, String spbill_create_ip,
			String trade_type, String body,String notifyUrl,String attach) throws Exception {
		System.out.println("---------------------微信回调地址:"+notifyUrl);
		WeXinAppPay pay = new WeXinAppPay();
		String prepayId = getWXPrepay(createUnifiedOrder(out_trade_no, total_fee, spbill_create_ip, trade_type, null,
				body, "", WeixinConfig.APP_APPID, WeixinConfig.PARTNERID, WeixinConfig.KEY,notifyUrl,attach));
		pay.setPrepayid(prepayId);
		pay.setTimestamp(System.currentTimeMillis() / 1000 + "");
		pay.setNoncestr(StringUtil.getUUID());
		pay.setSign(sign(pay, WeixinConfig.KEY));
		return pay;
	}
	

	public static String bulidWebPay(String out_trade_no, String total_fee, String spbill_create_ip, String trade_type,
			String body, String product_id) throws Exception {
		String ercode = getErCodeUrl(createUnifiedOrder(out_trade_no, total_fee, spbill_create_ip, trade_type, null,
				body, product_id, WeixinConfig.APPID, WeixinConfig.MCHID, WeixinConfig.KEY));
		return ercode;
	}

	public static String sign(Object order, String key) {
		Map<String, Object> signMap = MapUtil.form2Map(order, "key");
		if (signMap.containsKey("_package")) {
			signMap.put("package", signMap.get("_package"));
			signMap.remove("_package");
		}
		String signStr = MapUtil.sortMap(signMap);
		signStr += "&key=" + key;
		String sign = MD5Util.encode(signStr, "utf-8").toUpperCase();
		return sign;
	}
}
