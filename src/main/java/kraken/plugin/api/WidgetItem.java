package kraken.plugin.api;

import java.util.Objects;

public class WidgetItem extends Item {

    private int slot;

    public WidgetItem() {
    }

    public WidgetItem(int id, int amount, int slot) {
        super(id, amount);
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    @Override
    public String toString() {
        return "WidgetItem{" +
                "id=" + getId() +
                ", amount= " + getAmount() +
                ", slot=" + slot +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        WidgetItem that = (WidgetItem) o;
        return slot == that.slot;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), slot);
    }
}
