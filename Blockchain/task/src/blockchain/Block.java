package blockchain;

import java.io.*;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Block implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    private long timestamp;
    private String prevHash;
    private String hash;
    private List<String> data;
    private int seed;

    private long totalTime;


    public static Block getInstance(Block prev, int prefix){
        return new Block(prev, prefix);
    }


    private Block(Block prev, int prefix) {
        this.timestamp = new Date().getTime();
        if(prev == null){
            this.id = 1;
            this.prevHash = "0";
        } else {
            this.id = prev.getId() + 1;
            this.prevHash = prev.getHash();
        }
        this.hash = mineBlock(prefix);
        data = new ArrayList<>();
        totalTime = (new Date().getTime() - timestamp) / 1000;
    }

    public void addChat(ArrayList<String> chat) {
        this.data = (ArrayList<String>) chat.clone();
    }

    @Override
    public String toString() {
        return
                "Id: " + id + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Magic number: " + seed + "\n" +
                "Hash of the previous block: \n" + prevHash + "\n" +
                "Hash of the block: \n" + hash + "\n" +
                        "Block data: \n"  + viewChat() +
                "Block was generating for " + totalTime + " seconds \n";
    }

    public String mineBlock(int prefix) {
        String prefixString = "0".repeat(prefix);
        String hash = "";
        do {
                seed++;
                hash = new StringUtil().applySha256(this.toString());
            } while (!hash.substring(0, prefix).equals(prefixString));
        return hash;
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

    public List<String> getData() {
        return data;
    }

    public String viewChat(){
        if (data == null) return "no messages";
        StringBuilder sb = new StringBuilder();
        for (String s : data) {
            sb.append(s).append("\n");
        }
        return sb.toString();
    }

}

