package com.forkexec.hub.ws;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import javax.jws.WebService;

import com.forkexec.cc.ws.cli.CreditCardClient;
import com.forkexec.cc.ws.cli.CreditCardClientException;
import com.forkexec.hub.domain.Cart;
import com.forkexec.hub.domain.Hub;
import com.forkexec.hub.domain.HubFood;
import com.forkexec.hub.domain.HubFoodId;
import com.forkexec.hub.domain.HubManager;
import com.forkexec.hub.domain.exceptions.InvalidUserIdFaultException;

import com.forkexec.hub.domain.exceptions.PointsNotFoundException;
import com.forkexec.pts.ws.EmailAlreadyExistsFault_Exception;
import com.forkexec.pts.ws.InvalidEmailFault_Exception;
import com.forkexec.pts.ws.cli.PointsClient;
import com.forkexec.pts.ws.cli.PointsClientException;
import com.forkexec.rst.ws.BadInitFault_Exception;
import com.forkexec.rst.ws.BadMenuIdFault_Exception;
import com.forkexec.rst.ws.BadTextFault_Exception;
import com.forkexec.rst.ws.Menu;
import com.forkexec.rst.ws.MenuId;
import com.forkexec.rst.ws.MenuInit;
import com.forkexec.rst.ws.cli.RestaurantClient;
import com.forkexec.rst.ws.cli.RestaurantClientException;

import pt.ulisboa.tecnico.sdis.ws.uddi.UDDINaming;
import pt.ulisboa.tecnico.sdis.ws.uddi.UDDINamingException;
import pt.ulisboa.tecnico.sdis.ws.uddi.UDDIRecord;



/**
 * This class implements the Web Service port type (interface). The annotations
 * below "map" the Java class to the WSDL definitions.
 */
@WebService(endpointInterface = "com.forkexec.hub.ws.HubPortType",
            wsdlLocation = "HubService.wsdl",
            name ="HubWebService",
            portName = "HubPort",
            targetNamespace="http://ws.hub.forkexec.com/",
            serviceName = "HubService"
)
public class HubPortImpl implements HubPortType {

	/**
	 * The Endpoint manager controls the Web Service instance during its whole
	 * lifecycle.
	 */
	private HubEndpointManager endpointManager;

	/** Constructor receives a reference to the endpoint manager. */
	public HubPortImpl(HubEndpointManager endpointManager) {
		this.endpointManager = endpointManager;
	}

	
	// Main operations -------------------------------------------------------
	
	@Override
	public void activateAccount(String userId) throws InvalidUserIdFault_Exception {
		//  Auto-generated method stub

		if (userId == null) {
			throwInvalidUserId("UserId cannot be null");
		}

		if(!Pattern.matches("(\\w\\.?)*\\w+@\\w+(\\.?\\w)*", userId)) throwInvalidUserId(userId);

		UDDINaming uddiNaming = this.endpointManager.getUddiNaming();
		Collection<UDDIRecord> namingList;
		try {
			namingList = uddiNaming.listRecords("T22_Points%");
			for(UDDIRecord record : namingList) {
			               
				if(record == null  || record.getUrl() == ""  || record.getUrl().trim() == "" || record.getUrl() == null)
					break;
				
				PointsClient pointsClient = new PointsClient(record.getUrl());
				
				pointsClient.activateUser(userId);
			}
		}catch(UDDINamingException exception) {
			System.out.println("UDDINamingException: " + exception.toString());
		}catch(PointsClientException exception) {
			System.out.println("PointsClientException: " + exception.toString());
		}catch(InvalidEmailFault_Exception exception) {
			System.out.println("InvalidEmailFault_Exception: " + exception.toString());
		}catch(EmailAlreadyExistsFault_Exception exception) {
			System.out.println("EmailAlreadyExistsFault_Exception: " + exception.toString());
		}
	}
	
	@Override
	public void loadAccount(String userId, int moneyToAdd, String creditCardNumber)
			throws InvalidCreditCardFault_Exception, InvalidMoneyFault_Exception, InvalidUserIdFault_Exception {
		//  TODO should call write operation

		if (userId == null) {
			throwInvalidUserId("UserId cannot be null");
		}
		
		String userIdTrimmed = userId.trim();
		if (userIdTrimmed.length() == 0)
			throwInvalidUserId("UserId cannot be empty or whitespace");

		if(creditCardNumber == null)
			throwInvalidCreditCard("Credit Card cannot be null");

		
		try {
			int pointsToAdd = 0;
			if(moneyToAdd == 10) pointsToAdd = 1000;
			else if(moneyToAdd == 20) pointsToAdd = 2100;
			else if(moneyToAdd == 30) pointsToAdd = 3300;
			else if(moneyToAdd == 50) pointsToAdd = 5500;
			else { 
				moneyToAdd = 0; throwInvalidMoney("Wrong money value to add");
			}
			
			CreditCardClient creditCardClient = new CreditCardClient("http://ws.sd.rnl.tecnico.ulisboa.pt:8080/cc");
			
			creditCardClient.validateNumber(creditCardNumber);
			
			HubManager hubManager = HubManager.getInstance();
			
			synchronized(hubManager) {
				hubManager.write(userId, pointsToAdd, 0);
			}

		} catch (PointsNotFoundException exception) {
			System.out.println("PointsNotFoundException: " + exception.toString());
		} catch(CreditCardClientException exception) {
			System.out.println("CreditCardClientException: " + exception.toString());
		} 
	}
	
	
	@Override
	public List<Food> searchDeal(String description) throws InvalidTextFault_Exception {
		// return lowest price menus first
		if (description == null) {
			throwInvalidText("Description cannot be null");
		}
		
		String descriptionTrimmed = description.trim();
		if (descriptionTrimmed.length() == 0) {
			throwInvalidText("Description cannot be empty or whitespace");
		}
		
		
		UDDINaming uddiNaming = this.endpointManager.getUddiNaming();
		List<Food> foodList = new ArrayList<Food>();
		Collection<UDDIRecord> namingList;
		try {
			namingList = uddiNaming.listRecords("T22_Restaurant%");
			for(UDDIRecord record : namingList) {
				if(record == null  || record.getUrl() == ""  || record.getUrl().trim() == "" || record.getUrl() == null)
					break;
				
				RestaurantClient restaurantClient = new RestaurantClient(record.getUrl());
				List<Menu> list = restaurantClient.searchMenus(description);
						
				for(Menu menu: list) {
					foodList.add(this.buildFoodInfo(menu, record));
				}
			} 
			
		} catch(UDDINamingException exception) {
			System.out.println("UDDINamingException: " + exception.toString());
		} catch (RestaurantClientException exception) {
			System.out.println("RestaurantClientException: " + exception.toString());
		} catch (BadTextFault_Exception exception) {
			System.out.println("BadTextFault_Exception: " + exception.toString());
		}
	      	
		Comparator<Food> comparator = new Comparator<Food>() { 
			public int compare(Food f1, Food f2) { 
				return f1.getPrice() - f2.getPrice();
			}
		};
      	
      	Collections.sort(foodList, comparator);
		
		return foodList;
	}
	
	@Override
	public List<Food> searchHungry(String description) throws InvalidTextFault_Exception {
		// return lowest preparation time first
		if (description == null) {
			throwInvalidText("Description cannot be null");
		}
		
		String descriptionTrimmed = description.trim();
		if (descriptionTrimmed.length() == 0) {
			throwInvalidText("Description cannot be empty or whitespace");
		}
		
		
		UDDINaming uddiNaming = this.endpointManager.getUddiNaming();
		List<Food> foodList = new ArrayList<Food>();
		Collection<UDDIRecord> namingList;
		try {
			namingList = uddiNaming.listRecords("T22_Restaurant%");
			for(UDDIRecord record : namingList) {
			               
				if(record == null  || record.getUrl() == ""  || record.getUrl().trim() == "" || record.getUrl() == null)
					break;
				
				RestaurantClient restaurantClient = new RestaurantClient(record.getUrl());
				List<Menu> list = restaurantClient.searchMenus(description);
						
				for(Menu menu: list){
					foodList.add(this.buildFoodInfo(menu, record));
				}
			} 
			
		} catch(UDDINamingException exception) {
			System.out.println("UDDINamingException: " + exception.toString());
		} catch (RestaurantClientException exception) {
			System.out.println("RestaurantClientException: " + exception.toString());
		} catch (BadTextFault_Exception exception) {
			System.out.println("BadTextFault_Exception: " + exception.toString());
		}
	      	
		Comparator<Food> comparator = new Comparator<Food>() { 
			public int compare(Food f1, Food f2) { 
				return f1.getPreparationTime() - f2.getPreparationTime();
			}
		};
      	
      	Collections.sort(foodList, comparator);
		
		return foodList;
	}

	
	@Override
	public void addFoodToCart(String userId, FoodId foodId, int foodQuantity)
			throws InvalidFoodIdFault_Exception, InvalidFoodQuantityFault_Exception, InvalidUserIdFault_Exception {
		if (userId == null) {
			throwInvalidUserId("UserId cannot be null");
		}
		
		String userIdTrimmed = userId.trim();
		if (userIdTrimmed.length() == 0)
			throwInvalidUserId("UserId cannot be empty or whitespace");
		
		if(foodId == null) {
			throwInvalidFoodId("FoodId cannot be null");
		}
		
		if(foodId.getMenuId() == null) {
			throwInvalidFoodId("MenuId cannot be null");
		}
		
		if(foodId.getRestaurantId() == null) {
			throwInvalidFoodId("RestaurantId cannot be null");
		}
		
		if (foodId.getRestaurantId().trim().length() == 0) {
			throwInvalidFoodId("RestaurantId cannot be empty or whitespace");
		}
		
		if (foodId.getMenuId().trim().length() == 0) {
			throwInvalidFoodId("MenuId cannot be empty or whitespace");
		}
		
		if (foodQuantity <= 0) {
			throwInvalidFoodQuantity("Food quantity cannot be 0 or negative");
		}
		
		Hub hub = Hub.getInstance();
		synchronized(hub) {
				
				HubFood food = new HubFood(new HubFoodId(this.getFood(foodId).getId().getRestaurantId(),this.getFood(foodId).getId().getMenuId()),
						this.getFood(foodId).getEntree(), this.getFood(foodId).getPlate(), this.getFood(foodId).getDessert(), 
						this.getFood(foodId).getPrice(), this.getFood(foodId).getPreparationTime(), foodQuantity);
				
				
				hub.addFood(userId, food);
		}
	}

	@Override
	public void clearCart(String userId) throws InvalidUserIdFault_Exception {
		if (userId == null) {
			throwInvalidUserId("UserId cannot be null");
		}
		
		String userIdTrimmed = userId.trim();
		if (userIdTrimmed.length() == 0)
			throwInvalidUserId("UserId cannot be empty or whitespace");
		
		Hub hub = Hub.getInstance();
		synchronized(hub) {
			try {
				hub.clearCart(userId);
				
			} catch(InvalidUserIdFaultException exception) {
				throwInvalidUserId("UserId does not exist");
			}
		}
	}

	@Override
	public FoodOrder orderCart(String userId)
			throws EmptyCartFault_Exception, InvalidUserIdFault_Exception, NotEnoughPointsFault_Exception {
		// TODO should call write operation
		int totalPrice = 0;

		if (userId == null) {
			throwInvalidUserId("UserId cannot be null");
		}
		
		String userIdTrimmed = userId.trim();
		if (userIdTrimmed.length() == 0)
			throwInvalidUserId("UserId cannot be empty or whitespace");
		
		String idString = null;
		FoodOrder foodOrderFinal = null;
		
		Hub hub = Hub.getInstance();
		synchronized(hub) {
			Cart cart = hub.getCart(userId);
			
			if(cart == null || cart.getList().size() == 0) {
				throwEmptyCart("Cart is empty");
			}
			else {
				List<HubFood> list = cart.getList();
				
				int id = hub.increaseId();
				idString = "" + id;
				
				FoodOrderId foodOrderId = new FoodOrderId();
				foodOrderId.setId(idString);
				
				foodOrderFinal = new FoodOrder();
				foodOrderFinal.setFoodOrderId(foodOrderId);
				
				
				for(HubFood food : list) {
					foodOrderFinal.getItems().add(this.buildFoodOrderInfo(food));
					totalPrice += food.getPrice();
				}
			}

			try {
				
				HubManager hubManager = HubManager.getInstance();
				
				synchronized(hubManager) {
					if(hubManager.read(userId).getValue() < totalPrice) {
						throwNotEnoughPoints("Not Enough Points!");
					}
					clearCart(userId);
					hubManager.write(userId, totalPrice, 1);
				}
			}catch (PointsNotFoundException exception) {
				System.out.println("PointsNotFoundException: " + exception.toString());
			}
		}

		return foodOrderFinal;
	}

	@Override
	public int accountBalance(String userId) throws InvalidUserIdFault_Exception {
		// TODO should call read operation
		BalanceView view = null;
		if (userId == null) {
			throwInvalidUserId("UserId cannot be null");
		}
		
		String userIdTrimmed = userId.trim();
		if (userIdTrimmed.length() == 0)
			throwInvalidUserId("UserId cannot be empty or whitespace");
		
		
		try {
			HubManager hubManager = HubManager.getInstance();
			
			synchronized(hubManager) {
				view = hubManager.read(userId);
			}
			return view.getValue();
		} catch (PointsNotFoundException exception) {
			System.out.println("PointsNotFoundException: " + exception.toString());
		}
		
		return 0;
	}

	@Override
	public Food getFood(FoodId foodId) throws InvalidFoodIdFault_Exception {
		if(foodId == null) {
			throwInvalidFoodId("FoodId cannot be null");
		}
		
		if(foodId.getRestaurantId() == null || foodId.getRestaurantId().trim().length() == 0 || foodId.getMenuId() == null || foodId.getMenuId().trim().length() == 0) {
			throwInvalidFoodId("Invalid FoodId");
		}
		
		UDDINaming uddiNaming = this.endpointManager.getUddiNaming();
		Collection<UDDIRecord> namingList;
		Food wantedFood = null;
		try {
			namingList = uddiNaming.listRecords("T22_Restaurant%");
			for(UDDIRecord record : namingList) {
				if(record == null  || record.getUrl() == ""  || record.getUrl().trim() == "" || record.getUrl() == null) {
					break;
				}
				
			    if(foodId.getRestaurantId().equals(record.getOrgName())) {
			    	MenuId id = new MenuId();
			    	id.setId(foodId.getMenuId());
			    	
			    	
					RestaurantClient restaurantClient = new RestaurantClient(record.getUrl());
					Menu menu = restaurantClient.getMenu(id);
							
					wantedFood= this.buildFoodInfo(menu, record);
					break;
			    }
			}
			
		} catch(UDDINamingException exception) {
			System.out.println("UDDINamingException: " + exception.toString());
		} catch (BadMenuIdFault_Exception exception) {
			throwInvalidFoodId("Invalid MenuId");
		} catch (RestaurantClientException exception) {
			System.out.println("RestaurantClientException: " + exception.toString());
		}
		
		return wantedFood;
	}

	@Override
	public List<FoodOrderItem> cartContents(String userId) throws InvalidUserIdFault_Exception {
		
		if (userId == null) {
			throwInvalidUserId("UserId cannot be null");
		}
		
		String userIdTrimmed = userId.trim();
		if (userIdTrimmed.length() == 0)
			throwInvalidUserId("UserId cannot be empty or whitespace");
		
		Hub hub = Hub.getInstance();
		List<FoodOrderItem> list = new ArrayList<FoodOrderItem>();
		synchronized(hub) {
			Cart cart = hub.getCart(userId);
			
			if(cart != null) {
				Iterator<HubFood> cartFoodIterator = cart.getList().iterator();
				while (cartFoodIterator.hasNext()) {
					HubFood food = cartFoodIterator.next();
					list.add(this.buildFoodOrderInfo(food));
				}
			}
			
			else {
				throwInvalidUserId("User Not found");
			}
			
		}
		return list;
	}

	// Control operations ----------------------------------------------------

	/** Diagnostic operation to check if service is running. */
	@Override
	public String ctrlPing(String inputMessage) {
		// If no input is received, return a default name.
		if (inputMessage == null || inputMessage.trim().length() == 0)
			inputMessage = "friend";

		// If the service does not have a name, return a default.
		String wsName = endpointManager.getWsName();
		if (wsName == null || wsName.trim().length() == 0)
			wsName = "Hub";
		
		UDDINaming uddiNaming = this.endpointManager.getUddiNaming();
		
		try {
			// Build a string with a message to return.
			StringBuilder builder = new StringBuilder();
			builder.append("\n");
			
			Collection<String> namingList = uddiNaming.list("T22_Restaurant%");
			for(String string : namingList) {
				if(string == null || string == "" || string.trim() == "")
					break;

                RestaurantClient restaurantClient = new RestaurantClient(string);
                builder.append("****   ").append(restaurantClient.ctrlPing(inputMessage)).append("   ****\n");
			}
			
			namingList = uddiNaming.list("T22_Points%");
			for(String string : namingList) {
				if(string == null || string == "" || string.trim() == "")
					break;

                PointsClient pointsClient = new PointsClient(string);
                builder.append("****   ").append(pointsClient.ctrlPing(inputMessage)).append("   ****\n");
			}

			return builder.toString();
		} catch (UDDINamingException e) {
			System.out.println("Error on dealing with UDDI: " + e);
		} catch (RestaurantClientException e) {
			System.out.println("Error on dealing with Supplier Client: " + e);
		} catch(PointsClientException exception) {
			System.out.println("PointsClientException: " + exception.toString());
		}
		
		return null;
	}

	/** Return all variables to default values. */
	@Override
	public void ctrlClear() {
		Hub.getInstance().resetHub();

		UDDINaming uddiNaming = this.endpointManager.getUddiNaming();
		
		try {
			Collection<String> wsURLList = uddiNaming.list("T22_Restaurant%");

			for(String wsURL : wsURLList) {
                RestaurantClient restaurantClient = new RestaurantClient(wsURL);
                restaurantClient.ctrlClear();
			}
			
			wsURLList = uddiNaming.list("T22_Points%");

			for(String wsURL : wsURLList) {
                PointsClient pointsClient = new PointsClient(wsURL);
                pointsClient.ctrlClear();
			}

		} catch (RestaurantClientException exception) {
			System.out.println("RestaurantClientException: " + exception);
		} catch (UDDINamingException exception) {
			System.out.println("UDDIException : " + exception);
		} catch(PointsClientException exception) {
			System.out.println("PointsClientException: " + exception.toString());
		}
	}

	/** Set variables with specific values. */
	@Override
	public void ctrlInitFood(List<FoodInit> initialFoods) throws InvalidInitFault_Exception {
		// Auto-generated method stub
		if (initialFoods == null) {
			throwInvalidInit("Initial Foods cannot be null");
		}
		
		// Access restaurantServer
		Hub hub = Hub.getInstance();
		
		synchronized(hub) {
			Iterator<FoodInit> foodInitIterator = initialFoods.iterator();
			while (foodInitIterator.hasNext()) {
				FoodInit foodInitAux = foodInitIterator.next();
				Food foodAux = foodInitAux.getFood();
				FoodId foodIdAux = foodAux.getId();

				UDDINaming uddiNaming = this.endpointManager.getUddiNaming();
				Collection<UDDIRecord> namingList;
				List<MenuInit> list = new ArrayList<MenuInit>();
				try {
					namingList = uddiNaming.listRecords("T22_Restaurant%");
					for(UDDIRecord record : namingList) {
						if(record == null  || record.getUrl() == ""  || record.getUrl().trim() == "" || record.getUrl() == null) {
							break;
						}


					    if(foodIdAux.getRestaurantId().equals(record.getOrgName())) {
							RestaurantClient restaurantClient = new RestaurantClient(record.getUrl());
							list.add(this.buildMenuInitInfo(foodInitAux));
							restaurantClient.ctrlInit(list);
					    }

					    list.clear();
					}
				} catch (UDDINamingException exception) {
					System.out.println("UDDIException : " + exception);
				} catch (RestaurantClientException exception) {
					System.out.println("RestaurantClientException: " + exception);
				} catch (BadInitFault_Exception exception) {
					throwInvalidInit("Invalid Menu Created");
				}
			}
		}
		
	}
	
	@Override
	public void ctrlInitUserPoints(int startPoints) throws InvalidInitFault_Exception {
		//  Auto-generated method stub

		if (startPoints < 0) throwInvalidInit("Invalid start points!");

		Hub hub = Hub.getInstance();

		synchronized(hub) {


			UDDINaming uddiNaming = this.endpointManager.getUddiNaming();

			try {
				Collection<String> wsURLList = uddiNaming.list("T22_Points%");

				for(String wsURL : wsURLList) {
	                PointsClient pointsClient = new PointsClient(wsURL);
	                pointsClient.ctrlInit(startPoints);
				}


			} catch (UDDINamingException exception) {
				System.out.println("UDDIException : " + exception);
			} catch (com.forkexec.pts.ws.BadInitFault_Exception exception) {
				throwInvalidInit("Invalid start points");
			} catch (PointsClientException exception) {
				System.out.println("PointsClientException: " + exception.toString());
			}
		}
		
	}



	// View helpers ----------------------------------------------------------

	/** Helper to convert a Menu to Food. */
	private Food buildFoodInfo(Menu menu, UDDIRecord record ) {
		Food food = new Food();
		FoodId foodId = new FoodId();
		
		foodId.setMenuId(menu.getId().getId());
		foodId.setRestaurantId(record.getOrgName());		
		food.setId(foodId);
		food.setEntree(menu.getEntree());
		food.setPlate(menu.getPlate());
		food.setDessert(menu.getDessert());
		food.setPrice(menu.getPrice());
		food.setPreparationTime(menu.getPreparationTime());
		return food;
	}
	
	/** Helper to convert a HubFood to FoodOrderItem. */
	private FoodOrderItem buildFoodOrderInfo(HubFood food) {
		FoodOrderItem orderItem = new FoodOrderItem();
		FoodId foodIdAux = new FoodId();
		
		foodIdAux.setMenuId(food.getId().getMenuId());
		foodIdAux.setRestaurantId(food.getId().getRestaurantId());
		
		orderItem.setFoodId(foodIdAux);
		orderItem.setFoodQuantity(food.getQuantity());
		return orderItem;
	}
	
	/** Helper to convert a FoodInit to MenuInit. */
	private MenuInit buildMenuInitInfo(FoodInit foodInit) {
		Food foodAux = foodInit.getFood();
		
		MenuId menuIdAux = new MenuId();
		menuIdAux.setId(foodAux.getId().getMenuId());
		
		Menu menuAux = new Menu();
		menuAux.setId(menuIdAux);
		menuAux.setEntree(foodAux.getEntree());
		menuAux.setPlate(foodAux.getPlate());
		menuAux.setDessert(foodAux.getDessert());
		menuAux.setPrice(foodAux.getPrice());
		menuAux.setPreparationTime(foodAux.getPreparationTime());
		
		MenuInit menuInit = new MenuInit();
		menuInit.setMenu(menuAux);
		menuInit.setQuantity(foodInit.getQuantity());
		
		return menuInit;
	}
	

	
	// Exception helpers -----------------------------------------------------

	/** Helper to throw a new InvalidInit exception. */
	private void throwInvalidInit(final String message) throws InvalidInitFault_Exception {
		InvalidInitFault faultInfo = new InvalidInitFault();
		faultInfo.message = message;
		throw new InvalidInitFault_Exception(message, faultInfo);
	}
	
	/** Helper to throw a new EmptyCart exception. */
	private void throwEmptyCart(final String message) throws EmptyCartFault_Exception {
		EmptyCartFault faultInfo = new EmptyCartFault();
		faultInfo.message = message;
		throw new EmptyCartFault_Exception(message, faultInfo);
	}
	
	/** Helper to throw a new InvalidCreditCard exception. */
	private void throwInvalidCreditCard(final String message) throws InvalidCreditCardFault_Exception {
		InvalidCreditCardFault faultInfo = new InvalidCreditCardFault();
		faultInfo.message = message;
		throw new InvalidCreditCardFault_Exception(message, faultInfo);
	}
	
	/** Helper to throw a new InvalidFoodQuantity exception. */
	private void throwInvalidFoodQuantity(final String message) throws InvalidFoodQuantityFault_Exception {
		InvalidFoodQuantityFault faultInfo = new InvalidFoodQuantityFault();
		faultInfo.message = message;
		throw new InvalidFoodQuantityFault_Exception(message, faultInfo);
	}
	
	/** Helper to throw a new InvalidFoodId exception. */
	private void throwInvalidFoodId(final String message) throws InvalidFoodIdFault_Exception {
		InvalidFoodIdFault faultInfo = new InvalidFoodIdFault();
		faultInfo.message = message;
		throw new InvalidFoodIdFault_Exception(message, faultInfo);
	}
	
	/** Helper to throw a new InvalidMoney exception. */
	private void throwInvalidMoney(final String message) throws InvalidMoneyFault_Exception {
		InvalidMoneyFault faultInfo = new InvalidMoneyFault();
		faultInfo.message = message;
		throw new InvalidMoneyFault_Exception(message, faultInfo);
	}
	
	/** Helper to throw a new InvalidText exception. */
	private void throwInvalidText(final String message) throws InvalidTextFault_Exception {
		InvalidTextFault faultInfo = new InvalidTextFault();
		faultInfo.message = message;
		throw new InvalidTextFault_Exception(message, faultInfo);
	}
	
	/** Helper to throw a new InvalidUserId exception. */
	private void throwInvalidUserId(final String message) throws InvalidUserIdFault_Exception {
		InvalidUserIdFault faultInfo = new InvalidUserIdFault();
		faultInfo.message = message;
		throw new InvalidUserIdFault_Exception(message, faultInfo);
	}
	
	/** Helper to throw a new NotEnoughPoints exception. */
	private void throwNotEnoughPoints(final String message) throws NotEnoughPointsFault_Exception {
		NotEnoughPointsFault faultInfo = new NotEnoughPointsFault();
		faultInfo.message = message;
		throw new NotEnoughPointsFault_Exception(message, faultInfo);
	}

}
