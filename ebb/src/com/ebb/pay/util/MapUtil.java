package com.ebb.pay.util;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;




public class MapUtil {

	public static Map<String, Object> form2Map(Object obj, String... exc) {
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			Method[] methods = obj.getClass().getMethods();
			for (int i = 0; i < methods.length; i++) {
				Method m = methods[i];
				String name = m.getName();
				if (name.startsWith("get")) {
					String param = name.substring(3, name.length());
					param = param.substring(0, 1).toLowerCase() + param.substring(1, param.length());
					boolean flag = false;
					if(exc != null ) {
						for (String s : exc) {
							if(s.equals(param)) {
								flag = true;
								break;
							}
						}
					}
					if (param.equals("class") || flag) {
						continue;
					}
					
					Object value = m.invoke(obj);
					if (value != null && !value.equals("")) {
						System.out.println(param+"----"+value);
						res.put(param, value);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	
	public static String sortMap(Map<String, Object> map) {
		Map<String, Object> newMap = new TreeMap<String, Object>(new Comparator<String>() {
			public int compare(String arg0, String arg1) {
				return arg0.compareTo(arg1);
			}
		});
		for(String key : map.keySet()) {
			newMap.put(key,map.get(key));
		}
		Set<String> keys = newMap.keySet();
		Iterator<String> ite = keys.iterator();
		StringBuffer sb = new StringBuffer() ;
		while(ite.hasNext()) {
			String key = ite.next();
			sb.append("&");
			//微信处理package_
			Object value = newMap.get(key);
			if(key.endsWith("_")) key = key.substring(0, key.length()-1);
			sb.append(key+"="+ value);
		}
		String res = sb.toString();
		if(res.length()>0) {
			res = res.substring(1, res.length());
		}
		return res;
	}
	public static String sortMap1(Map<String, String> map) {
		Map<String, Object> newMap = new TreeMap<String, Object>(new Comparator<String>() {
			public int compare(String arg0, String arg1) {
				return arg0.compareTo(arg1);
			}
		});
		for(String key : map.keySet()) {
			newMap.put(key,map.get(key));
		}
		Set<String> keys = newMap.keySet();
		Iterator<String> ite = keys.iterator();
		StringBuffer sb = new StringBuffer() ;
		while(ite.hasNext()) {
			String key = ite.next();
			sb.append("&");
			//微信处理package_
			Object value = newMap.get(key);
			if(key.endsWith("_")) key = key.substring(0, key.length()-1);
			sb.append(key+"="+ value);
		}
		String res = sb.toString();
		if(res.length()>0) {
			res = res.substring(1, res.length());
		}
		return res;
	}
	
	public static String map2XMLStr (Map<String, Object> m) {
		String xml  ="<xml>";
		for(String key : m.keySet()) {
			xml += "<"+key+"><![CDATA["+m.get(key)+"]]></"+key+">";
		}
		xml +="</xml>";
		return xml;
	}
}
