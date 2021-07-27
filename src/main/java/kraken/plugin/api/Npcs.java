package kraken.plugin.api;

/**
 * A provider of NPCs.
 */
public class Npcs {

    private Npcs() { }

    /**
     * Finds the closest NPC matching the provided filter.
     *
     * @param filter The filter NPCs must pass through in order to be accepted.
     * @return The found NPC, or NULL if one was not found.
     */
    public static native Npc closest(Filter<Npc> filter);

    /**
     * Iterates over each NPC.
     *
     * @param cb The callback for invoke for each NPC.
     */
    public static native void forEach(Callback<Npc> cb);

    /**
     * Retrieves all NPCs.
     *
     * @return All NPCs.
     */
    public static native Npc[] all();

    /**
     * Finds a NPC by their server side index.
     *
     * @param index The server index to search for.
     * @return The found NPC, or NULL if one was not found.
     */
    public static Npc byServerIndex(int index) {
        return closest((n) -> n.getServerIndex() == index);
    }

}
