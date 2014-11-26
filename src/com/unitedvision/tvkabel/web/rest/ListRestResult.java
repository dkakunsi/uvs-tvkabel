package com.unitedvision.tvkabel.web.rest;

public class ListRestResult extends RestResult {
	protected int page;
	protected long total;
	protected long count;

	protected ListRestResult(String message) {
		super(message);
	}
	
	protected ListRestResult(String message, int page, long total, long count) {
		super(message);
		this.type = "list-model";
		this.page = page;
		this.total = total;
		this.count = count;
	}
	
	public int getPage() {
		return page;
	}

	public long getTotal() {
		return total;
	}
	
	public long getCount() {
		return count;
	}
}
