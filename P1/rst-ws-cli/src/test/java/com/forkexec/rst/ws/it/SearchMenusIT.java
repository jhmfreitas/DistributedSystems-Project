package com.forkexec.rst.ws.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.forkexec.rst.ws.BadInitFault_Exception;
import com.forkexec.rst.ws.BadMenuIdFault_Exception;
import com.forkexec.rst.ws.BadTextFault_Exception;
import com.forkexec.rst.ws.Menu;
import com.forkexec.rst.ws.MenuId;
import com.forkexec.rst.ws.MenuInit;

/**
 * Class that tests SearchMenus operation
 */


public class SearchMenusIT extends BaseIT {
	private static List<MenuInit> menus = new ArrayList<MenuInit>();

	@BeforeClass
	public static void oneTimeSetUp() throws BadInitFault_Exception{
		
		client.ctrlClear();
	
	
		Menu menu = new Menu();
		MenuId menuId = new MenuId();
		menuId.setId("01");
		menu.setId(menuId);
		menu.setEntree("Chilli Prawns");
		menu.setPlate("Pizza Julietta");
		menu.setDessert("Epic Chocolate Brownie");
		menu.setPrice(30);
		menu.setPreparationTime(40);
		
		MenuInit menuInit = new MenuInit();
		menuInit.setMenu(menu);
		menuInit.setQuantity(30);
		menus.add(menuInit);
		
		Menu menu2 = new Menu();
		MenuId menuId2 = new MenuId();
		menuId2.setId("02");
		menu2.setId(menuId2);
		menu2.setEntree("Crispy Squid");
		menu2.setPlate("Chicken Milanese");
		menu2.setDessert("Molten Chocolate Praline Pudding");
		menu2.setPrice(35);
		menu2.setPreparationTime(46);
		
		MenuInit menuInit2 = new MenuInit();
		menuInit2.setMenu(menu2);
		menuInit2.setQuantity(50);
		menus.add(menuInit2);
		
		Menu menu3 = new Menu();
		MenuId menuId3 = new MenuId();
		menuId3.setId("03");
		menu3.setId(menuId3);
		menu3.setEntree("Ultimate Garlic Bread");
		menu3.setPlate("Sicilian Chicken Breast");
		menu3.setDessert("Vanilla Panna Cotta");
		menu3.setPrice(25);
		menu3.setPreparationTime(27);
		
		MenuInit menuInit3 = new MenuInit();
		menuInit3.setMenu(menu3);
		menuInit3.setQuantity(23);
		menus.add(menuInit3);
		
		client.ctrlInit(menus);
	}
	
	@Test(expected = BadTextFault_Exception.class)
	public void searchMenusNullTest() throws BadTextFault_Exception {
		client.searchMenus(null);
	}

	@Test(expected = BadTextFault_Exception.class)
	public void searchMenusEmptyTest() throws BadTextFault_Exception {
		client.searchMenus("");
	}

	@Test(expected = BadTextFault_Exception.class)
	public void searchMenusWhiteSpaceTest() throws BadTextFault_Exception {
		client.searchMenus(" ");
	}

	@Test
	public void searchMenusSuccessTest() throws BadTextFault_Exception, BadMenuIdFault_Exception {
		String description = "Chocolate Brownie";
		List<Menu> menus = client.searchMenus(description);
		MenuId id = new MenuId();
		id.setId("01");
		assertTrue(client.getMenu(id).getDessert().contains(description));
		assertEquals(1,menus.size());
	}
	
	@Test
	public void searchMenusSuccess2Test() throws BadTextFault_Exception, BadMenuIdFault_Exception {
		String description = "Panna Cotta";
		List<Menu> menus = client.searchMenus(description);
		MenuId id = new MenuId();
		id.setId("03");
		assertTrue(client.getMenu(id).getDessert().contains(description));
		assertEquals(1,menus.size());
	}
	
	@Test
	public void searchMenusDoubleSuccessTest() throws BadTextFault_Exception, BadMenuIdFault_Exception {
		String description = "Chicken";
		List<Menu> menus = client.searchMenus(description);
		MenuId id2 = new MenuId();
		id2.setId("02");
		MenuId id3 = new MenuId();
		id3.setId("03");
		assertTrue(client.getMenu(id2).getPlate().contains(description));
		assertTrue(client.getMenu(id3).getPlate().contains(description));
		assertEquals(2,menus.size());
	}
	
	@Test
	public void searchMenusNotExistsTest() throws BadTextFault_Exception {
		String description = "Potatoes";
		assertEquals(0,client.searchMenus(description).size());
	}
	
	@AfterClass
	public static void oneTimeTearDown() {
		client.ctrlClear();
	}
		
}
