package com.mycompany.bankaccountserver;

public abstract class Observer {
    
    //observer abstract class containign the update method which can be called upon by mutliple 
    //observers, one observer created for this assignment
    
    protected Account bankAccount = null;
    
    abstract public void update(String freeTransactionLimit);
    
}
