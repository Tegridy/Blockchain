package blockchain;

import java.io.*;

public class SerializationUtils {

    public void saveBlock(Block block) {
        try(FileOutputStream fileOut = new FileOutputStream("blockchain.dat", false)) {
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(block);
            out.close();
            System.out.printf("Serialized data is saved in /tmp/employee.ser");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Block readBlock(){
        Block block = new Block();
        try(FileInputStream fileIn = new FileInputStream("blockchain.dat")) {
            ObjectInputStream in = new ObjectInputStream(fileIn);
            block = (Block) in.readObject();
            in.close();

            return block;
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
        }

        return block;
    }

}
