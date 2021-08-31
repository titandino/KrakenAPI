package kraken.plugin.api;

import static kraken.plugin.api.Actions.*;

/**
 * An object within the game world.
 */
public class SceneObject extends Entity {

    /**
     * Do not make instances of this.
     */
    SceneObject() { }

    private int id;
    private boolean hidden;
    private Vector2i size;

    private static final int OPTION_NAME_MAP[] = {
            MENU_EXECUTE_OBJECT1,
            MENU_EXECUTE_OBJECT2,
            MENU_EXECUTE_OBJECT3,
            MENU_EXECUTE_OBJECT4,
            MENU_EXECUTE_OBJECT5,
            MENU_EXECUTE_OBJECT6,
    };

    /**
     * Retrieves the id of this object.
     *
     * @return The id of this object.
     */
    public int getId() {
        return id;
    }

    /**
     * Determines if this object has been hidden.
     *
     * @return If this object has been hidden.
     */
    public boolean hidden() {
        return hidden;
    }

    /**
     * Interacts with this object.
     */
    public void interact(int type) {
        entity(this, type);
    }

    /**
     * Interacts with this object.
     */
    public void interact(int type, int xOff, int yOff) {
        entity(this, type, xOff, yOff);
    }

    /**
     * Interacts with this object.
     */
    public boolean interact(String option) {
        String[] options = Cache.getObject(id).getOptionNames();
        if (options == null) {
            return false;
        }

        int m = Math.min(OPTION_NAME_MAP.length, options.length);
        for (int i = 0; i < m; i++) {
            if (option.equalsIgnoreCase(options[i])) {
                interact(OPTION_NAME_MAP[i]);
                return true;
            }
        }

        return false;
    }

    /**
     * Retrieves the size of this object.
     *
     * @return The size of this object.
     */
    public Vector2i getSize() {
        return size;
    }

    /**
     * Retrieves the name of this object.
     *
     * @return The name of this object.
     */
    public String getName() {
        return Cache.getObject(id).getName();
    }

    @Override
    public String toString() {
        return "SceneObject{" +
                "id=" + id +
                ", hidden=" + hidden +
                ", size=" + size +
                '}';
    }
}
