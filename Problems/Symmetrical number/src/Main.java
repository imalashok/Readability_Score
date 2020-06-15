import java.util.Scanner;
import java.util.Random;

class Main {
    public static void main(String[] args) {
        int outputNumber = new Random().nextInt();
        String number = new Scanner(System.in).next();

        if (number.length() < 4) {
            switch (number.length()) {
                case 3:
                    if (0 == number.charAt(2) && number.charAt(0) == number.charAt(1)) {
                        outputNumber = 1;
                    }
                    break;
                case 2:
                    if (0 == number.charAt(1) && 0 == number.charAt(0)) {
                        outputNumber = 1;
                    }
                    break;
                case 1:
                    if (0 == number.charAt(0)) {
                        outputNumber = 1;
                    }
                    break;
                default:
                    break;
            }
        } else if (number.charAt(0) == number.charAt(3) && number.charAt(1) == number.charAt(2)) {
            outputNumber = 1;
        }
        System.out.println(outputNumber);
    }
}