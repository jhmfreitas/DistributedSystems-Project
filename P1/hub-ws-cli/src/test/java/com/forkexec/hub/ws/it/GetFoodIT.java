package com.forkexec.hub.ws.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.forkexec.hub.ws.Food;
import com.forkexec.hub.ws.FoodId;
import com.forkexec.hub.ws.FoodInit;
import com.forkexec.hub.ws.InvalidFoodIdFault_Exception;
import com.forkexec.hub.ws.InvalidInitFault_Exception;


/**
 * Class that tests GetFood operation
 */
public class GetFoodIT extends BaseIT {
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
	
	@Test(expected = InvalidFoodIdFault_Exception.class)
	public void getFoodNullTest() throws InvalidFoodIdFault_Exception {
		client.getFood(null);
	}
	
	@Test(expected = InvalidFoodIdFault_Exception.class)
	public void getFoodRestaurantNullTest() throws InvalidFoodIdFault_Exception {
		FoodId foodId = new FoodId();
		foodId.setMenuId("M01");
		foodId.setRestaurantId(null);
		client.getFood(foodId);
	}
	
	@Test(expected = InvalidFoodIdFault_Exception.class)
	public void getFoodMenuNullTest() throws InvalidFoodIdFault_Exception {
		FoodId foodId = new FoodId();
		foodId.setMenuId(null);
		foodId.setRestaurantId("T22_Restaurant2");
		client.getFood(foodId);
	}
	
	@Test(expected = InvalidFoodIdFault_Exception.class)
	public void getFoodRestaurantEmptyTest() throws InvalidFoodIdFault_Exception {
		FoodId foodId = new FoodId();
		foodId.setMenuId("M01");
		foodId.setRestaurantId("");
		client.getFood(foodId);
	}
	
	@Test(expected = InvalidFoodIdFault_Exception.class)
	public void getFoodMenuEmptyTest() throws InvalidFoodIdFault_Exception {
		FoodId foodId = new FoodId();
		foodId.setMenuId("");
		foodId.setRestaurantId("T22_Restaurant2");
		client.getFood(foodId);
	}
	
	@Test(expected = InvalidFoodIdFault_Exception.class)
	public void getFoodMenuWhiteSpaceTest() throws InvalidFoodIdFault_Exception {
		FoodId foodId = new FoodId();
		foodId.setMenuId("    ");
		foodId.setRestaurantId("T22_Restaurant2");
		client.getFood(foodId);
	}
	
	@Test(expected = InvalidFoodIdFault_Exception.class)
	public void getFoodRestaurantWhiteSpaceTest() throws InvalidFoodIdFault_Exception {
		FoodId foodId = new FoodId();
		foodId.setMenuId("M01");
		foodId.setRestaurantId("   ");
		client.getFood(foodId);
	}
	
	@Test
	public void getFoodSuccessTest() throws InvalidFoodIdFault_Exception {
		FoodId foodId = new FoodId();
		foodId.setMenuId("M02");
		foodId.setRestaurantId("T22_Restaurant2");
		Food food = client.getFood(foodId);


		assertNotNull(food);
		assertEquals(food.getPrice(), 35);
		assertEquals(food.getPreparationTime(), 46);
		assertEquals(food.getId().getRestaurantId(), "T22_Restaurant2");
		assertEquals(food.getId().getMenuId(), "M02");
		assertEquals(food.getDessert(), "Molten Chocolate Praline Pudding");
		assertEquals(food.getPlate(), "Chicken Milanese");
		assertEquals(food.getEntree(), "Crispy Squid");
	}
	
	@Test
	public void getFoodSuccessTwoTest() throws InvalidFoodIdFault_Exception {
		FoodId foodId = new FoodId();
		foodId.setMenuId("M01");
		foodId.setRestaurantId("T22_Restaurant1");
		Food food = client.getFood(foodId);


		assertNotNull(food);
		assertEquals(food.getPrice(), 30);
		assertEquals(food.getPreparationTime(), 40);
		assertEquals(food.getId().getRestaurantId(), "T22_Restaurant1");
		assertEquals(food.getId().getMenuId(), "M01");
		assertEquals(food.getDessert(), "Epic Chocolate Brownie");
		assertEquals(food.getPlate(), "Pizza Julietta");
		assertEquals(food.getEntree(), "Chilli Prawns");
		
	}

	@Test(expected = InvalidFoodIdFault_Exception.class)
	public void getFoodFailureTest() throws InvalidFoodIdFault_Exception{
		FoodId foodId = new FoodId();
		foodId.setMenuId("M09");
		foodId.setRestaurantId("T22_Restaurant2");
		client.getFood(foodId);
	}
	
	@AfterClass
	public static void oneTimeTearDown() {
		client.ctrlClear();
	}
}