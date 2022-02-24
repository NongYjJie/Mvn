package com.yr.entity;

import java.util.ArrayList;
import java.util.List;

public class SchStuPage<T>{
	//每页显示多少条数据
	private int pageSize = 3;
	//当前第几页
	private int pageNumb = 1;
	//总页数
	private int pageCount = 0;
	//总数据
	private int sum;

	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNumb() {
		return pageNumb;
	}
	public void setPageNumb(int pageNumb) {
		this.pageNumb = pageNumb;
	}

	public int getPageCount() {
		if(sum % pageSize == 0) {
			return sum / pageSize;
		}else {
			return (sum / pageSize)+1;
		}
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}

	private List<T> list = new ArrayList<T>();
}
