package com.fractal.concordia.common;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

public class BaseDao{

	public List<Object> fetchList(SqlSession session, String query) {
		return session.selectList(query);
	}

	public List<Object> fetchList(SqlSession session, String query, Object object) {
		return session.selectList(query, object);

	}

	public void insertObject(SqlSession session, String query, Object object) {
		session.insert(query, object);
	}

	public Object insertFetchObject(SqlSession session, String query, Object object) {
		return session.insert(query, object);
	}

	public Object insertObjectReturn(SqlSession session, String query, Object object) {
		return session.insert(query, object);
	}

	public void updateObject(SqlSession session, String query, Object object) {
		session.update(query, object);
	}

	public Object fetchObject(SqlSession session, String query, Object object) {
		return session.selectOne(query, object);
	}

	public Object fetchObject(SqlSession session, String query) {
		return session.selectOne(query);
	}

}
