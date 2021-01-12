package com.mycompany.bankaccountclient;

import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class TransactionThread extends Thread{
    
    private Integer accountNumber;
    private String transactionType;
    private Double amount;
    
    private BufferedReader serverInput;
    private Socket connSocket;
    private ClientMain clientMain;
    private DataOutputStream serverOutput;

    public TransactionThread(Socket connSocket, ClientMain main, int accountNum, String type, double am) {
        this.connSocket = connSocket;
        this.clientMain = main;
        this.accountNumber = accountNum;
        this.transactionType = type;
        this.amount = am;
    //opens the input and output stream when the thread is called within the execute method
        try{
            serverInput = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));
            serverOutput = new DataOutputStream(connSocket.getOutputStream());
        }catch(IOException e){
            System.out.println("Error getting input stream " +e.getMessage());
        }
    }

    @Override
    public void run(){
        try{
            //sending data to server
            System.out.println("Connection established");
            String transactionRequest = (accountNumber + "," +transactionType + "," +amount + "\n");
            System.out.println(transactionRequest);
            serverOutput.writeBytes(transactionRequest);
            System.out.println("Waiting for reply");
            
            //receiving response from server
            for (int i = 0; i <= 4; i++) {
                try{
                    String response = serverInput.readLine();
                        //if statement prevents null being printed when withdraw results in error msg
                        if (response == null){
                        break;
                        }
                    System.out.println("\n" + response); 

                    }catch(IOException e){
                        System.out.println("Error with input from server" + e.getMessage());
                        break;
                    }
                }
            //closing connection
            serverOutput.close();
            serverInput.close();
            connSocket.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
