/**
 * 
 */
package com.greenstar.mapper;

import java.util.List;
import java.util.Map;

import com.greenstar.annotation.MapperClass;
import com.greenstar.conf.mybatis.MyBaseMapper;
import com.greenstar.entity.Demo;

/**
 * @author yuanhualiang
 *
 */
@MapperClass(value=DemoMapper.class)
public interface DemoMapper extends MyBaseMapper<Demo>{

	List<Demo> selectList(Map<String, Object> condition);
}
