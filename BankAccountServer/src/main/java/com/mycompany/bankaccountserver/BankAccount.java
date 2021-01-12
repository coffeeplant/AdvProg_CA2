/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankaccountserver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Bebhin
 */
public class BankAccount {
    
    //private List<BankAccount> accounts = new ArrayList();
    //private Set<BankAccount> accounts = new HashSet<>();

    
    private int accountNumber;
    private int numOfDeposits = 0;
    private int numOfWithdrawals = 0;
    private double currentBalance;
    private double amount;
    
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
    
    public void createAccount(){
        BankAccount newAccount = new BankAccount(accountNumber);
        newAccount.setNumOfDeposits(1);
        newAccount.setCurrentBalance(amount);
        sm.addAccount(accountNumber, newAccount);
        //should this happen here or in thread?
    }

    //THIS NEEDS TIDYING UP, DOUBLING UP ON CODE AT THE MOMENT
    public double deposit(){
        System.out.println("is deposit method running");
        System.out.println("Amount: " +amount);
        try{
            if(sm.getAccount(accountNumber)== false){
            createAccount();
            System.out.println("Creating new account with a/c number: " +accountNumber);
            sm.addAccount(accountNumber, this);
            System.out.println("Going to deposit" +amount);
            this.currentBalance = currentBalance +amount;
            System.out.println("Available Balance " +this.currentBalance);
            numOfDeposits++;
        }else{
            sm.addAccount(accountNumber, this);
            System.out.println("Going to deposit" +amount);
            this.currentBalance += amount;
            System.out.println("Available Balance " +this.currentBalance);
            numOfDeposits++;
        }
        }catch(NullPointerException e){
            System.out.println("null pointer in deposit method");
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

 

        return currentBalance;
    }
    
    public double withdraw(double amount){
        //check accoutn exists: if not generate error
        //check balance sufficent, if not no change to balance, generate error
        //decreses balance by amount
        numOfWithdrawals++;


        return currentBalance;
    }
    
}
