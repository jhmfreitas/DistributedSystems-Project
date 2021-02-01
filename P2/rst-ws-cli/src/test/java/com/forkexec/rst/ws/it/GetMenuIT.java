package com.forkexec.rst.ws.it;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.forkexec.rst.ws.BadInitFault_Exception;
import com.forkexec.rst.ws.BadMenuIdFault_Exception;
import com.forkexec.rst.ws.Menu;
import com.forkexec.rst.ws.MenuId;
import com.forkexec.rst.ws.MenuInit;

/**
 * Class that tests GetMenu operation
 */


public class GetMenuIT extends BaseIT {
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
	
	@Test(expected = BadMenuIdFault_Exception.class)
	public void getMenuNullTest() throws BadMenuIdFault_Exception {
		client.getMenu(null);
	}

	@Test(expected = BadMenuIdFault_Exception.class)
	public void getMenuEmptyTest() throws BadMenuIdFault_Exception {
		MenuId id = new MenuId();
		id.setId("");
		client.getMenu(id);
	}

	@Test(expected = BadMenuIdFault_Exception.class)
	public void getMenuWhitespaceTest() throws BadMenuIdFault_Exception {
		MenuId id = new MenuId();
		id.setId(" ");
		client.getMenu(id);
	}

	@Test
	public void getMenuSuccessTest() throws BadMenuIdFault_Exception {
		MenuId id = new MenuId();
		id.setId("01");
		Menu menu = client.getMenu(id);
		assertEquals("01", menu.getId().getId());
		assertEquals("Chilli Prawns", menu.getEntree());
		assertEquals("Pizza Julietta", menu.getPlate());
		assertEquals("Epic Chocolate Brownie", menu.getDessert());
		assertEquals(30, menu.getPrice());
		assertEquals(40, menu.getPreparationTime());
	}
	
	@Test
	public void getMenuSuccess2Test() throws BadMenuIdFault_Exception {
		MenuId id = new MenuId();
		id.setId("02");
		Menu menu = client.getMenu(id);
		assertEquals("02", menu.getId().getId());
		assertEquals("Crispy Squid", menu.getEntree());
		assertEquals("Chicken Milanese", menu.getPlate());
		assertEquals("Molten Chocolate Praline Pudding", menu.getDessert());
		assertEquals(35, menu.getPrice());
		assertEquals(46, menu.getPreparationTime());
	}
	
	@Test
	public void getMenuSuccess3Test() throws BadMenuIdFault_Exception {
		MenuId id = new MenuId();
		id.setId("03");
		Menu menu = client.getMenu(id);
		assertEquals("03", menu.getId().getId());
		assertEquals("Ultimate Garlic Bread", menu.getEntree());
		assertEquals("Sicilian Chicken Breast", menu.getPlate());
		assertEquals("Vanilla Panna Cotta", menu.getDessert());
		assertEquals(25, menu.getPrice());
		assertEquals(27, menu.getPreparationTime());
	}

	

	@Test(expected = BadMenuIdFault_Exception.class)
	public void getMenuNotExistsTest() throws BadMenuIdFault_Exception {
		MenuId id = new MenuId();
		id.setId("05");
		client.getMenu(id);
	}
	
	@AfterClass
	public static void oneTimeTearDown() {
		client.ctrlClear();
	}
		
}
