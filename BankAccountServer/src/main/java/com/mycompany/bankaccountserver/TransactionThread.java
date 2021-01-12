/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankaccountserver;




import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
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
    String[] sp;
    
    BankAccount bankAccount;
    
    public TransactionThread(){
        this.bankAccount = new BankAccount();
    }
    
    public TransactionThread(Socket cs, ServerMain sm){
        this.connSocket = cs;
        this.serverMain = sm;
        this.bankAccount = new BankAccount();
         
    }
    
    public BankAccount fileAccountCheck(int accountNumber, double amount){

        File file = new File("bankaccount.csv");
            if (file.exists()){
                try{
                    FileInputStream fileIn = new FileInputStream(file);
                    ObjectInputStream accountIn = new ObjectInputStream(fileIn);
                    bankAccount = (BankAccount) accountIn.readObject();
                    return bankAccount;
                }catch(FileNotFoundException e){
                   System.out.println("File not found");
                }catch(IOException e){
                   System.out.println(e.getMessage());
                }catch(Exception e){
                   System.out.println("Exception");
                }
           }else{
                bankAccount = new BankAccount(accountNumber, amount);
           }
        return bankAccount;
    }

    public void run(){

        try{

    //        InputStream input = this.connSocket.getInputStream();
    //        BufferedReader serverInput = new BufferedReader(new InputStreamReader(input));
            clientInput = new BufferedReader(new InputStreamReader(this.connSocket.getInputStream()));

            OutputStream output = connSocket.getOutputStream();
            serverOutput = new PrintWriter(output, true);
              //  System.out.println("after streams made");

            //this works for now but may be way to pass over and user object instead
//CLIENT INPUT
            String clientRequest = clientInput.readLine();
      //          System.out.println("TEsting clientRequest: " +clientRequest);
            sp = clientRequest.split(",");
     //           System.out.println("sp[0]:" +sp[0]);
            accountNumber = Integer.parseInt(sp[0]);
                System.out.println("accountNumber: " +accountNumber);
            transactionType = (sp[1]);
                System.out.println("transactionType: " +transactionType);
            amount = Double.parseDouble(sp[2]);
                System.out.println("amount: " +amount);
                
                
            TransactionThread t = new TransactionThread();
            BankAccount ba = t.fileAccountCheck(accountNumber, amount);
//PASSING TO DEPOSIT/WITHDRAW
                if(transactionType.equalsIgnoreCase("DEPOSIT")){
                    ba.deposit(accountNumber, amount);
                    serverOutput.println("accountNumber: " + ba.getAccountNumber());
                    serverOutput.println("Number of Deposits: " +ba.getNumOfDeposits());
                    System.out.println("Number of Deposits: " +ba.getNumOfDeposits());
                    serverOutput.println("Number of Withdrawals: " +ba.getNumOfWithdrawals());
                    System.out.println("Number of Withdrawals: " +ba.getNumOfWithdrawals());
                    serverOutput.println("Current Balance: " +ba.getCurrentBalance()+ "\n");
                    System.out.println("Current Balance: " +ba.getCurrentBalance()+ "\n");
                }else if (transactionType.equalsIgnoreCase("WITHDRAW")){
                    String withdrawString = ba.withdraw(1, 10);
                        try{
                            double test = Double.parseDouble(withdrawString);
                            serverOutput.println("accountNumber: " + ba.getAccountNumber());
                            System.out.println("Sent TEST: accountNumber:" + ba.getAccountNumber());
                            serverOutput.println("Number of Deposits: " +ba.getNumOfDeposits());
                            System.out.println("Number of Deposits: " +ba.getNumOfDeposits());
                            serverOutput.println("Number of Withdrawals: " +ba.getNumOfWithdrawals());
                            System.out.println("Number of Withdrawals: " +ba.getNumOfWithdrawals());
                            serverOutput.println("Current Balance: " +ba.getCurrentBalance()+ "\n");
                            System.out.println("Current Balance: " +ba.getCurrentBalance()+ "\n");
                        }catch( NumberFormatException e){
                            serverOutput.println(withdrawString +"\n");
                            
                        }
                } 

            //serverMain.removeUser(userName, this);
            clientInput.close();
            serverOutput.close();
            connSocket.close();

            //serverMessage = userName + "has quitted.";
            //serverOutput.println(serverMessage);

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
    
//    void sendMessage(String message){
//        serverOutput.println(message);
//    }
    

        
    

}
