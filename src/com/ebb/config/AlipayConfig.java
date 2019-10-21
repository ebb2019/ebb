package com.ebb.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.4
 *修改日期：2016-03-08
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://b.alipay.com/order/pidAndKey.htm
//	public static String partner = "2088721542247781";
	public static String partner = "2017081108144602";
	
	// 收款支付宝账号，以2088开头由16位纯数字组成的字符串，一般情况下收款账号就是签约账号
	public static String seller_id = "jonlyn@sohu.com";

	//商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
	public static String private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDAYvOx0W270pd5rF+o5SewkqBo6nvfJyRkeMnnFD24mFfxqJ/py2rVLKCPwf1AjFNaxMOXRhC/LDnJvzZUdTaR3tK0f5LGnzn5ysMoqGDNp2X/AQ1XpYKLMXxyUJaW+LgBU/Eonl80r833Nvwp2FE7ErbiST5+Zy7wQ+Tx2u1i4HHIMVVKEnq1TyHooltHy3kw/k4j/DQ6/94OLGbNpAi2FrigHn4omQQMAWHhvXbZxhBJ88e4F/fmJTqRUaTVws8WYxQ7O1CPVglpYVaIqMVRnp/hb4TqWCqrRFsupNbsH5ujS2rjzp3zWs6unrBpnG+Cy+a9NBc9QlO7W/vQt9x5AgMBAAECggEAaNuGB30mWiDF/sM8wElmAI9qbsSfD1m+NCTBpvkZSut9w3Es97ivWdEhEjmhVvUk2Ww1TtYZepkYRiW57v6T552TKl3+GdPfPY6OIDLl0DFVHk/sfcDwDf3C3mueKFsXr2GZJ5JqQcUwdXW6qvQEEjZ5B4z0SuDibIIU3t18VU8dsgFpOR1Xj8i/KL+H0N+Rxpsd6u+su7hfjdswCVxwnhtUVZFY8KDJLkfw8VMauYtqnK19r2gMVziFf3ALiB3oBqyLAd7M24mHClff3jHocBeRn5lKxymPfgGrz/UqVdrpGmgOm2ybetEWa5Y09xNCOBEnhdOrCtT/e0FLXS2igQKBgQDkLi0BgOxyby81zGwwFdz/oTsORwFRoIYf6xxvqrJhoksdsF13QvThsUtrbpBKvQphDB1l8LE0dOCt+7oux+BEMkf+8bdpfHdJn7CC/rFkSNrSntSyW/1rRz9C7RnlWgUTJSjFdAQXgCIZyRSfAlWmIkgugM1GB8QYWbo+dZZvXQKBgQDX15nVWCbjMupSYoNzuIAzdEOlpX9GDhVTcxUGpEJdq6Whz28MgG7MwpfswFHzxq/YAYU8eeamjQpSC7gQOfcRQER3g1pmvdqo8NuyacnsHTu75Mi7C6hGIXIh7xaYR1vYUnAZK7Yt3zUkm9S2xstzPHvs0bxpcZRnaacyRct7zQKBgGKncD/5LMGhPqkk9BWqLj27yNu4aWKQY80DCu1gwPUjhRSV/yMkKNo3nGMbj3kw2H7zDPbZJNSIP+gZMPsu4dC6aJRaj7Ek5jJHv7ozQiEYurtbfpyK1zt9t2VJO+wEirMnWsRcD7TVamj5nlfNW5iTs9+fQqEnlGnuyW5dyqflAoGBAK1LUx1xezdrB6tBoRzmAzhMnot+RqNuZzzq5vTAKPCgAYnay+QTseIAUo4pEyNQYJpjDiAyFzedga3B7vRINc/J866o7YoT4F1HlHy4ZwCFsBH5bBiREXfNHp+UIGX+1QTnm0z6RXiq/zX3T1+4WFybMTxAYK+gG3Cz6SN52hf5AoGBAMaDk4JzGVqP6rcBEyULQmtWQLrb/Y1h1WXC5MwwYf8Qk1cBOqf+G13ZE5KaCxmCjcb7wCPCmn5VbYu3FcrQQDIS8w8/jzM0v0u22WYDi6+aCVyLumfuhhNGF9S9hhzXc/CsTLzaP5sDO6O/YUoM29HStgtiUtHCqpApUAZ7Q8o1";
	
	// 支付宝的公钥,查看地址：https://b.alipay.com/order/pidAndKey.htm
	public static String alipay_public_key  = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwGLzsdFtu9KXeaxfqOUnsJKgaOp73yckZHjJ5xQ9uJhX8aif6ctq1Sygj8H9QIxTWsTDl0YQvyw5yb82VHU2kd7StH+Sxp85+crDKKhgzadl/wENV6WCizF8clCWlvi4AVPxKJ5fNK/N9zb8KdhROxK24kk+fmcu8EPk8drtYuBxyDFVShJ6tU8h6KJbR8t5MP5OI/w0Ov/eDixmzaQItha4oB5+KJkEDAFh4b122cYQSfPHuBf35iU6kVGk1cLPFmMUOztQj1YJaWFWiKjFUZ6f4W+E6lgqq0RbLqTW7B+bo0tq486d81rOrp6waZxvgsvmvTQXPUJTu1v70LfceQIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
//	public static String notify_url = "http://localhost:8080/eshop/alipay/notify_url.jsp";
//
//	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
//	public static String return_url = "http://localhost:8080/eshop/alipay/toPaySuccess";

	// 签名方式
	public static String sign_type = "RSA";
	
	// 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。
	public static String log_path = "C:/log";
		
	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
		
	// 支付类型 ，无需修改
	public static String payment_type = "1";
		
	// 调用的接口名，无需修改
	public static String service = "create_direct_pay_by_user";
	public static String mo_service = "alipay.wap.create.direct.pay.by.user";

//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	
//↓↓↓↓↓↓↓↓↓↓ 请在这里配置防钓鱼信息，如果没开通防钓鱼功能，为空即可 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	
	// 防钓鱼时间戳  若要使用请调用类文件submit中的query_timestamp函数
	public static String anti_phishing_key = "";
	
	// 客户端的IP地址 非局域网的外网IP地址，如：221.0.0.1
	public static String exter_invoke_ip = "";
		
//↑↑↑↑↑↑↑↑↑↑请在这里配置防钓鱼信息，如果没开通防钓鱼功能，为空即可 ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	
}

