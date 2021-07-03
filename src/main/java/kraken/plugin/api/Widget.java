package kraken.plugin.api;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static kraken.plugin.api.Text.filterSpecialChars;

/**
 * A widget.
 */
public class Widget {

    public static final int CONTAINER = 0;
    public static final int TEXT = 4;
    public static final int ITEM = 5;

    // internal values, attempting to use these will break the client

    private long internal1;

    /**
     * Do not make instances of this.
     */
    private Widget() { }

    /**
     * Retrieves the type of this widget.
     *
     * @return The type of this widget.
     */
    public native int getType();

    /**
     * Retrieves the children in this widget.
     *
     * @return The children in this widget.
     */
    public native Widget[] getChildren();

    /**
     * Retrieves the text being stored in this widget.
     *
     * @return The text being stored in this widget, or NULL if the widget has no text.
     */
    public native byte[] getTextBinary();

    /**
     * Retrieves the text being stored in this widget.
     *
     * @return The text being stored in this widget, or NULL if the widget has no text.
     */
    public String getText() {
        byte[] bin = getTextBinary();
        if (bin == null) {
            return null;
        }

        return new String(filterSpecialChars(bin), StandardCharsets.US_ASCII);
    }

    /**
     * Retrieves the item being stored in this widget.
     *
     * @return The item being stored in this widget, or NULL if the widget has no item.
     */
    public native Item getItem();

    /**
     * Retrieves the position of this widget on the screen. May not be valid for all widgets.
     *
     * @return The position of this widget.
     */
    public native Vector2i getPosition();

    /**
     * Retrieves the size of this widget on the screen. May not be valid for all widgets.
     *
     * @return The size of this widget.
     */
    public native Vector2i getSize();

    @Override
    public String toString() {
        return "Widget{" +
                "type= " + getType() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Widget widget = (Widget) o;
        return internal1 == widget.internal1;
    }

    @Override
    public int hashCode() {
        return Objects.hash(internal1);
    }
}
