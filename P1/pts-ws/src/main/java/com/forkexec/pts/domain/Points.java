package com.forkexec.pts.domain;

import com.forkexec.pts.domain.exceptions.*;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

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
    /**
     * Global with the current value for the initial balance of every new client
     */
    private final AtomicInteger initialBalance = new AtomicInteger(DEFAULT_INITIAL_BALANCE);


    //private int points;
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
        this.startPoints = DEFAULT_INITIAL_BALANCE;
        this.users.clear();
    }

    public void setInitialBalance (int points) throws BadInitFaultException {
        if(points >= 0) {
            this.startPoints = points;
        }
        else {
            throw new BadInitFaultException();
        }
    }



    public void activateUser (String userEmail) throws EmailAlreadyExistsFaultException, InvalidEmailFaultException {

        if(getUsers().get(userEmail) != null) throw new EmailAlreadyExistsFaultException("User with email " + userEmail + " already exists");

        User newUser = new User(userEmail, this.startPoints);

        // add user to the hashmap
        addUser(newUser);
    }


    public void addUser(User user) {
        users.put(user.getEmail(), user);
    }


    //throw exception if empty
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



    public int addPoints(String userEmail, int pointsToAdd) throws InvalidPointsFaultException, InvalidEmailFaultException {

        if (pointsToAdd <= 0) throw new InvalidPointsFaultException("Invalid points to add");

        User user = getUser(userEmail);

        return user.addBalance(pointsToAdd);
    }


    public int spendPoints(String userEmail, int pointsToSpend) throws InvalidPointsFaultException, NotEnoughBalanceFaultException, InvalidEmailFaultException {

        if (pointsToSpend <= 0) throw new InvalidPointsFaultException("Invalid points to spend (negative value)");

        User user = getUser(userEmail);
        if (user.pointsBalance() < pointsToSpend) throw new NotEnoughBalanceFaultException("Points to spend higher than balance");

        return user.subBalance(pointsToSpend);
    }


    public int pointsBalance(String userEmail) throws InvalidEmailFaultException {
        return getUser(userEmail).pointsBalance();
    }
}
