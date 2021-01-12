package com.mycompany.bankaccountclient;

import java.util.Scanner;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientMain {
    
    private String hostname;
    private int port;
    private Integer accountNumber;
    private String transactionType;
    private Double amount;

    public ClientMain(String hostname, int port){
        this.hostname = hostname;
        this.port = port;
    }

    public ClientMain(String hostname, int port, int accountNumber, String transactionType, double amount) {
        this.hostname = hostname;
        this.port = port;
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.amount = amount;
    }
         
    public void execute(){
        try{       
            System.out.println("Connecting to server on port " + port);
            Socket connSocket = new Socket(hostname, port);
            System.out.println("Connected to the bank");

            new TransactionThread(connSocket, this,accountNumber, transactionType, amount ).start();

        }catch (UnknownHostException e){
            System.out.println("Server not found at port" +port);
        }
        catch(IOException e){
            System.out.println("IO Exception");
        }
    }//end of exceute method
 
    public static void main(String[] args) {
        
        String hostname = "localhost";
        int port = 9000;
        Integer accountNumber;
        String transactionType;
        Double amount;

    //input from customer
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter your account number: ");
        accountNumber = scan.nextInt();
        System.out.println("Enter transaction type: ");
        transactionType = scan.next();
        System.out.println("Enter amount: ");
        amount = scan.nextDouble();
                   
    //creating connection to server by calling execute method
        ClientMain client = new ClientMain(hostname, port, accountNumber, transactionType, amount );
        client.execute();

    }//end of main
 
}//end of class

