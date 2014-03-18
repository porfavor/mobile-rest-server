package com.coffeebean.mobile.rest.server.mvc.service;

import com.coffeebean.mobile.rest.server.mvc.model.PrimaryId;

public interface BasicService {
	<T extends PrimaryId> void addDomain(T domain);

	<T> void addDomainLink(T link);

	<T> T selectDomainLink(T link, Class<T> clz, String... primaryKeyField);

	<T> T selectDomain(T link, Class<T> clz, String... primaryKeyField);

	<T extends PrimaryId> void updateDomain(T domain);

	<T> void updateDomainLink(T link, String primaryKeyField);

	<T extends PrimaryId> void deleteDomain(T domain);

	<T> void deleteDomainLink(T link, String... primaryKeyField);

	<T extends PrimaryId> void logicdel(T domain);
}
