package com.ebb.pay.form;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ebb.common.Const;
import com.ebb.pay.config.AlipayConfig;



public class AlipayAppForm {

	private String app_id = AlipayConfig.APP_ID;

	private String method = "alipay.trade.app.pay";

	private String charset = "utf-8";

	private String sign_type = "RSA";

	private String sign;

	private String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

	private String version = "1.0";

//	private String notify_url = PathUtil.getBasePath() + "pay/ali";
	private String notify_url = Const.ROOT_PATH + "pay/ali";

	private String biz_content;

	public String getApp_id() {
		return app_id;
	}

	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getBiz_content() {
		return biz_content;
	}

	public void setBiz_content(String biz_content) {
		this.biz_content = biz_content;
	}

	public void setBiz_content(String out_trade_no, String total_amount) {
//		BusinessMetrics businessMetrics = new BusinessMetrics();
//		businessMetrics.setOut_trade_no(out_trade_no);
//		businessMetrics.setTotal_amount(total_amount);
//		String biz_content = JSONObject.toJSONString(businessMetrics);
		String biz = "{\"timeout_express\":\"30m\",\"seller_id\":\""+AlipayConfig.seller_id+"\",\"product_code\":\"QUICK_MSECURITY_PAY\",\"total_amount\":\""+total_amount+"\",\"subject\":\"极峰派\",\"body\":\"极峰派\",\"out_trade_no\":\""
				+ out_trade_no + "\"}";
		this.biz_content = biz;
	}

	/**
	 * 业务参数
	 * 
	 * @author Administrator
	 */
	class BusinessMetrics {
		private String subject = "极峰派";

		private String out_trade_no;

		private String total_amount;

		private String product_code = "QUICK_MSECURITY_PAY";

		public String getSubject() {
			return subject;
		}

		public void setSubject(String subject) {
			this.subject = subject;
		}

		public String getOut_trade_no() {
			return out_trade_no;
		}

		public void setOut_trade_no(String out_trade_no) {
			this.out_trade_no = out_trade_no;
		}

		public String getTotal_amount() {
			return total_amount;
		}

		public void setTotal_amount(String total_amount) {
			this.total_amount = total_amount;
		}

		public String getProduct_code() {
			return product_code;
		}

		public void setProduct_code(String product_code) {
			this.product_code = product_code;
		}

	}

}
