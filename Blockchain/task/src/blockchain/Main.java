package blockchain;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorMiner = Executors.newFixedThreadPool(4);
        String filename = "";

        Blockchain blockchain = Blockchain.getBlockchain(filename);
        int size = blockchain.getSize();

        while (true) {
            executorMiner.submit(new Miner(blockchain));
            Thread.sleep(1000);
            if (blockchain.getSize() - size >= 5) {
                executorMiner.shutdownNow();
                break;
            }
        }

        if (!"".equals(filename)) {
            try {
                SerializationUtils.serialize(blockchain, filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
