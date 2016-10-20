package com.hh.util;

import java.net.URL;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.impl.NutDao;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.json.JsonLoader;

public class NutzDaoUtil {

	private static Dao instance = null;
	private static Ioc ioc = null;
	private static DataSource ds = null;
	
	public synchronized static Dao getDao(){
		if( instance == null ){
			ds = setDataSource();
			instance = new NutDao(ds);
		}
		return instance;
	}
	
	public synchronized static Dao getDao(DataSource ds){
		if( instance == null ){
			ds = setDataSource(ds);
			instance = new NutDao(ds);
		}
		return instance;
	}
	
	public synchronized static Dao getDao(String path){
		if( instance == null ){
			ds = setDataSource(path);
			instance = new NutDao(ds);
		}
		return instance;
	}
	
	private NutzDaoUtil(){};
	
	public static DataSource setDataSource(){
		URL U = ClassLoader.getSystemResource("nutzDao.js");
		System.out.println(U.getPath());
		if( U == null || U.getPath() == null || "".equals(U.getPath() )){
			System.out.println("缺少 nutzDao.js 文件！");
			return null;
		}
		ioc = new NutIoc(new JsonLoader(U.getPath()));
		ds = ioc.get(DataSource.class);
		return ds;
	}
	
	public static DataSource setDataSource(String path){
		ioc = new NutIoc(new JsonLoader(path));
		ds = ioc.get(DataSource.class);
		return ds;
	}
	
	public static DataSource setDataSource(DataSource datasource){
		ds = datasource;
		return ds;
	}
	
}
