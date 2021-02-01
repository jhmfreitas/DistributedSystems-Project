package com.forkexec.rst.ws.it;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import com.forkexec.rst.ws.BadInitFault_Exception;
import com.forkexec.rst.ws.BadMenuIdFault_Exception;
import com.forkexec.rst.ws.BadQuantityFault_Exception;
import com.forkexec.rst.ws.InsufficientQuantityFault_Exception;
import com.forkexec.rst.ws.Menu;
import com.forkexec.rst.ws.MenuId;
import com.forkexec.rst.ws.MenuInit;
import com.forkexec.rst.ws.MenuOrder;

/**
 * Class that tests OrderMenu operation
 */


public class OrderMenuIT extends BaseIT {
	private static List<MenuInit> menus = new ArrayList<MenuInit>();

	@Before
	public void SetUp() throws BadInitFault_Exception{
		
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
	public void orderMenuNullTest() throws BadMenuIdFault_Exception, BadQuantityFault_Exception, InsufficientQuantityFault_Exception {
		client.orderMenu(null, 4);
	}
	
	@Test(expected = BadMenuIdFault_Exception.class)
	public void orderMenuBothWrongTest() throws BadMenuIdFault_Exception, BadQuantityFault_Exception, InsufficientQuantityFault_Exception {
		client.orderMenu(null, -1);
	}
	
	@Test(expected = BadQuantityFault_Exception.class)
	public void orderMenuZeroTest() throws BadMenuIdFault_Exception, BadQuantityFault_Exception, InsufficientQuantityFault_Exception {
		MenuId id = new MenuId();
		id.setId("01");
		client.orderMenu(id, 0);
	}
	
	@Test(expected = BadQuantityFault_Exception.class)
	public void orderMenuNegativeTest() throws BadMenuIdFault_Exception, BadQuantityFault_Exception, InsufficientQuantityFault_Exception {
		MenuId id = new MenuId();
		id.setId("01");
		client.orderMenu(id, -14);
	}
	
	@Test(expected = InsufficientQuantityFault_Exception.class)
	public void orderMenuNotEnoughTest() throws BadMenuIdFault_Exception, BadQuantityFault_Exception, InsufficientQuantityFault_Exception {
		MenuId id = new MenuId();
		id.setId("01");
		client.orderMenu(id, 200);
	}
	
	@Test(expected = BadMenuIdFault_Exception.class)
	public void orderMenuIdNotExistsTest() throws BadMenuIdFault_Exception, BadQuantityFault_Exception, InsufficientQuantityFault_Exception {
		MenuId id = new MenuId();
		id.setId("16");
		client.orderMenu(id, 1);
	}
	
	@Test
	public void orderMenuIdSuccessTest() throws BadMenuIdFault_Exception, BadQuantityFault_Exception, InsufficientQuantityFault_Exception {
		MenuId id = new MenuId();
		id.setId("01");
		MenuOrder order = client.orderMenu(id, 20);
		assertEquals("1", order.getId().getId());
	}
	
	@Test
	public void orderMenuThreeSuccessTest() throws BadMenuIdFault_Exception, BadQuantityFault_Exception, InsufficientQuantityFault_Exception {
		MenuId id = new MenuId();
		id.setId("01");
		MenuOrder order = client.orderMenu(id, 20);
		assertEquals("1", order.getId().getId());
		
		MenuId id2 = new MenuId();
		id2.setId("02");
		MenuOrder order2 = client.orderMenu(id2, 2);
		assertEquals("2", order2.getId().getId());
		
		MenuId id3 = new MenuId();
		id3.setId("03");
		MenuOrder order3 = client.orderMenu(id3, 5);
		assertEquals("3", order3.getId().getId());
	}
	
	@Test(expected = InsufficientQuantityFault_Exception.class)
	public void orderMenuThreeFailureTest() throws BadMenuIdFault_Exception, BadQuantityFault_Exception, InsufficientQuantityFault_Exception {
		MenuId id = new MenuId();
		id.setId("01");
		MenuOrder order = client.orderMenu(id, 20);
		assertEquals("1", order.getId().getId());
		
		MenuId id2 = new MenuId();
		id2.setId("01");
		MenuOrder order2 = client.orderMenu(id2, 5);
		assertEquals("2", order2.getId().getId());
		
		MenuId id3 = new MenuId();
		id3.setId("01");
		MenuOrder order3 = client.orderMenu(id3, 15);
		assertEquals("3", order3.getId().getId());
	}
	
	@AfterClass
	public static void oneTimeTearDown() {
		client.ctrlClear();
	}
		
}
