//package com.mycompany.bankaccountclient;
//
//import java.io.BufferedReader;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.PrintWriter;
//import java.net.Socket;
//import java.util.Scanner;
//
///**
// *
// * @author Bebhin
// */
//public class ToBank extends Thread {
//    
//
//    private PrintWriter toBank;
//    private Socket socket;
//    private ClientMain clientMain;
//    
//    public ToBank(Socket socket, ClientMain clientMain){
//        
//        this.socket= socket;
//        this.clientMain = clientMain;
//        
//        try{
//           
//            OutputStream serverOutput = socket.getOutputStream();
//            toBank = new PrintWriter(serverOutput, true);
//            
//        }catch(IOException e){
//            System.out.println("Error with output stream to server" +e.getMessage());
//        }
//    }
//    
//    public void run(){
//        
//                
//        BufferedReader userEntry = new BufferedReader(new InputStreamReader(System.in));
//        String userName = null;
//         
//        System.out.println("Enter your name");
//        try{
//            userName = userEntry.readLine();
//            clientMain.setUserName(userName);
//            toBank.println(userName);
//         }catch(IOException e){
//             System.out.println("IO Exception when taking username");
//         }
//
//        String message = null;
//        
//        do{
//            //"[" +userName + "]: " - this was added for the console example but doenst work for 
//            // bufferedreader reader, need to come back to this
//            try{
//                message=userEntry.readLine();
//            toBank.println(message);
//            }catch(IOException e){
//                System.out.println("IO Exception writing to the server");
//            }
//        }while (!message.equals("<exit room>"));
//            
//         
//        
//    }
//    
//
//}
//
