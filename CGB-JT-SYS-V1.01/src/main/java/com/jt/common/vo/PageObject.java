package com.jt.common.vo;

import java.io.Serializable;
import java.util.List;
/**
 * 封装业务层分析信息
 * 当前页面数据
 * 页码信息
 * @author Administrator
 *
 * @param <T>
 */
public class PageObject<T> implements Serializable{
	
	private static final long serialVersionUID = 3361603153415782373L;
	private List<T> records;
	//储存当前页数
	private int rowCount;
	//总记录数
	private int pageCount;
	//页面大小
	private int pageSize=3;
	private int pageCurrent=1;
	
	
	public List<T> getRecords() {
		return records;
	}
	public void setRecords(List<T> records) {
		this.records = records;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageCurrent() {
		return pageCurrent;
	}
	public void setPageCurrent(int pageCurrent) {
		this.pageCurrent = pageCurrent;
	}
	
	
}
