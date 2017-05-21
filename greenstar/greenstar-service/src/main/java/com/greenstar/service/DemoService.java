/**
 * 
 */
package com.greenstar.service;

import java.util.List;
import java.util.Map;

import com.greenstar.entity.Demo;


/**
 * @author yuanhualiang
 *
 */
public interface DemoService extends BaseService<Demo>{

	List<Demo> selectList(Map<String, Object> condition);
}
