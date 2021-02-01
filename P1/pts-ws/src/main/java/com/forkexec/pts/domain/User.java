package com.forkexec.pts.domain;

import com.forkexec.pts.domain.exceptions.EmailAlreadyExistsFaultException;
import com.forkexec.pts.domain.exceptions.InvalidEmailFaultException;


public class User {


    private String email;
    private int balance;




    public User (String email, int startBalance) throws EmailAlreadyExistsFaultException, InvalidEmailFaultException {
        checkEmail(email);
        this.email = email;
        this.balance = startBalance;

    }

    public void checkEmail(String email) throws InvalidEmailFaultException, EmailAlreadyExistsFaultException {

        String pattern = "^(([A-Za-z0-9+_-]+)|(([A-Za-z0-9+_.-]+)([A-Za-z0-9+_-]+)))@(([A-Za-z0-9+_-]+)|(([A-Za-z0-9+_.-]+)([A-Za-z0-9+_-]+)))$";

        if(!email.matches(pattern)) throw new InvalidEmailFaultException(email);


    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public int pointsBalance() {
        return this.balance;
    }

    public int addBalance(int points) {
        this.balance += points;
        return this.balance;
    }

    public int subBalance(int points) {
        this.balance -= points;
        return this.balance;
    }



}
