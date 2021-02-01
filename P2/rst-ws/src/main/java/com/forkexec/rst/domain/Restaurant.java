package com.forkexec.rst.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.forkexec.rst.domain.exceptions.*;

/**
 * Restaurant
 *
 * A restaurant server.
 *
 */
public class Restaurant {

	private Collection<RestaurantMenu> menus;
	private AtomicInteger orderId;
	
	// Singleton -------------------------------------------------------------

	/** Private constructor prevents instantiation from other classes. */
	private Restaurant() {
		// Initialization of default values
		resetRestaurantServer();
	}

	/**
	 * SingletonHolder is loaded on the first execution of Singleton.getInstance()
	 * or the first access to SingletonHolder.INSTANCE, not before.
	 */
	private static class SingletonHolder {
		private static final Restaurant INSTANCE = new Restaurant();
	}

	public static synchronized Restaurant getInstance() {
		return SingletonHolder.INSTANCE;
	}
	
	public void resetRestaurantServer() {
		List<RestaurantMenu> list = new ArrayList<RestaurantMenu>();
		this.setMenus(Collections.synchronizedList(list));
		orderId = new AtomicInteger(0);
	}
	
	public synchronized Collection<RestaurantMenu> getMenus() {
		return this.menus;
	}

	public void setMenus(Collection<RestaurantMenu> menus) {
		this.menus = menus;
	}
	
	public synchronized AtomicInteger getOrderId() {
		return orderId;
	}

	public void setOrderId(AtomicInteger orderId) {
		this.orderId = orderId;
	}
	
	public synchronized int increaseId() {
		return this.getOrderId().incrementAndGet();
	}
	
	public Boolean validateMenu(RestaurantMenu menu) {
		return menu.getId() != null && !("".equals(menu.getId().getId())) && menu.getId().getId() != null && 
				!("".equals(menu.getEntree())) && menu.getEntree() != null && !("".equals(menu.getMainCourse())) &&
				menu.getMainCourse() != null && !("".equals(menu.getDessert())) &&
				menu.getDessert() != null && menu.getPrice() > 0 && menu.getPreparationTime() > 0;
	}
	
	public void addMenu(RestaurantMenu menu) throws BadInitFaultException{
		if(validateMenu(menu)) {
			this.getMenus().add(menu);
		}
		else {
			throw new BadInitFaultException();
		}
	}
	
	public RestaurantMenu getMenu(String id) throws BadMenuIdFaultException{
		Iterator<RestaurantMenu> restaurantMenuIterator = getMenus().iterator();
		while (restaurantMenuIterator.hasNext()) {
			RestaurantMenu menu = restaurantMenuIterator.next();
			if(menu.getId().getId().equals(id)) {
				return menu;
			}
		}
		throw new BadMenuIdFaultException("Invalid MenuId");
	}
	
	public synchronized List<RestaurantMenu> searchMenus(String descriptionText){
		List<RestaurantMenu> list = new ArrayList<RestaurantMenu>();
		Iterator<RestaurantMenu> restaurantMenuIterator = getMenus().iterator();
		while (restaurantMenuIterator.hasNext()) {
			RestaurantMenu menu = restaurantMenuIterator.next();
			if(menu.getEntree().contains(descriptionText) || menu.getMainCourse().contains(descriptionText)
					|| menu.getDessert().contains(descriptionText)) {
				list.add(menu);
			}
		}
		
		return list;
	}
	
	public synchronized RestaurantMenuOrder orderMenu(String menuId, int quant) throws BadMenuIdFaultException, BadQuantityFaultException, InsufficientQuantityFaultException {
		if(quant <= 0 ) {
			throw new BadQuantityFaultException("Invalid Quantity");
		}
		
		RestaurantMenu menu = null;
		
		try {
			menu = this.getMenu(menuId);
		} catch(BadMenuIdFaultException exception) {
			throw exception;
		}
		
		if(menu.getQuantity() < quant) {
			throw new InsufficientQuantityFaultException("Insufficient Quantity");
		}
		
		int id = this.increaseId();
		String idString = "" + id;
		RestaurantMenuOrderId orderId = new RestaurantMenuOrderId(idString);
		RestaurantMenuOrder menuOrder = new RestaurantMenuOrder(orderId,new RestaurantMenuId(menuId),quant);
		
		menu.setQuantity(menu.getQuantity() - quant);
		
		return menuOrder;
	}
}
