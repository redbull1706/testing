import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class FileIO {
    String pathSuccessfulTransactions;
    String pathFailedTransactions;
    String pathAllTransactions;


    public FileIO() {
        /* TODO: change to relative path */
        this.pathSuccessfulTransactions = "F:/IdeaProjects/ARZ_testing/DB/SuccessfulTransactions.csv";
        this.pathFailedTransactions = "F:/IdeaProjects/ARZ_testing/DB/FailedTransactions.csv";
        this.pathAllTransactions = "F:/IdeaProjects/ARZ_testing/DB/AllTransactions.csv";
    }


    public List<String> listAllTransactions() {
        List<String> transactions = new ArrayList<>();

        try {
            /* TODO: change to relative path */
            Path path = Paths.get(this.pathAllTransactions);
            List<String> lines = Files.readAllLines(path);

            for(String line : lines) {
                String[] data = line.split(";");
                String formattedData = data[0] + " -> " + data[1] + ": " + data[2];

                if (data.length == 4) { // for entry of failed transaction
                    formattedData += (" Reason: " + data[3]);   // append reason
                    formattedData = "[FAILED] " + formattedData;
                }
                else {
                    formattedData = "[SUCCESS] " + formattedData;
                }

                transactions.add(formattedData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    public void writeSuccessfulTransaction(String from_user, String to_user, double amount) {
        String data = String.join(";", from_user, to_user, String.valueOf(amount)) + "\n";

        try {
            /* TODO: change to relative path */
            Path path = Paths.get(this.pathSuccessfulTransactions);
            Files.write(path, data.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            /* TODO: change to relative path */
            Path path = Paths.get(this.pathAllTransactions);
            Files.write(path, data.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeFailedTransaction(String from_user, String to_user, double amount, String reason) {
        String data = String.join(";", from_user, to_user, String.valueOf(amount), reason) + "\n";

        try {
            /* TODO: change to relative path */
            Path path = Paths.get(this.pathFailedTransactions);
            Files.write(path, data.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            /* TODO: change to relative path */
            Path path = Paths.get(this.pathAllTransactions);
            Files.write(path, data.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        FileIO fileIO = new FileIO();
        User user1 = new User("AT611904300234573201", "Max Mustermann", "Musterstraße 10");
        User user2 = new User("AT611904300234556446", "Max Müller", "Sackgasse 1");
        double amount = 12.5;
        fileIO.writeSuccessfulTransaction(user1.getName(), user2.getName(), amount);
        String reason = "Account überzogen";
        fileIO.writeFailedTransaction(user2.getName(), user1.getName(), amount, reason);

        List<String> transactions = fileIO.listAllTransactions();
        System.out.println(transactions.toString());
    }
}
