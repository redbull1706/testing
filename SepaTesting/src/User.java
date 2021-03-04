import java.util.Random;

public class User {
    private String iban;
    private int bankCode;
    private String name;
    private String address;
    private double accountBalance;


    public User(String iban, String name, String address) {
        this.iban = iban;
        this.bankCode = Integer.parseInt(iban.substring(4, 9));   // user is registered at this bank
        this.name = name;
        this.address = address;
        /* to simulate an account balance of the user */
        Random r = new Random();
        double value = 50 + r.nextDouble() * (200 - 50);
        this.accountBalance = Math.round(value * 100.0) / 100.0;
    }

    public String getIban() {
        return iban;
    }

    public int getBankCode() {
        return bankCode;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public boolean withdraw(double amount) {
        double newBalance = this.accountBalance - amount;

        // Assumption: it is not allowed to overdraw an account -> if user has
        // not enough money on his/her account the transaction is not possible
        if (newBalance < 0.00) {
            return false;
        }
        else {
            this.accountBalance = newBalance;
            return true;
        }
    }

    public void deposit(double amount) {
        this.accountBalance += amount;
    }

    @Override
    public String toString() {
        return "User{" +
                "iban='" + iban + '\'' +
                ", bankCode=" + bankCode +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", accountBalance=" + accountBalance +
                '}';
    }

    public static void main(String[] args) {
        User user = new User("AT611904300234573201", "Max Mustermann", "Musterstraße 10");
        System.out.println(user);
    }
}
