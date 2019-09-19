package com.jt.common.vo;

import java.io.Serializable;

/**
 * 通过此对象封装服务端控制层要向客户端响应的数据（正确或错误的数据）
 * 
 * @author Administrator
 *
 */
public class JsonResult implements Serializable{
	/**
	 * 通过构造方法传入的不同参数可以进行不同功能的实现
	 */
	private static final long serialVersionUID = 1786367477077601769L;
	/**状态码：1.表示正确 ，0表示错误*/
	private Integer state=1;
	/**状态码对应的消息描述*/
	private String message;
	/**服务端要客户端呈现的具体数据,例如可以是一个查询的结果*/
	private Object data;
	
	public JsonResult() {
	}
	public JsonResult(String message){
		this.message=message;
	}
	
	public JsonResult(Throwable e){
		this.state=0;
		this.message=e.getMessage();
	}
	public JsonResult(Object data){
		this.data=data;
	}//通过构造参数传过来的参数 ，可以封装整个的数据信息（包括页面查询数据及分页数据信息）
	
	
	/*上边是构造方法*/
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	
}

