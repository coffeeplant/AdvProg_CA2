package com.mycompany.bankaccountserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerMain {
     
    private int port;
    private Map<Integer, BankAccount> accounts = new ConcurrentHashMap<>();

    public ServerMain(){

    }

    public ServerMain(int port){
        this.port = port;
    }

    public void execute(){
    try{
        //opening server socket
        System.out.println("Waiting for a connection on port " +port+ ".");
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Bank server connected on port " +port);
        while(true){
            //client connects 
            Socket connSocket = serverSocket.accept();
            System.out.println("New user connected\n");
            //new transaction thread started
            TransactionThread newAccountThread = new TransactionThread(connSocket, this);
            newAccountThread.start();
        }

    }catch(IOException e){
        System.out.println("Error in the server: " +e.getMessage());
    }
}//end of execute
    
    public static void main(String[] args) {

//**SAMPLE USE OF TRANSACTION MONITOR USING OBSERVER DESIGN PATTERN, METHOD IN USE IN 
// WITHDRAW AND DEPOSIT METHODS WITHIN BANKACCOUNT       
    BankAccount bankAccount = new BankAccount();
    TransactionMonitor tMonitor = new TransactionMonitor(bankAccount);

    System.out.println("Updating transaction monitor:");
    bankAccount.setFreeTransactionLimit();
    
    
    
    int port = 9000;

//** Running method to open ServerSocket
    ServerMain serverMain = new ServerMain(port);
    serverMain.execute();
           
    }//end of main

    
//** Issue with hashmap could not be resolved, contents not saving to map, getAccoutn always returns false
    //adds account to the hashmap of accounts
    void addAccount(int acNum, BankAccount account){
        accounts.put(acNum, account);
    }    
    //finds if an accoutn already exists
    boolean getAccount(int acNum){
        if(accounts.containsKey(acNum)){
            return true;
        }else{
            return false;
            
        }
    }     

}//end of class       
    






