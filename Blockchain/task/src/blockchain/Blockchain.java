package blockchain;

public class Blockchain {

    private int id = 0;
    private final int prefix;
    private final Block[] blockChain = new Block[5];
    private SerializationUtils blockChainData;

    public Blockchain(int prefix) {
        this.prefix = prefix;
        this.blockChainData = new SerializationUtils();
        generateBlocks();
        printChain();
    }

    public void generateBlocks() {
        long startTime = System.nanoTime();
        blockChain[0] = new Block(++id, "0", prefix);
        blockChain[0].setTotalTime((System.nanoTime() - startTime) / 1_000_000_000);
        blockChainData.saveBlock(blockChain[0]);

        for (int i = 1; i < blockChain.length; i++) {
            startTime = System.nanoTime();
            blockChain[i] = new Block(++id, blockChain[i-1].getHash(), prefix);
            blockChain[i].setTotalTime((System.nanoTime() - startTime) / 1_000_000_000);
            blockChainData.saveBlock(blockChain[i]);
        }
    }

    public boolean isValid() {
        String prefixString = new String(new char[prefix]).replace('\0', '0');

        boolean flag = true;
        for (int i = 0; i < blockChain.length; i++) {
            String previousHash = i == 0 ? "0" : blockChain[i - 1].getHash();
            flag = blockChain[i].getHash().equals(blockChain[i].calculateHash(this.prefix)) // The stored hash of the current block is actually what it calculates
                    && previousHash.equals(blockChain[i].getPrevHash()) // The hash of the previous block stored in the current block is the hash of the previous block
                     && blockChain[i].getHash().substring(0, prefix).equals(prefixString); // The current block has been mined
            if (!flag) break;
        }
        return flag;
    }




    public void printChain(){
        for (int i = 0; i < blockChain.length; i++) {
            System.out.println(blockChain[i].toString());
        }
    }

    public void loadFromFile(){
        for (int i = 0; i < blockChain.length; i++) {
            blockChain[i] = blockChainData.readBlock();
        }
    }



}
