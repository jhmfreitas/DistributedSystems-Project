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
import com.forkexec.hub.ws.InvalidInitFault_Exception;
import com.forkexec.hub.ws.InvalidTextFault_Exception;


/**
 * Class that tests SearchHungry operation
 */
public class SearchHungryIT extends BaseIT {
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
	
	@Test(expected = InvalidTextFault_Exception.class)
	public void searchHungryNullTest() throws InvalidTextFault_Exception {
		client.searchHungry(null);
	}
	
	@Test(expected = InvalidTextFault_Exception.class)
	public void searchHungryEmptyTest() throws InvalidTextFault_Exception {
		client.searchHungry("");
	}
	
	@Test(expected = InvalidTextFault_Exception.class)
	public void searchHungryWhitespaceTest() throws InvalidTextFault_Exception {
		client.searchHungry("   ");
	}
	
	@Test
	public void searchHungrySuccessTest() throws InvalidTextFault_Exception, InvalidFoodIdFault_Exception{
		List<Food> expectedResult = new ArrayList<Food>();
		
		FoodId foodId = new FoodId();
		foodId.setMenuId("M02");
		foodId.setRestaurantId("T22_Restaurant2");
		Food food = client.getFood(foodId);
		expectedResult.add(food);


		assertEquals(1, client.searchHungry("Pudding").size());
		assertEquals(food.getId().getRestaurantId(), client.searchHungry("Pudding").get(0).getId().getRestaurantId());
		assertEquals(food.getId().getMenuId(), client.searchHungry("Pudding").get(0).getId().getMenuId());
		assertEquals(food.getDessert(), client.searchHungry("Pudding").get(0).getDessert());
	}
	
	
	@Test
	public void searchHungrySuccessThreeResultsTest() throws InvalidTextFault_Exception, InvalidFoodIdFault_Exception{
		List<Food> expectedResult = new ArrayList<Food>();
		
		FoodId foodId = new FoodId();
		foodId.setMenuId("M03");
		foodId.setRestaurantId("T22_Restaurant1");
		Food food = client.getFood(foodId);
		expectedResult.add(food);
		
		foodId = new FoodId();
		foodId.setMenuId("M04");
		foodId.setRestaurantId("T22_Restaurant2");
		food = client.getFood(foodId);
		expectedResult.add(food);
		
		foodId = new FoodId();
		foodId.setMenuId("M02");
		foodId.setRestaurantId("T22_Restaurant2");
		food = client.getFood(foodId);
		expectedResult.add(food);
		
		List<Food> list = client.searchHungry("Chicken");
		assertEquals(3, list.size());
		assertEquals(expectedResult.get(0).getPrice(), list.get(0).getPrice());
		assertEquals(expectedResult.get(0).getId().getRestaurantId(), list.get(0).getId().getRestaurantId());
		assertEquals(expectedResult.get(0).getId().getMenuId(), list.get(0).getId().getMenuId());
		assertEquals(expectedResult.get(0).getDessert(), list.get(0).getDessert());
		assertEquals(expectedResult.get(0).getPlate(), list.get(0).getPlate());
		assertEquals(expectedResult.get(0).getEntree(), list.get(0).getEntree());
		
		assertEquals(expectedResult.get(1).getPrice(), list.get(1).getPrice());
		assertEquals(expectedResult.get(1).getId().getRestaurantId(), list.get(1).getId().getRestaurantId());
		assertEquals(expectedResult.get(1).getId().getMenuId(), list.get(1).getId().getMenuId());
		assertEquals(expectedResult.get(1).getDessert(), list.get(1).getDessert());
		assertEquals(expectedResult.get(1).getPlate(), list.get(1).getPlate());
		assertEquals(expectedResult.get(1).getEntree(), list.get(1).getEntree());
		
		assertEquals(expectedResult.get(2).getPrice(), list.get(2).getPrice());
		assertEquals(expectedResult.get(2).getId().getRestaurantId(), list.get(2).getId().getRestaurantId());
		assertEquals(expectedResult.get(2).getId().getMenuId(), list.get(2).getId().getMenuId());
		assertEquals(expectedResult.get(2).getDessert(), list.get(2).getDessert());
		assertEquals(expectedResult.get(2).getPlate(), list.get(2).getPlate());
		assertEquals(expectedResult.get(2).getEntree(), list.get(2).getEntree());
		
	}
	
	@Test
	public void searchHungryFailureTest() throws InvalidTextFault_Exception, InvalidFoodIdFault_Exception{
		assertEquals(0,client.searchHungry("Potatoes").size());
		
	}
	
	@AfterClass
	public static void oneTimeTearDown() {
		client.ctrlClear();
	}
}