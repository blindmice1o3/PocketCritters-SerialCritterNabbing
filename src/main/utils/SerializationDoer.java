package main.utils;

import main.Handler;
import main.gfx.GameCamera;
import model.entities.Player;

import java.io.*;

public class SerializationDoer {

    private static int counter = 1;
    private String defaultName = "default";

    private Handler handler;

    public SerializationDoer(Handler handler) {
        this.handler = handler;
    } // **** end SerializationDoer(Handler) constructor ****

    public void saveWriteToFile() {
        //counter++;

        String counterAsString = String.format("%05d", counter);
        String concatenatedDefaultName = defaultName + counterAsString + ".bin";

        //Try-with-resources (automatically calls close() method of fs... java7+).
        try ( FileOutputStream fs = new FileOutputStream(concatenatedDefaultName) ) {
            ObjectOutputStream os = new ObjectOutputStream(fs);

            os.writeObject( handler.getGame().getGameCamera() );
            os.writeObject( handler.getGame().getPlayer() );
            os.writeObject( handler.getGame().getJames() );
            os.writeObject( handler.getGame().getJessie() );

            ////////////
            os.close();
            ////////////
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /* //PRIOR TO java7.
        try {
            FileOutputStream fs = new FileOutputStream(concatenatedDefaultName);



            ///////////
            fs.close();
            ///////////
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }

    public void loadReadFromFile() {
        String counterAsString = String.format("%05d", counter);
        String concatenatedDefaultName = defaultName + counterAsString + ".bin";

        try (FileInputStream fi = new FileInputStream(concatenatedDefaultName)) {
            ObjectInputStream os = new ObjectInputStream(fi);

            handler.getGame().setGameCamera( (GameCamera)os.readObject() );
            handler.getGame().setPlayer( (Player)os.readObject() );
            handler.getGame().setJames( (Player)os.readObject() );
            handler.getGame().setJessie( (Player)os.readObject() );

            ////////////
            os.close();
            ////////////
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

} // **** end SerializationDoer class ****