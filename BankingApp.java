
import java.util.Scanner;



class BankAccount{
    double balance;
    String userName;
    final private long NIC;
    final private long AccountNumber;
    private int pin;
    private boolean isLogin=false;
    Scanner scanner=new Scanner(System.in);

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

    public boolean isLoggedIn(){
        return isLogin;
    }
}


public class BankingApp{
    


    public static void main(String[] args){
        BankAccount bankAccount=null;
        Scanner scanner=new Scanner(System.in);
       
        char option='\0';
        do { 
            System.out.println("---------------------------------------|");
            System.out.print("A. Create an account. \nB. Login into existing acccount. \nE. Exit\n");
            System.out.println("Choose one of the option :");
            System.out.println("---------------------------------------|");
            option=scanner.next().charAt(0);
            switch (option) {
                case 'A':{

                    bankAccount=new BankAccount();

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