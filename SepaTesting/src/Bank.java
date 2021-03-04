import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Bank {
    private int bankNumber;
    private int blz;
    private String name;


    public Bank(int bankNumber, int blz, String name) {
        this.bankNumber = bankNumber;
        this.blz = blz;
        this.name = name;
    }

    public int getBlz() {
        return blz;
    }

    public String getName() {
        return name;
    }

    private String readInString() {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    private double readInDouble() {
        Scanner in = new Scanner(System.in);
        return in.nextDouble();
    }

    public void makeTransaction(User user) {
        InputChecker inputChecker = new InputChecker();

        String receiver_iban;
        do {
            System.out.println("Bitte geben Sie eine gültige IBAN des Empfängers ein: ");
            receiver_iban = readInString();
        } while (!inputChecker.checkIBAN(receiver_iban));

        String receiver_name;
        do {
            System.out.println("Bitte geben Sie einen gültigen Namen als Empfänger ein: ");
            receiver_name = readInString();
        } while (!inputChecker.checkName(receiver_name));

        double transferAmount;
        do {
            System.out.println("Bitte geben Sie den zu überweisenden Betrag ein: ");
            transferAmount = readInDouble();
        } while (!inputChecker.checkAmount(transferAmount));

        System.out.println("Kommende Felder sind optional. " +
                "Wenn eines davon nicht benötigt wird bitte einfach ENTER drücken");

        String receiver_address;
        do {
            System.out.println("Bitte geben Sie die Empfänger-Adresse ein: ");
            receiver_address = readInString();

            if (receiver_address.equals("")) {
                break;
            }
        } while (!inputChecker.checkAddress(receiver_address));

        String purpose;
        do {
            System.out.println("Bitte geben Sie den Verwendungszweck ein: ");
            purpose = readInString();

            if (purpose.equals("")) {
                break;
            }
        } while (!inputChecker.checkPurpose(purpose));

        String receiver_reference;
        if (purpose.equals("")) {
            do {
                System.out.println("Bitte geben Sie die Zahlungsreference ein: ");
                receiver_reference = readInString();

                if (receiver_address.equals("")) {
                    break;
                }
            } while (receiver_reference.equals("") && !inputChecker.checkReference(receiver_reference));
        }

        /* simulate the receiver */
        // User receiver = new User("AT611234511685787911", "Max Müller", "Sackgasse 1");
        User receiver = new User(receiver_iban, receiver_name, receiver_address);

        FileIO fileIO = new FileIO();
        try {
            performTransaction(user, receiver, transferAmount);
        } catch (IOException e) {
            fileIO.writeFailedTransaction(user.getName(), receiver.getName(), transferAmount, e.getMessage());
            return;
        }

        // if transaction was successful
        fileIO.writeSuccessfulTransaction(user.getName(), receiver.getName(), transferAmount);
    }

    private void performTransaction(User user, User receiver, double transferAmount) throws IOException {
        // "die Bank des Auftraggebers muss mit der Bank des Empfänger übereinstimmen"
        if (user.getBankCode() != receiver.getBankCode()) {
            throw new IOException("Sender and Receiver Bank are different");
        }

        // if the inputs are correct and sender and receiver are customers of the same bank
        boolean succes = user.withdraw(transferAmount);
        if (!succes) {
            throw new IOException("Sender has not enough money");
        }
        receiver.deposit(transferAmount);
        System.out.println("Überweisung in der Höhe von €" + transferAmount + " von " + user.getName() + " zu " + receiver.getName() + " dürchgeführt.");
    }

    public void showTransactions() {
        FileIO fileIO = new FileIO();

        List<String> transactions = fileIO.listAllTransactions();

        System.out.println("\nEs sind insgesamt folgende Überweisungen getätigt worden:");
        for(String transaction : transactions) {
            System.out.println(transaction);
        }
    }
}
