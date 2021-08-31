package kraken.plugin.api;

/**
 * A provider of players.
 */
public final class Players {

    private Players() { }

    /**
     * Retrieves the local player.
     *
     * @return The local player, or NULL if there is no local player.
     */
    public static native Player self();

    /**
     * Finds the closest player matching the provided filter.
     *
     * @param filter The filter players must pass through in order to be accepted.
     * @return The found player, or NULL if one was not found.
     */
    public static native Player closest(Filter<Player> filter);

    /**
     * Iterates over each player.
     *
     * @param cb The callback for invoke for each player.
     */
    public static native void forEach(Action1<Player> cb);

    /**
     * Retrieves all players.
     *
     * @return All players.
     */
    public static native Player[] all();

    /**
     * Finds a player by their server side index.
     *
     * @param index The server index to search for.
     * @return The found player, or NULL if one was not found.
     */
    public static Player byServerIndex(int index) {
        return closest((p) -> p.getServerIndex() == index);
    }
}
