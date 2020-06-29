package src.server;

import com.sun.org.glassfish.gmbal.ManagedObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.logic.Packet;
import src.logic.ServerPacket;

import java.util.concurrent.*;

public class Handler implements Callable<ServerPacket> {

    private Packet packet;
    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    Handler(Packet packet) {
        this.packet = packet;
    }

    @Override
    public ServerPacket call() {
        logger.info("Handler");
        return packet.getCommand().executeOnServer(Server.server, packet.getUser(), packet.getArgument());
    }

}
