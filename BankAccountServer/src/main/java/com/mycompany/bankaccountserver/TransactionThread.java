/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankaccountserver;




import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Bebhin
 */
public class TransactionThread extends Thread{
    
    
    private Socket connSocket;
    private ServerMain serverMain;
    private PrintWriter serverOutput;
    private BufferedReader clientInput;
    private DataOutputStream clientOutput;
    private ObjectOutputStream objectOutput;
    
    private Integer accountNumber;
    private String transactionType;
    private Double amount;
    
    BankAccount ba = new BankAccount();
    
    //User u = new User();
    
    public TransactionThread(Socket connSocket, ServerMain serverMain){
        this.connSocket = connSocket;
        this.serverMain = serverMain;
    }

    public void run(){
        try{
            
        //will need objectinout stream to take in file
        // if File (false) create new BankAccount object


        InputStream input = connSocket.getInputStream();
        BufferedReader serverInput = new BufferedReader(new InputStreamReader(input));
     
        OutputStream output = connSocket.getOutputStream();
        serverOutput = new PrintWriter(output, true);
        


 
        String newTransaction = serverInput.readLine();
        //serverMain.addUserName(userName);
        
        //printUsers();
        
        String serverMessage = "New transaction requested";
        //sends a msg to all the users, excluing this current thread
        //serverMain.broadcast(serverMessage, this);
        
        String clientRequest;
        

        
        do{
            clientRequest = serverInput.readLine();
            serverMessage = clientRequest;
            serverOutput.println(serverMessage);
            ba.deposit(12);
            ba.withdraw(12);
            
        }while(!clientRequest.equals("<exit room>"));
        
        //serverMain.removeUser(userName, this);
        connSocket.close();
        
        //serverMessage = userName + "has quitted.";
        serverOutput.println(serverMessage);

        }catch (IOException e){
                        System.out.println("IO Exception");
                    }
    }//end of run  
    
//    void printUsers(){
//        if(serverMain.hasUsers()){
//            serverOutput.println("Connected users:" + serverMain.getUserName());
//        }else{
//            serverOutput.println("No other users connected");
//            
//        }
//    }//end of print users
    
    void sendMessage(String message){
        serverOutput.println(message);
    }
    

}
