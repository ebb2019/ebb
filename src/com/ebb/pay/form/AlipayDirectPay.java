package com.ebb.pay.form;

/**
 * 即时到账交易接口
 * 
 * @author Administrator
 *
 */
public class AlipayDirectPay extends Alipay {

	private String service = "create_direct_pay_by_user";

	private String return_url;

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getReturn_url() {
		return return_url;
	}

	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}
	
	
}
