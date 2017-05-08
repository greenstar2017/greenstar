package com.greenstar.entity;

import javax.persistence.Column;

import com.greenstar.annotation.MapperClass;
import com.greenstar.mapper.DemoMapper;

/**
 * @author yuanhualiang
 *
 */
@MapperClass(value=DemoMapper.class)
public class Demo extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -44537785346581879L;
	
	@Column
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
