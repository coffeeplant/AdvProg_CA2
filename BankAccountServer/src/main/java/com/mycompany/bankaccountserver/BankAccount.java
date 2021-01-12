/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankaccountserver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Bebhin
 */
public class BankAccount implements Serializable, Account{
    
    //private List<BankAccount> accounts = new ArrayList();
    //private Set<BankAccount> accounts = new HashSet<>();

    
    private int accountNumber;
    private int numOfDeposits = 0;
    private int numOfWithdrawals = 0;
    private double currentBalance;
    private double amount;
    final private List<Observer> observers = new ArrayList();
    
    private String freeTransactionLimit;
    
    ServerMain sm;

    public BankAccount() {
        this.sm = new ServerMain();
    }

    public BankAccount(int accountNumber, double amount) {
        this.sm = new ServerMain();
        this.accountNumber = accountNumber;
        this.amount = amount;

    }
    
    public BankAccount(int accountNumber) {
        this.sm = new ServerMain();
        this.accountNumber = accountNumber;
        this.numOfDeposits = numOfDeposits;
        this.numOfWithdrawals = numOfWithdrawals;
        //accounts.add(this);
     }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getNumOfDeposits() {
        return numOfDeposits;
    }

    public void setNumOfDeposits(int numOfDeposits) {
        this.numOfDeposits = numOfDeposits;
    }

    public int getNumOfWithdrawals() {
        return numOfWithdrawals;
    }

    public void setNumOfWithdrawals(int numOfWithdrawals) {
        this.numOfWithdrawals = numOfWithdrawals;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }
    
//    public void createAccount(){
//        System.out.println("Inside create account");
//        BankAccount newAccount = new BankAccount(accountNumber);
//
//        //should this happen here or in thread?
//    }

    //THIS NEEDS TIDYING UP, DOUBLING UP ON CODE AT THE MOMENT
    public synchronized double deposit(int accountNumber, double amount){
        System.out.println("is deposit method running");
        System.out.println("Amount: " +amount);
       
            if(!sm.getAccount(accountNumber)){
            //createAccount();
            System.out.println("Creating new account with a/c number: " +accountNumber);
            BankAccount newAccount = new BankAccount(accountNumber);
                System.out.println("newaccount: "+newAccount);
            newAccount.setNumOfDeposits(1);
            newAccount.setCurrentBalance(amount);
            sm.addAccount(accountNumber, newAccount);
            
            numOfDeposits = newAccount.getNumOfDeposits();
            currentBalance = newAccount.getCurrentBalance();
            System.out.println("Transaction complete");
            notify();
        }else{

            System.out.println("Going to deposit" +amount);
            this.currentBalance += amount;
            System.out.println("Available Balance " +this.currentBalance);
            numOfDeposits++;
            sm.addAccount(accountNumber, this);
            System.out.println("Transaction complete");
            notify();
        }
        
        
//                })
//        if(!sm.accounts(accountNumber)){
//            createAccount(accountNumber, amount);
//        }
//        for (int i = 0; i < accounts.size(); i++) {
//            System.out.println(accounts.get(i));
//        }
//        for(BankAccount accountNumber : accounts){
//            System.out.println("testing iterator" +accountNumber);
//        }
//        
//        return currentBalance;
//    }
//        for (BankAccount b : accounts.getAccountNumber()) {
//            if (c.getId() == customerID) {
//                foundCustomer = c;
//            }
//        
//        if(true){
//        accounts.keySet().contains(accountNumber);
//        }
//        if(!accounts.contains(accounts.getAccountNumber(accountNumber))){
//            createAccount(accountNumber, amount);
//        }
 
        //check account exists. if accountNumber does not exist new account made 

        checkTransactionLimit();

        return currentBalance;
    }
    
    public synchronized double withdraw(int accountNumber, double amount){
        if(sm.getAccount(accountNumber)== false){
            System.out.println("No such account exists");
        }else if (sm.getAccount(accountNumber) && amount>currentBalance){
            System.out.println("Insufficient funds");
            try{
                wait();
            }catch(Exception e){
                System.out.println("Interruption occured");
            }
            this.currentBalance = currentBalance - amount;
            this.numOfWithdrawals++;
        }
        else{
            this.currentBalance = currentBalance - amount;
            this.numOfWithdrawals++;
        }
//        //check balance sufficent, if not no change to balance, generate error
//        //decreses balance by amount
//        
        checkTransactionLimit();

        return currentBalance;
    }
    
    //**************METHODS RLEATED TO OBSERVER DESIGN PATTERN****************
    public void checkTransactionLimit(){
        if(numOfDeposits + numOfWithdrawals >1){
            setFreeTransactionLimit();
        }
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observers.indexOf(observer));

    }

    @Override
    public void notifyObserver() {
        observers.forEach((observer) -> {
            observer.update(freeTransactionLimit);
        });
    }
    
    public String getFreeTransactionLimit(){
        return freeTransactionLimit;
    }
    
    public void setFreeTransactionLimit(){
        this.freeTransactionLimit = "Transaction limit reached, all transactions will now be charged";
        notifyObserver();
    }
    
    
}
