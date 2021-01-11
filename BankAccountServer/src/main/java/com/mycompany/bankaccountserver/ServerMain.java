/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankaccountserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMain {
     
        private int port;
        //private Set<String> accountNo = new HashSet<>();
        //private List<TransactionThread> transactions = new ArrayList();
        
        public ServerMain(int port){
            this.port = port;
        }
        
        public void execute(){
        try{
            System.out.println("Waiting for a connection on port " +port+ ".");
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Chat server connected on port " +port);
            while(true){
                Socket connSocket = serverSocket.accept();
                System.out.println("New user connected");
            
            // if File (false) create new BankAccount object
                
            TransactionThread newAccountThread = new TransactionThread(connSocket, this);
            //transactions.add(newAccountThread);
            newAccountThread.start();
            }
                
        }catch(IOException e){
            System.out.println("Error in the server: " +e.getMessage());
        }
    }//end of execute
    
    public static void main(String[] args) {
        
        int port = 9000;
        
        ServerMain serverMain = new ServerMain(port);
        serverMain.execute();
        
    }//end of main
    
//    void broadcast(String message, BankAccountThread excludeUser){
//        for (BankAccountThread aUser : accountThreads) {
//            if(aUser != excludeUser) {
//                aUser.sendMessage(message);
//            }
//        }
//    }//end of broadcast
    
//    void addUserName(String userName){
//        userNames.add(userName);
//    }    
    
//    void removeUser(String userName, BankAccountThread aUser){
//        boolean present = userNames.remove(userName);
//        if(present){
//            accountThreads.remove(aUser);
//            System.out.println("The user " + userName + " quitted");
//        }    
//    }
    
//    Set<String> getUserName(){
//        return this.userNames;
//    }
    
//    boolean hasUsers(){
//        return !this.userNames.isEmpty();
//    }    

}//end of class       
    






