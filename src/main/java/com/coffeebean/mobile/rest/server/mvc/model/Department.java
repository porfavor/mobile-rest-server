package com.coffeebean.mobile.rest.server.mvc.model;

public class Department extends PrimaryId {
	public String name;
	public String remark;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "Department [name=" + name + ", remark=" + remark + ", id=" + id
				+ "]";
	}

}
