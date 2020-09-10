package blockchain;

import java.io.*;
import java.security.MessageDigest;
import java.util.Date;

public class Block implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    private long timestamp;
    private String prevHash;
    private String hash;
    private String data;
    private int seed;

    private long totalTime;

    private boolean mined;

    public static Block getInstance(Block prev, int prefix){
        return new Block(prev, prefix);
    }


    public Block(Block prev, int prefix) {
        this.timestamp = new Date().getTime();
        if(prev == null){
            this.id = 1;
            this.prevHash = "0";
        } else {
            this.id = prev.getId() + 1;
            this.prevHash = prev.getHash();
        }
        this.hash = mineBlock(prefix);
        this.data = "Block data: \n";
        totalTime = new Date().getTime() - timestamp;
    }

    @Override
    public String toString() {
        return
                "Id: " + id + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Magic number: " + seed + "\n" +
                "Hash of the previous block: \n" + prevHash + "\n" +
                "Hash of the block: \n" + hash + "\n" +
                "Block was generating for " + totalTime + " seconds \n";
    }

    public String mineBlock(int prefix) {
        mined = false;
        String prefixString = "0".repeat(prefix);
        String hash = "";
        while (mined) {
            do {
                seed++;
                hash = new StringUtil().applySha256(this.toString());
            } while (!hash.substring(0, prefix).equals(prefixString));
            mined = true;
        }
        return hash;
    }

    public synchronized void reciveMsg(String msg){
        // TODO
        // Add StringBuilder
        if(id > 1){
            this.data += msg;
        } else {
            this.data += "no messages";
        }
    }

    public String getHash() {
        return hash;
    }

    public String getPrevHash() {
        return prevHash;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public int getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

