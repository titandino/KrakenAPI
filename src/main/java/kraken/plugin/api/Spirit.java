package kraken.plugin.api;

/**
 * A spirit in the game world.
 */
public class Spirit extends Entity {

    /**
     * Do not make instances of this.
     */
    Spirit() { }

    /**
     * Retrieves this spirit's server index.
     *
     * @return This spirit's server index.
     */
    public native int getServerIndex();

    /**
     * Determines if this spirit is currently moving.
     * @return If this spirit is currently moving.
     */
    public native boolean isMoving();

    /**
     * Determines if a status bar is active.
     * @return If the status bar with the provided id is active.
     */
    public native boolean isStatusBarActive(int id);

    /**
     * Retrieves the fill of a status bar (0-1.)
     * @return The fill of a status bar.
     */
    public native float getStatusBarFill(int id);

    /**
     * Retrieves the id of the playing animation.
     *
     * @return The id of the playing animation, or -1 if no animation is playing.
     */
    public native int getAnimationId();

    /**
     * Determines if this spirit has an animation playing.
     *
     * @return If this spirit has an animation playing.
     */
    public native boolean isAnimationPlaying();

    /**
     * Retrieves the server index of the spirit being interacted with.
     *
     * @return The server index of the spirit being interacted with.
     */
    public native int getInteractingIndex();

    /**
     * Retrieves the directional offset of this spirit.
     *
     * @return The directional offset of this spirit.
     */
    public native Vector2 getDirectionOffset();

    /**
     * Retrieves the spirit being interacted with.
     *
     * @return The spirit being interacted with.
     */
    public Spirit getInteracting() {
        int index = getInteractingIndex();
        if (index == -1) {
            return null;
        }

        return (Spirit)Entities.byServerIndex(index);
    }

}
