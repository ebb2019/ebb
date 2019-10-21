package com.ebb.pay.form;

public class AlipayMobilePay extends Alipay {

	private String service = "alipay.wap.create.direct.pay.by.user";

	private String show_url;

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getShow_url() {
		return show_url;
	}

	public void setShow_url(String show_url) {
		this.show_url = show_url;
	}

}
