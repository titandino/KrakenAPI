package kraken.plugin.api;

import java.util.Objects;

/**
 * An item on the ground.
 */
public class GroundItem extends Entity {

    // internal values, attempting to use these will break the client

    private int internal10;

    private int id;
    private int amount;

    /**
     * Do not make instances of this.
     */
    private GroundItem() { }

    /**
     * Retrieves the id of the item stack.
     *
     * @return The id of the item stack.
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the amount of item in the item stack.
     *
     * @return The amount of item in the item stack.
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Retrieves the name of this item.
     *
     * @return The name of this item.
     */
    public String getName() {
        return Cache.getItem(id).getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GroundItem that = (GroundItem) o;
        return internal10 == that.internal10;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), internal10);
    }
}
