package com.coffeebean.mobile.rest.server.mvc.model;

import java.util.Date;

public class Employee extends PrimaryId {
	public String name;
	public long depart;
	public String email;
	public Date joinDate;

	public Employee() {
	}

	public Employee(long id, String name, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getDepart() {
		return depart;
	}

	public void setDepart(long depart) {
		this.depart = depart;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	@Override
	public String toString() {
		return "Employee [name=" + name + ", depart=" + depart + ", email="
				+ email + ", joinDate=" + joinDate + ", id=" + id + "]";
	}
}
