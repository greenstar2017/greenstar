package com.greenstar.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.greenstar.entity.Resources;

/**
 * @author yuanhualiang
 *
 */
public interface ResourcesService extends BaseService<Resources> {
	PageInfo<Resources> selectByPage(Resources resources, int start, int length);

	public List<Resources> queryAll();

	public List<Resources> loadUserResources(Map<String, Object> map);

	public List<Resources> queryResourcesListWithSelected(Integer rid);
}
