package com.coffeebean.mobile.rest.server.mvc.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.juffrou.reflect.ReflectionUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.coffeebean.mobile.rest.server.core.exception.BizException;
import com.coffeebean.mobile.rest.server.mvc.model.PrimaryId;
import com.coffeebean.mobile.rest.server.mvc.util.Page;
import com.coffeebean.mobile.rest.server.mvc.dao.BasicDao;

@Repository
public class BasicDaoImpl implements BasicDao {

	private static final Logger LOG = LoggerFactory.getLogger(BasicDaoImpl.class);

	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Resource
	public void setNamedJdbcTemplate(NamedParameterJdbcTemplate namedJdbcTemplate) {
		this.namedJdbcTemplate = namedJdbcTemplate;
	}

	public <T extends PrimaryId> void add(String sql,T t) {
		BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(t);
		KeyHolder autoKey = new GeneratedKeyHolder();
		LOG.debug("The number of rows effected is {}.", namedJdbcTemplate.update(sql, source,autoKey));
		t.setId(autoKey.getKey().intValue());
	}

	public <T> void update(String sql,T t) {
		BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(t);
		LOG.debug("The number of rows effected is {}.",namedJdbcTemplate.update(sql, source));
	}
	
	@Override
	public void updateByMap(String sql, Map<String, Object> t) {
		LOG.debug("The number of rows effected is {}.",namedJdbcTemplate.update(sql, t));
	}
	
	public <T> void delete(String sql,T t) {
		try {
			BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(t);
			LOG.debug("The number of rows effected is {}.",namedJdbcTemplate.update(sql, source));
		} catch (Exception e) {
			throw new BizException("删除失败，请先删除掉关联的数据");
		}
	}
	
	public <T> List<T> gets(String sql,Object t,Class<T> cls) {
		BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(t);
		return namedJdbcTemplate.query(sql, source, new BeanPropertyRowMapper<T>(cls));
	}
	
	@Override
	public <T> List<T> getsByMap(String sql, Map<String, Object> t, Class<T> cls) {
		return namedJdbcTemplate.query(sql,t, new BeanPropertyRowMapper<T>(cls));
	}
	
	@Override
	public <T> T getByMap(String sql, Map<String, Object> t, Class<T> cls) {
		List<T> results = getsByMap(sql, t, cls);
		return selectOne(sql, results);
	}
	
	public <T> T get(String sql,Object t,Class<T> cls) {
		List<T> results = gets(sql, t, cls);
		return selectOne(sql, results);
	}

	private <T> T selectOne(String sql, List<T> results) {
		int size = (results != null ? results.size() : 0);
		if(size>1){
			throw new BizException("more than 1 record:"+sql);
		}
		return size==0?null:results.get(0);
	}
	
	public <T> Page pagingFetch(String sql,String totalSql,Object t,Class<T> cls,int start, int limit) {
		Map<String, Object> paramMap = ReflectionUtil.getMapFromBean(t);
		paramMap.put("start", start-1);
		paramMap.put("limit", limit);
		
		List<T> rows = namedJdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<T>(cls));
		Number number = namedJdbcTemplate.queryForObject(totalSql, paramMap, Integer.class);
		int total = (number != null ? number.intValue() : 0);
		return new Page(rows, total);
	}
	
	@Override
	public <T> Page pagingFetchByMap(String sql, String totalSql, Map<String, Object> paramMap, Class<T> cls, int start, int limit) {
		
		paramMap.put("start", start-1);
		paramMap.put("limit", limit);
		
		List<T> rows = namedJdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<T>(cls));
		Number number = namedJdbcTemplate.queryForObject(totalSql, paramMap, Integer.class);
		int total = (number != null ? number.intValue() : 0);
		return new Page(rows, total);
	}

	@Override
	public <T> void addSimpleDomain(String sql, T t) {
		BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(t);
		LOG.debug("The number of rows effected is {}.", namedJdbcTemplate.update(sql, source));
	}

	@Override
	public <T> void batchOpt(String sql, List<T> rows) {
		SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(rows.toArray());
		LOG.debug("The number of rows effected is {}.", namedJdbcTemplate.batchUpdate(sql, params));
	}

}
