package blockchain;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Blockchain {

    public static Blockchain instance = new Blockchain();

    private int prefix;
    private final ArrayList<Block> blockChain;

    public Blockchain() {
      this.prefix = 0;
      this.blockChain = new ArrayList<>();
    }

//    public Blockchain getInstance(){
//        return instance;
//    }

    public static Blockchain getBlockchain(String filename) {
        if (instance != null) {
            return instance;
        }

        File file = new File(filename);

        if (file.exists() && file.length() != 0) {
            try {
                instance = (Blockchain) SerializationUtils.deserialize(file.getName());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            instance = new Blockchain();
        }
        return instance;
    }

//    public Block getBlock(int id) {
//        if (id < blockChain.size()) {
//            return blockChain.get(id);
//        }
//        throw new IllegalArgumentException("There is no such block");
//    }

    public synchronized Block peek() {
        if (blockChain.isEmpty()){
            return null;
        }
        return blockChain.get(blockChain.size() - 1);
    }

    public synchronized int getPrefix() {
        return prefix;
    }

    public synchronized void getMsgFromClient(String msg){

    }


    public synchronized boolean put(Block newBlock, String miner) {
        String prevHash = blockChain.isEmpty() ? "0" : blockChain.get(blockChain.size() - 1).getHash();

        if (prevHash.equals(newBlock.getPrevHash())) {
            blockChain.add(newBlock);
            System.out.println("Block:");
            System.out.println("Created by miner # " + miner);
            System.out.print(newBlock);
            if (newBlock.getTotalTime() / 1000 < 60) {
                System.out.println("Current N: " + prefix++ + " was increased by 1\n");
            } else {
                System.out.println("Current N: " + prefix-- + " was decreased by 1\n");
            }
            return true;
        }
        return false;
    }

    public int getSize() {
        return blockChain.size();
    }

//    public boolean validate() {
//        for (int i = 1; i < blockChain.size(); i++) {
//            if (Objects.equals(blockChain.get(i).getPrevHash(), blockChain.get(i - 1).getHash())) {
//                return false;
//            }
//        }
//        return true;
//    }
}
