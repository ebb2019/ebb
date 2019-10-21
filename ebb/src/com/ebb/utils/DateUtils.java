package com.ebb.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.hutool.core.date.DateUtil;


/**
 * 时间工具
 * 
 * @author cnmobi_db
 */
public final class DateUtils {
	/**
	 * 获得当前时间 格式为：yyyy-MM-dd HH:mm:ss
	 */
	public static String getNowTime() {
		Date nowday = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 精确到秒
		String time = sdf.format(nowday);
		return time;
	}

	/**
	 * 获得当前时间 格式为：yyyy-MM-dd HH:mm:ss
	 */
	public static String getNowTimeNo() {
		Date nowday = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");// 精确到秒
		String time = sdf.format(nowday);
		return time;
	}

	/**
	 * 获取当前系统时间戳
	 * 
	 * @return
	 */
	public static Long getNowTimeStamp() {
		return System.currentTimeMillis();
	}

	public static Long getNowDateTime() {
		return new Date().getTime() / 1000;
	}

	/**
	 * 自定义日期格式
	 * 
	 * @param format
	 * @return
	 */
	public static String getNowTime(String format) {
		Date nowday = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format);// 精确到秒
		String time = sdf.format(nowday);
		return time;
	}

	/**
	 * 将时间字符转成Unix时间戳
	 * 
	 * @param timeStr
	 * @return
	 * @throws java.text.ParseException
	 */
	public static Long getTime(String timeStr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 精确到秒
		Date date = sdf.parse(timeStr);
		return date.getTime() / 1000;
	}

	/**
	 * 将时间字符转成Unix时间戳,精确到毫秒
	 * 
	 * @param timeStr
	 * @return
	 * @throws java.text.ParseException
	 */
	public static Long getTimeM(String timeStr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 精确到毫秒
		Date date = null;
		date = sdf.parse(timeStr);
		return date.getTime();
	}

	/**
	 * 将Unix时间戳转成时间字符
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String getTime(long timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 精确到秒
		Date date = new Date(timestamp);
		return sdf.format(date);
	}

	/**
	 * 获取半年后的时间 时间字符格式为：yyyy-MM-dd HH:mm:ss
	 * 
	 * @return 时间字符串
	 */
	public static String getHalfYearLaterTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 精确到秒

		Calendar calendar = Calendar.getInstance();
		int currMonth = calendar.get(Calendar.MONTH) + 1;

		if (currMonth >= 1 && currMonth <= 6) {
			calendar.add(Calendar.MONTH, 6);
		} else {
			calendar.add(Calendar.YEAR, 1);
			calendar.set(Calendar.MONTH, currMonth - 6 - 1);
		}

		return sdf.format(calendar.getTime());
	}

	// 获得当天0点的的时间字符串
	public static String getTimesMorningDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 精确到秒
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return sdf.format(cal.getTime());
	}

	// 获得当天0点时间戳
	public static long getTimesMorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return (cal.getTimeInMillis());

	}

	/**
	 * 获得当前时间30分钟之前的的时间字符串
	 * 
	 * @return
	 */
	public static String getTimesBefore30MinDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 精确到秒
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -30);
		return sdf.format(cal.getTime());
	}

	/**
	 * 获得当前时间30分钟之前的时间戳
	 * 
	 * @return
	 */
	public static long getTimesBefore30Min() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -30);
		return (cal.getTimeInMillis());
	}

	/**
	 * 获得当前时间30秒之前的时间字符串
	 * 
	 * @return
	 */
	public static String getTimesBefore30SDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 精确到秒
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, -30);
		return sdf.format(cal.getTime());
	}
	
	
	
	/**
	 * 获得当前时间60秒之前的时间字符串
	 * 
	 * @return
	 */
	public static String getTimesBefore60SDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 精确到秒
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, -60);
		return sdf.format(cal.getTime());
	}
	

	/**
	 * 获得当前时间30秒之前的时间戳
	 * 
	 * @return
	 */
	public static long getTimesBefore30S() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, -30);
		return (cal.getTimeInMillis());
	}

	/**
	 * 获得指定天数的0点时间戳
	 * 
	 * @param day
	 * @return
	 */
	public static long getTimesMorningAfterDay(int day) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.DAY_OF_MONTH, day);
		return (cal.getTimeInMillis());
	}

	/**
	 * 一天包含多少秒
	 */
	public static final long oneDay = 24 * 3600;

	/**
	 * 比较输入时间和当前时间是否大于一天
	 * 
	 * @param timeStr
	 *            当前时间字符串
	 * @return
	 */
	public static boolean timeSpanThanOneDay(String timeStr) {
		try {
			Long time = getTime(timeStr);
			Long nowTime = getNowDateTime();
			if ((nowTime - time) > oneDay) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}

		return false;
	}

	/**
	 * 是否是昨天以前
	 * 
	 * @param timeStr
	 * @return
	 */
	public static boolean isBeforeYesterday(String timeStr) {
		long yesterdayL = getTimesMorningAfterDay(-1);
		try {
			Long time = getTimeM(timeStr);
			if (time < yesterdayL) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}

		return false;
	}

	/**
	 * 是否是昨天
	 * 
	 * @param timeStr
	 * @return
	 */
	public static boolean isYesterday(String timeStr) {
		long yesterdayL = getTimesMorningAfterDay(-1);
		long nowL = getTimesMorningAfterDay(0);
		try {
			Long time = getTimeM(timeStr);
			if (yesterdayL <= time && time < nowL) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}

		return false;
	}

	/**
	 * 是否是今天
	 */
	public static boolean isToday(String timeStr) {
		long nowL = getTimesMorningAfterDay(0);
		long tomorrowL = getTimesMorningAfterDay(1);
		try {
			Long time = getTimeM(timeStr);
			if (nowL <= time && time < tomorrowL) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 将时间字符转成Unix时间戳
	 * 
	 * @param timeStr
	 * @return
	 * @throws java.text.ParseException
	 */
	public static Long getTimeS(String timeStr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");// 精确到秒
		Date date = sdf.parse(timeStr);
		return date.getTime() / 1000;
	}

	/**
	 * 校验时间格式（仅格式）
	 */
	public static boolean checkHHMM(String time) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
		try {
			@SuppressWarnings("unused")
			Date t = dateFormat.parse(time);
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	/**
	 * 校验时间格式HH:MM（精确）
	 */
	public static boolean checkTime(String time) {
		if (checkHHMM(time)) {
			String[] temp = time.split(":");
			if ((temp[0].length() == 2 || temp[0].length() == 1) && temp[1].length() == 2) {
				int h, m;
				try {
					h = Integer.parseInt(temp[0]);
					m = Integer.parseInt(temp[1]);
				} catch (NumberFormatException e) {
					return false;
				}
				if (h >= 0 && h <= 24 && m <= 60 && m >= 0) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 格式化贴吧与小知识时间格式
	 * 
	 * @throws ParseException
	 */
	public static String topicTimeFomat(Date topicTime) throws ParseException {
		String topicTimeStr = "";
		// Date nowTime = new Date();
		Calendar startToday = Calendar.getInstance();
		startToday.set(Calendar.HOUR_OF_DAY, 00);
		startToday.set(Calendar.MINUTE, 0);
		startToday.set(Calendar.SECOND, 0);

		Calendar endToday = Calendar.getInstance();
		endToday.set(Calendar.HOUR_OF_DAY, 23);
		endToday.set(Calendar.MINUTE, 59);
		endToday.set(Calendar.SECOND, 59);

		Calendar startYes = Calendar.getInstance();
		startYes.set(Calendar.HOUR_OF_DAY, 00);
		startYes.set(Calendar.MINUTE, 0);
		startYes.set(Calendar.SECOND, 0);

		Calendar endYes = Calendar.getInstance();
		endYes.add(Calendar.DATE, -1);
		endYes.set(Calendar.HOUR_OF_DAY, 23);
		endYes.set(Calendar.MINUTE, 59);
		endYes.set(Calendar.SECOND, 59);

		Calendar endBefore = Calendar.getInstance();
		endYes.add(Calendar.DATE, -2);
		endYes.set(Calendar.HOUR_OF_DAY, 23);
		endYes.set(Calendar.MINUTE, 59);
		endYes.set(Calendar.SECOND, 59);

		Calendar startYear = Calendar.getInstance();
		startYear.set(Calendar.DATE, 1);
		startYear.set(Calendar.MONTH, 1);
		startYear.set(Calendar.HOUR_OF_DAY, 00);
		startYear.set(Calendar.MINUTE, 0);
		startYear.set(Calendar.SECOND, 0);

		SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数
		// long ns = 1000;// 一秒钟的毫秒数
		//
		// System.out.println("start:" + sm.format(startToday.getTime()));
		// System.out.println("end:" + sm.format(endToday.getTime()));

		long diffDay = 0;
		long diffNow = System.currentTimeMillis() - topicTime.getTime();// 当前时刻与发帖时间之差
		diffDay = diffNow / nd;// 计算差多少天
		long diffHour = diffNow % nd / nh + diffDay * 24;// 计算差多少小时
		long diffMin = diffNow % nd % nh / nm + diffDay * 24 * 60;// 计算差多少分钟
		// long diffSec = diffNow % nd % nh % nm / ns;// 计算差多少秒

		long diffTodayEnd = endToday.getTime().getTime() - topicTime.getTime();// 今天23:59:59与发帖时间之差
		long diffTodayEndNum = diffTodayEnd / nd;// 今天23:59:59与发帖时间之差多少天
		long diffYesStart = startYes.getTime().getTime() - topicTime.getTime();// 昨天00:00:00与发帖时间之差
		long diffYesEnd = endYes.getTime().getTime() - topicTime.getTime();// 昨天23:59:59与发帖时间之差
		long diffBeforeEnd = endBefore.getTime().getTime() - topicTime.getTime();// 前天23:59:59与发帖时间之差
		long diffYearStart = startYear.getTime().getTime() - topicTime.getTime();// 当年1月1日
																					// 00:00:00与发帖时间之差

		if (diffDay == 0 && diffHour == 0 && diffMin < 60) {
			// 如果是一个小时之内
			if (diffMin == 0) {
				// 如果是60秒以内
				topicTimeStr = "刚刚";
			} else {
				topicTimeStr = String.valueOf(diffMin) + "分钟前";
			}

		} else if (diffDay == 0 && diffHour > 0 && diffTodayEndNum == 0) {
			// 如果今天23:59:59之前
			topicTimeStr = String.valueOf(diffHour) + "小时前";

		} else if (diffYesStart <= 0 && diffYesEnd > 0) {
			// 如果是介于昨天与前天之间
			SimpleDateFormat dfMh = new SimpleDateFormat("HH:mm");
			topicTimeStr = "昨天" + dfMh.format(topicTime);

		} else if (diffYearStart <= 0 && diffBeforeEnd > 0) {
			// 今年1月1日00:00:00 与前天之间
			SimpleDateFormat dfMdhm = new SimpleDateFormat("MM-dd HH:mm");
			topicTimeStr = dfMdhm.format(topicTime);

		} else if (diffYearStart > 0) {
			// 5000年前至去年12月31日24：00分
			SimpleDateFormat dfYMdhm = new SimpleDateFormat("yy-MM-dd HH:mm");
			topicTimeStr = dfYMdhm.format(topicTime);
		}

		return topicTimeStr;
	}

	/**
	 * 得到某年某周的第一天
	 *
	 * @param year
	 * @param week
	 * @return
	 */
	public static Date getFirstDayOfWeek(int year, int week) {
		week = week - 1;
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, Calendar.JANUARY);
		calendar.set(Calendar.DATE, 1);

		Calendar cal = (Calendar) calendar.clone();
		cal.add(Calendar.DATE, week * 7);

		return getFirstDayOfWeek(cal.getTime());
	}

	/**
	 * 得到某年某周的最后一天
	 *
	 * @param year
	 * @param week
	 * @return
	 */
	public static Date getLastDayOfWeek(int year, int week) {
		week = week - 1;
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, Calendar.JANUARY);
		calendar.set(Calendar.DATE, 1);
		Calendar cal = (Calendar) calendar.clone();
		cal.add(Calendar.DATE, week * 7);

		return getLastDayOfWeek(cal.getTime());
	}

	/**
	 * 取得当前日期所在周的第一天
	 *
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek()); // Sunday
		return calendar.getTime();
	}

	/**
	 * 取得当前日期所在周的最后一天
	 *
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 6); // Saturday
		return calendar.getTime();
	}

	/**
	 * 取得当前日期所在周的前一周最后一天
	 *
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfLastWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return getLastDayOfWeek(calendar.get(Calendar.YEAR), calendar.get(Calendar.WEEK_OF_YEAR) - 1);
	}

	/**
	 * 得到某年某月的第一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getFirstDayOfMonth(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));

		return c.getTime();
	}

	/**
	 * 得到某年某月的最后一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getLastDayOfMonth(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));

		return c.getTime();
	}

	/**
	 * 得到某年某季度第一天
	 * 
	 * @param year
	 * @param quarter
	 * @return
	 */
	public static Date getFirstDayOfQuarter(int year, int quarter) {
		int month = 0;
		if (quarter > 4) {
			return null;
		} else {
			month = (quarter - 1) * 3 + 1;
		}
		return getFirstDayOfMonth(year, month);
	}

	/**
	 * 得到某年某季度最后一天
	 * 
	 * @param year
	 * @param quarter
	 * @return
	 */
	public static Date getLastDayOfQuarter(int year, int quarter) {
		int month = 0;
		if (quarter > 4) {
			return null;
		} else {
			month = quarter * 3;
		}
		return getLastDayOfMonth(year, month);
	}

	/**
	 * 得到某年第一天
	 * 
	 * @param year
	 * @return
	 */
	public static Date getFirstDayOfYear(int year) {
		return getFirstDayOfQuarter(year, 1);
	}

	/**
	 * 得到某年最后一天
	 * 
	 * @param year
	 * @return
	 */
	public static Date getLastDayOfYear(int year) {
		return getLastDayOfQuarter(year, 4);
	}

	/**
	 * 一小时包含多少毫秒
	 */
	public static final long oneHour = 3600000;
	/**
	 * 一分钟包含多少毫秒
	 */
	public static final long oneMin = 60000;

	/**
	 * 得到指定时间的有效截至时间和当前时间相隔多少分钟
	 * 
	 * @param date
	 *            时间
	 * @param hour
	 *            小时
	 * @return
	 */
	public static long getLeftTime(Date date, int hour) {
		long endTime = date.getTime() + hour * oneHour;
		return (endTime - System.currentTimeMillis()) / oneMin;
	}

	/**
	 * 得到指定时间和当前时间相隔多少分钟
	 * 
	 * @param date
	 *            时间
	 * @return
	 */
	public static long getIntervalTime(Date date) {
		if(date == null)
			return 0;
		return (System.currentTimeMillis() - date.getTime()) / oneMin;
	}

	/**
	 * 得到指定时间相隔多少分钟
	 * 
	 * @param date
	 *            时间
	 * @return
	 */
	public static long getIntervalTime(Date date1, Date date2) {
		return (date2.getTime() - date1.getTime()) / oneMin;
	}

	/**
	 * 得到指定时间相隔多少分钟
	 * 
	 * @param date
	 *            时间
	 * @return
	 */
	public static long getIntervalTime(Date date1, Object date2) {
		if (date2 == null)
			return getIntervalTime(date1);
		else
			return getIntervalTime(date1, (Date) date2);
	}

	/**
	 * 得到指定时间m小时之后的时间
	 * 
	 * @param date
	 * @param m
	 * @return
	 */
	public static Date getAfterHourDate(Date date, int m) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR_OF_DAY, m);
		return c.getTime();
	}

	/**
	 * 得到当前时间m小时之后的时间
	 * 
	 * @param date
	 * @param m
	 * @return
	 */
	public static Date getAfterHourDate(int m) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR_OF_DAY, m);
		return c.getTime();
	}

	/**
	 * 得到当前时间m分钟之后的时间
	 * 
	 * @param date
	 * @param m
	 * @return
	 */
	public static Date getAfterMinDate(int m) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, m);
		return c.getTime();
	}

	/**
	 * 获得当前时间 格式为：HH:mm
	 */
	public static String getNowHourAndMinTime() {
		Date nowday = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String time = sdf.format(nowday);
		return time;
	}

	/**
	 * 工作时间段[09:00-22:30)
	 */
	public static final String workTime = "09:00-22:30";

	/**
	 * 是否在工作时间段[09:00-22:30)
	 * 
	 * @param date
	 * @param m
	 * @return
	 */
	public static boolean isWorkTime() {
		return isInTime(workTime, getNowHourAndMinTime());
	}

	/**
	 * 判断某一时间是否在一个区间内
	 * 
	 * @param sourceTime
	 *            时间区间,半闭合,如[09:00-22:30)
	 * @param curTime
	 *            需要判断的时间 如10:00
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static boolean isInTime(String sourceTime, String curTime) {
		if (sourceTime == null || !sourceTime.contains("-") || !sourceTime.contains(":")) {
			throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
		}
		if (curTime == null || !curTime.contains(":")) {
			throw new IllegalArgumentException("Illegal Argument arg:" + curTime);
		}
		String[] args = sourceTime.split("-");
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		try {
			long now = sdf.parse(curTime).getTime();
			long start = sdf.parse(args[0]).getTime();
			long end = sdf.parse(args[1]).getTime();
			if (args[1].equals("00:00")) {
				args[1] = "24:00";
			}
			if (end < start) {
				if (now >= end && now < start) {
					return false;
				} else {
					return true;
				}
			} else {
				if (now >= start && now < end) {
					return true;
				} else {
					return false;
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
		}

	}

	/**
	 * 获得当前时间 格式为：yyyy-MM-dd HH:mm:ss
	 */
	public static String getTime(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 精确到秒
		String time = sdf.format(date);
		return time;
	}
	
	/**
	 * 获得当前时间 格式为：yyyy-MM-dd HH:mm:ss
	 */
	public static String getTimeToMin(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 精确到秒
		String time = sdf.format(date);
		return time;
	}
	
	/**
	 * 获得当前时间 格式为：yyyy-MM-dd
	 */
	public static String getTimeToDay(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 精确到秒
		String time = sdf.format(date);
		return time;
	}

	/**
	 * 获得当前时间 格式为：yyyy-MM-dd
	 */
	public static String getNowDayTime() {
		Date nowday = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 精确到秒
		String time = sdf.format(nowday);
		return time;
	}

	/**
	 * 是否是今天
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isToday(Date date) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date);
		int year1 = c1.get(Calendar.YEAR);
		int month1 = c1.get(Calendar.MONTH) + 1;
		int day1 = c1.get(Calendar.DAY_OF_MONTH);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(new Date());
		int year2 = c2.get(Calendar.YEAR);
		int month2 = c2.get(Calendar.MONTH) + 1;
		int day2 = c2.get(Calendar.DAY_OF_MONTH);
		if (year1 == year2 && month1 == month2 && day1 == day2) {
			return true;
		}
		return false;
	}

	/**
	 * 得到指定时间的间隔分钟数
	 * 
	 * @param time
	 *            开始时间，形如：09:30
	 * @param interval
	 *            间隔分钟数
	 * @return
	 */
	public static String getMinDate(String time, int interval) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");// 精确到秒
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.split(":")[0]));
		cal.set(Calendar.MINUTE, Integer.parseInt(time.split(":")[1]));
		cal.add(Calendar.MINUTE, interval);
		return sdf.format(cal.getTime());
	}

	/**
	 * 是否预约时间段已过
	 * 
	 * @param appointDate
	 *            预约日期
	 * @param beginTime
	 *            开始时间（24小时制）
	 * @param appointMinute
	 *            预约时长（单位：分钟）
	 * @return
	 */
	public static boolean isAppointTimePass(Date appointDate, String beginTime, int appointMinute) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(appointDate);
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(beginTime.split(":")[0]));
		cal.set(Calendar.MINUTE, Integer.parseInt(beginTime.split(":")[1]));
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.MINUTE, appointMinute);
		return System.currentTimeMillis() > cal.getTimeInMillis();
	}
	
	/**
	 * 获取预约开始时间
	 * 
	 * @param appointDate
	 *            预约日期
	 * @param beginTime
	 *            开始时间（24小时制）
	 * @param appointMinute
	 *            预约时长（单位：分钟）
	 * @return
	 */
	public static Date getAppointStarDate(Date appointDate, String beginTime, int appointMinute) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(appointDate);
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(beginTime.split(":")[0]));
		cal.set(Calendar.MINUTE, Integer.parseInt(beginTime.split(":")[1]));
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	/**
	 * 获取预约结束时间
	 * 
	 * @param appointDate
	 *            预约日期
	 * @param beginTime
	 *            开始时间（24小时制）
	 * @param appointMinute
	 *            预约时长（单位：分钟）
	 * @return
	 */
	public static Date getAppointEndDate(Date appointDate, String beginTime, int appointMinute) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(appointDate);
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(beginTime.split(":")[0]));
		cal.set(Calendar.MINUTE, Integer.parseInt(beginTime.split(":")[1]));
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.MINUTE, appointMinute);
		return cal.getTime();
	}

	
	

	
	 /**  
	 * 计算两个日期之间相差的天数  
	 * @param smdate 较小的时间 
	 * @param bdate  较大的时间 
	 * @return 相差天数 
	 * @throws ParseException  
	 */    
	 public static int daysBetween(Date smdate,Date bdate) throws ParseException {  
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
	    smdate=sdf.parse(sdf.format(smdate));  
	    bdate=sdf.parse(sdf.format(bdate));  
	    Calendar cal = Calendar.getInstance();    
	    cal.setTime(smdate);    
	    long time1 = cal.getTimeInMillis();                 
	    cal.setTime(bdate);    
	    long time2 = cal.getTimeInMillis();         
	    long between_days=(time2-time1)/(1000*3600*24);  
	        
	   return Integer.parseInt(String.valueOf(between_days));           
	}    
      
	/** 
	*字符串的日期格式的计算 
	*/  
    public static int daysBetween(String smdate,String bdate) throws ParseException{  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(sdf.parse(smdate));    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(sdf.parse(bdate));    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));     
    }  
	  
    
    
    /**
     * 比较两个日期大小
     * @param DATE1
     * @param DATE2
     * @return
     */
    public static int compareDate(String DATE1, String DATE2) {
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	try {
    		Date dt1 = df.parse(DATE1);
	        Date dt2 = df.parse(DATE2);
			if (dt2.getTime() > dt1.getTime()) {
				//System.out.println("dt1 在dt2前");
			    return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				//System.out.println("dt1在dt2后");
	            return -1;
	        } else {
	        	 //System.out.println("dt1和dt2时间相等");
	            return 0;
	        }
	    } catch (Exception exception) {
	        exception.printStackTrace();
	    }
	    return 0;
	}
    
    /**
     * 时间戳转时间格式str
     * @param stamp
     * @return
     */
    public static String stampToStr(Long stamp, String format){
    	Calendar c = DateUtil.calendar(stamp);
    	String beginDate = DateUtil.format(c.getTime(), "yyyy-MM-dd 00:00:00");
    	return beginDate;
    }
    
	 

	public static void main(String[] args) {
		// System.out.println(isAppointTime(new Date(), "14:20", 30, 0));

//		System.out.println(isAppointTimePass(new Date(), "14:16", 20));
		System.out.println(getAppointEndDate(new Date(), "14:16", 20).toLocaleString());
	}

}
