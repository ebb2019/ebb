package com.ebb.pay.form;

public class AlipayAppPay extends Alipay{

	
	private String service = "mobile.securitypay.pay";
	
	private String body;

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	
	
}
