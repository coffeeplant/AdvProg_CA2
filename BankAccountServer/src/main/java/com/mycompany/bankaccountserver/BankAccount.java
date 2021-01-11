/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankaccountserver;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bebhin
 */
public class BankAccount {
    
    private List<BankAccount> accounts = new ArrayList();
    
    int accountNumber;
    private int numOfDeposits = 0;
    private int numOfWithdrawals = 0;
    private double currentBalance;
    private double amount;

    public BankAccount() {
    }

    public BankAccount(int accountNumber, double amount) {
        this.accountNumber = accountNumber;
        this.amount = amount;

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
        accounts.add(1234);
        new BankAccount();
        //should this happen here or in thread?
    }

    public double deposit(double amount){
        //check account exists. if accountNumber does not exist new account made 
        //increases balance by amount

        return currentBalance;
    }
    
    public double withdraw(double amount){
        //check accoutn exists: if not generate error
        //check balance sufficent, if not no change to balance, generate error
        //decreses balance by amount
        //numOfWithdrawals++;


        return currentBalance;
    }
    
}
