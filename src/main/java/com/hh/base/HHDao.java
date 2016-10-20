package com.hh.base;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.nutz.dao.Chain;
import org.nutz.dao.Condition;
import org.nutz.dao.ConnCallback;
import org.nutz.dao.Dao;
import org.nutz.dao.DatabaseMeta;
import org.nutz.dao.FieldMatcher;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Entity;
import org.nutz.dao.entity.Record;
import org.nutz.dao.impl.NutDao;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import com.hh.DaoInterface.HHDaoI;
import com.hh.util.NutzDaoUtil;
import com.hh.util.SqlUtil;

public class HHDao extends NutDao implements HHDaoI{
	//单例模式
	private static HHDao INSTANCE = null;
	public synchronized static HHDao getDao(){
		if( INSTANCE == null ){
			INSTANCE = new HHDao();
		}
		return INSTANCE;
	}
	public synchronized static HHDao getDao(DataSource ds){
		if( INSTANCE == null ){
			INSTANCE = new HHDao(ds);
		}
		return INSTANCE;
	}
	public synchronized static HHDao getDao(String path){
		if( INSTANCE == null ){
			INSTANCE = new HHDao(path);
		}
		return INSTANCE;
	}
	
	private HHDao(){
		super(NutzDaoUtil.setDataSource());
	}
	private HHDao(DataSource ds){
		super(NutzDaoUtil.setDataSource(ds));
	}
	private HHDao(String path){
		super(NutzDaoUtil.setDataSource(path));
	}
	
	@Override
	public List<Record> query(String sqlStr, Map<String, Object> param) {
		Sql sql = Sqls.create(sqlStr);
		//由于nutz无法将通过set方法设置参数到in里面去，特殊处理
		if( param != null && param.size() > 0){
			for( Map.Entry<String, Object> entry : param.entrySet() ){
				String key = entry.getKey();
				Object value = entry.getValue();
				String [] valueArray  = (String[])value;
				String conditionValue = null;
				if( value instanceof String[] ){
					conditionValue = SqlUtil.getSqlInParam((String[])value,String.class);
				}else if( value instanceof int[] ){
					conditionValue = SqlUtil.getSqlInParam((String[])value,Integer.class);
				}
				sqlStr = sqlStr.replaceAll("@"+key,conditionValue);
				sql = Sqls.create(sqlStr);
			}
			//配置参数
			for( Map.Entry<String, Object> entry : param.entrySet() ){
				Object value = entry.getValue();
				String key = entry.getKey();
				if( value instanceof int[] || value instanceof String[] ){
					continue;
				}else{
					sql.params().set(key, value);
				}
			}
		}
		return SqlUtil.getRecords(this, sql);
	}
	
}
