package com.forkexec.pts.domain;

import com.forkexec.pts.domain.exceptions.*;

import java.util.HashMap;

/**
 * Points
 * <p>
 * A points server.
 */
public class Points {

    /**
     * Constant representing the default initial balance for every new client
     */
    private static final int DEFAULT_INITIAL_BALANCE = 100;

    private int startPoints = DEFAULT_INITIAL_BALANCE;
    private HashMap<String, User> users = new HashMap<String, User>();


    // Singleton -------------------------------------------------------------

    /**
     * Private constructor prevents instantiation from other classes.
     */
    private Points() { }

    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance()
     * or the first access to SingletonHolder.INSTANCE, not before.
     */
    private static class SingletonHolder {
        private static final Points INSTANCE = new Points();
    }

    public static synchronized Points getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void reset() {
        this.setStartPoints(DEFAULT_INITIAL_BALANCE);
        this.users.clear();
    }

    public synchronized void setInitialBalance (int points) throws BadInitFaultException {
        if(points >= 0) {
            this.setStartPoints(points);
        }
        else {
            throw new BadInitFaultException();
        }
    }



    public synchronized void activateUser (String userEmail) throws EmailAlreadyExistsFaultException, InvalidEmailFaultException {

        if(getUsers().get(userEmail) != null) throw new EmailAlreadyExistsFaultException("User with email " + userEmail + " already exists");

        User newUser = new User(userEmail, this.getStartPoints());
        // add user to the hashmap
        addUser(newUser);
    }


    public synchronized void addUser(User user) {
        users.put(user.getEmail(), user);
    }

    public synchronized HashMap<String, User> getUsers() {
        return this.users;
    }


    public User getUser(String email) throws InvalidEmailFaultException {
        User user = getUsers().get(email);
        if (user == null) throw new InvalidEmailFaultException("User with email " + email + " is not registered");
        return user;
    }

    public void deleteUser(String email) {
        users.remove(email);
    }
    
    public synchronized int setPointsBalance(String userEmail, int value, int tag) throws InvalidPointsFaultException, InvalidEmailFaultException, NotEnoughBalanceFaultException {
    	if (value == 0) {
    		throw new InvalidPointsFaultException("Invalid points to spend (zero)");
    	}
    	
    	//spend operation
    	else if(value < 0) {
            User user = getUser(userEmail);
            
            if (user.pointsBalance() < value) {
            	throw new NotEnoughBalanceFaultException("Points to spend higher than balance");
            }
            
            if(tag > user.getTag()) {
            	user.setTag(user.getTag() + 1);
                return user.subBalance(value);
            }
            else {
            	return user.pointsBalance();
            }
            
    	}
    	
    	//add operation
    	else {
    		User user = getUser(userEmail);
    		
    		if(tag > user.getTag()) {
            	user.setTag(user.getTag() + 1);
                return user.addBalance(value);
            }
            else {
            	return user.pointsBalance();
            }
    	}
    }

	public int getStartPoints() {
		return this.startPoints;
	}

	public synchronized void setStartPoints(int startPoints) {
		this.startPoints = startPoints;
	}

}
