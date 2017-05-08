package com.greenstar.entity;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import com.fasterxml.jackson.annotation.JsonProperty;
  
@NameStyle(value = Style.camelhumpAndLowercase)  
public class BaseEntity implements Serializable {  
    /**
	 * 
	 */
	private static final long serialVersionUID = -8070701524570051741L;

	@Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    @JsonProperty("id")  
    private Integer id;  
  
    /** 
     * 创建时间 
     */  
    @Column  
    private Date createTime;  
      
    /** 
     * 最后修改时间 
     */  
    @Column  
    private Date updateTime;  
    public Integer getId() {  
        return id;  
    }  
  
    public void setId(Integer id) {  
        this.id = id;  
    }  
  
    public Date getCreateTime() {  
        return createTime;  
    }  
  
    public void setCreateTime(Date createTime) {  
        this.createTime = createTime;  
    }

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}  
    
}  