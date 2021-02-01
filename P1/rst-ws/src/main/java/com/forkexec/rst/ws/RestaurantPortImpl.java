package com.forkexec.rst.ws;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jws.WebService;

import com.forkexec.rst.domain.Restaurant;
import com.forkexec.rst.domain.RestaurantMenu;
import com.forkexec.rst.domain.RestaurantMenuId;
import com.forkexec.rst.domain.RestaurantMenuOrder;
import com.forkexec.rst.domain.RestaurantMenuOrderId;
import com.forkexec.rst.domain.exceptions.*;

/**
 * This class implements the Web Service port type (interface). The annotations
 * below "map" the Java class to the WSDL definitions.
 */
@WebService(endpointInterface = "com.forkexec.rst.ws.RestaurantPortType",
            wsdlLocation = "RestaurantService.wsdl",
            name ="RestaurantWebService",
            portName = "RestaurantPort",
            targetNamespace="http://ws.rst.forkexec.com/",
            serviceName = "RestaurantService"
)
public class RestaurantPortImpl implements RestaurantPortType {

	/**
	 * The Endpoint manager controls the Web Service instance during its whole
	 * lifecycle.
	 */
	private RestaurantEndpointManager endpointManager;

	/** Constructor receives a reference to the endpoint manager. */
	public RestaurantPortImpl(RestaurantEndpointManager endpointManager) {
		this.endpointManager = endpointManager;
	}
	
	// Main operations -------------------------------------------------------
	
	@Override
	public Menu getMenu(MenuId menuId) throws BadMenuIdFault_Exception {
		
		if (menuId == null) {
			throwBadMenuId("MenuId cannot be null");
		}
		
		String id = menuId.getId().trim();
		if (id.length() == 0)
			throwBadMenuId("MenuId cannot be empty or whitespace");
		
		// Access restaurantServer
		Restaurant restaurantServer = Restaurant.getInstance();
		RestaurantMenu menu = null;
		
		// Accesses all menus and shows the wanted menu
		synchronized(restaurantServer) {
			try {
				menu = restaurantServer.getMenu(id);
			} catch (BadMenuIdFaultException e) {
				throwBadMenuId("MenuId not found");
			}
		}
		
		return this.buildMenuInfo(menu);
	}

	@Override
	public List<Menu> searchMenus(String descriptionText) throws BadTextFault_Exception {
		
		if (descriptionText== null) {
			throwBadText("Description cannot be null");
		}
		
		String descriptionTrimmed = descriptionText.trim();
		if (descriptionTrimmed.length() == 0)
			throwBadText("Description cannot be empty or whitespace");
		
		
		// Access restaurantServer
		Restaurant restaurantServer = Restaurant.getInstance();
		List<RestaurantMenu> list = null;
		List<Menu> menu_list = new ArrayList<Menu>();
		
		// Accesses all menus and looks for menus with wanted food
		synchronized(restaurantServer) {
			list = restaurantServer.searchMenus(descriptionTrimmed);
		}
		
		Iterator<RestaurantMenu> restaurantMenuIterator = list.iterator();
		while (restaurantMenuIterator.hasNext()) {
			menu_list.add(this.buildMenuInfo(restaurantMenuIterator.next()));
		}
		
		return menu_list;
	}

	@Override
	public MenuOrder orderMenu(MenuId arg0, int arg1)
			throws BadMenuIdFault_Exception, BadQuantityFault_Exception, InsufficientQuantityFault_Exception {
		
		if (arg0 == null) {
			throwBadMenuId("Description cannot be null");
		}
		
		// Access restaurantServer
		Restaurant restaurantServer = Restaurant.getInstance();
		RestaurantMenuOrder menuOrder = null;
		
		synchronized(restaurantServer) {
			try {
				menuOrder = restaurantServer.orderMenu(arg0.getId(), arg1);
			} catch (BadMenuIdFaultException e) {
				this.throwBadMenuId("Invalid menu id");
			} catch (BadQuantityFaultException e) {
				this.throwBadQuantity("Invalid quantity");
			} catch (InsufficientQuantityFaultException e) {
				this.throwInsufficientQuantity("Insufficient Quantity");
			}
		}
		
		return this.buildMenuOrderInfo(menuOrder);
	}

	
	// Control operations ----------------------------------------------------

	/** Diagnostic operation to check if service is running. */
	@Override
	public String ctrlPing(String inputMessage) {
		// If no input is received, return a default name.
		if (inputMessage == null || inputMessage.trim().length() == 0)
			inputMessage = "friend";

		// If the park does not have a name, return a default.
		String wsName = this.endpointManager.getWsName();
		if (wsName == null || wsName.trim().length() == 0)
			wsName = "Restaurant";

		// Build a string with a message to return.
		StringBuilder builder = new StringBuilder();
		builder.append("Hello ").append(inputMessage);
		builder.append(" from ").append(wsName);
		return builder.toString();
	}

	/** Return all variables to default values. */
	@Override
	public void ctrlClear() {
		Restaurant.getInstance().resetRestaurantServer();
	}

	/** Set variables with specific values. */
	@Override
	public void ctrlInit(List<MenuInit> initialMenus) throws BadInitFault_Exception {
		if (initialMenus == null) {
			throwBadInit("Initial Menus cannot be null");
		}
		
		// Access restaurantServer
		Restaurant restaurantServer = Restaurant.getInstance();
		
		synchronized(restaurantServer) {
			Iterator<MenuInit> menuInitIterator = initialMenus.iterator();
			while (menuInitIterator.hasNext()) {
				MenuInit menuInitAux = menuInitIterator.next();
				Menu menuAux = menuInitAux.getMenu();
	
				RestaurantMenu menu = new RestaurantMenu(new RestaurantMenuId(menuAux.getId().getId()) ,menuAux.getEntree(), menuAux.getPlate(), menuAux.getDessert(), 
						menuAux.getPrice(), menuAux.getPreparationTime(), menuInitAux.getQuantity());
				try {
					restaurantServer.addMenu(menu);
				} catch(BadInitFaultException exception) {
					throwBadInit("Cannot add menu with invalid properties");
				}
			}
		}
	}

	// View helpers ----------------------------------------------------------

	 /** Helper to convert a Menu object to a view. */
	 private Menu buildMenuInfo(RestaurantMenu menu) {
		 Menu info = new Menu();
		 info.setId(this.buildMenuIdInfo(menu.getId()));
		 info.setEntree(menu.getEntree());
		 info.setPlate(menu.getMainCourse());
		 info.setDessert(menu.getDessert());
		 info.setPrice(menu.getPrice());
		 info.setPreparationTime(menu.getPreparationTime());
		 return info;
	 }
	 
	 /** Helper to convert a MenuId object to a view. */
	 private MenuId buildMenuIdInfo(RestaurantMenuId id) {
		 MenuId info = new MenuId();
		 info.setId(id.getId());
		 return info;
	 }
	 
	 /** Helper to convert a RestaurantMenuOrder object to a view. */
	 private MenuOrder buildMenuOrderInfo(RestaurantMenuOrder menu) {
		 MenuOrder info = new MenuOrder();
		 info.setId(this.buildMenuOrderIdInfo(menu.getId()));
		 info.setMenuId(this.buildMenuIdInfo(menu.getMenuId()));
		 info.setMenuQuantity(menu.getMenuQuantity());
		 return info;
	 }
	 
	 /** Helper to convert a RestaurantMenuOrderId object to a view. */
	 private MenuOrderId buildMenuOrderIdInfo(RestaurantMenuOrderId id) {
		 	MenuOrderId info = new MenuOrderId();
		 	info.setId(id.getId());
			return info;
		}
	
	// Exception helpers -----------------------------------------------------

	/** Helper to throw a new BadInit exception. */
	private void throwBadInit(final String message) throws BadInitFault_Exception {
		BadInitFault faultInfo = new BadInitFault();
		faultInfo.message = message;
		throw new BadInitFault_Exception(message, faultInfo);
	}
	
	/** Helper to throw a new BadMenuId exception. */
	private void throwBadMenuId(final String message) throws BadMenuIdFault_Exception {
		BadMenuIdFault faultInfo = new BadMenuIdFault();
		faultInfo.message = message;
		throw new BadMenuIdFault_Exception(message, faultInfo);
	}
	
	/** Helper to throw a new BadQuantity exception. */
	private void throwBadQuantity(final String message) throws BadQuantityFault_Exception {
		BadQuantityFault faultInfo = new BadQuantityFault();
		faultInfo.message = message;
		throw new BadQuantityFault_Exception(message, faultInfo);
	}

	/** Helper to throw a new BadText exception. */
	private void throwBadText(final String message) throws BadTextFault_Exception {
		BadTextFault faultInfo = new BadTextFault();
		faultInfo.message = message;
		throw new BadTextFault_Exception(message, faultInfo);
	}
	
	/** Helper to throw a new InsufficientQuantity exception. */
	private void throwInsufficientQuantity(final String message) throws InsufficientQuantityFault_Exception {
		InsufficientQuantityFault faultInfo = new InsufficientQuantityFault();
		faultInfo.message = message;
		throw new InsufficientQuantityFault_Exception(message, faultInfo);
	}
}
