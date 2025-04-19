package org.banking;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

class Accounts {
    private final ArrayList<BankAccount> storedAccounts;

    public Accounts() {
        storedAccounts = new ArrayList<BankAccount>();

    }

    public Accounts load() {
        File f = new File("accounts.json");
        if (f.exists() && !f.isDirectory()) {
            ObjectMapper mapper = new ObjectMapper();
            try {

                return mapper.readValue(f, Accounts.class);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public void add(BankAccount toSave) {
        storedAccounts.add(toSave);
        save();
    }

    public BankAccount findByUserName(String userName){
       return storedAccounts.stream().filter(acc->userName.equals(acc.getUserName())).findFirst().orElse(null);
    }

    public void save() {
        try {

            ObjectMapper mapper = new ObjectMapper();
            File file=new File("accounts.json");
            mapper.writerWithDefaultPrettyPrinter().writeValue(file,this);
            System.out.println("accounts JSON file saved successfully!");

        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
    }



    public ArrayList<BankAccount> getStoredAccounts() {
        return storedAccounts;
    }
}
