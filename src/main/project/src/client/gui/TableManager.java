package src.client.gui;

import src.client.gui.CollectionTableModel;
import src.elements.Product;
import src.logic.SerializationManager;
import src.logic.ServerPacket;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class TableManager extends Thread {

    private CollectionTableModel collectionTableModel;
    private DataInputStream ois;
    private final int BUFFER_SIZE = 4096;

    TableManager(DataInputStream ois, CollectionTableModel collectionTableModel) {
        this.ois = ois;
        this.collectionTableModel = collectionTableModel;
    }

    @Override
    public void run() {

        while (true) {

            try {

                while (ois.available() <= 0) {
                }

                byte[] bytes = new byte[BUFFER_SIZE];
                ois.read(bytes);

                ServerPacket serverPacket = SerializationManager.deserializeObject(bytes);
                if (serverPacket != null) {
                    if (!serverPacket.getIsMessage()) {
                        ArrayList<Product> products = (ArrayList<Product>) serverPacket.getObject();
                        collectionTableModel.updateAll(products);
                    }
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public CollectionTableModel getCollectionTableModel() {
        return collectionTableModel;
    }
}
