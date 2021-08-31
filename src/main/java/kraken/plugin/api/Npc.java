package kraken.plugin.api;

import static kraken.plugin.api.Actions.*;
import static kraken.plugin.api.Actions.MENU_EXECUTE_NPC6;

/**
 * A non-playable-character within the game world.
 */
public class Npc extends Spirit {

    private int id;
    private int health;
    private int transformedId = -1;

    private static final int OPTION_NAME_MAP[] = {
            MENU_EXECUTE_NPC1,
            MENU_EXECUTE_NPC2,
            MENU_EXECUTE_NPC3,
            MENU_EXECUTE_NPC4,
            MENU_EXECUTE_NPC5,
            MENU_EXECUTE_NPC6,
    };

    /**
     * Do not make instances of this.
     */
    Npc() { }

    /**
     * Rerieves this NPC's id.
     *
     * @return This NPC's id.
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves this NPC's health.
     *
     * @return This NPC's health.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Interacts with this NPC.
     */
    public void interact(int type) {
        Actions.entity(this, type);
    }

    /**
     * Retrieves the names of options when right clicking this NPC.
     *
     * @return The names of options when right clicking this NPC.
     */
    public String[] getOptionNames() {
        return Cache.getNpc(transformedId).getOptionNames();
    }

    /**
     * Interacts with this NPC.
     */
    public boolean interact(String option) {
        String[] options = getOptionNames();
        if (options == null) {
            return false;
        }

        int m = Math.min(OPTION_NAME_MAP.length, options.length);
        for (int i = 0; i < m; i++) {
            if (option.equalsIgnoreCase(options[i])) {
                interact(OPTION_NAME_MAP[i]);
                return true;
            }
        }

        return false;
    }

    /**
     * Retrieves the id of the NPC cache description that this NPC is transformed into.
     *
     * @return The id of the NPC that this NPC is transformed into.
     */
    public int getTransformedId() {
        return transformedId;
    }

    @Override
    public String toString() {
        return "Npc{" +
                "serverIndex=" + getServerIndex() +
                '}';
    }

}
