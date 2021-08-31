package kraken.plugin.api;

/**
 * A provider of widgets.
 */
public final class Widgets {

    private Widgets() { }

    /**
     * Retrieves a widget group by id.
     *
     * @param id The id of the widget to retrieve.
     * @return The group with the provided id, or NULL if one was not found.
     */
    public static native WidgetGroup getGroupById(int id);

}
