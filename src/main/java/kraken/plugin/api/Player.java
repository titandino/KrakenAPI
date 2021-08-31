package kraken.plugin.api;

import java.util.HashMap;
import java.util.Map;

/**
 * A player within the game world.
 */
public class Player extends Spirit {

    public static final int ADRENALINE = 5;

    private Map<EquipmentSlot, Integer> equipment = new HashMap<>();

    /**
     * Do not make instances of this.
     */
    private Player() { }

    /**
     * Retrieves all equipment this player is wearing. Not all equipment is visible to remote players (e.g. ring slot.)
     *
     * @return All equipment this player is wearing.
     */
    public Map<EquipmentSlot, Item> getEquipment() {
        Map<EquipmentSlot, Item> conv = new HashMap<>();
        for (EquipmentSlot slot : equipment.keySet()) {
            int id = equipment.get(slot);
            conv.put(slot, new Item(id));
        }
        return conv;
    }

    @Override
    public String toString() {
        return "Player{" +
                "serverIndex=" + getServerIndex() +
                '}';
    }
}
