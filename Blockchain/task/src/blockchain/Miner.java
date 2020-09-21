package blockchain;

import java.util.Optional;

public class Miner implements Runnable {

    private final Blockchain blockchain;
    private String miner;

    public Miner(Blockchain blockchain) {
        this.blockchain = blockchain;
    }

    private Block generateBlock() {
        int prefix = blockchain.getPrefix();
        Block newBlock;
        Block prevBlock = blockchain.peek();
        newBlock = Block.getInstance(prevBlock, prefix);
        blockchain.put(newBlock, miner);
        return newBlock;
    }

    @Override
    public void run() {
        String[] s = Thread.currentThread().getName().split("-");
        this.miner = s[s.length - 1];
        blockchain.receiveMsg("test msg");
        generateBlock();
    }

}
