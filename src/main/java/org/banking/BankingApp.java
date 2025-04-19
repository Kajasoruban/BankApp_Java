package org.banking;

import java.util.Scanner;


public class BankingApp{

    static Accounts accountStorage = new Accounts();
    static BankAccount currentBankAccount=null;

    public static void showAccounts() {
        if (!accountStorage.getStoredAccounts().isEmpty()) {

            for (BankAccount acc : accountStorage.getStoredAccounts()) {
                System.out.println(acc);
            }
        } else {
            System.out.println("\n\nNo Stored Accounts Found\n\n");
        }
    }

    public static void main(String[] args){

        Scanner scanner=new Scanner(System.in);

        Accounts value=accountStorage.load();
        if(value != null){
        accountStorage=value;
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

                    currentBankAccount=new BankAccount();

                    accountStorage.add(currentBankAccount);

                    currentBankAccount=null;

                    System.out.print("\nPress Enter to continue...");
                    scanner.nextLine();
                    scanner.nextLine();




                }break;
                case 'B':{
                    if (accountStorage.getStoredAccounts().isEmpty()) {
                        System.out.println("\n\nYou don't have an existing account\n\n\n\n");
                        continue;
                    }
                    System.out.print("Enter Your User Name :");
                    currentBankAccount= accountStorage.findByUserName(scanner.next());

                    if(currentBankAccount==null){
                        System.out.println("\n\nNo account found with that user name..\n\n");
                    }else{

                    currentBankAccount.logIn();

                    if(currentBankAccount.isLoggedIn()){
                        currentBankAccount.openMenu();
                    }

                    }

                }break;
                case 'C':{
                    showAccounts();
                }break;
                case 'E':{

                    currentBankAccount=null;
                    System.out.println("Exited");
                }break;
                default:
                    System.out.println("\n\nInvalid option\n\n");
            }



        } while (option!='E');



    }
}