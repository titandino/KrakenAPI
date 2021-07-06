package kraken.plugin.api;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Provides access to the local player's inventory.
 */
public class Inventory {

    private Inventory() { }

    /**
     * Retrieves all items in the inventory.
     *
     * @return All items in the inventory.
     */
    public static native WidgetItem[] getItems();

    /**
     * Counts the number of items with the provided id.
     *
     * @param id The id of the item to search for.
     * @return The number of items with the provided id.
     */
    public static native int count(int id);

    /**
     * Finds the first widget item that passes the provided filter.
     *
     * @param filter The filter that items must pass through in order to be accepted.
     * @return The first item that passed the filter.
     */
    public static native WidgetItem first(Filter<WidgetItem> filter);

    /**
     * Determines if the inventory is full.
     *
     * @return If the inventory is full.
     */
    public static boolean isFull() {
        return getItems().length == 28;
    }

    /**
     * Determines if the inventory is empty.
     *
     * @return If the inventory is empty.
     */
    public static boolean isEmpty() {
        return getItems().length == 0;
    }

    /**
     * Determines if the inventory contains an item.
     *
     * @return If the inventory contains an item with the provided id.
     */
    public static boolean contains(int id) {
        return count(id) > 0;
    }

    /**
     * Iterates over each of the elements in the inventory.
     *
     * @param cb The callback to invoke with each element.
     */
    public static void forEach(ElementCallback<WidgetItem> cb) {
        for (WidgetItem item : getItems()) {
            if (item != null) {
                cb.iterate(item);
            }
        }
    }
}
