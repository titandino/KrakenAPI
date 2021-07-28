package kraken.plugin.api;

import static kraken.plugin.api.Text.filterSpecialChars;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * A character.
 */
public class Entity {

    // internal values, attempting to use these will break the client

    private long internal1;
    private int internal2;
    private long internal3;

    /**
     * Do not make instances of this.
     */
    Entity() { }

    /**
     * Retrieves the name of this entity.
     *
     * @return The name of this entity.
     */
    private native byte[] getNameBinary();

    /**
     * Retrieves the name of this entity.
     *
     * @return The name of this entity.
     */
    public String getName() {
        byte[] bin = getNameBinary();
        if (bin == null) {
            return "Unknown";
        }

        return new String(filterSpecialChars(bin), StandardCharsets.US_ASCII);
    }

    /**
     * Retrieves this character's position within the 3d scene.
     *
     * @return This character's position within the 3d scene.
     */
    public native Vector3 getScenePosition();

    /**
     * Retrieves this character's global position within the world.
     *
     * @return This character's global position within the world.
     */
    public native Vector3i getGlobalPosition();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return internal1 == entity.internal1 &&
                internal2 == entity.internal2 &&
                internal3 == entity.internal3;
    }

    @Override
    public int hashCode() {
        return Objects.hash(internal1, internal2, internal3);
    }

}
