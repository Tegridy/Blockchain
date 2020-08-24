package blockchain;

import java.io.*;
import java.security.MessageDigest;
import java.util.Date;

public class Block implements Serializable{

    private int id;
    private long timestamp;
    private String prevHash;
    private String hash;
    private int seed;

    private long totalTime;


    public Block(int id, String prevHash, int prefix) {
        this.id = id;
        this.timestamp = new Date().getTime();
        this.hash = calculateHash(prefix);
        this.prevHash = prevHash;
    }

    public Block() {
    }

    @Override
    public String toString() {
        return "Block: \n" +
                "Id: " + id + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Magic number: " + seed + "\n" +
                "Hash of the previous block: \n" + prevHash + "\n" +
                "Hash of the block: \n" + hash + "\n" +
                "Block was generating for " + totalTime + " seconds \n";
    }


    public String getHash() {
        return hash;
    }

    public String getPrevHash() {
        return prevHash;
    }

    public String calculateHash(int prefix){
        String hash = new StringUtil().applySha256(this.toString());

        return mineBlock(prefix, hash);
    }

    public String mineBlock(int prefix, String hash) {
        String prefixString = new String(new char[prefix]).replace('\0', '0');
        while (!hash.substring(0, prefix).equals(prefixString)) {
            seed++;
            hash = new StringUtil().applySha256(this.toString());
        }
        return hash;
    }


    public long getTimestamp() {
        return timestamp;
    }


    class StringUtil {
        /* Applies Sha256 to a string and returns a hash. */
        public String applySha256(String input) {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                /* Applies sha256 to our input */
                byte[] hash = digest.digest(input.getBytes("UTF-8"));
                StringBuilder hexString = new StringBuilder();
                for (byte elem : hash) {
                    String hex = Integer.toHexString(0xff & elem);
                    if (hex.length() == 1) hexString.append('0');
                    hexString.append(hex);
                }
                return hexString.toString();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }
}

