package src.server;

import src.commands.Command_Show;
import src.elements.Product;
import src.logic.CollectionManager;
import src.logic.ServerPacket;

import java.lang.reflect.Array;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Observer extends Thread {

    private ArrayList<SocketChannel> socketChannels;
    private CollectionManager collectionManager;

    Observer(ArrayList<SocketChannel> socketChannels, CollectionManager collectionManager) {
        this.socketChannels = socketChannels;
        this.collectionManager = collectionManager;
    }

    @Override
    public void run() {

        ExecutorService executorService = Executors.newFixedThreadPool(20);

        ArrayList<Product> products = collectionManager.show();

        for(SocketChannel s : socketChannels) {
            Sender sender = new Sender(s, new ServerPacket(products, null, true, false));
            executorService.submit(sender);
        }
    }
}
