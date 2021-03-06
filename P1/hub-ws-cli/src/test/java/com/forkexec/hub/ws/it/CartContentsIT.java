package com.forkexec.hub.ws.it;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.forkexec.hub.ws.Food;
import com.forkexec.hub.ws.FoodId;
import com.forkexec.hub.ws.FoodInit;
import com.forkexec.hub.ws.InvalidFoodIdFault_Exception;
import com.forkexec.hub.ws.InvalidFoodQuantityFault_Exception;
import com.forkexec.hub.ws.InvalidInitFault_Exception;
import com.forkexec.hub.ws.InvalidUserIdFault_Exception;


/**
 * Class that tests CartContents operation
 */
public class CartContentsIT extends BaseIT {
	private static List<FoodInit> foodList = new ArrayList<FoodInit>();

	@BeforeClass
	public static void oneTimeSetUp() throws InvalidInitFault_Exception{
		
		client.ctrlClear();
	
		
		Food food = new Food();
		FoodId foodId = new FoodId();
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
		FoodId foodId2 = new FoodId();
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
		FoodId foodId3 = new FoodId();
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
		FoodId foodId4 = new FoodId();
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
	}
	
	@Test(expected = InvalidUserIdFault_Exception.class)
	public void cartContentsUserNullTest() throws InvalidUserIdFault_Exception {
		client.cartContents(null);
	}
	
	@Test(expected = InvalidUserIdFault_Exception.class)
	public void cartContentsUserEmptyTest() throws InvalidUserIdFault_Exception {
		client.cartContents("");
	}
	
	@Test(expected = InvalidUserIdFault_Exception.class)
	public void cartContentsUserWhiteSpaceTest() throws InvalidUserIdFault_Exception {
		client.cartContents("    ");
	}
	
	
	@Test
	public void cartContentsSuccessTest() throws InvalidFoodIdFault_Exception, InvalidUserIdFault_Exception, InvalidFoodQuantityFault_Exception {
		FoodId foodId = new FoodId();
		foodId.setMenuId("M02");
		foodId.setRestaurantId("T22_Restaurant2");
		client.addFoodToCart("45454@4545", foodId, 3);
		
		client.cartContents("45454@4545");

		assertEquals(client.cartContents("45454@4545").size(), 1);
		assertEquals(client.cartContents("45454@4545").get(0).getFoodQuantity(), 3);
		assertEquals(client.cartContents("45454@4545").get(0).getFoodId().getMenuId(), "M02");
		assertEquals(client.cartContents("45454@4545").get(0).getFoodId().getRestaurantId(), "T22_Restaurant2");
	}
	
	@Test
	public void cartContentsSuccessTwoTest() throws InvalidFoodIdFault_Exception, InvalidUserIdFault_Exception, InvalidFoodQuantityFault_Exception {
		FoodId foodId = new FoodId();
		foodId.setMenuId("M01");
		foodId.setRestaurantId("T22_Restaurant1");
		client.addFoodToCart("45454@4545", foodId, 3);
		
		client.cartContents("45454@4545");

		assertEquals(client.cartContents("45454@4545").size(), 2);
		assertEquals(client.cartContents("45454@4545").get(1).getFoodQuantity(), 3);
		assertEquals(client.cartContents("45454@4545").get(1).getFoodId().getMenuId(), "M01");
		assertEquals(client.cartContents("45454@4545").get(1).getFoodId().getRestaurantId(), "T22_Restaurant1");
	}
	
	@Test(expected = InvalidUserIdFault_Exception.class)
	public void cartContentsFailureTest() throws InvalidUserIdFault_Exception{
		client.cartContents("455564@4545");
	}
	
	
	@AfterClass
	public static void oneTimeTearDown() {
		client.ctrlClear();
	}
}