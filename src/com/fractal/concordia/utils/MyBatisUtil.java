package com.fractal.concordia.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

public class MyBatisUtil {
	static Logger log = Logger.getLogger(MyBatisUtil.class.getName());
	private static SqlSessionFactory sqlSessionFactory;
	static {

		String resource = "sqlconfigfile.xml";
		Reader reader = null;

		try {
			reader = Resources.getResourceAsReader(resource);
			if (sqlSessionFactory == null) {
				sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, "concordiaTransactional");
			}

		} catch (FileNotFoundException fileNotFoundException) {
			log.error(fileNotFoundException.getMessage());
		} catch (IOException iOException) {
			log.error(iOException.getMessage());
		}finally{
			if(reader!= null){
				try {
					reader.close();
				} catch (IOException e) {
					log.error("Error closing reader in MyBatisUtil: ",e);
					e.printStackTrace();
				}
			}
		}

	}

	public static SqlSessionFactory getSqlSessionFactory() {

		return sqlSessionFactory;
	}

	private MyBatisUtil() {
	}
}
