import java.io.*;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {

    private static int menu() {
        System.out.println("Welche Aktion möchten Sie ausführen?");
        System.out.println("[1] - Überweisungen durchführen.");
        System.out.println("[2] - Durchgeführte Überweisungen anzeigen.");
        System.out.println("[3] - Program beenden.");

        Scanner in = new Scanner(System.in);
        System.out.print("Ihre Eingabe: ");
        return in.nextInt();
    }

    private static List<Bank> setupBanks(String pathToCsv, String separator) {
        List<Bank> banks = new ArrayList<>();

        try {
            /* TODO: change to relative path */
            Path path = Paths.get(pathToCsv);
            List<String> lines = Files.readAllLines(path);

            for(String line : lines) {
                String[] data = line.split(separator);
                int bankNumber = Integer.parseInt(data[0]);
                int blz = Integer.parseInt(data[1]);
                String name = data[2];
                banks.add(new Bank(bankNumber, blz, name));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return banks;
    }

    private static Bank getBankOfUser(User user, List<Bank> banks) {
        for (Bank bank : banks) {
            if(user.getBankCode() == bank.getBlz()) {
                return bank;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        /* preliminary work to have some banks ready for use for users */
        List<Bank> banks = setupBanks("F:/IdeaProjects/ARZ_testing_local/DB/Bank.csv", ";");

        User user = new User("AT611234500234573201", "Max Mustermann", "Musterstraße 10");
        Bank bank = getBankOfUser(user, banks);

        if (bank == null) {
            System.err.println("Bank is not available");
            return;
        }

        /* Actual program starts here */

        System.out.println("Hallo " + user.getName() + "! Willkommen bei der " + bank.getName() + ".");

        int input = menu();

        switch (input) {
            case 1:
                System.out.println("Auswahl: Überweisungen durchführen.");
                bank.makeTransaction(user);
                break;
            case 2:
                System.out.println("Auswahl: Durchgeführte Überweisungen anzeigen.");
                bank.showTransactions();
                break;
            case 3:
                System.out.println("Auswahl: Programm beenden.");
                System.exit(0);
                break;
            default:
                System.out.println("Ungültige Eingabe!");
        }
    }
}
