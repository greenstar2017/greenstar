/**
 * 
 */
package com.greenstar.service;

import com.github.pagehelper.PageInfo;
import com.greenstar.dto.FlexiPageDto;
import com.greenstar.entity.Demo;


/**
 * @author yuanhualiang
 *
 */
public interface DemoService extends BaseService<Demo>{

	PageInfo<Demo> selectMyPage(FlexiPageDto pageHelper);
}
