package com.techelevator;

import java.math.BigDecimal;

public interface VendingItem {

	public String getName();

	public String getLocation();

	public BigDecimal getPrice();

	public String getType();

	public int getInventory();

	public int getTotalSold();

	public String getMessage();

	public void subtractItem();

	public String getAvailability();
}
