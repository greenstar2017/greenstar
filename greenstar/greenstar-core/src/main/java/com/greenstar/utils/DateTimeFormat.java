package com.greenstar.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;

/**
 * 提供日期或者是时间格式化处理工具类. 采用String.format格式化时间,避免使用SimpleDateFormat.format出现多线程问题.
 * 
 * <pre>
 * {@link #formatDateTime(Date)}:根据日期时间格式yyyy-MM-dd HH:mm:ss来获取指定的对象的字符串信息
 * {@link #formatShortDateTime(Date)}:根据日期时间格式yyyy-MM-dd HH:mm来获取指定的对象的字符串信息
 * {@link #formatDate(Date)}:根据日期时间格式yyyy-MM-dd来获取指定的对象的字符串信息
 * {@link #formatShortDate(Date)}:根据日期格式yyyy-MM来获取指定的对象的字符串信息
 * {@link #formatTime(Date)}:根据时间格式HH:mm:ss来获取指定的对象的字符串信息
 * {@link #formatDateTime(Date, String)}:根据指定的日期时间格式来获取指定的对象的字符串信息
 * </pre>
 * 
 * @author 周振兴
 * @since 1.0
 * @version 2013-11-26 周振兴
 */
public final class DateTimeFormat {

	/** 带年月日时分秒和毫秒的日期时间格式字符串: yyyy-MM-dd HH:mm:ss.S */
	public final static String ISO_DATETIME_FORMAT_LONG = "yyyy-MM-dd HH:mm:ss.S";

	/** 带年月日时分秒的日期时间格式字符串: yyyy-MM-dd HH:mm:ss */
	public final static String ISO_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/** 带年月日时分的日期时间格式字符串: yyyy-MM-dd HH:mm */
	public final static String ISO_DATETIME_FORMAT_SHORT = "yyyy-MM-dd HH:mm";

	/** 带年月日的日期格式字符串: yyyy-MM-dd */
	public final static String ISO_DATE_FORMAT = "yyyy-MM-dd";

	/** 带年月的日期格式字符串: yyyy-MM */
	public final static String ISO_SHORT_DATE_FORMAT = "yyyy-MM";
	

	/** 带年月的日期格式字符串: yyyy-MM */
	public final static String ISO_SHORT_MONTH_DATE_FORMAT = "MM-dd";

	/** 带时分秒的时间格式字符串: HH:mm:ss */
	public final static String ISO_TIME_FORMAT = "HH:mm:ss";
	
	/** 带时分秒的时间格式字符串: HH:mm:ss */
	public final static String ISO_TIMNE_DIVISION_FORMAT = "HH:mm";

	/** 格式化映射 */
	private final static Map<String, String> styleMap = new HashMap<String, String>();

	/**
	 * 将时间格式与String.format格式进行映射关联
	 */
	static {
		styleMap.put(ISO_DATETIME_FORMAT_LONG, "%1$tF %1$tT.%1$tL");
		styleMap.put(ISO_DATETIME_FORMAT, "%1$tF %1$tT");
		styleMap.put(ISO_DATETIME_FORMAT_SHORT, "%1$tY-%1$tm-%1$td %1$tH:%1$tM");
		styleMap.put(ISO_DATE_FORMAT, "%1$tY-%1$tm-%1$td");
		styleMap.put(ISO_SHORT_DATE_FORMAT, "%1$tY-%1$tm");
		styleMap.put(ISO_TIME_FORMAT, "%1$tT");
	}

	/**
	 * 构造函数
	 */
	private DateTimeFormat() {
	}

	/**
	 * 根据日期时间格式yyyy-MM-dd HH:mm:ss来获取指定的对象的字符串信息
	 * 
	 * @param value
	 *            需要被转换的日期时间对象引用
	 * @return 返回的是格式化后的字符串
	 */
	public static String formatDateTime(Date value) {
		return formatDateTime(value, styleMap.get(ISO_DATETIME_FORMAT));
	}

	/**
	 * 根据日期时间格式yyyy-MM-dd HH:mm来获取指定的对象的字符串信息
	 * 
	 * @param value
	 *            需要被转换的日期时间对象引用
	 * @return 返回的是格式化后的字符串
	 */
	public static String formatShortDateTime(Date value) {
		return formatDateTime(value, styleMap.get(ISO_DATETIME_FORMAT_SHORT));
	}

	/**
	 * 根据日期格式yyyy-MM-dd来获取指定的对象的字符串信息
	 * 
	 * @param value
	 *            需要被转换的日期时间对象引用
	 * @return 返回的是格式化后的字符串
	 */
	public static String formatDate(Date value) {
		return formatDateTime(value, styleMap.get(ISO_DATE_FORMAT));
	}

	/**
	 * 根据日期格式yyyy-MM来获取指定的对象的字符串信息
	 * 
	 * @param value
	 *            需要被转换的日期时间对象引用
	 * @return 返回的是格式化后的字符串
	 */
	public static String formatShortDate(Date value) {
		return formatDateTime(value, styleMap.get(ISO_SHORT_DATE_FORMAT));
	}

	/**
	 * 根据时间格式HH:mm:ss来获取指定的对象的字符串信息
	 * 
	 * @param value
	 *            需要被转换的日期时间对象引用
	 * @return 返回的是格式化后的字符串
	 */
	public static String formatTime(Date value) {
		return formatDateTime(value, styleMap.get(ISO_TIME_FORMAT));
	}

	/**
	 * 根据指定的日期时间格式来获取指定的对象的字符串信息
	 * 
	 * @param value
	 *            需要被转换的日期时间对象引用
	 * @param defaultFormat
	 *            指定的日期时间格式,为空时将使用默认的日期时间格式yyyy-MM-dd HH:mm:ss
	 * @return 返回的是格式化后的字符串
	 */
	public static String formatDateTime(Date value, String defaultFormat) {
		if (value == null) {
			return "";
		}
		String strFormatStyle = StringUtils.isEmpty(defaultFormat) ? styleMap.get(ISO_DATETIME_FORMAT)
				: styleMap.get(defaultFormat);
		if (StringUtils.isEmpty(strFormatStyle)) {
			strFormatStyle = defaultFormat;
		}
		// SimpleDateFormat objSimpleDateFormat = new
		// SimpleDateFormat(strFormatStyle);
		// return objSimpleDateFormat.format(value);
		return String.format(strFormatStyle, value);
	}

	/**
	 * 根据指定的日期时间格式来获取指定的字符串对应的时间信息
	 * 
	 * @param date
	 *            时间字符串
	 * @param defaultFormat
	 *            时间格式字符串
	 * @return 指定时间
	 */
	public static Date parseDateTime(String date, String defaultFormat) {
		if (StringUtils.isEmpty(date)) {
			return null;
		}
		String strFormatStyle = StringUtils.isEmpty(defaultFormat) ? styleMap.get(ISO_DATETIME_FORMAT_LONG)
				: defaultFormat;
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(strFormatStyle);

			return simpleDateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据指定的日期时间格式来获取指定的对象的字符串信息
	 * 
	 * @param format
	 *            指定的日期时间格式,为空时将使用默认的日期时间格式yyyy-MM-dd HH:mm:ss
	 * @return 返回的是格式化后的字符串
	 */
	public static String getCurrentFormatDateTime(String format) {
		return formatDateTime(new Date(), format);
	}

	/**
	 * 获取指定日期所在的季度。
	 * 
	 * <pre>
	 * DateTimeFormatUtils.getQuarter(java.sql.Date.valueOf(&quot;2004-01-01&quot;)=1
	 * DateTimeFormatUtils.getQuarter(java.sql.Date.valueOf(&quot;2004-05-01&quot;)=2
	 * DateTimeFormatUtils.getQuarter(java.sql.Date.valueOf(&quot;2004-09-01&quot;)=3
	 * DateTimeFormatUtils.getQuarter(java.sql.Date.valueOf(&quot;2004-12-01&quot;)=4
	 * DateTimeFormatUtils.getQuarter(null) = 0;
	 * </pre>
	 * 
	 * @param date
	 *            需要判断的时间，类型为java.util.Date.
	 * @return 返回指定日期所在的季度。
	 */
	public static int getQuarter(Date date) {
		if (date == null) {
			return 0;
		}
		Calendar objCalendar = Calendar.getInstance();
		objCalendar.setTime(date);
		int iMonth = objCalendar.get(Calendar.MONTH) + 1;
		if ((iMonth >= 1) && (iMonth <= 3)) {
			return 1;
		} else if ((iMonth >= 4) && (iMonth <= 6)) {
			return 2;
		} else if ((iMonth >= 7) && (iMonth <= 9)) {
			return 3;
		} else {
			return 4;
		}
	}

	/**
	 * 得到指定日期所在的周。
	 * 
	 * @param dateValue
	 *            需要判断的日期类型。(java.util.Date)
	 * @return 得到日期所在的周。
	 */
	public static String getWeekID(Date dateValue) {
		String strWeekID = "";
		Calendar objCalendar = Calendar.getInstance();
		objCalendar.setTime(dateValue);
		int iYear = objCalendar.get(Calendar.YEAR);
		int iWeek = objCalendar.get(Calendar.WEEK_OF_YEAR);
		if (iWeek >= 10) {
			strWeekID = iYear + String.valueOf(iWeek);
		} else {
			strWeekID = iYear + "0" + iWeek;
		}
		return strWeekID;
	}

	/**
	 * 得到所在周的第一天
	 * 
	 * @param year
	 *            需要判断的年份。
	 * @param week
	 *            需要判断的第几周。
	 * @return 得到指定年份，指定周的第一天。
	 */
	public static String firstDayOfWeek(int year, int week) {
		Calendar objCalendar = Calendar.getInstance();
		objCalendar.set(Calendar.YEAR, year);
		objCalendar.set(Calendar.WEEK_OF_YEAR, week);
		// 考虑Calendar.setFirstDayOfWeek()
		objCalendar.add(Calendar.DAY_OF_WEEK, (-1 * objCalendar.get(Calendar.DAY_OF_WEEK)) + 2);
		return formatDate(objCalendar.getTime());
	}

	/**
	 * 得到所在周的最后一天
	 * 
	 * @param year
	 *            需要判断的年份。
	 * @param week
	 *            需要判断的第几周。
	 * @return 得到指定年份，指定周的最后一天。
	 */
	public static String endDayOfWeek(int year, int week) {
		Calendar objCalendar = Calendar.getInstance();
		objCalendar.set(Calendar.YEAR, year);
		objCalendar.set(Calendar.WEEK_OF_YEAR, week);
		// 考虑Calendar.setFirstDayOfWeek()
		objCalendar.add(Calendar.DAY_OF_WEEK, (-1 * objCalendar.get(Calendar.DAY_OF_WEEK)) + 2 + 6);
		return formatDate(objCalendar.getTime());
	}

	/**
	 * 得到指定日期所在周的前一周或是下一周
	 * 
	 * @param year
	 *            需要判断的年份。
	 * @param week
	 *            需要判断的第几周。
	 * @param direction
	 *            true 获取前一周。<BR>
	 *            false 获取后一周<BR>
	 * @return 得到指定日期所在周的前一周或是下一周
	 */
	public static String rollWeek(int year, int week, boolean direction) {
		String strWeekID = "";
		Calendar objCalendar = Calendar.getInstance();
		objCalendar.set(Calendar.YEAR, year);
		objCalendar.set(Calendar.WEEK_OF_YEAR, week);
		if (direction) { //
			objCalendar.add(Calendar.DAY_OF_WEEK, 7);
		} else {
			objCalendar.add(Calendar.DAY_OF_WEEK, -7);
		}
		int iYear = objCalendar.get(Calendar.YEAR);
		int iWeek = objCalendar.get(Calendar.WEEK_OF_YEAR);
		if (iWeek < 10) {
			strWeekID = iYear + "0" + iWeek;
		} else {
			strWeekID = iYear + String.valueOf(iWeek);
		}
		return strWeekID;
	}

	/**
	 * 返回当前周除指定日期以外的日期集合.
	 * 
	 * @param year
	 *            需要判断的年份。
	 * @param week
	 *            需要判断的第几周。
	 * @param deviateDate
	 *            需要被排除的日期字符串集合
	 * @return 返回当前周除指定日期以外的日期集合。
	 */
	public static String[] otherDayOfWeek(int year, int week, String[] deviateDate) {
		List<String> objDates = new Vector<String>();
		Calendar objCalendar = Calendar.getInstance();
		objCalendar.set(Calendar.YEAR, year);
		objCalendar.set(Calendar.WEEK_OF_YEAR, week);
		for (int i = 0; i < 7; i++) {
			objCalendar.add(Calendar.DAY_OF_WEEK, (-1 * objCalendar.get(Calendar.DAY_OF_WEEK)) + 2 + i);
			objDates.add(formatDate(objCalendar.getTime()));
		}
		if (deviateDate != null) {
			for (int j = 0; j < deviateDate.length; j++) {
				if (objDates.contains(deviateDate[j])) {
					objDates.remove(deviateDate[j]);
				}
			}
		}
		String[] strRtn = new String[objDates.size()];
		for (int k = 0; k < objDates.size(); k++) {
			strRtn[k] = objDates.get(k);
		}
		return strRtn;
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 字符串的日期格式的计算
	 */
	public static int daysBetween(String smdate, String bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 时间相差天、时、分
	 */
	public static Long dateDiff(String startTime, String endTime, String format, String str) {
		// 按照传入的格式生成一个simpledateformate对象
		SimpleDateFormat sd = new SimpleDateFormat(format);
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数
		long ns = 1000;// 一秒钟的毫秒数
		long diff;
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		// 获得两个时间的毫秒时间差异
		try {
			diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
			day = diff / nd;// 计算差多少天
			hour = diff % nd / nh + day * 24;// 计算差多少小时
			min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟
			sec = diff % nd % nh % nm / ns;// 计算差多少秒
			if (str.equalsIgnoreCase("d")) {
				return day;
			} else if (str.equalsIgnoreCase("h")) {
				return hour;
			} else {
				return min;
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (str.equalsIgnoreCase("d")) {
			return day;
		} else if (str.equalsIgnoreCase("h")) {
			return hour;
		} else {
			return min;
		}
	}
	
	/**
	 * 剩余天、时、分
	 */
	public static long[] lastTime(Date startTime, Date endTime) {
		// 按照传入的格式生成一个simpledateformate对象
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数
		long ns = 1000;// 一秒钟的毫秒数
		long diff;
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		
		long[] result = new long[5];
		// 获得两个时间的毫秒时间差异
		diff = endTime.getTime() - startTime.getTime();
		day = diff / nd;// 计算差多少天
		hour = diff % nd / nh;// 计算差多少小时
		min = diff % nd % nh / nm;// 计算差多少分钟
		sec = diff % nd % nh % nm / ns;// 计算差多少秒
		
		result[0] = diff;
		result[1] = day;
		result[2] = hour;
		result[3] = min;
		result[4] = sec;
		
		return result;
	}
	
	/**
	 * 
	 * @Title: formatLastTime 
	 * @Description: 格式化剩余时间
	 * @param @param day
	 * @param @param hour
	 * @param @param min
	 * @param @param sec
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @author yuanhualiang
	 * @throws
	 */
	public static String formatLastTime(long day, long hour, long min, long sec) {
		String result = "";
		result += day + "天";
		if(hour > 0 && hour < 10) {
			result += "0" + hour + "小时";
		}else {
			result += hour + "小时";
		}
		if(min > 0 && min < 10) {
			result += "0" + min + "分";
		}else {
			result += min + "分";
		}
		if(sec > 0 && sec < 10) {
			result += "0" + sec + "秒";
		}else {
			result += sec + "秒";
		}
		
		return result;
	}
	
	public static void main(String[] args) throws InterruptedException {
		for(int i=0; i<1000; i++) {
			long[] lastTime = lastTime(new Date(), parseDateTime("2016-10-26 15:36:30", "yyyy-MM-dd HH:mm:ss"));
			System.out.println(lastTime[0]);
			System.out.println(formatLastTime(lastTime[1], lastTime[2], lastTime[3], lastTime[4]));
			Thread.sleep(1000);
		}
	}

	/**
	 * 展示时间 如果一个小时以内，则显示“5分钟前”此处的5为当前时间减去发布时间得到的分钟值 如果是当天的，且大于1个小时的，则显示“5小时前”
	 * 如果是昨天的，则显示“昨天” 如果早于昨天，则显示具体上传的日期，精确的天，如“4-21”
	 */
	public static String showTime(Date now, Date createTime) {
		String showTime = "";
		String nextStr = formatDateTime(now, "yyyy-MM-dd HH:mm");
		String beforeStr = formatDateTime(createTime, "yyyy-MM-dd HH:mm");
		long d = dateDiff(beforeStr, nextStr, "yyyy-MM-dd HH:mm", "d");
		if(d == 1) {
			showTime = "昨天";
		}else if(d > 1) {
			showTime = formatDateTime(createTime, "yyyy-MM-dd");
			showTime = showTime.substring(5, 10);
		}else {
			long h = dateDiff(beforeStr, nextStr, "yyyy-MM-dd HH:mm", "h");
			if(h >= 1) {
				showTime = h + "小时前";
			}else {
				long m = dateDiff(beforeStr, nextStr, "yyyy-MM-dd HH:mm", "m");
				if(m == 0) {
					m = 1;
				}
				showTime = m + "分钟前";
			}
		}
		return showTime;
	}
	
	   /**
     * 展示时间 如果一个小时以内，则显示“5分钟前”此处的5为当前时间减去发布时间得到的分钟值 如果是当天的，且大于1个小时的，则显示“5小时前”
     * 如果是昨天的，则显示“昨天” 如果早于昨天，则显示具体上传的日期，精确的天，如“4-21”
     */
    public static String showTimeAll(Date now, Date createTime) {
        String showTime = "";
        String nextStr = formatDateTime(now, "yyyy-MM-dd HH:mm");
        String beforeStr = formatDateTime(createTime, "yyyy-MM-dd HH:mm");
        long d = dateDiff(beforeStr, nextStr, "yyyy-MM-dd HH:mm", "d");
        if(d == 1) {
            showTime = "昨天";
        }else if(d > 1) {
            showTime = d + "天";
//            showTime = showTime.substring(5, 10);
        }else {
            long h = dateDiff(beforeStr, nextStr, "yyyy-MM-dd HH:mm", "h");
            if(h >= 1) {
                showTime = h + "小时前";
            }else {
                long m = dateDiff(beforeStr, nextStr, "yyyy-MM-dd HH:mm", "m");
                if(m == 0) {
                    m = 1;
                }
                showTime = m + "分钟前";
            }
        }
        return showTime;
    }
	
}
