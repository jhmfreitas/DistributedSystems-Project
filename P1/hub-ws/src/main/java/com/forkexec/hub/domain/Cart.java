package com.forkexec.hub.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Cart
 *
 * A cart.
 *
 */
public class Cart {
	
	private final String id;
	
	private List<HubFood> foodList = new ArrayList<HubFood>();
	 
	public Cart(String id, HubFood food) {
		this.id = id;
		this.foodList.add(food); 
	}
	
	public Cart(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public List<HubFood> getList() {
		return this.foodList;
	}
	
	public void addFood(HubFood food) {
		this.getList().add(food);
	}
	
	public HubFood getHubFood(String restaurantId, String menuId){
		for(HubFood food : foodList) {
			if(food.getId().getRestaurantId().equals(restaurantId) && food.getId().getMenuId().equals(menuId)) {
				return food;
			}
		}
		return null;
	}

}