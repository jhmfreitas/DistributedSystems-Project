package com.forkexec.rst.domain;

/**
 * RestaurantMenuOrder
 *
 * A restaurant menu order.
 *
 */
public class RestaurantMenuOrder {
	private RestaurantMenuOrderId id;
	private RestaurantMenuId menuId;
	private int menuQuantity;
	
	public RestaurantMenuOrder(RestaurantMenuOrderId id, RestaurantMenuId menuId, int menuQuantity) {
		this.id = id;
		this.menuId = menuId;
		this.menuQuantity = menuQuantity;
	}
	
	
	public RestaurantMenuOrderId getId() {
		return id;
	}
	
	public void setId(RestaurantMenuOrderId id) {
		this.id = id;
	}
	
	public RestaurantMenuId getMenuId() {
		return menuId;
	}
	
	public void setMenuId(RestaurantMenuId menuId) {
		this.menuId = menuId;
	}
	
	public int getMenuQuantity() {
		return menuQuantity;
	}
	
	public void setMenuQuantity(int menuQuantity) {
		this.menuQuantity = menuQuantity;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MenuOrder Description:\n");
		builder.append("MenuOrderID: ");
		builder.append(this.getId().toString());
		builder.append("\n");
		builder.append("MenuID: ");
		builder.append(this.getMenuId().toString());
		builder.append("\n");
		builder.append("Quantity: ");
		builder.append(this.getMenuQuantity());
		builder.append("\n");
		return builder.toString();
	}
	
}