/**
 * 
 */
package com.greenstar.mapper;

import java.util.List;

import com.greenstar.annotation.MapperClass;
import com.greenstar.conf.mybatis.MyBaseMapper;
import com.greenstar.dto.FlexiPageDto;
import com.greenstar.entity.Demo;

/**
 * @author yuanhualiang
 *
 */
@MapperClass(value=DemoMapper.class)
public interface DemoMapper extends MyBaseMapper<Demo>{

	List<Demo> selectMyPage(FlexiPageDto pageHelper);
}
