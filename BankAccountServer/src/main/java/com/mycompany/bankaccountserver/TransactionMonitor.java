/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankaccountserver;

/**
 *
 * @author Bebhin
 */
public class TransactionMonitor extends Observer{

    private String freeTransactionLimit;
    private final int monitorId;
    
    public static int observerId = 0;
    
    public TransactionMonitor(Account bankAccount){
        this.bankAccount = bankAccount;
        this.monitorId = ++observerId;
        bankAccount.addObserver(this);
    }
       
    @Override
    public void update(String freeTransactionLimit) {
       this.freeTransactionLimit = freeTransactionLimit;
       setMonitor();
    
    }
    
    private void setMonitor(){
        System.out.println("Monitor " +monitorId+ " started with message: " +this.freeTransactionLimit);
    }
    
    public void unsubscribe(){
        this.bankAccount.removeObserver(this);
    }
    
    
}
