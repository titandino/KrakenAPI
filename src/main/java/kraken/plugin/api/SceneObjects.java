package kraken.plugin.api;

/**
 * A provider of objects.
 *
 * These methods may increase RAM usage significantly due to the amount of objects available
 * in the scene.
 */
public final class SceneObjects {

    private SceneObjects() { }

    /**
     * Finds the closest object matching the provided filter.
     *
     * @param filter The filter objects must pass through in order to be accepted.
     * @return The found object, or NULL if one was not found.
     */
    public static native SceneObject closest(Filter<SceneObject> filter);

    /**
     * Iterates over each object.
     *
     * @param cb The callback for invoke for each object.
     */
    public static native void forEach(Action1<SceneObject> cb);

    /**
     * Retrieves all objects.
     *
     * @return All objects in the scene.
     */
    public static native SceneObject[] all();

    /**
     * Retrieves all objects at the provided coordinate.
     */
    public static native SceneObject[] at(int x, int y);

}
