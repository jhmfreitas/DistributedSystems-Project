package com.forkexec.pts.domain;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import com.forkexec.pts.domain.exceptions.EmailAlreadyExistsFaultException;
import com.forkexec.pts.domain.exceptions.InvalidEmailFaultException;


public class User {


    private String email;
    private AtomicInteger balance;
    private int tag;

    public User (String email, int startBalance) throws EmailAlreadyExistsFaultException, InvalidEmailFaultException {
        checkEmail(email);
        this.email = email;
        this.balance = new AtomicInteger(startBalance);
        this.tag = 0;
    }

	public void checkEmail(String email) throws InvalidEmailFaultException, EmailAlreadyExistsFaultException {

        if(!Pattern.matches("(\\w\\.?)*\\w+@\\w+(\\.?\\w)*", email)) throw new InvalidEmailFaultException(email);
    }

    public synchronized int getTag() {
		return this.tag;
	}

	public synchronized void setTag(int tag) {
		this.tag = tag;
	}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int pointsBalance() {
        return this.balance.get();
    }

    public synchronized int addBalance(int points) {
    	return this.balance.addAndGet(points);
    }

    public synchronized int subBalance(int points) {
    	return this.balance.addAndGet(-points);
    }
    
    public synchronized void setBalance(int points) {
    	this.balance = new AtomicInteger(points);
    }
}
