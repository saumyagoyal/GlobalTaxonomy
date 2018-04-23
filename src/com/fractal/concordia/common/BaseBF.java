package com.fractal.concordia.common;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import com.fractal.concordia.utils.MyBatisUtil;

public class BaseBF {

	private static Logger logger = Logger.getLogger(BaseBF.class);

	public Object fetchList(String query) {

		SqlSessionFactory sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
		SqlSession session = sqlSessionFactory.openSession();
		List<Object> list = null;
		try {
			list = new BaseDao().fetchList(session, query);

		} catch (Exception e) {

			logger.error("Error in fetching list for query : " + query, e);
		} finally {
			session.close();
		}
		return list;

	}

	public Object fetchList(String query, Object object) {

		SqlSessionFactory sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
		SqlSession session = sqlSessionFactory.openSession();
		List<Object> list = null;
		try {
			list = new BaseDao().fetchList(session, query, object);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in fetching list for query : " + query, e);
		} finally {
			session.close();
		}
		return list;

	}

	public Object fetchObject(String query) {

		SqlSessionFactory sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
		SqlSession session = sqlSessionFactory.openSession();
		Object obj = null;
		try {
			obj = new BaseDao().fetchObject(session, query);
		} catch (Exception e) {
			logger.error("Error in fetching object for query : " + query, e);
		} finally {
			session.close();
		}
		return obj;

	}

	public Object fetchObject(String query, Object object) {

		SqlSessionFactory sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
		SqlSession session = sqlSessionFactory.openSession();
		Object obj = null;
		try {
			obj = new BaseDao().fetchObject(session, query, object);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in fetching object for query : " + query, e);
		} finally {
			session.close();
		}
		return obj;

	}

	public void updateObject(String query, Object object) throws Exception {
		SqlSessionFactory sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			new BaseDao().updateObject(session, query, object);
			session.commit();
		} catch (Exception e) {
			session.rollback();
			logger.error("Error in updating object for query : " + query, e);
			throw new Exception();
		} finally {
			session.close();
		}
	}

	public Object updateFetchObject(String query, Object object, String queryFetch) throws Exception {
		SqlSessionFactory sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
		SqlSession session = sqlSessionFactory.openSession();
		List<Object> list = null;
		try {
			new BaseDao().updateObject(session, query, object);
			list = new BaseDao().fetchList(session, queryFetch, object);
			session.commit();

		} catch (Exception e) {
			session.rollback();
			logger.error("Error in updating object for query : " + query, e);
			throw new Exception();
		} finally {
			session.close();
		}
		return list;
	}

	public void insertObject(String query, Object object) throws Exception {
		SqlSessionFactory sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			new BaseDao().insertObject(session, query, object);
			session.commit();
		} catch (Exception e) {
			session.rollback();
			logger.error("Error in saving object for query : " + query, e);
			throw new Exception();
		} finally {
			session.close();
		}
	}

	public Object insertFetchObject(String query, Object object) throws Exception {
		SqlSessionFactory sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
		SqlSession session = sqlSessionFactory.openSession();
		Object object2 = new Object();
		try {
			object2 = new BaseDao().insertFetchObject(session, query, object);
			session.commit();
		} catch (Exception e) {
			session.rollback();
			logger.error("Error in saving object for query : " + query, e);
			throw new Exception();
		} finally {
			session.close();
		}
		return object2;
	}

	public void delectObject(String query, Object object) {

		SqlSessionFactory sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			new BaseDao().fetchObject(session, query, object);
		} catch (Exception e) {
			logger.error("Error in delete object for query : " + query, e);
		} finally {
			session.close();
		}
	}

}
