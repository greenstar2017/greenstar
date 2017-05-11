/******************************************************************************
 * Copyright (C) 2016 Dksj WorkRoom
 * All Rights Reserved.
 * 本软件为深圳大咖时尚设计有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
 * 复制、修改或发布本软件.
 ******************************************************************************/

package com.greenstar.velocity;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.tools.config.DefaultKey;

import com.greenstar.utils.DateTimeFormat;

/***
 * 
 * @ClassName: CommonTool
 * @Description: velocity工具类
 * @author yuanhualiang
 * @since JDK 1.7
 */
@DefaultKey("common")
public class CommonTool {

	protected static final String DEFAULT_FORUM_ID = "000000";

	public static String getVersion() {
		return String.valueOf(new Date().getTime());
	}

	/**
	 * @return DEFAULT_FORUM_ID
	 */
	public static String getDefaultForumId() {
		return DEFAULT_FORUM_ID;
	}

	/***
	 * 
	 * @Title: escape
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param: @param src
	 * @param: @return 输入参数
	 * @return: String 返回类型
	 * @author wuhl
	 * @throws
	 */
	public static String escape(String src) {
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for (i = 0; i < src.length(); i++) {
			j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j)
					|| Character.isUpperCase(j))
				tmp.append(j);
			else if (j < 256) {
				tmp.append("%");
				if (j < 16)
					tmp.append("0");
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}

	/**
	 * @param src
	 * @return
	 */
	public static String unescape(String src) {
		if (src == null)
			return null;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(
							src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else if (src.charAt(pos + 1) == ' '
						|| src.charAt(pos + 1) == ';') {
					tmp.append(src.substring(pos, pos + 1));
					lastPos = pos + 1;
				} else {
					ch = (char) Integer.parseInt(
							src.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}

	/**
	 * @param text
	 * @return
	 */
	public static String changeToHtml(String text) {
		if (StringUtils.isBlank(text)) {
			return text;
		}
		text = text.replace("&amp;", "&");
		text = text.replace("&nbsp;", " ");
		text = text.replace("&lt;", "<");
		text = text.replace("&gt;", ">");
		text = text.replace("&quot;", "\"");
		text = text.replace("&ldquo;", "\"");
		text = text.replace("&rdquo;", "\"");
		text = text.replace("&ndash;", "–");
		text = text.replace("&mdash;", "—");
		return text;
	}

	/**
	 * src是否包含sub字符串
	 * 
	 * @param src
	 * @param sub
	 * @return
	 */
	public static boolean contains(String src, String sub) {
		return src.contains(sub);
	}

	/**
	 * 替换字符串
	 * 
	 * @param str
	 * @param src
	 * @param target
	 * @return
	 */
	public static String replaceStr(String str, String src, String target) {
		str = str.replaceAll(src, target);
		return str;
	}

	/**
	 * 转换Date 到字符串 "yyyy-MM-dd"
	 * 
	 * @param date
	 * @return String
	 */
	public static String formatDate(Date date) {
		return DateTimeFormat.formatDateTime(date, "yyyy-MM-dd");
	}

	/**
	 * 转换字符串 "yyyy-MM-dd" 到 Date
	 */
	public static Date parseDate(String strDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 转换字符串 "yyyy-MM-dd HH:mm:ss" 到 Date
	 */
	public static Date parseDateWithTime(String strDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 转换Date 到字符串 "yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param date
	 * @return String
	 */
	public static String formatDateWithTime(Date date) {
		return DateTimeFormat.formatDateTime(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 截取指定长度的字符串
	 * 
	 * @param date
	 *            date
	 * @param pattern
	 *            pattern
	 * @return String
	 */
	public static String subString(String original, int length) {
		if (null == original || "".equals(original)) {
			return original;
		}
		if (original.length() <= length) {
			return original;
		}
		return original.substring(0, length);
	}

	/**
	 * 判断字符相等
	 * 
	 * @param date
	 *            date
	 * @param pattern
	 *            pattern
	 * @return String
	 */
	public static boolean equal(String original, String eqStr) {
		if (StringUtils.isBlank(original)) {
			return false;
		}
		return original.trim().equals(eqStr);
	}

	/**
	 * 删除Html标签
	 */
	public static String delHTMLTag(String htmlStr) {
		String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
		String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
		String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
		String regEx_space = "\\s*|\t|\r|\n";// 定义空格回车换行符

		Pattern p_script = Pattern.compile(regEx_script,
				Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll(""); // 过滤script标签

		Pattern p_style = Pattern
				.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(htmlStr);
		htmlStr = m_style.replaceAll(""); // 过滤style标签

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);
		htmlStr = m_html.replaceAll(""); // 过滤html标签

		Pattern p_space = Pattern
				.compile(regEx_space, Pattern.CASE_INSENSITIVE);
		Matcher m_space = p_space.matcher(htmlStr);
		htmlStr = m_space.replaceAll(""); // 过滤空格回车标签

		htmlStr = htmlStr.replaceAll("&nbsp;", "");
		htmlStr = htmlStr.substring(0, htmlStr.indexOf("。") + 1);
		return htmlStr;
	}

	/**
	 * 获取当天开始时间
	 *
	 * @return
	 */
	public static Date getCurrentDate() {
		Calendar todayStart = Calendar.getInstance();
		todayStart.set(Calendar.HOUR, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		todayStart.set(Calendar.MILLISECOND, 0);
		return todayStart.getTime();
	}

	/*
	 * 在java中调用js，jdk1.6中有加载js引擎类，然后由它来调用js方法。 并通过JDK平台给script的方法中的形参赋值
	 */
	public static String deCompress(String name) {
		ScriptEngineManager sem = new ScriptEngineManager();
		/*
		 * sem.getEngineByExtension(String extension)参数为js
		 * sem.getEngineByMimeType(String mimeType) 参数为application/javascript
		 * 或者text/javascript sem.getEngineByName(String
		 * shortName)参数为js或javascript或JavaScript
		 */
		ScriptEngine se = sem.getEngineByName("JavaScript");
		try {
			// String script = "function say(){ return 'hello,'" + name + "; }";
			StringBuffer objSB = new StringBuffer();
			objSB.append("function Decompress(strCompressedString){       ");
			objSB.append("               var strNormalString = \"\"; ");
			objSB.append("               var ht = new Array; ");
			objSB.append(" ");
			objSB.append("  for(i = 0; i < 128; i++){ ");
			objSB.append(" ht[i] = String.fromCharCode(i); ");
			objSB.append(" } ");
			objSB.append(" ");
			objSB.append(" var used = 128; ");
			objSB.append(" var intLeftOver = 0; ");
			objSB.append(" var intOutputCode = 0; ");
			objSB.append(" var ccode = 0; ");
			objSB.append("var pcode = 0; ");
			objSB.append("  var key = 0; ");

			objSB.append("  for(var i=0; i<strCompressedString.length; i++) { ");
			objSB.append("  intLeftOver += 16; ");
			objSB.append("  intOutputCode <<= 16; ");
			objSB.append("  intOutputCode |= strCompressedString.charCodeAt(i); ");
			objSB.append("  while(1) { ");
			objSB.append("    if(intLeftOver >= 12) { ");
			objSB.append("       ccode = intOutputCode >> (intLeftOver - 12); ");
			objSB.append("    if( typeof( key = ht[ccode] ) != \"undefined\" ) {  ");
			objSB.append("   strNormalString += key; ");
			objSB.append("    if(used > 128) { ");
			objSB.append("         ht[ht.length] = ht[pcode] + key.substr(0, 1); ");
			objSB.append("     } ");
			objSB.append("     pcode = ccode; ");
			objSB.append("  } else { ");
			objSB.append("      key = ht[pcode] + ht[pcode].substr(0, 1); ");
			objSB.append("     strNormalString += key; ");
			objSB.append("        ht[ht.length] = ht[pcode] + key.substr(0, 1); ");
			objSB.append("   pcode = ht.length - 1; ");
			objSB.append("   } ");
			objSB.append("  used ++; ");
			objSB.append("  intLeftOver -= 12; ");
			objSB.append("  intOutputCode &= (Math.pow(2,intLeftOver) - 1); ");
			objSB.append("  } else { ");
			objSB.append("     break; ");
			objSB.append("  } ");
			objSB.append(" } ");
			objSB.append(" } ");
			objSB.append("  return strNormalString; ");
			objSB.append(" } ");
			se.eval(objSB.toString());
			Invocable inv2 = (Invocable) se;
			String res = (String) inv2.invokeFunction("Decompress", name);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}


	/***
	 * 
	 * @Title: intToByteArray1
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param: @param i
	 * @param: @return 输入参数
	 * @return: byte[] 返回类型
	 * @author wuhl
	 * @throws
	 */
	public static byte[] intToByteArray1(int i) {
		byte[] result = new byte[4];
		result[0] = (byte) ((i >> 24) & 0xFF);
		// 必须把我们要的值弄到最低位去，有人说不移位这样做也可以， result[0] = (byte)(i & 0xFF000000);
		// ，这样虽然把第一个字节取出来了，但是若直接转换为byte类型，会超出byte的界限，出现error。再提下数//之间转换的原则（不管两种类型的字节大小是否一样，原则是不改变值，内存内容可能会变，比如int转为//float肯定会变）所以此时的int转为byte会越界，只有int的前三个字节都为0的时候转byte才不会越界。虽//然
		// result[0] = (byte)(i & 0xFF000000); 这样不行，但是我们可以这样 result[0] =
		// (byte)((i & //0xFF000000) >>24);
		result[1] = (byte) ((i >> 16) & 0xFF);
		result[2] = (byte) ((i >> 8) & 0xFF);
		result[3] = (byte) (i & 0xFF);
		return result;
	}

	public static int codePointAt(String theString, int point) {
		return toUnicode(theString, false).charAt(point);
		// return 0;
	}

	/**
	 * 
	 * @Title: toUnicode
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param: @param theString
	 * @param: @param escapeSpace
	 * @param: @return 输入参数
	 * @return: String 返回类型
	 * @author wuhl
	 * @throws
	 */
	public static String toUnicode(String theString, boolean escapeSpace) {
		int len = theString.length();
		int bufLen = len * 2;
		if (bufLen < 0) {
			bufLen = Integer.MAX_VALUE;
		}
		StringBuffer outBuffer = new StringBuffer(bufLen);

		for (int x = 0; x < len; x++) {
			char aChar = theString.charAt(x);
			// Handle common case first, selecting largest block that
			// avoids the specials below
			if ((aChar > 61) && (aChar < 127)) {
				if (aChar == '\\') {
					outBuffer.append('\\');
					outBuffer.append('\\');
					continue;
				}
				outBuffer.append(aChar);
				continue;
			}
			switch (aChar) {
			case ' ':
				if (x == 0 || escapeSpace)
					outBuffer.append('\\');
				outBuffer.append(' ');
				break;
			case '\t':
				outBuffer.append('\\');
				outBuffer.append('t');
				break;
			case '\n':
				outBuffer.append('\\');
				outBuffer.append('n');
				break;
			case '\r':
				outBuffer.append('\\');
				outBuffer.append('r');
				break;
			case '\f':
				outBuffer.append('\\');
				outBuffer.append('f');
				break;
			case '=': // Fall through
			case ':': // Fall through
			case '#': // Fall through
			case '!':
				outBuffer.append('\\');
				outBuffer.append(aChar);
				break;
			default:
				if ((aChar < 0x0020) || (aChar > 0x007e)) {
					outBuffer.append('\\');
					outBuffer.append('u');
					outBuffer.append(toHex((aChar >> 12) & 0xF));
					outBuffer.append(toHex((aChar >> 8) & 0xF));
					outBuffer.append(toHex((aChar >> 4) & 0xF));
					outBuffer.append(toHex(aChar & 0xF));
				} else {
					outBuffer.append(aChar);
				}
			}
		}
		return outBuffer.toString();
	}

	private static final char[] hexDigit = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	private static char toHex(int nibble) {
		return hexDigit[(nibble & 0xF)];
	}

	/**
	 * 转换Date 到字符串 "yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param date
	 * @return String
	 */
	public static String formatManey(BigDecimal date) {
		java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
		return df.format(date);
	}

	/**
	 * 两数相除，返回整除结果
	 * 
	 * @param date
	 * @return String
	 */
	public static long digitalDivisible(long divisor, long dividend) {
		return divisor / dividend;
	}

	/***
	 * 展示时间 如果一个小时以内，则显示“5分钟前”此处的5为当前时间减去发布时间得到的分钟值 如果是当天的，且大于1个小时的，则显示“5小时前”
	 * 
	 * @param date
	 *            需要与当前时间比较的时间值,格式必须为"yyyy-MM-dd HH:mm:ss"
	 * @return
	 */
	public static String showTime(String strDate) {
		return DateTimeFormat.showTime(new Date(),
				DateTimeFormat.parseDateTime(strDate, "yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * 求余-两数相除，返回整除返回余数
	 * 
	 * @param date
	 * @return String
	 */
	public static long remainder(long divisor, long dividend) {
		return divisor % dividend;
	}

	public static void main(String[] args) {
		System.out
				.print(Html2Text("<p style=\"margin-top: 1em; margin-bottom: 0px; padding-top: 0px; padding-bottom: 0px; color: rgb(34, 34, 34); font-family: &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;WenQuanYi Micro Hei&quot;, &quot;Helvetica Neue&quot;, Arial, sans-serif; font-size: 16px; line-height: 32px; white-space: normal;\">1月4号晚上七点多的时候，李冰冰在微博上承认了恋情：一切都是最好的安排。相差16岁的姐弟恋曝光，我们四旦双冰最后一个位大牌女星终于脱单。算是2017年的第一波狗粮不~</p><p style=\"margin-top: 1em; margin-bottom: 0px; padding-top: 0px; padding-bottom: 0px; color: rgb(34, 34, 34); font-family: &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;WenQuanYi Micro Hei&quot;, &quot;Helvetica Neue&quot;, Arial, sans-serif; font-size: 16px; line-height: 32px; white-space: normal;\"><img src=\"http://p3.pstatp.com/large/14a0000388e219a37912\" img_width=\"587\" img_height=\"307\" alt=\"愤愤不平的人们，她们只是冒犯了你们将就的人生。\" style=\"border-style: initial; max-width: 100%; display: block; margin: 1em auto;\"></p><p style=\"margin-top: 1em; margin-bottom: 0px; padding-top: 0px; padding-bottom: 0px; color: rgb(34, 34, 34); font-family: &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;WenQuanYi Micro Hei&quot;, &quot;Helvetica Neue&quot;, Arial, sans-serif; font-size: 16px; line-height: 32px; white-space: normal;\">要说冰冰姐俘获小鲜肉不是没原因的，充分验证了一句话，只要保养好，老公在高考！紧致无暇的肌肤，纤细的身材，超高的衣品装少女根本不在怕的！</p><p style=\"margin-top: 1em; margin-bottom: 0px; padding-top: 0px; padding-bottom: 0px; color: rgb(34, 34, 34); font-family: &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;WenQuanYi Micro Hei&quot;, &quot;Helvetica Neue&quot;, Arial, sans-serif; font-size: 16px; line-height: 32px; white-space: normal;\">无论是少女感十足的粉红色针织连衣裙搭配果冻凉鞋和丸子头。</p><p style=\"margin-top: 1em; margin-bottom: 0px; padding-top: 0px; padding-bottom: 0px; color: rgb(34, 34, 34); font-family: &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;WenQuanYi Micro Hei&quot;, &quot;Helvetica Neue&quot;, Arial, sans-serif; font-size: 16px; line-height: 32px; white-space: normal;\"><img src=\"http://p3.pstatp.com/large/14a2000387b3af260ec6\" img_width=\"640\" img_height=\"426\" alt=\"愤愤不平的人们，她们只是冒犯了你们将就的人生。\" style=\"border-style: initial; max-width: 100%; display: block; margin: 1em auto;\"></p><p style=\"margin-top: 1em; margin-bottom: 0px; padding-top: 0px; padding-bottom: 0px; color: rgb(34, 34, 34); font-family: &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;WenQuanYi Micro Hei&quot;, &quot;Helvetica Neue&quot;, Arial, sans-serif; font-size: 16px; line-height: 32px; white-space: normal;\"><img src=\"http://p3.pstatp.com/large/149d0000e287c56dd0cb\" img_width=\"640\" img_height=\"427\" alt=\"愤愤不平的人们，她们只是冒犯了你们将就的人生。\" style=\"border-style: initial; max-width: 100%; display: block; margin: 1em auto;\"></p><p style=\"margin-top: 1em; margin-bottom: 0px; padding-top: 0px; padding-bottom: 0px; color: rgb(34, 34, 34); font-family: &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;WenQuanYi Micro Hei&quot;, &quot;Helvetica Neue&quot;, Arial, sans-serif; font-size: 16px; line-height: 32px; white-space: normal;\">还是穿着Dior2017早春系列白色深V连衣裙的优雅女人范儿，</p><p style=\"margin-top: 1em; margin-bottom: 0px; padding-top: 0px; padding-bottom: 0px; color: rgb(34, 34, 34); font-family: &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;WenQuanYi Micro Hei&quot;, &quot;Helvetica Neue&quot;, Arial, sans-serif; font-size: 16px; line-height: 32px; white-space: normal;\"><img src=\"http://p3.pstatp.com/large/14a2000387b411ee52ce\" img_width=\"640\" img_height=\"960\" alt=\"愤愤不平的人们，她们只是冒犯了你们将就的人生。\" style=\"border-style: initial; max-width: 100%; display: block; margin: 1em auto;\"></p><p style=\"margin-top: 1em; margin-bottom: 0px; padding-top: 0px; padding-bottom: 0px; color: rgb(34, 34, 34); font-family: &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;WenQuanYi Micro Hei&quot;, &quot;Helvetica Neue&quot;, Arial, sans-serif; font-size: 16px; line-height: 32px; white-space: normal;\"><img src=\"http://p3.pstatp.com/large/142200072d3d950faf25\" img_width=\"640\" img_height=\"960\" alt=\"愤愤不平的人们，她们只是冒犯了你们将就的人生。\" style=\"border-style: initial; max-width: 100%; display: block; margin: 1em auto;\"></p><p style=\"margin-top: 1em; margin-bottom: 0px; padding-top: 0px; padding-bottom: 0px; color: rgb(34, 34, 34); font-family: &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;WenQuanYi Micro Hei&quot;, &quot;Helvetica Neue&quot;, Arial, sans-serif; font-size: 16px; line-height: 32px; white-space: normal;\">又或是在戛纳电影节上的“冰雪女王”童话造型。</p><p style=\"margin-top: 1em; margin-bottom: 0px; padding-top: 0px; padding-bottom: 0px; color: rgb(34, 34, 34); font-family: &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;WenQuanYi Micro Hei&quot;, &quot;Helvetica Neue&quot;, Arial, sans-serif; font-size: 16px; line-height: 32px; white-space: normal;\"><img src=\"http://p3.pstatp.com/large/14a30000db91e0fa42de\" img_width=\"640\" img_height=\"959\" alt=\"愤愤不平的人们，她们只是冒犯了你们将就的人生。\" style=\"border-style: initial; max-width: 100%; display: block; margin: 1em auto;\"></p><p style=\"margin-top: 1em; margin-bottom: 0px; padding-top: 0px; padding-bottom: 0px; color: rgb(34, 34, 34); font-family: &quot;PingFang SC&quot;, &quot;Hiragino Sans GB&quot;, &quot;Microsoft YaHei&quot;, &quot;WenQuanYi Micro Hei&quot;, &quot;Helvetica Neue&quot;, Arial, sans-serif; font-size: 16px; line-height: 32px; white-space: normal;\">或是穿着针织衫搭配长裙的温柔女人。</p><p><br></p>"));
	}

	/**
	 * 
	 * @Title: Html2Text
	 * @Description: HTML提取文本
	 * @param @param inputString
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @author yuanhualiang
	 * @throws
	 */
	public static String Html2Text(String inputString) {
		if(StringUtils.isBlank(inputString)) {
			return "";
		}
		String htmlStr = inputString; // 含html标签的字符串
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;

		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
																										// }
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
																									// }
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签

			textStr = htmlStr;

		} catch (Exception e) {
			System.err.println("Html2Text: " + e.getMessage());
		}

		return textStr;// 返回文本字符串
	}
}
