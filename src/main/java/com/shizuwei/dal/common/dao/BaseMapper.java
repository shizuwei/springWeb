package com.shizuwei.dal.common.dao;

import java.util.Collection;
import java.util.List;

public interface BaseMapper<PK, PO> {
	PO getById(PK id);
	List<PO> getByIds(Collection<PK> ids);
	List<PO> list(PO po);
	void insert(PO po);
	void batchInsert(Collection<PO> pos);
	void delById(PK id);
	void delByIds(Collection<PK> ids);
	void update(PO po);
	//void batchUpdate(Collection<PO> pos);
}
