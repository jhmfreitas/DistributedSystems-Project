package com.forkexec.rst.domain;

/**
 * RestaurantMenuId
 *
 * A restaurant menu id.
 *
 */
public class RestaurantMenuId {
	private String id;
	
	public RestaurantMenuId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public boolean equals(Object obj) {
		
        if (obj == this) { 
            return true; 
        } 
 
        if (!(obj instanceof RestaurantMenuId)) { 
            return false; 
        } 

        RestaurantMenuId other = (RestaurantMenuId) obj;
        
        if (this.getId() != other.getId()) {
        	return false;
        } 
        
        return true;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MenuId Description:\n");
		builder.append("ID: ");
		builder.append(this.getId());
		builder.append("\n");
		return builder.toString();
	}
}