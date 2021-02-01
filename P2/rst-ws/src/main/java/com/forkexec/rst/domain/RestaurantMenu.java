package com.forkexec.rst.domain;

/**
 * RestaurantMenu
 *
 * A restaurant's menu.
 *
 */
public class RestaurantMenu {
	private RestaurantMenuId id;
	private String entree;
	private String mainCourse;
	private String dessert;
    private int price;
    private int preparationTime;
    private int quantity;
	
	public RestaurantMenu(RestaurantMenuId id, String entree, String mainCourse, String dessert, int price, int preparationTime, int quantity) {
		this.setId(id);
		this.setEntree(entree);
		this.setMainCourse(mainCourse);
		this.setDessert(dessert);
		this.setPrice(price);
		this.setPreparationTime(preparationTime);
		this.setQuantity(quantity);
	}

	public RestaurantMenuId getId() {
		return id;
	}

	public void setId(RestaurantMenuId id) {
		this.id = id;
	}

	public String getEntree() {
		return entree;
	}

	public void setEntree(String entree) {
		this.entree = entree;
	}

	public String getMainCourse() {
		return mainCourse;
	}

	public void setMainCourse(String mainCourse) {
		this.mainCourse = mainCourse;
	}

	public String getDessert() {
		return dessert;
	}

	public void setDessert(String dessert) {
		this.dessert = dessert;
	}
	
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getPreparationTime() {
		return preparationTime;
	}

	public void setPreparationTime(int preparationTime) {
		this.preparationTime = preparationTime;
	}

	public synchronized int getQuantity() {
		return quantity;
	}

	public synchronized void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public boolean equals(Object obj) {
		
        if (obj == this) { 
            return true; 
        } 
 
        if (!(obj instanceof RestaurantMenu)) { 
            return false; 
        } 

        RestaurantMenu other = (RestaurantMenu) obj;
        
        if (this.getId() != other.getId()) {
        	return false;
        } 
          
        if (this.getEntree() != other.getEntree()) {
        	return false;
        } 
        
        if (this.getMainCourse() != other.getMainCourse()) {
        	return false;
        } 
        
        if (this.getDessert() != other.getDessert()) {
        	return false;
        } 
        
        if (this.getPrice() != other.getPrice()) {
        	return false;
        }
        
        if (this.getPreparationTime() != other.getPreparationTime()) {
        	return false;
        }
        
        return true;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Menu Description:\n");
		builder.append("ID: \n");
		builder.append(this.getId().toString());
		builder.append("Entree: ");
		builder.append(this.getEntree());
		builder.append("\n");
		builder.append("Main Course: ");
		builder.append(this.getMainCourse());
		builder.append("\n");
		builder.append("Dessert: ");
		builder.append(this.getDessert());
		builder.append("\n");
		builder.append("Price: ");
		builder.append(this.getPrice());
		builder.append("\n");
		builder.append("PreparationTime: ");
		builder.append(this.getPreparationTime());
		builder.append("\n");
		builder.append("Quantity: ");
		builder.append(this.getQuantity());
		builder.append("\n");
		return builder.toString();
	}
}