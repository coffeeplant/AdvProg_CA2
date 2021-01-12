package com.mycompany.bankaccountserver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BankAccount implements Serializable, Account{
        
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
//deposit method synchronized
    public synchronized double deposit(int accountNumber, double amount){
//checks to see if accounts exists in hashmap       
    if(!sm.getAccount(accountNumber)){
        //creating new account and setting variables
        System.out.println("Creating new account with a/c number: " +accountNumber +"\n");
        BankAccount newAccount = new BankAccount(accountNumber);
        newAccount.setNumOfDeposits(1);
        newAccount.setCurrentBalance(amount);
        //adding a/c to hashmap
        sm.addAccount(accountNumber, newAccount);

        numOfDeposits = 1;
        currentBalance = amount;
        System.out.println("Transaction complete");
    }else{
        this.currentBalance += amount;
        numOfDeposits++;
        sm.addAccount(accountNumber, this);
        notify();
    }
    //calling method linked to observer each time num of deposits is updated   
        checkTransactionLimit();

        return currentBalance;
    }

//withdraw method synchronized    
    public synchronized String withdraw(int accountNumber, double amount){
        if(sm.getAccount(accountNumber)== false){
            return "No such account exists";
        }else if (sm.getAccount(accountNumber) && amount>currentBalance){
            return "Insufficient funds";  
        }
        else{
            this.currentBalance = currentBalance - amount;
            this.numOfWithdrawals++;
            checkTransactionLimit();
            
            //creating string value to return
            String balance = Double.toString(this.currentBalance);
            return balance;
        }
    }
    
    //**************METHODS RELATED TO OBSERVER DESIGN PATTERN****************


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
    
    public void checkTransactionLimit(){
        if(numOfDeposits + numOfWithdrawals >10){
            setFreeTransactionLimit();
        }
    }
    
    public String getFreeTransactionLimit(){
        return freeTransactionLimit;
    }
    
    public void setFreeTransactionLimit(){
        this.freeTransactionLimit = "Transaction limit reached, all transactions will now be charged\n";
        notifyObserver();
    }
    
    
}
