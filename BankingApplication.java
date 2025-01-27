package wiprotrainingday2;

import java.util.Scanner;

public class BankingApplication {

    static class Account {
        protected String accountNumber;
        protected double balance;

        public Account(String accountNumber, double balance) {
            this.accountNumber = accountNumber;
            this.balance = balance;
        }

        public void deposit(double amount) {
            if (amount > 0) {
                balance += amount;
                System.out.println("Deposited " + amount + ". New balance is " + balance);
            } else {
                System.out.println("Deposit amount must be greater than 0.");
            }
        }

        public void withdraw(double amount) {
            if (amount > 0 && amount <= balance) {
                balance -= amount;
                System.out.println("Withdrew " + amount + ". New balance is " + balance);
            } else if (amount <= 0) {
                System.out.println("Withdrawal amount must be greater than 0.");
            } else {
                System.out.println("Insufficient funds.");
            }
        }

        public double getBalance() {
            return balance;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        @Override
        public String toString() {
            return "Account Number: " + accountNumber + ", Balance: " + balance;
        }
    }

    static class SavingsAccount extends Account {
        private double interestRate;

        public SavingsAccount(String accountNumber, double balance, double interestRate) {
            super(accountNumber, balance);
            this.interestRate = interestRate;
        }

        public void applyInterest() {
            double interest = balance * interestRate;
            balance += interest;
            System.out.println("Applied interest of " + interest + ". New balance is " + balance);
        }
    }

    static class CurrentAccount extends Account {
        private double overdraftLimit;

        public CurrentAccount(String accountNumber, double balance, double overdraftLimit) {
            super(accountNumber, balance);
            this.overdraftLimit = overdraftLimit;
        }

        @Override
        public void withdraw(double amount) {
            if (amount > 0 && (balance - amount) >= -overdraftLimit) {
                balance -= amount;
                System.out.println("Withdrew " + amount + ". New balance is " + balance);
            } else {
                System.out.println("Exceeds overdraft limit or invalid amount.");
            }
        }
    }

    static class Customer {
        private String customerId;
        private String name;
        private Account[] accounts;

        public Customer(String customerId, String name) {
            this.customerId = customerId;
            this.name = name;
            this.accounts = new Account[10]; // Max 10 accounts
        }

        public void addAccount(Account account) {
            for (int i = 0; i < accounts.length; i++) {
                if (accounts[i] == null) {
                    accounts[i] = account;
                    System.out.println("Added account: " + account);
                    return;
                }
            }
            System.out.println("Unable to add account. Maximum accounts reached.");
        }

        public void printAccounts() {
            System.out.println(name + "'s accounts:");
            for (Account account : accounts) {
                if (account != null) {
                    System.out.println(account);
                }
            }
        }

        public Account getAccountByNumber(String accountNumber) {
            for (Account account : accounts) {
                if (account != null && account.getAccountNumber().equals(accountNumber)) {
                    return account;
                }
            }
            return null;
        }
    }

    static class Transaction {
        public static void transferFunds(Account fromAccount, Account toAccount, double amount) {
            if (fromAccount.getBalance() >= amount) {
                fromAccount.withdraw(amount);
                toAccount.deposit(amount);
                System.out.println("Transferred " + amount + " from " + fromAccount.getAccountNumber() + " to " + toAccount.getAccountNumber());
            } else {
                System.out.println("Insufficient funds for transfer.");
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create a customer
        System.out.println("Enter customer ID:");
        String customerId = scanner.nextLine();
        System.out.println("Enter customer name:");
        String customerName = scanner.nextLine();
        Customer customer = new Customer(customerId, customerName);

        while (true) {
            System.out.println("\nSelect an action:");
            System.out.println("1. Add Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer Funds");
            System.out.println("5. Apply Interest (Savings Account)");
            System.out.println("6. Print Accounts");
            System.out.println("7. Exit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    System.out.println("Enter account type (savings/current):");
                    String accountType = scanner.next();
                    System.out.println("Enter account number:");
                    String accountNumber = scanner.next();
                    System.out.println("Enter initial balance:");
                    double balance = scanner.nextDouble();
                    if ("savings".equalsIgnoreCase(accountType)) {
                        System.out.println("Enter interest rate:");
                        double interestRate = scanner.nextDouble();
                        customer.addAccount(new SavingsAccount(accountNumber, balance, interestRate));
                    } else if ("current".equalsIgnoreCase(accountType)) {
                        System.out.println("Enter overdraft limit:");
                        double overdraftLimit = scanner.nextDouble();
                        customer.addAccount(new CurrentAccount(accountNumber, balance, overdraftLimit));
                    } else {
                        System.out.println("Invalid account type.");
                    }
                }
                case 2 -> {
                    System.out.println("Enter account number:");
                    String accountNumber = scanner.next();
                    Account account = customer.getAccountByNumber(accountNumber);
                    if (account != null) {
                        System.out.println("Enter deposit amount:");
                        double amount = scanner.nextDouble();
                        account.deposit(amount);
                    } else {
                        System.out.println("Account not found.");
                    }
                }
                case 3 -> {
                    System.out.println("Enter account number:");
                    String accountNumber = scanner.next();
                    Account account = customer.getAccountByNumber(accountNumber);
                    if (account != null) {
                        System.out.println("Enter withdrawal amount:");
                        double amount = scanner.nextDouble();
                        account.withdraw(amount);
                    } else {
                        System.out.println("Account not found.");
                    }
                }
                case 4 -> {
                    System.out.println("Enter sender's account number:");
                    String fromAccountNumber = scanner.next();
                    Account fromAccount = customer.getAccountByNumber(fromAccountNumber);
                    if (fromAccount != null) {
                        System.out.println("Enter recipient's account number:");
                        String toAccountNumber = scanner.next();
                        Account toAccount = customer.getAccountByNumber(toAccountNumber);
                        if (toAccount != null) {
                            System.out.println("Enter transfer amount:");
                            double amount = scanner.nextDouble();
                            Transaction.transferFunds(fromAccount, toAccount, amount);
                        } else {
                            System.out.println("Recipient account not found.");
                        }
                    } else {
                        System.out.println("Sender's account not found.");
                    }
                }
                case 5 -> {
                    System.out.println("Enter savings account number:");
                    String accountNumber = scanner.next();
                    Account account = customer.getAccountByNumber(accountNumber);
                    if (account instanceof SavingsAccount savingsAccount) {
                        savingsAccount.applyInterest();
                    } else {
                        System.out.println("Not a savings account.");
                    }
                }
                case 6 -> customer.printAccounts();
                case 7 -> {
                    System.out.println("Exiting. Thank you!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
