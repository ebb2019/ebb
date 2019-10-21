package com.ebb.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtils {
	/**
	 * 加载当前classloader下的默认配置文件并获取属�?
	 * @param key
	 * @return
	 */
	public static String getProperty(String key){
        String val = getProperty("config.properties",key);
        return val;
	}
	
	/**
	 * 加载当前classloader下的配置文件并获取属�?
	 * @param fileName
	 * @param key
	 * @return
	 */
	public static String getProperty(String fileName,String key){
		Properties properties = new Properties();
        InputStream is = ConfigUtils.class.getClassLoader().getResourceAsStream(fileName);//加载文件内容
        try {
			properties.load(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        String val = properties.getProperty(key);
        return val;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(ConfigUtils.getProperty("uploadURL"));
	}

}
