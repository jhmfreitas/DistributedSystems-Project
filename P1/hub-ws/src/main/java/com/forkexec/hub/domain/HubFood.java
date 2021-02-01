package com.forkexec.hub.domain;

public class HubFood {
	private final HubFoodId id;
	private final String entree;
	private final String plate;
	private final String dessert;
	private final int price;
	private final int preparationTime;
	private int quantity;
	
	public HubFood(HubFoodId id, String entree, String plate, String dessert, int price, int preparationTime, int quantity) {
		this.id = id;
		this.entree = entree;
		this.plate = plate;
		this.dessert = dessert;
		this.price = price;
		this.preparationTime = preparationTime;
		this.quantity = quantity;
	}

	public HubFoodId getId() {
		return id;
	}

	public String getEntree() {
		return entree;
	}

	public String getPlate() {
		return plate;
	}

	public String getDessert() {
		return dessert;
	}

	public int getPrice() {
		return price;
	}

	public int getPreparationTime() {
		return preparationTime;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
