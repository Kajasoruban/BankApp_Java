package org.banking;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class Accounts{
    private ArrayList<BankAccount> storedAccounts;

    public Accounts(){
        storedAccounts=new ArrayList<BankAccount>();






    }

    public Accounts load(){
        System.out.println("accounts loaded start");
        File f = new File("accounts.json");
        if(f.exists() && !f.isDirectory()) {
            ObjectMapper mapper =new ObjectMapper();
            try {
                Accounts retrieved =mapper.readValue(f,  Accounts.class);



                for(BankAccount acc : retrieved.storedAccounts){
                    System.out.println(acc);
                }
                System.out.println("accounts loaded end");
                return retrieved;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public void add(BankAccount toSave){
        storedAccounts.add(toSave);
    }

    public ArrayList<BankAccount> getStoredAccounts() {
        return storedAccounts;
    }
}

class BankAccount{
    String userName;
    @JsonProperty("nic")
    final private long NIC;
    @JsonProperty("accountNumber")
    final private long AccountNumber;
    private int pin;
    double balance;
    @JsonProperty("login")
    private boolean isLogin=false;
    Scanner scanner=new Scanner(System.in);

    @JsonCreator
    public BankAccount(
        @JsonProperty("userName") String userName,
        @JsonProperty("nic") long NIC,
        @JsonProperty("accountNumber") long AccountNumber,
        @JsonProperty("pin") int pin,
        @JsonProperty("balance") double balance,
        @JsonProperty("login") boolean isLogin
    ) {
        this.userName = userName;
        this.NIC = NIC;
        this.AccountNumber = AccountNumber;
        this.pin = pin;
        this.balance = balance;
        this.isLogin = isLogin;
    }

    public BankAccount(){
        System.out.print("Enter your name : ");
        userName=scanner.next();

        System.out.print("Enter your NIC no : ");
        NIC=scanner.nextLong();

        AccountNumber=(long) Integer.parseInt(String.format("%08d", (int) (Math.random() * 100000000)));

        balance=0;

        pin= Integer.parseInt(String.format("%04d", (int) (Math.random() * 10000)));
        System.out.print("\n\nYour bank account has been created\nYour account number is "+AccountNumber+"\nYour pin number is "+pin+"\nuse this pin number to access your account\n\n\n");
    }

    public void checkBalance(){
        System.out.println("\n\nYour Account Balance  : "+balance+"\n\n");
    }

    public void deposit(double amount){
        if(amount>0){
            balance += amount;
            System.out.println("\n\ndeposited amount : "+amount);
        }else{
            System.out.println("Invalid");
        }
    }


    public void withdraw(double amount){
        if(amount>0){

            if(balance-amount > 0){

                balance -= amount;
                System.out.println("\n\nwithdrawed amount : "+amount);
            }else{
                System.out.println("\n\nYou don't have enough balance\n\n");
            }

        }else{
            System.out.println("Invalid");
        }
    }

    public void openMenu(){

        System.out.println("Wel come! "+ this.userName+"\n");
        char option='\0';
        do {
            System.out.println("---------------------------------------|");
            System.out.print("A. Balance Enquiry. \nB. Withdraw.\nC. Deposit. \nE. Exit\n");
            System.out.println("Choose one of the option :");
            System.out.println("---------------------------------------|");
            option=scanner.next().charAt(0);
            switch (option) {
                case 'A':{


                    checkBalance();
                    System.out.print("\nPress Enter to continue...");
                    scanner.nextLine();
                    scanner.nextLine();

                }break;
                case 'B':{
                    System.out.print("Enter the amount to withdraw :");
                    withdraw(scanner.nextDouble());
                    System.out.print("\nPress Enter to continue...");
                    scanner.nextLine();
                    scanner.nextLine();

                }break;
                case 'C':{

                    System.out.print("Enter the amount to deposit :");
                    deposit(scanner.nextDouble());
                    System.out.print("\nPress Enter to continue...");
                    scanner.nextLine();
                    scanner.nextLine();

                }break;
                case 'E':{

                    logOut();
                    System.out.println("\nExited...\n");

                }break;
                default:
                    System.out.println("\n\nInvalid option\n\n");
            }

            // System.out.print("Press Enter to continue...");
            // scanner.nextLine();
            // scanner.nextLine();

        } while (option!='E');

    }

    public void logIn(){
        System.out.print("Enter your pin number : ");
        int value=scanner.nextInt();
        if(pin==value){
            isLogin=true;
            System.out.println("\n\nLogged in\n\n");
        }else{
            System.out.println("\n\nWrong pin\n\n");
        }

    }

    public void logOut(){
        isLogin=false;
        System.out.println("\n\nLogged out\n\n");
    }

    @JsonIgnore
    public boolean isLoggedIn(){
        return isLogin;
    }

    public void save(){
        try {

            BankingApp.storedAccounts.add(this);
            FileOutputStream fos = new FileOutputStream("accounts.json");
            ObjectMapper mapper =new ObjectMapper();
            String jsonStr=mapper.writerWithDefaultPrettyPrinter().writeValueAsString(BankingApp.storedAccounts);
            fos.write(jsonStr.getBytes());
            System.out.println("accounts JSON file created successfully!");

            fos.close();

        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
    }


    public String getUserName() {
        return userName;
    }

    public long getNIC() {
        return NIC;
    }

    public long getAccountNumber() {
        return AccountNumber;
    }

    public int getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public boolean isLogin() {
        return isLogin;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "userName='" + userName + '\'' +
                ", balance=" + balance +
                '}';
    }
}


public class BankingApp{

    static Accounts storedAccounts = new Accounts();

    public static void showAccounts(){
        for(BankAccount acc : storedAccounts.getStoredAccounts()){
            System.out.println(acc);
        }
    }

    public static void main(String[] args){
        BankAccount bankAccount=null;
        Scanner scanner=new Scanner(System.in);

        Accounts value=storedAccounts.load();
        if(value != null){
        storedAccounts=value;
        }

        char option='\0';
        do {
            System.out.println("---------------------------------------|");
            System.out.print("A. Create an account. \nB. Login into existing acccount.\nC. Show Accounts. \nE. Exit\n");
            System.out.println("Choose one of the option :");
            System.out.println("---------------------------------------|");
            option=scanner.next().charAt(0);
            switch (option) {
                case 'A':{

                    bankAccount=new BankAccount();

                    bankAccount.save();

                    System.out.print("\nPress Enter to continue...");
                    scanner.nextLine();
                    scanner.nextLine();




                }break;
                case 'B':{
                    if(bankAccount==null){
                        System.out.println("You don't have an existing account\n\n\n\n");
                        continue;
                    }
                    bankAccount.logIn();
                    if(bankAccount.isLoggedIn()){
                        bankAccount.openMenu();
                    }

                }break;
                case 'C':{
                    showAccounts();
                }break;
                case 'E':{
                    if(bankAccount!=null){
                        bankAccount.logOut();
                    }

                    System.out.println("Exited");
                }break;
                default:
                    System.out.println("\n\nInvalid option\n\n");
            }

            // System.out.print("Press Enter to continue...");
            // scanner.nextLine();
            // scanner.nextLine();
            // System.out.println("continue...");

        } while (option!='E');



    }
}