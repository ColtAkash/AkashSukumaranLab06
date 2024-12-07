package com.example.akashsukumaran_lab06;

import java.util.ArrayList;
import java.util.concurrent.*;

// Account class to handle deposit and withdraw operations
class Account {
    private double balance;

    public Account(double initialBalance) {
        this.balance = initialBalance;
    }

    // Synchronized method for deposit operation
    public synchronized void deposit(double amount) {
        balance += amount;
        System.out.println("Deposit: " + amount + ", New Balance: " + balance);
    }

    // Synchronized method for withdraw operation
    public synchronized void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.println("Withdrawal: " + amount + ", New Balance: " + balance);
        } else {
            System.out.println("Insufficient funds for withdrawal");
        }
    }
}

// Transaction class implementing Runnable interface for handling deposit/withdraw operations
class Transaction implements Runnable {
    private final Account account;
    private final String type;
    private final double amount;

    public Transaction(Account account, String type, double amount) {
        this.account = account;
        this.type = type;
        this.amount = amount;
    }

    @Override
    public void run() {
        if (type.equals("deposit")) {
            account.deposit(amount);
        } else if (type.equals("withdraw")) {
            account.withdraw(amount);
        }
    }
}

// AccountTest class to test multiple transactions (threads)
public class AccountTest {
    public static void main(String[] args) {
        Account account = new Account(1000); // Initial balance

        // Creating a list of Transaction objects
        ExecutorService executor = Executors.newFixedThreadPool(10);
        ArrayList<Transaction> transactions = new ArrayList<>();

        transactions.add(new Transaction(account, "deposit", 200));
        transactions.add(new Transaction(account, "withdraw", 300));
        transactions.add(new Transaction(account, "deposit", 500));
        transactions.add(new Transaction(account, "withdraw", 100));
        transactions.add(new Transaction(account, "deposit", 200));
        transactions.add(new Transaction(account, "withdraw", 300));
        transactions.add(new Transaction(account, "deposit", 500));
        transactions.add(new Transaction(account, "withdraw", 100));
        transactions.add(new Transaction(account, "deposit", 200));
        transactions.add(new Transaction(account, "withdraw", 300));

        // Executing the threads
        for (Transaction transaction : transactions) {
            executor.execute(transaction);
        }

        // Shutdown the executor
        executor.shutdown();
    }
}