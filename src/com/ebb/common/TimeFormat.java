package com.ebb.common;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.plugin.activerecord.Record;

public class TimeFormat {
	
	public static Map<String,Record> timeTocharact() {
		Record record = new Record();
		record.set("today", "今天");
		record.set("tomorrow", "明天");
		record.set("afterTomorrow", "后天");
		Record rd = new Record();
		rd.set("morning", "上午");
		rd.set("afternoon", "中午");
		rd.set("night", "晚上");
		
		Map<String,Record> map = new HashMap<>();
		map.put("day", record);
		map.put("night", rd);
		
		return map;
	}
	
	public static Map<String,Record> getTimeTocharact() {
		Record record = new Record();
		record.set("today", "今天");
		record.set("tomorrow", "明天");
		record.set("afterTomorrow", "后天");
		Record rd = new Record();
		rd.set("morning", "上午");
		rd.set("afternoon", "中午");
		rd.set("night", "晚上");
		
		Map<String,Record> map = new HashMap<>();
		map.put("day", record);
		map.put("night", rd);
		
		return map;
	}
}
