package kraken.plugin.api;


import java.nio.charset.StandardCharsets;

/**
 * Provides access to various client state.
 */
public class Client {

    public static final int ATTACK = 0;
    public static final int DEFENSE = 1;
    public static final int STRENGTH = 2;
    public static final int HITPOINTS = 3;
    public static final int RANGE = 4;
    public static final int PRAYER = 5;
    public static final int MAGIC = 6;
    public static final int COOKING = 7;
    public static final int WOODCUTTING = 8;
    public static final int FLETCHING = 9;
    public static final int FISHING = 10;
    public static final int FIREMAKING = 11;
    public static final int CRAFTING = 12;
    public static final int SMITHING = 13;
    public static final int MINING = 14;
    public static final int HERBLORE = 15;
    public static final int AGILITY = 16;
    public static final int THIEVING = 17;
    public static final int SLAYER = 18;
    public static final int FARMING = 19;
    public static final int RUNECRAFTING = 20;
    public static final int HUNTER = 21;
    public static final int CONSTRUCTION = 22;
    public static final int SUMMONING = 23;
    public static final int DUNGEONEERING = 24;
    public static final int DIVINATION = 25;
    public static final int INVENTION = 26;
    public static final int ARCHAEOLOGY = 27;

    public static final int AT_LOGIN = 10;
    public static final int IN_LOBBY = 20;
    public static final int IN_GAME = 30;

    private Client() {
    }

    /**
     * Retrieves the state that the client is currently in.
     *
     * @return The state that the client is currently in.
     */
    public static native int getState();

    /**
     * Determines if the client is in a loading state.
     *
     * @return If the client is in a loading state.
     */
    public static native boolean isLoading();

    /**
     * Retrieves a stat by id.
     *
     * @param id The id of the stat to retrieve.
     * @return The stat with the provided id, or NULL if one was not found.
     */
    public static native Stat getStatById(int id);

    /**
     * Retrieves a ConVar by id.
     *
     * @param id The id of the ConVar to search for.
     * @return The found ConVar with the provided id, or NULL if one was not found.
     */
    public static native ConVar getConVarById(int id);

    /**
     * Retrieves the current health of the local player.
     *
     * @return The current health of the local player.
     */
    public static native int getCurrentHealth();

    /**
     * Retrieves the maximum health of the local player.
     *
     * @return The maximum health of the local player.
     */
    public static native int getMaxHealth();

    /**
     * Retrieves the current prayer of the local player.
     *
     * @return The current prayer of the local player.
     */
    public static native int getCurrentPrayer();

    /**
     * Retrieves the maximum prayer of the local player.
     *ac
     * @return The maximum prayer of the local player.
     */
    public static native int getMaxPrayer();

    /**
     * Projects a world point to the screen.
     *
     * @param vec The world point to project.
     * @return The projected point, or NULL if projection failed.
     */
    public static native Vector2i worldToScreen(Vector3 vec);

    /**
     * Projects a world point to the minimap.
     *
     * @param vec The world point to project.
     * @return The projected poinr, or NULL if projection failed.
     */
    public static native Vector2i worldToMinimap(Vector3 vec);

    /**
     * Projects multiple world points to the screen.
     *
     * Good for projecting tile points, model points, etc.
     *
     * @param in The input vectors to project.
     * @return If all points were successfully projected.
     */
    public static boolean multiWorldToScreen(Vector3[] in, Vector2i[] out) {
        if (in.length != out.length) {
            return false;
        }

        for (int i = 0; i < in.length; i++) {
            Vector3 v = in[i];
            if (v == null) {
                return false;
            }

            Vector2i projected = worldToScreen(v);
            if (projected == null) {
                return false;
            }

            out[i] = projected;
        }

        return true;
    }

    /**
     * Retrieves the path to the plugin directory.
     *
     * @return The path to the plugin directory.
     */
    public static native byte[] getPluginDirBinary();

    /**
     * Retrieves the path to the plugin directory.
     *
     * @return The path to the plugin directory.
     */
    public static String getPluginDir() {
        return new String(getPluginDirBinary(), StandardCharsets.US_ASCII);
    }
}
