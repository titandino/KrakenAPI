package kraken.plugin.api;

import java.nio.charset.StandardCharsets;

/**
 * An item configuration from the cache.
 */
public class CacheItem extends AsyncData {

    private int id = 0;
    private long address = 0;
    private byte[] binaryName = "N/A".getBytes(StandardCharsets.US_ASCII);
    private byte[][] binaryOptionNames = new byte[0][];

    public CacheItem(int id) {
        this.id = id;
    }

    /**
     * @return The id of the item.
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the address of this cache descriptor in memory.
     * This will only be valid in developer builds.
     *
     * @return The address of this cache descriptor in memory.
     */
    public long getAddress() {
        return address;
    }

    /**
     * @return The name of the item.
     */
    public String getName() {
        return new String(binaryName);
    }

    /**
     * @return The name of options when right clicking this item.
     */
    public String[] getOptionNames() {
        String[] options = new String[binaryOptionNames.length];
        for (int i = 0; i < binaryOptionNames.length; i++) {
            options[i] = binaryOptionNames[i] == null ? "" : new String(binaryOptionNames[i]);
        }
        return options;
    }

    /**
     * Determines if this item is loaded.
     *
     * @return If this item is loaded.
     */
    @Override
    public boolean isLoaded() {
        return super.isLoaded() && binaryName != null && binaryOptionNames != null;
    }
}
