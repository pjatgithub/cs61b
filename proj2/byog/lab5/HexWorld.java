package byog.lab5;

import static org.junit.Assert.assertEquals;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import org.junit.Test;

import java.util.Random;

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
     * @param p position of the bottom-left vertex of the hexagon.
     * @param size size of the hexagon.
     * @param filled tile filled into the hexagon.
     */
    public static void addHexagon(TETile[][] world, Position p,
                                  int size, TETile filled) {
        int width = getHexagonWidth(size);
        int height = getHexagonHeight(size);

        for (int i = 0, initialLength = 2; i < size; i++) {
            int length = initialLength + 2 * i;
            int offset = startForCentral(height, length);

            fillCol(world[p.getX() + i], p.getY() + offset,
                    length, filled);
        }

        for (int i = 0, span = size - 2; i < span; i++) {
            fillCol(world[p.getX() + size + i], p.getY(), height, filled);
        }

        for (int i = 0; i < size; i++) {
            int length = height - 2 * i;
            int offset = startForCentral(height, length);

            fillCol(world[p.getX() + width - size + i],
                    p.getY() + offset, length, filled);
        }
    }

    /**
     * Draws a column of hexagons.
     * @param world world to which a column of hexagons are added.
     * @param p position of bottom-left vertex of the bottom-most hexagon.
     * @param size size of hexagons.
     * @param number number of hexagons.
     * @param filled a array of tiles filled into the column (first is bottom-most).
     *
     * @throws IllegalArgumentException if {@code size} or {@code number} is not
     *         positive, or length of {@code number} does not equal {@code number}.
     */
    private static void addHexagonColumn(TETile[][] world, Position p,
                                        int size, int number, TETile[] filled) {
        if (number <= 0 || number != filled.length) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < number; i++) {
            addHexagon(world, p, size, filled[i]);

            p = topNeighbor(p, size);
        }
    }

    /**
     * Draws a tesselation of hexagons. Every hexagon is filled with randomly
     * selected tiles.
     *
     * @param world the world on which hexagons are drawn.
     * @param p position of the bottom-left hexagon.
     * @param size size of hexagons.
     * @param length length of the edge.
     */
    public static void drawHexagonTesselation(TETile[][] world, Position p,
                                              int size, int length) {
        Random random = new Random();
        TETile[] candidates = {
                Tileset.WALL, Tileset.FLOOR, Tileset.GRASS, Tileset.WATER,
                Tileset.SAND, Tileset.MOUNTAIN, Tileset.TREE
        };
        TETile[] filled;
        int i, j;

        for (i = 0, j = length; i < length - 1; i++, j++) {
            filled = select(candidates, random, j);
            addHexagonColumn(world, p, size, j, filled);

            p = bottomRightNeighbor(p, size);
        }

        filled = select(candidates, random, j);
        addHexagonColumn(world, p, size, j, filled);

        p = topRightNeighbor(p, size);
        for (i =0, j--; i < length - 1; i++, j--) {
            filled = select(candidates, random, j);
            addHexagonColumn(world, p, size, j, filled);

            p = topRightNeighbor(p, size);
        }
    }

    /**
     * Randomly selects {@code number} tiles from {@code candidates}.
     * @param candidates tiles from which the result is selected.
     * @param random random generator used to select tiles.
     * @param number number of tiles being selected.
     * @return randomly selected tiles.
     */
    private static TETile[] select(TETile[] candidates, Random random,
                                   int number) {
        TETile[] result = new TETile[number];

        for (int i = 0; i < number; i++) {
            result[i] = candidates[random.nextInt(number)];
        }

        return result;
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
     * @throws IllegalArgumentException if {@code rowLength} or {@code length}
     *         is not positive, or {@code length} is greater than {@code rowLength}.
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

    /**
     * Gets the position of the neighbor at the top of the give hexagon.
     *
     * @param p position of the bottom-left vertex of the given hexagon.
     * @param size size of the given hexagon.
     * @return position of bottom-left vertex of the neighbor.
     */
    private static Position topNeighbor(Position p, int size) {
        int height = getHexagonHeight(size);

        return new Position(p.getX(), p.getY() + height);
    }

    /**
     * Gets the position of the neighbor at the bottom-right of the give hexagon.
     *
     * @param p position of the bottom-left vertex of the given hexagon.
     * @param size size of the given hexagon.
     * @return position of bottom-left vertex of the neighbor.
     */
    private static Position bottomRightNeighbor(Position p, int size) {
        int width = getHexagonWidth(size);
        int xOffset = width - size + 1;
        int yOffset = -size;

        return new Position(p.getX() + xOffset, p.getY() + yOffset);
    }

    /**
     * Gets the position of the neighbor at the top-right of the give hexagon.
     *
     * @param p position of the bottom-left vertex of the given hexagon.
     * @param size size of the given hexagon.
     * @return position of bottom-left vertex of the neighbor.
     */
    private static Position topRightNeighbor(Position p, int size) {
        int width = getHexagonWidth(size);
        int xOffset = width - size + 1;
        int yOffset = size;

        return new Position(p.getX() + xOffset, p.getY() + yOffset);
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

    @Test
    public void testTopNeighbor() {
        int size = 3;
        Position p1 = new Position(0, 0);
        Position p2 = new Position(0, 6);
        Position p3 = new Position(0, 12);

        assertEquals(p2, topNeighbor(p1, size));
        assertEquals(p3, topNeighbor(p2, size));
    }

    @Test
    public void testBottomRightNeighbor() {
        int size = 3;
        Position p1 = new Position(0, 6);
        Position p2 = new Position(5, 3);
        Position p3 = new Position(10, 0);

        assertEquals(p2, bottomRightNeighbor(p1, size));
        assertEquals(p3, bottomRightNeighbor(p2, size));
    }

    @Test
    public void testTopRightNeighbor() {
        int size = 3;
        Position p1 = new Position(10, 0);
        Position p2 = new Position(15, 3);
        Position p3 = new Position(20, 6);

        assertEquals(p2, topRightNeighbor(p1, size));
        assertEquals(p3, topRightNeighbor(p2, size));
    }

    public static void main(String[] args) {
        int width = 80, height = 80;
        TERenderer renderer = new TERenderer();
        renderer.initialize(width, height);

        TETile[][] world = new TETile[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }

//        int x = 0, y = 0, size = 3;
//        addHexagon(world, new Position(x, y), size, Tileset.FLOWER);

        int size = 3;
        int length = 4;
//        int number = 3;
        Position p = new Position(15, 15);
//        TETile[] filled = {Tileset.FLOOR, Tileset.GRASS, Tileset.FLOWER};
//        addHexagonColumn(world, p, size, number, filled);
        drawHexagonTesselation(world, p, size, length);

        renderer.renderFrame(world);
    }
}