package com.forkexec.hub.domain;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.forkexec.hub.domain.exceptions.InvalidUserIdFaultException;

/**
 * Hub
 *
 * A restaurants hub server.
 *
 */
public class Hub {
	
	private ConcurrentHashMap<String, Cart> carts = new ConcurrentHashMap<String, Cart>();
	
	private AtomicInteger orderId;
	// Singleton -------------------------------------------------------------

	/** Private constructor prevents instantiation from other classes. */
	private Hub() {
		// Initialization of default values
	}

	/**
	 * SingletonHolder is loaded on the first execution of Singleton.getInstance()
	 * or the first access to SingletonHolder.INSTANCE, not before.
	 */
	private static class SingletonHolder {
		private static final Hub INSTANCE = new Hub();
	}

	public static synchronized Hub getInstance() {
		return SingletonHolder.INSTANCE;
	}

	public ConcurrentHashMap<String, Cart> getCarts() {
		return carts;
	}

	public void setCarts(ConcurrentHashMap<String, Cart> carts) {
		this.carts = carts;
	}
	
	public void resetHub() {
		carts.clear();
		orderId = new AtomicInteger(0);
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
	
	public synchronized Cart getCart(String userId) {
		return this.getCarts().get(userId);
	}
	
	public void clearCart(String userId) throws InvalidUserIdFaultException {
		if(this.getCarts().containsKey(userId)) {
			Cart cart = this.getCarts().get(userId);
			cart.getList().clear();
		}
		else {
			throw new InvalidUserIdFaultException("UserId not valid");
		}
		
	}
	
	public synchronized void addFood(String userId, HubFood food) {
		if(this.getCarts().containsKey(userId)) {
			Cart cart = this.getCart(userId);
			
			HubFood hubFood = cart.getHubFood(food.getId().getRestaurantId(), food.getId().getMenuId());
			
			if(hubFood == null) {
				cart.addFood(food);
			}
			
			else {
				int quant = hubFood.getQuantity() + food.getQuantity();
				hubFood.setQuantity(quant);

			}

		}
		else {
			Cart cart = new Cart(userId);
			this.getCarts().put(userId, cart);
			this.getCart(userId).addFood(food);
		}
	}
	
}
