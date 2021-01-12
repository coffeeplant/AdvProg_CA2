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
public abstract class Observer {
    
    protected Account bankAccount = null;
    
    abstract public void update(String freeTransactionLimit);
    
}
