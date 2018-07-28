package byog.lab5;

import java.util.Objects;

/**
 * {@code Position} represents a tile in the world.
 */
public class Position {
    private int x;
    private int y;

    /**
     * Creates a position representing a tile in the tile.
     * @param x x coordinate (from left to right) of the tile.
     * @param y y coordinate (from bottom to top) of the tile.
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x coordinate of the tile.
     * @return x coordinate of the tile.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y coordinate of the tile.
     * @return y coordinate of the tile.
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the hash code of the position.
     * @return hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Compares this position with another.
     * @param obj another position.
     * @return true if two positions' x coordinates and y coordinates are equal
     *         respectively.
     *
     * @throws ClassCastException if object is not an instance of {@code Position}
     *                            or its subclass.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Position)) {
            return false;
        }

        Position other = (Position) obj;

        return this.x == other.x && this.y == other.y;
    }
}
