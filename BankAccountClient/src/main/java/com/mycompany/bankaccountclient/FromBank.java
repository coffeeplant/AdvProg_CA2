package com.mycompany.bankaccountclient;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author Bebhin
 */
public class FromBank extends Thread{
    
    private BufferedReader fromBank;
    private Socket connSocket;
    private ClientMain clientMain;

    public FromBank(Socket connSocket, ClientMain main) {
        this.connSocket = connSocket;
        this.clientMain = main;

        try{
            InputStream input = connSocket.getInputStream();
            fromBank = new BufferedReader(new InputStreamReader(input));
        }catch(IOException e){
            System.out.println("Error getting input stream " +e.getMessage());
        }
        
    }
    
    public void run(){
        while(true){
            try{
                String response = fromBank.readLine();
                System.out.println("\n" + response);
                
                if(clientMain.getUserName() != null){
                    System.out.println("[" +clientMain.getUserName() + "]: ");
                }
                break;
            }catch(IOException e){
                System.out.println("Error with input from server" + e.getMessage());

            }
        }
    }
    
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
