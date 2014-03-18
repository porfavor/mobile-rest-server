package com.coffeebean.mobile.rest.server.mvc.dao;

import java.util.List;
import java.util.Map;

import com.coffeebean.mobile.rest.server.mvc.model.PrimaryId;
import com.coffeebean.mobile.rest.server.mvc.util.Page;

public interface BasicDao {

	<T extends PrimaryId> void add(String sql, T t);

	<T> void addSimpleDomain(String sql, T t);

	<T> void update(String sql, T t);

	void updateByMap(String sql, Map<String, Object> t);

	<T> void delete(String sql, T t);

	<T> List<T> gets(String sql, Object t, Class<T> cls);

	<T> List<T> getsByMap(String sql, Map<String, Object> t, Class<T> cls);

	<T> T get(String sql, Object t, Class<T> cls);

	<T> T getByMap(String sql, Map<String, Object> t, Class<T> cls);

	<T> Page pagingFetch(String sql, String totalSql, Object t, Class<T> cls,
			int start, int limit);

	<T> Page pagingFetchByMap(String sql, String totalSql,
			Map<String, Object> t, Class<T> cls, int start, int limit);

	<T> void batchOpt(String sql, List<T> rows);
}
