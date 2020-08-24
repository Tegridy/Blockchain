package blockchain;

import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {


        System.out.print("Enter how many zeros the hash must start with: ");

        int chainLen = Integer.parseInt(scanner.nextLine());

        Blockchain blockchain = new Blockchain(chainLen);
    }
}
