package com.techelevator;

import java.math.BigDecimal;

public class Chip implements VendingItem {

	private String location;
	private String name;
	private BigDecimal price;
	private int inventory = 5;
	private int totalSold;
	private String message = "Crunch Crunch, Yum!";
	private String type;

	public Chip(String location, String name, BigDecimal price, String type) {
		super();
		this.location = location;
		this.name = name;
		this.price = price;
		this.type = type;
	}

	@Override
	public String getName() {

		return this.name;
	}

	@Override
	public String getLocation() {

		return this.location;
	}

	@Override
	public BigDecimal getPrice() {

		return this.price;
	}

	@Override
	public String getType() {

		return this.type;
	}

	@Override
	public int getInventory() {
		return this.inventory;
	}

	@Override
	public int getTotalSold() {
		this.totalSold = 5 - this.inventory;
		return this.totalSold;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	@Override
	public void subtractItem() {
		this.inventory = inventory - 1;
	}

	@Override
	public String getAvailability() {
		if (this.inventory > 0) {
			return "";
		} else {
			return " -- SOLD OUT";
		}

	}
}
