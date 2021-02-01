package com.forkexec.hub.ws.it;

import com.forkexec.hub.ws.*;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;

public class OrderCartIT extends BaseIT {
	private static List<FoodInit> foodList = new ArrayList<FoodInit>();
	static FoodId foodId;
	static FoodId foodId2;
	static FoodId foodId3;
	static FoodId foodId4;
	
	@BeforeClass
	public static void oneTimeSetUp() throws InvalidInitFault_Exception, InvalidUserIdFault_Exception{
		System.out.println("***** Iniciar setup do teste *****");
		client.ctrlClear();
	
		
		Food food = new Food();
		foodId = new FoodId();
		foodId.setMenuId("M01");
		foodId.setRestaurantId("T22_Restaurant1");
		food.setId(foodId);
		food.setEntree("Chilli Prawns");
		food.setPlate("Pizza Julietta");
		food.setDessert("Epic Chocolate Brownie");
		food.setPrice(30);
		food.setPreparationTime(40);
		
		FoodInit foodInit = new FoodInit();
		foodInit.setFood(food);
		foodInit.setQuantity(30);
		foodList.add(foodInit);
		
		Food food2 = new Food();
		foodId2 = new FoodId();
		foodId2.setMenuId("M02");
		foodId2.setRestaurantId("T22_Restaurant2");
		food2.setId(foodId2);
		food2.setEntree("Crispy Squid");
		food2.setPlate("Chicken Milanese");
		food2.setDessert("Molten Chocolate Praline Pudding");
		food2.setPrice(35);
		food2.setPreparationTime(46);
		
		FoodInit foodInit2 = new FoodInit();
		foodInit2.setFood(food2);
		foodInit2.setQuantity(50);
		foodList.add(foodInit2);
		
		Food food3 = new Food();
		foodId3 = new FoodId();
		foodId3.setMenuId("M03");
		foodId3.setRestaurantId("T22_Restaurant1");
		food3.setId(foodId3);
		food3.setEntree("Ultimate Garlic Bread");
		food3.setPlate("Sicilian Chicken Breast");
		food3.setDessert("Vanilla Panna Cotta");
		food3.setPrice(25);
		food3.setPreparationTime(27);
		
		FoodInit foodInit3 = new FoodInit();
		foodInit3.setFood(food3);
		foodInit3.setQuantity(23);
		foodList.add(foodInit3);
		
		
		Food food4 = new Food();
		foodId4 = new FoodId();
		foodId4.setMenuId("M04");
		foodId4.setRestaurantId("T22_Restaurant2");
		food4.setId(foodId4);
		food4.setEntree("Flash-Fried Prawns");
		food4.setPlate("Lemon Chicken");
		food4.setDessert("Vin Santo Tiramisù");
		food4.setPrice(50);
		food4.setPreparationTime(35);
		
		FoodInit foodInit4 = new FoodInit();
		foodInit4.setFood(food4);
		foodInit4.setQuantity(23);
		foodList.add(foodInit4);
		
		client.ctrlInitFood(foodList);
		client.activateAccount("sd.test@tecnico.ulisboa");
		client.ctrlInitUserPoints(500);
		System.out.println("***** Começar testes *****");
	}

    @Test
    public void orderCartOneItemTest() throws InvalidFoodIdFault_Exception, InvalidUserIdFault_Exception, InvalidFoodQuantityFault_Exception, EmptyCartFault_Exception, NotEnoughPointsFault_Exception {
    	client.addFoodToCart("sd.test@tecnico.ulisboa", foodId4, 1);
        FoodOrder order = client.orderCart("sd.test@tecnico.ulisboa");
        assertNotNull(order.getFoodOrderId());
        assertNotNull(order.getFoodOrderId().getId());
        assertFalse(order.getFoodOrderId().getId().trim().isEmpty());
        assertEquals(1, order.getItems().size());
        assertEquals(foodId4.getMenuId(), order.getItems().get(0).getFoodId().getMenuId());
        assertEquals(foodId4.getRestaurantId(), order.getItems().get(0).getFoodId().getRestaurantId());
        assertEquals(1, order.getItems().get(0).getFoodQuantity());
    }



    @Test(expected = EmptyCartFault_Exception.class)
    public void orderEmptyCartTest() throws InvalidUserIdFault_Exception, EmptyCartFault_Exception, NotEnoughPointsFault_Exception, InvalidFoodQuantityFault_Exception {
        client.orderCart("sd.test@tecnico.ulisboa");
    }

    @Test(expected = InvalidUserIdFault_Exception.class)
    public void nullUserTest() throws InvalidUserIdFault_Exception, EmptyCartFault_Exception, NotEnoughPointsFault_Exception, InvalidFoodQuantityFault_Exception {
        client.orderCart(null);
    }

    @AfterClass
    public static void oneTimeTearDown() {
        client.ctrlClear();
    }
}
