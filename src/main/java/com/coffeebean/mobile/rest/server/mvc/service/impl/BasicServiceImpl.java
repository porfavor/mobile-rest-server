package com.coffeebean.mobile.rest.server.mvc.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.coffeebean.mobile.rest.server.core.exception.BizException;
import com.coffeebean.mobile.rest.server.mvc.dao.BasicDao;
import com.coffeebean.mobile.rest.server.mvc.model.PrimaryId;
import com.coffeebean.mobile.rest.server.mvc.service.BasicService;
import com.coffeebean.mobile.rest.server.mvc.util.BizUtils;
import com.coffeebean.mobile.rest.server.mvc.util.DslSql;

@Service(value="basicService")
public class BasicServiceImpl implements BasicService {

	@Resource
	private BasicDao basicDao;

	@Override
	public <T extends PrimaryId> void addDomain(T domain) {
		String sql = new DslSql(domain).insert("id").toSql();
		basicDao.add(sql, domain);
	}

	@Override
	public <T> void addDomainLink(T link) {
		String sql = new DslSql(link).insert().toSql();
		basicDao.addSimpleDomain(sql, link);
	}

	@Override
	public <T extends PrimaryId> void updateDomain(T domain) {
		if (domain.getId() <= 0) {
			throw new BizException("参数id必须大于0");
		}
		String sql = new DslSql(domain).update("id").where("id").toSql();
		basicDao.update(sql, domain);
	}

	@Override
	public <T extends PrimaryId> void deleteDomain(T domain) {
		if (domain.getId() <= 0) {
			throw new BizException("参数id必须大于0");
		}
		String table = BizUtils.underscoreName(StringUtils.uncapitalize(domain
				.getClass().getSimpleName()));
		String sql = "delete from " + table + " where id=:id";
		basicDao.delete(sql, domain);
	}

	@Override
	public <T extends PrimaryId> void logicdel(T domain) {
		if (domain.getId() <= 0) {
			throw new BizException("参数id必须大于0");
		}
		String table = BizUtils.underscoreName(StringUtils.uncapitalize(domain
				.getClass().getSimpleName()));
		String sql = "update " + table + " set status=0 where id=:id";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", domain.getId());
		basicDao.update(sql, domain);
	}

	@Override
	public <T> T selectDomainLink(T link, Class<T> clz,
			String... primaryKeyField) {
		String sql = new DslSql(link).select().where(primaryKeyField).toSql();
		return basicDao.get(sql, link, clz);
	}

	@Override
	public <T> T selectDomain(T domain, Class<T> clz, String... primaryKeyField) {
		String sql = new DslSql(domain).select().where(primaryKeyField).toSql();
		return basicDao.get(sql, domain, clz);
	}

	@Override
	public <T> void updateDomainLink(T link, String primaryKeyField) {
		String sql = new DslSql(link).update(primaryKeyField)
				.where(primaryKeyField).toSql();
		basicDao.update(sql, link);
	}

	@Override
	public <T> void deleteDomainLink(T link, String... primaryKeyField) {
		String sql = new DslSql(link).delete().where(primaryKeyField).toSql();
		basicDao.delete(sql, link);
	}
}
