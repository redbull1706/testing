import java.math.BigDecimal;

public class InputChecker {

    private boolean numInRange(int number, int start, int end) {
        return number >= start && number <= end;
    }

    private int parseNumber(String intAsStr) {
        try {
            return Integer.parseInt(intAsStr);
        }
        catch (NumberFormatException e) {
            return -1;
        }
    }

    public boolean checkIBAN(String iban) {
        if (iban.length() != 20) {
            return false;
        }

        String countryCode = iban.substring(0, 2);

        // we work only on Austrian SEPA for this exercise
        if (!countryCode.equals("AT")) {
            return false;
        }

        String checkDigit = iban.substring(2, 4);
        int checkDigitNum = parseNumber(checkDigit);

        if (!numInRange(checkDigitNum, 1, 97)) {
            return false;
        }

        String bankCode = iban.substring(4, 9);
        int bankCodeNum = parseNumber(bankCode);

        if (!numInRange(bankCodeNum, 10000, 99999)) {
            return false;
        }

        String accountNumber = iban.substring(9, 20);

        if (!numInRange(accountNumber.length(), 11, 11)) {
            return false;
        }

        return true;
    }

    public boolean checkName(String name) {
        // Sender and Receiver name should only have a length of <= 70
        return name.length() <= 70;
    }

    public boolean checkAddress(String address) {
        // Sender and Receiver address should only have a length of <= 70
        return address.length() <= 70;
    }

    public boolean checkAmount(double amount) {
        // > 0 && only 2 digits after decimal
        return amount > 0.0 && BigDecimal.valueOf(amount).scale() <= 2;
    }

    public boolean checkPurpose(String purpose) {
        // length of purpose should be <= 140 and alphanumeric

        return purpose.length() <= 140 && purpose.matches("[A-Za-z0-9]+");
    }

    public boolean checkReference(String reference) {
        // length of reference should be <= 35 and alphanumeric
        return reference.length() <= 35 && reference.matches("[A-Za-z0-9]+");
    }


    public static void main(String[] args) {
        InputChecker inputChecker = new InputChecker();

        System.out.println(inputChecker.checkIBAN("AT611904300234573201"));
        System.out.println(inputChecker.checkIBAN("AT6119043002345732010"));
        System.out.println(inputChecker.checkIBAN("DE611904300234573201"));
        System.out.println(inputChecker.checkIBAN("AT991904300234573201"));
        System.out.println(inputChecker.checkIBAN("AT610999900234573201"));

        System.out.println(inputChecker.checkName("Max Mustermann"));
        System.out.println(inputChecker.checkName("Maaaaaaaaaaaaaaaaaaaax Mustermaaaaaaaaaaaaaaaaaaaannnnnnnnnnnnnnnnnnnnnnnnnnnnnn"));

        System.out.println(inputChecker.checkAmount(12));
        System.out.println(inputChecker.checkAmount(12.33));
        System.out.println(inputChecker.checkAmount(12.3));
        System.out.println(inputChecker.checkAmount(-1));
        System.out.println(inputChecker.checkAmount(12.333));

        System.out.println(inputChecker.checkPurpose("BL2ZK"));
        System.out.println(inputChecker.checkPurpose("B!L2ZK"));
    }
}
