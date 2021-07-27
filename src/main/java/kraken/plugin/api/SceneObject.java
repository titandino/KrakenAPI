package kraken.plugin.api;

/**
 * An object within the game world.
 */
public class SceneObject extends Entity {

    /**
     * Do not make instances of this.
     */
    SceneObject() { }

    /**
     * Retrieves the id of this object.
     *
     * @return The id of this object.
     */
    public native int getId();

    /**
     * Determines if this object has been hidden.
     *
     * @return If this object has been hidden.
     */
    public native boolean hidden();

    /**
     * Interacts with this object.
     */
    public void interact(int type) {
        Actions.entity(this, type);
    }

    /**
     * Interacts with this object.
     */
    public void interact(int type, int xOff, int yOff) {
        Actions.entity(this, type, xOff, yOff);
    }


}
