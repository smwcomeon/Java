package com.jt.sys.entity;

import java.io.Serializable;
import java.sql.Date;

/**
 * POJO对象:entity 对象 (与sys_configs表有映射关系)
 * 1)封装数据(set方法)
 * 2)数据传递
 * 思考:
 * 1)项目应用中何时会调用此对象的set方法
 * 2)项目应用中何时会调用此对象的get方法
 */
public class SysConfig implements Serializable{
	private static final long serialVersionUID = 6787816067025596323L;
	private Integer id;
	private String name;
	private String value;
	private String note;
	private Date createdTime;
	private Date modifiedTime;
	private String createdUser;
	private String modifiedUser;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public String getCreatedUser() {
		return createdUser;
	}
	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}
	public String getModifiedUser() {
		return modifiedUser;
	}
	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}
	
}
