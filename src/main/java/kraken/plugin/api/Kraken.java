package kraken.plugin.api;

import java.nio.charset.StandardCharsets;

/**
 * Provides access to kraken itself.
 */
public class Kraken {

    // these will be randomized, do not use
    public static final int PROTECTION_ID_DECRYPT_INT = 8851721;

    /**
     * Loads a new plugin into the client.
     *
     * @param entry The entry-point of the plugin to load.
     */
    public static native void loadNewPlugin(Class<?> entry);

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

    /**
     * Performs a call into the protection subsystem. Documentation will not be provided for this system.
     * This will be used internally, and in bundled plugins such as the SDN to provide increased protection
     * for paid products which cannot be provided in pure Java.
     *
     * @param id The protection id.
     * @param args The protection arguments.
     * @return The protection result.
     */
    public static native Object protection(int id, Object[] args);

}
