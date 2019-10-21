package com.ebb.common;

/**
 * @author AndrewTseng
 */
public class Code {

	/**
	 * 成功
	 */
	public static final int SUCCESS = 1;

	/**
	 * 失败
	 */
	public static final int FAIL = 0;

	/**
	 * 参数错误: 一般是缺少或参数值不符合要求
	 */
	public static final int ARGUMENT_ERROR = 2;

	/**
	 * 服务器错误
	 */
	public static final int ERROR = 500;

	/**
	 * 接口不存在
	 */
	public static final int NOT_FOUND = 404;

	/**
	 * token无效
	 */
	public static final int TOKEN_INVALID = 422;

	/**
	 * 帐号已存在*
	 */
	public static final int ACCOUNT_EXISTS = 3;

	/**
	 * 验证码错误
	 */
	public static final int CODE_ERROR = 4;
	/**
	 * 签名无效
	 */
	public static final int SIGN_INVALID = 5;
	/**
	 * 时间戳超过范围
	 */
	public static final int TIMESTAMP_INVALID = 6;
	/**
	 * 包含敏感字
	 */
	public static final int SENSITIVE_WORD = 7;
	/**
	 * 服务结束
	 */
	public static final int SERVICE_END = 8;
	/**
	 * 注册
	 */
	public static final String SmsRegist = "SMS_173475478";
	
	/**
	 * 忘记密码
	 */
	public static final String SmsForget = "";
	
	/**
	 * 当前页
	 */
	public static final int CURRENTPAGE = 1;
	
	/**
	 * 显示条数
	 */
	public static final int PAGESIZE= 5;
	
	/**
	 * OSS视频存储
	 */
	public static final String VIDEODUCKET ="videoDucket";
	
	/**
	 * OSS图片存储
	 */
	public static final String IMGDUCKET ="imgDucket";
}
