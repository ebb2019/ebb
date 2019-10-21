package com.ebb.pay.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


public class XMLparse {
	public static Map<String, String> xml2map(String xml) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		Document document = DocumentHelper.parseText(xml);
		Element es = document.getRootElement();
		getSonElement(map, es);
		return map;
	}
	public static void getSonElement(Map<String, String> map, Element ele) {
		@SuppressWarnings("rawtypes")
		List sons = ele.elements();
		if (0 != sons.size()) {
			for (Iterator<?> i = ele.elementIterator(); i.hasNext();) {
				Element son = (Element) i.next();
				map.put(son.getName(), son.getText().trim());
				getSonElement(map, son);
			}
		}
	}

}
