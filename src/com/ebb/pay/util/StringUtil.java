package com.ebb.pay.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;


public class StringUtil {

	public static String getNowStr() {
		return getNowStr("yyyy-MM-dd HH:mm:ss");
	}

	public static String getNowStr(String pattern) {
		return new SimpleDateFormat(pattern).format(new Date());
	}

	public static String createOrder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String res = sdf.format(new Date());
		Random r = new Random();
		res = "a" + res;
		for (int i = 0; i < 5; i++) {
			res += r.nextInt(9);
		}
		return res;
	}

	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().toLowerCase().replace("-", "");
	}

	public static String formateDate(String ptn) {
		return new SimpleDateFormat(ptn).format(new Date());
	}

	/**
	 * 判断是否空 或 null
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object... obj) {
		for (Object o : obj) {
			if (o == null || "".equals(o.toString())) {
				return true;
			}
		}
		return false;
	}

	public static boolean isMobile(String mobile) {
		if (mobile == null)
			return false;
		return mobile.matches("^1[0-9]{10}$");
	}

	public static boolean isEmail(String email) {
		if (email == null)
			return false;
		if (email.length() > 50)
			return false;
		return email.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
	}

	public static boolean isNum(String str) {
		try {
			Double.parseDouble(str);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

//	public static List<Map<String, Object>> jsonArray2List(JSONArray array) {
//		List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
//		if (array == null) {
//			return res;
//		}
//		int length = array.size();
//		for (int i = 0; i < length; i++) {
//			JSONObject json = array.getJSONObject(i);
//			Map<String, Object> map = new HashMap<String, Object>();
//			for (String key : json.keySet()) {
//				map.put(key, json.get(key));
//			}
//			res.add(map);
//		}
//		return res;
//	}

	public static String dou2(double f) {
		DecimalFormat df = new DecimalFormat("######0.00");
		return df.format(f);
	}

	public static void isNull(Object o) throws Exception {
		isNull(o, "arg");
	}
	
	public static void isNull(Object o,String arg) throws Exception {
		if(o == null) {
			arg = arg == null ? "" : arg;
			throw new Exception(arg + " is null");
		}
	}
}
