package com.hh.base;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;
import javax.swing.border.AbstractBorder;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import com.alibaba.druid.pool.DruidDataSource;
import com.hh.util.SqlUtil;
import com.sun.org.apache.regexp.internal.RE;

public class HHDaoTest {

	public static void main(String[] agrs){
		HHDao dao = HHDao.getDao();
		String sqlstr = "select * from TFW_USER where ID IN ( @ID )";
		Map<String,Object> param = new HashMap<String,Object>();
		String[] s = new String[3];
		s[0] = "1";
		s[1] = "21";
		s[2] = "3";
		param.put("ID",s);
		List<Record> list = dao.query(sqlstr, param);
		System.out.println(list.size());
	}
	
}
