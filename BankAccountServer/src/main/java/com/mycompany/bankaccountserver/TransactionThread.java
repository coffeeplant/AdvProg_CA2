package com.mycompany.bankaccountserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class TransactionThread extends Thread{
    
    private Integer accountNumber;
    private String transactionType;
    private Double amount;
    String[] sp;
    
    private Socket connSocket;
    private ServerMain serverMain;
    private BankAccount bankAccount;
    
    private PrintWriter serverOutput;
    private BufferedReader clientInput;
    private DataOutputStream clientOutput;
    private ObjectOutputStream objectOutput;
    
    public TransactionThread(){
        this.bankAccount = new BankAccount();
    }
    
    public TransactionThread(Socket cs, ServerMain sm){
        this.connSocket = cs;
        this.serverMain = sm;
        this.bankAccount = new BankAccount();
    }
    
    public BankAccount fileAccountCheck(int accountNumber, double amount){
//method to check if bank account file exists
        File file = new File("bankaccount.csv");
            if (file.exists()){
                try{
                    //file exists, reading object
                    FileInputStream fileIn = new FileInputStream(file);
                    ObjectInputStream accountIn = new ObjectInputStream(fileIn);
                    //creating new BankAccount instance from file contents
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
                //file does not exist, creating new bank account instance
                bankAccount = new BankAccount(accountNumber, amount);
           }
        return bankAccount;
    }

    public void run(){
    try{
        clientInput = new BufferedReader(new InputStreamReader(this.connSocket.getInputStream()));
        OutputStream output = connSocket.getOutputStream();
        serverOutput = new PrintWriter(output, true);

//CLIENT INPUT
//string received from client, split into array and parsed to correct type
        String clientRequest = clientInput.readLine();
        sp = clientRequest.split(",");
        accountNumber = Integer.parseInt(sp[0]);
            System.out.println("Received from client:\n");
            System.out.println("AccountNumber: " +accountNumber);
        transactionType = (sp[1]);
            System.out.println("TransactionType: " +transactionType);
        amount = Double.parseDouble(sp[2]);
            System.out.println("Amount: " +amount+"\n");

//calling BankAccount instance created in fileAccountCheck method
        TransactionThread t = new TransactionThread();
        BankAccount ba = t.fileAccountCheck(accountNumber, amount);
        
//PASSING TO DEPOSIT/WITHDRAW
            if(transactionType.equalsIgnoreCase("DEPOSIT")){
                ba.deposit(accountNumber, amount);
                //cannot exceed 4 lines of output to match client input loop
                serverOutput.println("accountNumber: " + ba.getAccountNumber());
                serverOutput.println("Number of Deposits: " +ba.getNumOfDeposits());
                serverOutput.println("Number of Withdrawals: " +ba.getNumOfWithdrawals());
                serverOutput.println("Current Balance: " +ba.getCurrentBalance()+ "\n");
            }else if (transactionType.equalsIgnoreCase("WITHDRAW")){
                String withdrawString = ba.withdraw(1, 10);
                    try{
                        //if the withdraw string can be parsed to a double then the 
                        //transaction was successful and the details are sent to the client
                        double test = Double.parseDouble(withdrawString);
                        //cannot exceed 4 lines of output to match client input loop
                        serverOutput.println("accountNumber: " + ba.getAccountNumber());
                        serverOutput.println("Number of Deposits: " +ba.getNumOfDeposits());
                        serverOutput.println("Number of Withdrawals: " +ba.getNumOfWithdrawals());
                        serverOutput.println("Current Balance: " +ba.getCurrentBalance()+ "\n");
                    }catch( NumberFormatException e){
                        //the transaction was not successful, the error msg is sent to the client
                        serverOutput.println(withdrawString +"\n");
                    }
            } 
//closing input/output streams and connSocket for thread, server socket remains open to receive another client
        clientInput.close();
        serverOutput.close();
        connSocket.close();

        }catch (IOException e){
            System.out.println("IO Exception");
        }
}//end of run  

}//end of class
