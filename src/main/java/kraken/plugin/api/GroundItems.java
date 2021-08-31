package kraken.plugin.api;

/**
 * A provider of ground items.
 */
public final class GroundItems {

    private GroundItems() { }

    /**
     * Finds the closest ground item matching the provided filter.
     *
     * @param filter The filter ground items must pass through in order to be accepted.
     * @return The found ground item, or NULL if one was not found.
     */
    public static native GroundItem closest(Filter<GroundItem> filter);

}
