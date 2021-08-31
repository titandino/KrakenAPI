package kraken.plugin.api;

/**
 * An effect within the game world (glowing particles, glowing orbs, etc.)
 */
public class Effect extends Entity {

    /**
     * Do not make instances of this.
     */
    private Effect() { }

    private int id;

    /**
     * Retrieves the id of the effect.
     *
     * @return The id of the effect.
     */
    public int getId() {
        return id;
    }

}
