import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double p = scanner.nextDouble();
        double h = scanner.nextDouble();

        System.out.println(p * 9.8 * h);
    }
}