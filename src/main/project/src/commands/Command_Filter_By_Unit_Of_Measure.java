package src.commands;

import com.sun.org.glassfish.gmbal.ManagedObject;
import src.client.Client;
import src.logic.CollectionManager;
import src.logic.Packet;

import java.io.Serializable;

/**
 * Class of command Filter By Unit Of Measure.
 * This command filters collection by unit of measure.
 */

public class Command_Filter_By_Unit_Of_Measure extends Command implements Serializable {

    /**
     * Constructor
     */

    public Command_Filter_By_Unit_Of_Measure() { }

    @Override
    public boolean validateArgs(String ... args) {
        return args.length == 1;
    }

    /**
     * Execute method Filter By Unit Of Measure in Collection Manager.
     * @param collectionManager -the manager of collection
     */

    @Override
    public String executeOnServer(CollectionManager collectionManager, Object object) {
            return collectionManager.filterByUnitOfMeasure(object);
    }

    @Override
    public Packet executeOnClient(String ... args) {
        return new Packet(this, args[0]);
    }
}
