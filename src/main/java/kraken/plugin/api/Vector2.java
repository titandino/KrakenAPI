package kraken.plugin.api;

import java.util.Objects;

/**
 * A vector2.
 */
public class Vector2 {

    private float x;
    private float y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void add(float x, float y) {
        this.x += x;
        this.y += y;
    }

    public void sub(float x, float y) {
        this.x -= x;
        this.y -= y;
    }

    public void sub(Vector2 v) {
        this.x -= v.getX();
        this.y -= v.getY();
    }

    /**
     * Calculates the distance between 2 vectors.
     */
    public float distance(Vector2 other) {
        return (float)Math.sqrt(Math.pow(other.getX() - x, 2) + Math.pow(other.getY() - y, 2));
    }

    /**
     * Normalizes this vector.
     */
    public Vector2 normalize() {
        double length = Math.sqrt(getX() * getX() + getY() * getY());
        float x = 0.f;
        float y = 0.f;
        if (length != 0) {
            x = (float) (getX() / length);
            y = (float) (getY() / length);
        }

        return new Vector2(x, y);
    }

    @Override
    public String toString() {
        return "Vector2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2 vector2 = (Vector2) o;
        return Float.compare(vector2.x, x) == 0 &&
                Float.compare(vector2.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
