package kraken.plugin.api;

import java.util.HashMap;
import java.util.Map;

/**
 * A spirit in the game world.
 */
public class Spirit extends Entity {

    private int serverIndex;
    private boolean isMoving;
    private Map<Integer, Boolean> activeStatusBars = new HashMap<>();
    private Map<Integer, Float> statusBarFill = new HashMap<>();
    private int animationId;
    private boolean isAnimationPlaying;
    private int interactingIndex;
    private Vector2 directionOffset;

    /**
     * Do not make instances of this.
     */
    Spirit() { }

    public Map<Integer, Boolean> getActiveStatusBars() {
        return activeStatusBars;
    }

    public Map<Integer, Float> getStatusBarFill() {
        return statusBarFill;
    }

    /**
     * Retrieves this spirit's server index.
     *
     * @return This spirit's server index.
     */
    public int getServerIndex() {
        return serverIndex;
    }

    /**
     * Determines if this spirit is currently moving.
     * @return If this spirit is currently moving.
     */
    public boolean isMoving() {
        return isMoving;
    }

    /**
     * Determines if a status bar is active.
     * @return If the status bar with the provided id is active.
     */
    public boolean isStatusBarActive(int id) {
        return activeStatusBars.containsKey(id) && activeStatusBars.get(id);
    }

    /**
     * Retrieves the fill of a status bar (0-1.)
     * @return The fill of a status bar.
     */
    public float getStatusBarFill(int id) {
        return statusBarFill.getOrDefault(id, 0.0f);
    }

    /**
     * Retrieves the id of the playing animation.
     *
     * @return The id of the playing animation, or -1 if no animation is playing.
     */
    public int getAnimationId() {
        return animationId;
    }

    /**
     * Determines if this spirit has an animation playing.
     *
     * @return If this spirit has an animation playing.
     */
    public boolean isAnimationPlaying() {
        return isAnimationPlaying;
    }

    /**
     * Retrieves the server index of the spirit being interacted with.
     *
     * @return The server index of the spirit being interacted with.
     */
    public int getInteractingIndex() {
        return interactingIndex;
    }

    /**
     * Retrieves the directional offset of this spirit.
     *
     * @return The directional offset of this spirit.
     */
    public Vector2 getDirectionOffset() {
        return directionOffset;
    }

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
