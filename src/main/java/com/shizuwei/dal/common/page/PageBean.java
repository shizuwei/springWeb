package com.shizuwei.dal.common.page;

import java.io.Serializable;
import java.util.List;

import com.github.pagehelper.Page;

import lombok.ToString;

@ToString
public class PageBean<T> implements Serializable {

	private static final long serialVersionUID = 8656597559014685635L;
	private int total; // 总记录数
	private List<T> list; // 结果集
	private int pageNum; // 第几页
	private int pageSize; // 每页记录数
	private int pages; // 总页数
	private int size; // 当前页的数量 <= pageSize，该属性来自ArrayList的size属性

	/**
	 * 包装Page对象，因为直接返回Page对象，在JSON处理以及其他情况下会被当成List来处理， 而出现一些问题。
	 * 
	 * @param list
	 *            page结果
	 * @param navigatePages
	 *            页码数量
	 */
	public PageBean(List<T> list) {
		if (list instanceof Page) {
			this.setPateInfo((Page<T>) list);
		} else {
			throw new IllegalArgumentException("list must be subtype of com.github.pagehelper.Page.");
		}
	}

	public PageBean(Page<T> page) {
		this.setPateInfo(page);
	}
	
	public PageBean() {
	}


	protected void setPateInfo(Page<T> page) {
		this.pageNum = page.getPageNum();
		this.pageSize = page.getPageSize();
		this.total = (int)page.getTotal();
		this.pages = page.getPages();
		this.list = page;
		this.size = page.size();
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
