package kraken.plugin.api;

/**
 * Provides ways of moving the local player.
 */
public class Move {

    private Move() { }

    /**
     * Makes the local player walk to the provided tile.
     *
     * The way in which the bot walks is up to bot, and depends on heuristics
     * configured by the end-user.
     *
     * @param tile The tile to walk to
     */
    public static native void to(Vector2i tile);

    /**
     * Traverses the world using a web.
     *
     * @param tile The tile to traverse to.
     * @param context The traversal context.
     */
    public static native void traverse(Vector3i tile, TraverseContext context);
}
