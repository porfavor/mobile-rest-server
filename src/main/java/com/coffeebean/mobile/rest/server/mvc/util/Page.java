package com.coffeebean.mobile.rest.server.mvc.util;

import java.util.ArrayList;
import java.util.List;

public class Page {
	private List<? extends Object> rows = new ArrayList<Object>();
	private int total;

	public Page(List<? extends Object> rows, int total) {
		this.rows = rows;
		this.total = total;
	}

	public List<? extends Object> getRows() {
		return rows;
	}

	public void setRows(List<? extends Object> rows) {
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
