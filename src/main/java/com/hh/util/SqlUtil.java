package com.hh.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;

public class SqlUtil {
	
	public static Set<String> getColumnDatas(Dao dao ,Sql sql,String col){
		sql.setCallback(Sqls.callback.records());
		dao.execute(sql);
		List<Record> records = sql.getList(Record.class);
		Set<String> colValueSet = new HashSet<String>();
		if (records != null && records.size() > 0) {
			for( Record record : records ){
				colValueSet.add(record.getString(col));
			}
		}
		return colValueSet;
	}
	public static String getColumnData(Dao dao ,Sql sql,String col){
		String colValue = "";
		sql.setCallback(Sqls.callback.records());
		dao.execute(sql);
		List<Record> records = sql.getList(Record.class);
		if (records != null && records.size() > 0) {
			Record rec = records.get(0);
			colValue = rec.getString(col);
		}
		return colValue;
	}
	public static List<Record> getRecords(Dao dao , Sql sql){
		sql.setCallback(Sqls.callback.records());
		dao.execute(sql);
		return sql.getList(Record.class);
	}
	public static Record getRecord(Dao dao , Sql sql){
		sql.setCallback(Sqls.callback.records());
		dao.execute(sql);
		List<Record> records = sql.getList(Record.class);
		if( records.size() == 1 ){
			return records.get(0);
		}else{
			return new Record();
		}
	}
	public static String getSqlInParam(String[] strArray,Class type){
		if( type == String.class ){
			return getSqlInStrParam(strArray);
		}else{
			return getSqlInIntParam(strArray);
		}
	}
	
	public static String getSqlInStrParam(String [] strArr ){
		String appendChar = "'";
		StringBuilder sb = new StringBuilder();
		String res = null;
		for( int i=0;i<strArr.length;i++ ){
			sb.append(","+appendChar+strArr[i]+appendChar);
		}
		if( sb.length() > 0 )
			res = sb.substring(1);
		return res;
	}
	public static String getSqlInIntParam(String [] strArr ){
		StringBuilder sb = new StringBuilder();
		String res = null;
		for( int i=0;i<strArr.length;i++ ){
			sb.append(","+strArr[i]);
		}
		if( sb.length() > 0 )
			res = sb.substring(1);
		return res;
	}
}
