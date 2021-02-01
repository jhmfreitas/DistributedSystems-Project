package com.forkexec.hub.domain;

public class HubFoodId {

	private final String restaurantId;
	private final String menuId;
	
	public HubFoodId(String restaurantId, String menuId) {
		this.restaurantId = restaurantId;
		this.menuId = menuId;
	}

	public String getRestaurantId() {
		return restaurantId;
	}

	public String getMenuId() {
		return menuId;
	}
}
