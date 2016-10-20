package com.hh.DaoInterface;

import java.util.List;
import java.util.Map;
import org.nutz.dao.Dao;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;

public interface HHDaoI{

	/**
	 * 通用查询方法
	 * @param sql
	 * @param param  支持IN关键字
	 */
	public List<Record> query(String sql,Map<String,Object> param);
	
	
}
