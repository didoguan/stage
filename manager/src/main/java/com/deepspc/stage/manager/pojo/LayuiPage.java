package com.deepspc.stage.manager.pojo;

import java.util.List;

/**
 * @author gzw
 * @date 2020/12/8
 **/
public class LayuiPage {

	private Integer code = 0;

	private String msg = "请求成功";

	private List data;

	private long count;

	public LayuiPage() {

	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}
}
