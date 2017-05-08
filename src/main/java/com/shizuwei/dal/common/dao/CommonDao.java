package com.shizuwei.dal.common.dao;

public interface CommonDao<T,I> {
	
	public T get(I id);
	public T list(I id);

}
