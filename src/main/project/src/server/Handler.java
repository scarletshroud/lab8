package src.server;

import com.sun.org.glassfish.gmbal.ManagedObject;
import src.logic.Packet;

import java.util.concurrent.*;

public class Handler implements Callable<String> {

    private Packet packet;

     Handler(Packet packet) {
        this.packet = packet;
    }

   @Override
   public String call() {
      return packet.getCommand().executeOnServer(Server.server, packet.getUser(), packet.getArgument());
   }

}
