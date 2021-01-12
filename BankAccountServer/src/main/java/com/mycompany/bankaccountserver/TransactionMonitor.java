package com.mycompany.bankaccountserver;

public class TransactionMonitor extends Observer{
    
    //a concreate observer class to motnior when an account hodler has reached their limit of free
    // transactions, update sent when limit of ten reached. 

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
