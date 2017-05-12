package com.greenstar.utils;

import java.lang.reflect.Type;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.collections.CollectionUtils;

/**
 * @author chendazhou
 * @since JDK1.6
 * 
 */
public class JSONTool extends JSON {

	/** 空的 {@code JSON} 数据 - <code>"{}"</code>。 */
	public static final String EMPTY_JSON = "{}";

	/** 空的 {@code JSON} 数组(集合)数据 - {@code "[]"}。 */
	public static final String EMPTY_JSON_ARRAY = "[]";

	/** 默认的 {@code JSON} 日期/时间字段的格式化模式。 */
	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";// "yyyy-MM-dd
																			// HH:mm:ss
																			// SSS"
																			// 精确到毫秒

	/** 默认的 {@code JSON} 是否排除有 {@literal @Expose} 注解的字段。 */
	public static final boolean EXCLUDE_FIELDS_WITHOUT_EXPOSE = false;

	/** {@code Google Gson} 的 {@literal @Since} 注解常用的版本号常量 - {@code 1.0}。 */
	public static final Double SINCE_VERSION_10 = 1.0d;

	/** {@code Google Gson} 的 {@literal @Since} 注解常用的版本号常量 - {@code 1.1}。 */
	public static final Double SINCE_VERSION_11 = 1.1d;

	/** {@code Google Gson} 的 {@literal @Since} 注解常用的版本号常量 - {@code 1.2}。 */
	public static final Double SINCE_VERSION_12 = 1.2d;

	/**
	 * 将给定的目标对象根据指定的条件参数转换成 {@code JSON} 格式的字符串。
	 * <p />
	 * <strong>该方法转换发生错误时，不会抛出任何异常。若发生错误时，普通对象返回 <code>"{}"</code>； 集合或数组对象返回
	 * <code>"[]"</code> </strong>
	 * 
	 * @param target
	 *            目标对象。
	 * @param targetType
	 *            目标对象的类型。
	 * @param isSerializeNulls
	 *            是否序列化 {@code null} 值字段。
	 * @param version
	 *            字段的版本号注解。
	 * @param datePattern
	 *            日期字段的格式化模式。
	 * @param excludesFieldsWithoutExpose
	 *            是否排除未标注 {@literal @Expose} 注解的字段。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 */
	public static String toJson(Object target, Type targetType, boolean isSerializeNulls, Double version,
			String datePattern, boolean excludesFieldsWithoutExpose) {
		if (target == null) {
			return EMPTY_JSON;
		}

		GsonBuilder builder = new GsonBuilder();
		if (isSerializeNulls) {
			builder.serializeNulls();
		}

		if (version != null) {
			builder.setVersion(version.doubleValue());
		}

		if (isEmpty(datePattern)) {
			datePattern = DEFAULT_DATE_PATTERN;
		}

		builder.setDateFormat(datePattern);
		if (excludesFieldsWithoutExpose) {
			builder.excludeFieldsWithoutExposeAnnotation();
		}

		String result = "";

		Gson gson = builder.create();

		try {
			if (targetType != null) {
				result = gson.toJson(target, targetType);
			} else {
				result = gson.toJson(target);
			}
		} catch (Exception ex) {
			if (target instanceof Collection || target instanceof Iterator || target instanceof Enumeration
					|| target.getClass().isArray()) {
				result = EMPTY_JSON_ARRAY;
			} else {
				result = EMPTY_JSON;
			}

		}

		return result;
	}

	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean}
	 * 对象。</strong>
	 * <ul>
	 * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li>
	 * </ul>
	 * 
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 */
	public static String toJson(Object target) {
		return toJson(target, null, false, null, null, EXCLUDE_FIELDS_WITHOUT_EXPOSE);
	}

	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean}
	 * 对象。</strong>
	 * <ul>
	 * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
	 * </ul>
	 * 
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @param datePattern
	 *            日期字段的格式化模式。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 */
	public static String toJson(Object target, String datePattern) {
		return toJson(target, null, false, null, datePattern, EXCLUDE_FIELDS_WITHOUT_EXPOSE);
	}

	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean}
	 * 对象。</strong>
	 * <ul>
	 * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss}</li>
	 * </ul>
	 * 
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @param version
	 *            字段的版本号注解({@literal @Since})。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 */
	public static String toJson(Object target, Double version) {
		return toJson(target, null, false, version, null, EXCLUDE_FIELDS_WITHOUT_EXPOSE);
	}

	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean}
	 * 对象。</strong>
	 * <ul>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss}</li>
	 * </ul>
	 * 
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @param excludesFieldsWithoutExpose
	 *            是否排除未标注 {@literal @Expose} 注解的字段。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 */
	public static String toJson(Object target, boolean excludesFieldsWithoutExpose) {
		return toJson(target, null, false, null, null, excludesFieldsWithoutExpose);
	}

	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean}
	 * 对象。</strong>
	 * <ul>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss}</li>
	 * </ul>
	 * 
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @param version
	 *            字段的版本号注解({@literal @Since})。
	 * @param excludesFieldsWithoutExpose
	 *            是否排除未标注 {@literal @Expose} 注解的字段。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 */
	public static String toJson(Object target, Double version, boolean excludesFieldsWithoutExpose) {
		return toJson(target, null, false, version, null, excludesFieldsWithoutExpose);
	}

	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法通常用来转换使用泛型的对象。</strong>
	 * <ul>
	 * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss}</li>
	 * </ul>
	 * 
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @param targetType
	 *            目标对象的类型。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 */
	public static String toJson(Object target, Type targetType) {
		return toJson(target, targetType, false, null, null, EXCLUDE_FIELDS_WITHOUT_EXPOSE);
	}

	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法通常用来转换使用泛型的对象。</strong>
	 * <ul>
	 * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss}</li>
	 * </ul>
	 * 
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @param targetType
	 *            目标对象的类型。
	 * @param version
	 *            字段的版本号注解({@literal @Since})。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 */
	public static String toJson(Object target, Type targetType, Double version) {
		return toJson(target, targetType, false, version, null, EXCLUDE_FIELDS_WITHOUT_EXPOSE);
	}

	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法通常用来转换使用泛型的对象。</strong>
	 * <ul>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss}</li>
	 * </ul>
	 * 
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @param targetType
	 *            目标对象的类型。
	 * @param excludesFieldsWithoutExpose
	 *            是否排除未标注 {@literal @Expose} 注解的字段。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 */
	public static String toJson(Object target, Type targetType, boolean excludesFieldsWithoutExpose) {
		return toJson(target, targetType, false, null, null, excludesFieldsWithoutExpose);
	}

	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法通常用来转换使用泛型的对象。</strong>
	 * <ul>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss}</li>
	 * </ul>
	 * 
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @param targetType
	 *            目标对象的类型。
	 * @param version
	 *            字段的版本号注解({@literal @Since})。
	 * @param excludesFieldsWithoutExpose
	 *            是否排除未标注 {@literal @Expose} 注解的字段。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 */
	public static String toJson(Object target, Type targetType, Double version, boolean excludesFieldsWithoutExpose) {
		return toJson(target, targetType, false, version, null, excludesFieldsWithoutExpose);
	}

	/**
	 * 将给定的 {@code JSON} 字符串转换成指定的类型对象。
	 * 
	 * @param <T>
	 *            要转换的目标类型。
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param token
	 *            {@code com.google.gson.reflect.TypeToken} 的类型指示类对象。
	 * @param datePattern
	 *            日期格式模式。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
	 */
	public static <T> T fromJson(String json, TypeToken<T> token, String datePattern) throws Exception {
		if (isEmpty(json)) {
			return null;
		}

		GsonBuilder builder = new GsonBuilder();
		if (isEmpty(datePattern)) {
			datePattern = DEFAULT_DATE_PATTERN;
		}
		builder.setDateFormat(datePattern);
		Gson gson = builder.create();

		return gson.fromJson(json, token.getType());
	}

	/**
	 * 将给定的 {@code JSON} 字符串转换成指定的类型对象。
	 * 
	 * @param <T>
	 *            要转换的目标类型。
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param token
	 *            {@code com.google.gson.reflect.TypeToken} 的类型指示类对象。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
	 */
	public static <T> T fromJson(String json, TypeToken<T> token) throws Exception {
		return fromJson(json, token, null);
	}

	/**
	 * 将给定的 {@code JSON} 字符串转换成指定的类型对象。<strong>此方法通常用来转换普通的 {@code JavaBean}
	 * 对象。</strong>
	 * 
	 * @param <T>
	 *            要转换的目标类型。
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param clazz
	 *            要转换的目标类。
	 * @param datePattern
	 *            日期格式模式。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
	 */
	public static <T> T fromJson(String json, Class<T> clazz, String datePattern) throws Exception {
		if (isEmpty(json)) {
			return null;
		}

		GsonBuilder builder = new GsonBuilder();
		if (isEmpty(datePattern)) {
			datePattern = DEFAULT_DATE_PATTERN;
		}
		builder.setDateFormat(datePattern);
		Gson gson = builder.create();

		return gson.fromJson(json, clazz);
	}

	/**
	 * 将给定的 {@code JSON} 字符串转换成指定的类型对象。<strong>此方法通常用来转换普通的 {@code JavaBean}
	 * 对象。</strong>
	 * 
	 * @param <T>
	 *            要转换的目标类型。
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param clazz
	 *            要转换的目标类。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
	 */
	public static <T> T fromJson(String json, Class<T> clazz) throws Exception {
		return fromJson(json, clazz, null);
	}

	/**
	 * 判断json字符串是否为空
	 * 
	 * @param json
	 * @return
	 */
	private static boolean isEmpty(String json) {
		return json == null || json.trim().length() == 0;
	}

	/**
	 * 
	 * 打印对象为json字符串. <br/>
	 * 日期: 2013-9-17 下午11:59:25 <br/>
	 * 
	 * @author wangenzi
	 * @param obj
	 * @param prettyFormat
	 *            是否格式化打印
	 * @return
	 * @since JDK 1.6
	 */
	public static String toJsonString(Object obj, boolean prettyFormat) {
		return "\n" + JSON.toJSONString(obj, prettyFormat);
	}

	/*************************************** 分割线 *****************************************************/

	/**
	 * 扩展toJSONString，只序列化数组中指定的属性(解决hibernate延时加载等问题)
	 * @param obj
	 * @param propertys
	 * @return
	 */
	public static String toJSONStringEx(Object obj, String[] propertys) {
		PropertyFilter filter = new PropertyFilterA(propertys);
		// 默认如果重用对象的话，会使用引用的方式进行引用对象。SerializerFeature.DisableCircularReferenceDetect:指明不使用引用对象
		return toJSONString(obj, filter, SerializerFeature.DisableCircularReferenceDetect);
	}

	/**
	 * 过滤JSON序列化时属性，不列化 数组中指定的属性(解决hibernate延时加载等问题)
	 * @param obj
	 * @param propertys
	 * @return
	 */
	public static String toJSONStringExOut(Object obj, String[] propertys) {
		PropertyFilter filter = new PropertyFilterB(propertys);
		// 默认如果重用对象的话，会使用引用的方式进行引用对象。SerializerFeature.DisableCircularReferenceDetect:指明不使用引用对象
		return toJSONString(obj, filter, SerializerFeature.DisableCircularReferenceDetect);
	}

	/**
	 * 将对象转换为JSONObject(实现了map,此对象的toString方法是直接序列化对象,所以用起来方便)
	 * @param obj
	 * @return
	 */
	public static JSONObject toJSONObject(Object obj) {
		// 默认如果重用对象的话，会使用引用的方式进行引用对象。SerializerFeature.DisableCircularReferenceDetect:指明不使用引用对象
		String jsonValue = toJSONString(obj);
		return parseObject(jsonValue);
	}

	/**
	 * 将对象转换为JSONObject(实现了map,此对象的toString方法是直接序列化对象,所以用起来方便),
	 *               只转换数组中指定的属性 (解决hibernate延时加载等问题)
	 * @param obj
	 * @param propertys
	 * @return
	 */
	public static JSONObject toJSONObject(Object obj, String[] propertys) {
		PropertyFilter filter = new PropertyFilterA(propertys);
		// 默认如果重用对象的话，会使用引用的方式进行引用对象。SerializerFeature.DisableCircularReferenceDetect:指明不使用引用对象
		String jsonValue = toJSONString(obj, filter, SerializerFeature.DisableCircularReferenceDetect);
		return parseObject(jsonValue);
	}

	/**
	 * 将对象转换为JSONObject(实现了map,此对象的toString方法是直接序列化对象,所以用起来方便),
	 *               不转换数组中指定的属性 (解决hibernate延时加载等问题)
	 * @param obj
	 * @param propertys
	 * @return
	 */
	public static JSONObject toJSONObjectOut(Object obj, String[] propertys) {
		PropertyFilter filter = new PropertyFilterB(propertys);
		// 默认如果重用对象的话，会使用引用的方式进行引用对象。SerializerFeature.DisableCircularReferenceDetect:指明不使用引用对象
		String jsonValue = toJSONString(obj, filter, SerializerFeature.DisableCircularReferenceDetect);
		return parseObject(jsonValue);
	}

	/**
	 * 序列化对象,并且打印到控制台,(经常用于调试)
	 * @param object
	 * @return
	 */
	public static String toStringPrint(Object object) {
		String json = JSON.toJSONString(object);
		System.out.println(json);
		return json;
	}
}

/**
 * 过滤JSON序列化时属性，只序列化 set中指定的属性
 * @author yuanhualiang
 *
 * 2016年4月25日
 */
class PropertyFilterA implements PropertyFilter {
	private Set<String> propertys = new HashSet<String>();

	public PropertyFilterA(String[] propertys) {
		CollectionUtils.addAll(this.propertys, propertys);
	}

	@Override
	public boolean apply(Object source, String name, Object value) {
		if (propertys.contains(name)) {
			return true;
		}
		return false;
	}
}

/**
 * 过滤JSON序列化时属性，不列化 set中指定的属性
 * @author yuanhualiang
 *
 * 2016年4月25日
 */
class PropertyFilterB implements PropertyFilter {
	private Set<String> propertys = null;

	public PropertyFilterB(String[] propertys) {
		Set<String> setPropertys = new HashSet<String>();
		CollectionUtils.addAll(setPropertys, propertys);
		this.propertys = setPropertys;
	}

	@Override
	public boolean apply(Object source, String name, Object value) {
		if (!propertys.contains(name)) {
			return true;
		}
		return false;
	}
}