package com.greenstar.controller.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 控制器支持类
 * 
 * @author ThinkGem
 * @version 2013-3-23
 */
public abstract class BaseController {

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/** 默认每页显示条数 */
	protected static final String PAGE_SIZE = "10";

}
