package kraken.plugin.api;

/**
 * A player within the game world.
 */
public class Player extends Spirit {

    public static final int ADRENALINE = 5;

    /**
     * Do not make instances of this.
     */
    private Player() { }

    @Override
    public String toString() {
        return "Player{" +
                "serverIndex=" + getServerIndex() +
                '}';
    }
}
