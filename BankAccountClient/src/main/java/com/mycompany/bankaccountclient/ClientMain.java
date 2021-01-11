/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankaccountclient;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


public class ClientMain {
    
        private String hostname;
        private int port;
        private String userName;
        
        // Read user input

    
        public ClientMain(String hostname, int port){
            this.hostname = hostname;
            this.port = port;
        }
        
        public void execute(){
            try{       
                System.out.println("Connecting to server on port " + port);
                Socket connSocket = new Socket(hostname, port);
                System.out.println("Connected to the bank");
                
                //new FromBank(connSocket, this).start();
                //new ToBank(connSocket, this).start();
                
            }catch (UnknownHostException e){
                System.out.println("Server not found at port" +port);
            }
            catch(IOException e){
                System.out.println("IO Exception");
            }
        }//end of exceute method
        
        void setUserName(String userName){
            this.userName = userName;
        }
        
        String getUserName(){
            return this.userName;
        }
        
    public static void main(String[] args) {
        
        String hostname = "localhost";
        int port = 9000;
        
        Integer accountNumber;
        String transactionType;
        Double amount;

        BufferedReader serverInput;
        DataOutputStream serverOutput;
        Socket connSocket = null;
        
    //input from customer
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter your account number: ");
        accountNumber = scan.nextInt();
        System.out.print("Enter transaction type: ");
        transactionType = scan.next();
        System.out.print("Enter amount: ");
        amount = scan.nextDouble();
              
 //        while (!input.hasNextDouble()) {
//        System.out.println("Value is not a Double");
        
    //creating connection to server
        ClientMain client = new ClientMain(hostname, port);
        client.execute();
        
        try{
    //opening streams over socket
            serverInput = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));
            serverOutput = new DataOutputStream(connSocket.getOutputStream());
            System.out.println("Connection established");
            String transactionRequest = (accountNumber + " " +transactionType + " " +amount);
            serverOutput.writeBytes(transactionRequest);
            System.out.println("Waiting for reply");
            while(true){
                try{
                    String response = serverInput.readLine();
                    System.out.println("\n" + response);
                    break;
                }catch(IOException e){
                    System.out.println("Error with input from server" + e.getMessage());

                }
            }
            serverOutput.close();
            serverInput.close();
            connSocket.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }//end of main


    
}//end of class

