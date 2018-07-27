package byog.lab5;

import static org.junit.Assert.assertEquals;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import org.junit.Test;

/**
 * Draws a world consisting of hexagonal regions.
 *
 * @author Hongbin Jin
 */
public class HexWorld {
    /**
     * Adds a hexagon to the given world.
     *
     * @param world world to which the hexagon is added.
     * @param x x coordinate (from left to right) of the position at which the
     *          hexagon is added.
     * @param y y coordinate (from bottom to top) of the position at which the
     *          hexagon is added
     * @param size size of the hexagon.
     * @param filled tile filled into the hexagon.
     */
    public static void addHexagon(TETile[][] world, int x, int y,
                                  int size, TETile filled) {
        int width = getHexagonWidth(size);
        int height = getHexagonHeight(size);

        for (int i = 0, initialLength = 2; i < size; i++) {
            int length = initialLength + 2 * i;
            int offset = startForCentral(height, length);

            fillCol(world[x + i], y + offset, length, filled);
        }

        for (int i = 0, span = size - 2; i < span; i++) {
            fillCol(world[x + size + i], y, height, filled);
        }

        for (int i = 0; i < size; i++) {
            int length = height - 2 * i;
            int offset = startForCentral(height, length);

            fillCol(world[x + width - size + i], y + offset, length, filled);
        }
    }

    /**
     * Calculates the width of a hexagon, given the size of the hexagon.
     *
     * @param size size of the hexagon.
     * @return width of the hexagon.
     *
     * @throws IllegalArgumentException if {@code size} is non-positive.
     */
    private static int getHexagonWidth(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException();
        }

        return 3 * size - 2;
    }

    /**
     * Gets the starting position for centralization.
     *
     * @param rowLength length of the whole line.
     * @param length length of the content to be centralized.
     * @return starting position (maybe closer to the left end of the whole line).
     *
     * @throws
     */
    private static int startForCentral(int rowLength, int length) {
        if (rowLength <= 0 || length <= 0 || length > rowLength) {
            throw new IllegalArgumentException();
        }

        return (rowLength - length) / 2;
    }

    /**
     * Calculates the height of a hexagon, given the size of the hexagon.
     * @param size size of the hexagon.
     * @return height of the hexagon.
     *
     * @throws IllegalArgumentException if {@code size} is non-positive.
     */
    private static int getHexagonHeight(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException();
        }

        return 2 * size;
    }

    /**
     * Fills the column of tiles with the given tile.
     *
     * @param col column to be filled.
     * @param start starting position (inclusive) of the column to be filled.
     * @param length length of the column to be filled.
     * @param filled till filled.
     *
     * @throws NullPointerException if {@code col} is {@code null}.
     * @throws IllegalArgumentException if length of the column is negative.
     * @throws IndexOutOfBoundsException
     *         if accessing the data outside of the whole column.
     */
    private static void fillCol(TETile[] col, int start,
                                int length, TETile filled) {
        if (length < 0) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < length; i++) {
            col[start + i] = filled;
        }
    }

    @Test
    public void testGetHexagonWidth() {
        assertEquals(4, getHexagonWidth(2));
        assertEquals(7, getHexagonWidth(3));
        assertEquals(10, getHexagonWidth(4));
        assertEquals(13, getHexagonWidth(5));
    }

    @Test
    public void testGetHexagonHeight() {
        assertEquals(4, getHexagonHeight(2));
        assertEquals(6, getHexagonHeight(3));
        assertEquals(8, getHexagonHeight(4));
        assertEquals(10, getHexagonHeight(5));
    }

    @Test
    public void testStartForCentral() {
        assertEquals(4, startForCentral(10, 2));
        assertEquals(4, startForCentral(10, 1));
    }

    public static void main(String[] args) {
        int width = 60, height = 30;
        TERenderer renderer = new TERenderer();
        renderer.initialize(width, height);

        TETile[][] world = new TETile[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }

        addHexagon(world, 0, 0, 2, Tileset.FLOWER);
        renderer.renderFrame(world);
    }
}