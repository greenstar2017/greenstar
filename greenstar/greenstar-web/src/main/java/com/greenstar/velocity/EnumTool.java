package com.greenstar.velocity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.velocity.tools.config.DefaultKey;

import com.google.common.collect.Maps;

/***
 * 
 * @ClassName: EnumTool
 * @Description: 枚举类工具
 * @author yuanhualiang
 * @since JDK 1.7
 */
@DefaultKey("enum")
public class EnumTool
{
    private static Map<String, Object> MAP_ENUM = Maps.newHashMap();


    /**
     * 获取属性值
     * 
     * @param fieldName
     * @param o
     * @return
     */
    private static Object getFieldValueByName(String fieldName, Object o)
    {
        try
        {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        }
        catch(Exception e)
        {
            return null;
        }
    }

    /***
     * 
     * @Title: getEnumPropertyValue
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param: @param enumName
     * @param: @param property
     * @param: @param key
     * @param: @param showCode
     * @param: @return 输入参数
     * @return: Object 返回类型
     * @author wuhl
     * @throws
     */
    @SuppressWarnings("rawtypes")
	public static Object getEnumPropertyValue(String enumName, String property, String key, String showCode)
    {

        Object value = "";
        try
        {
            Enum[] obj = (Enum[]) MAP_ENUM.get(enumName);
            for (Enum o : obj)
            {
                Object k = PropertyUtils.getMappedProperty(o, property);
                if (k.equals(("" + k).equals(key)))
                {
                    return PropertyUtils.getMappedProperty(o, showCode);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();

        }
        return value;
    }

    /**
     * 解析ENUM属性
     * 
     * @param clazz
     * @return
     */
    @SuppressWarnings("rawtypes")
	public static List<Map<String, Object>> getEnumPropertyMap(Class clazz)
    {
        List<Map<String, Object>> lst = new ArrayList<Map<String, Object>>();
        Enum[] enums = (Enum[]) clazz.getEnumConstants();
        for (Enum em : enums)
        {
            Class clazz1 = em.getDeclaringClass();
            Map<String, Object> result = new HashMap<String, Object>();
            for (Field f : clazz1.getDeclaredFields())
            {
                String fName = f.getName();
                if (!f.isEnumConstant() && !"$VALUES".equals(fName))
                {
                    Object v = getFieldValueByName(f.getName(), em);
                    if (null != v)
                    {
                        result.put(fName, v);
                    }
                }
            }
            lst.add(result);
        }
        return lst;
    }

    /***
     * 
     * @Title: getEnumFieldValue
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param: @param keyField
     * @param: @param keyValue
     * @param: @param targetField
     * @param: @param clazz
     * @param: @return 输入参数
     * @return: Object 返回类型
     * @author wuhl
     * @throws
     */
    @SuppressWarnings("rawtypes")
	public static Object getEnumFieldValue(String keyField, String keyValue, String targetField, Class clazz)
    {
        Enum[] enums = (Enum[]) clazz.getEnumConstants();
        for (Enum em : enums)
        {
            Class clazz1 = em.getDeclaringClass();
            for (Field f : clazz1.getDeclaredFields())
            {
                String fName = f.getName();

                if (fName.equals(keyField))
                {// key名称
                    Object v = getFieldValueByName(f.getName(), em);// key的值
                    if (keyValue.equals(String.valueOf(v)))
                    {
                        return getFieldValueByName(targetField, em);
                    }
                    else
                    {
                        break;
                    }
                }

            }
        }

        return null;
    }
}
