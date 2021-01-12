package com.mycompany.bankaccountclient;

import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author Bebhin
 */
public class TransactionThread extends Thread{
    
    private BufferedReader serverInput;
    private Socket connSocket;
    private ClientMain clientMain;
    
    private Integer accountNumber;
    private String transactionType;
    private Double amount;
    DataOutputStream serverOutput;


    public TransactionThread(Socket connSocket, ClientMain main, int accountNum, String type, double am) {
        this.connSocket = connSocket;
        this.clientMain = main;
        this.accountNumber = accountNum;
        this.transactionType = type;
        this.amount = am;

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
            System.out.println("Connection established");
            String transactionRequest = (accountNumber + "," +transactionType + "," +amount + "\n");
            System.out.println(transactionRequest);
            serverOutput.writeBytes(transactionRequest);
            System.out.println("Waiting for reply");
            
            //while(true) not working here, need to find more sustainable way of reciving on a loop
            for (int i = 0; i <4; i++) {
                try{
                        String response = serverInput.readLine();
                        System.out.println("\n" + response);
                        
                    }catch(IOException e){
                        System.out.println("Error with input from server" + e.getMessage());
                        break;
                    }
                }
            
            

            serverOutput.close();
            serverInput.close();
            connSocket.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
//    public void run(){
//        while(true){
//            try{
//                String response = fromBank.readLine();
//                System.out.println("\n" + response);
//                
//                if(clientMain.getUserName() != null){
//                    System.out.println("[" +clientMain.getUserName() + "]: ");
//                }
//                break;
//            }catch(IOException e){
//                System.out.println("Error with input from server" + e.getMessage());
//
//            }
//        }
//    }
    
//    ObjectInputStream serverObjectInput=null;
//    String serverResponse;
//    String messageResponse;
//    String userUpdate;
//    
//      
//        serverObjectInput = new ObjectInputStream(connSocket.getInputStream());
//        
//            serverResponse = serverInput.readLine(); 
//            userUpdate = serverInput.readLine();  
//            System.out.println("Msg from chat room: "+ serverResponse );
//            System.out.println("Users logged in: "+ userUpdate +"\n" );
//            
//                           messageResponse = serverInput.readLine();
}
