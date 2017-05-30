package com.shizuwei.dal.common.page;

public class PaginationContext {
	// 第几页
	private static ThreadLocal<Integer> pageNum = new ThreadLocal<Integer>();
	// 每页记录条数
	private static ThreadLocal<Integer> pageSize = new ThreadLocal<Integer>();
	
	public static Integer getPageNum() {
		return pageNum.get();
	}

	public static void setPageNum(Integer pageNumValue) {
		pageNum.set(pageNumValue);
	}

	public static void removePageNum() {
		pageNum.remove();
	}

	public static Integer getPageSize() {
		return pageSize.get();
	}

	public static void setPageSize(Integer pageSizeValue) {
		pageSize.set(pageSizeValue);
	}

	public static void removePageSize() {
		pageSize.remove();
	}

}
