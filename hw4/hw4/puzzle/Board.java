package hw4.puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * {@code Board} is a class representing a board of 8-puzzle problems.
 *
 * @author Hongbin Jin
 */
public class Board implements WorldState {
    /**
     * Value representing a blank tile.
     */
    private static final int BLANK = 0;

    /**
     * Horizontal offset of neighbors.
     */
    private static final int[] HORIZONTAL_OFFSET = {-1, 0, 1, 0};

    /**
     * Vertical offset of neighbors.
     */
    private static final int[] VERTICAL_OFFSET = {0, -1, 0, 1};

    /**
     * 2D array represent tiles on a board.
     */
    private final int[][] tiles;

    /**
     * Index of the row at which the blank is.
     * Value -1 represents the index has not been detected, whereas non-negative
     * value indicates the index has not found.
     */
    private int blankRow = -1;

    /**
     * Index of the column at which the blank is.
     * Value -1 represents the index has not been detected, whereas non-negative
     * value indicates the index has not found.
     */
    private int blankCol = -1;

    /**
     * Cache for the sum of all tiles' Hamming distance to their goal position.
     * Value -1 means the sum has not been calculated.
     */
    private int hDist = -1;

    /**
     * Cache for the sum of all tiles' Manhattan distance to their
     * goal position. Value -1 means the sum has not been calculated.
     */
    private int mDist = -1;

    /**
     * Cache for the hash code.
     */
    private int hash;

    /**
     * A flag is used to indicate whether the cache for the hash code is made.
     * It is false the hash code has not been cached, otherwise it is true.
     */
    private boolean hashed = false;

    /**
     * Creates a board.
     * @param t values corresponding with tiles on the board
     */
    public Board(int[][] t) {
        this(t, true);
    }

    /**
     * Creates a board.
     * @param t values corresponding with tiles on the board
     * @param needCopy indicates whether a deep copy is needed
     */
    private Board(int[][] t, boolean needCopy) {
        if (needCopy) {
            this.tiles = copy(t);
        } else {
            this.tiles = t;
        }
    }

    /**
     * Gets value at the given position.
     * @param i index of the row (starting from zero)
     * @param j index of the column (starting from zero)
     * @return value at the given position
     * @throws IndexOutOfBoundsException if indices are invalid
     */
    public int tileAt(int i, int j) {
        if (invalid(i) || invalid(j)) {
            throw new IndexOutOfBoundsException(
                    String.format("index (%d, %d)", i, j));
        }
        return tiles[i][j];
    }

    /**
     * Indicates whether the given index is invalid.
     * @param i index
     * @return true if the index if invalid
     */
    private boolean invalid(int i) {
        return i < 0 || i >= size();
    }

    /**
     * Gets the size of the board.
     * @return size of the board
     */
    public int size() {
        return tiles.length;
    }

    /**
     * Gets a board with neighboring state of this board.
     * @return a new board with neighboring state
     */
    public Iterable<WorldState> neighbors() {
        List<WorldState> neighbors = new ArrayList<>();
        findBlank();

        for (int i = 0; i < HORIZONTAL_OFFSET.length; i++) {
            int offsetRow = HORIZONTAL_OFFSET[i];
            int offsetCol = VERTICAL_OFFSET[i];
            int row = blankRow + offsetRow;
            int col = blankCol + offsetCol;

            if (invalid(row) || invalid(col)) {
                continue;
            }

            int[][] newTiles = copy(tiles, blankRow, row);
            swap2D(newTiles, row, col, blankRow, blankCol);
            Board neighbor = new Board(newTiles, false);
            // Calculate distance estimates with differential
            int hDiff = neighbor.hamming(blankRow, blankCol)
                        - hamming(row, col);
            int mDiff = neighbor.manhattan(blankRow, blankCol)
                        - manhattan(row, col);
            neighbor.hDist = hamming() + hDiff;
            neighbor.mDist = manhattan() + mDiff;
            neighbors.add(neighbor);
        }

        return neighbors;
    }

    /**
     * Swaps the content of 2D array.
     * @param t 2D array
     * @param row1 index of row of position 1
     * @param row2 index of column of position 1
     * @param col1 index of row of position 2
     * @param col2 index of column of position 2
     */
    private void swap2D(int[][] t, int row1, int col1, int row2, int col2) {
        int tmp = t[row1][col1];
        t[row1][col1] = t[row2][col2];
        t[row2][col2] = tmp;
    }

    /**
     * Copies the underlying array representing tiles on the board.
     * @param tiles original tiles
     * @param exceptRow1 index of one of two rows not being copied.
     * @param exceptRow2 index of another row not being copied.
     * @return a deep copy of tiles
     */
    private static int[][] copy(int[][] tiles, int exceptRow1, int exceptRow2) {
        int size = tiles.length;
        int[][] t = new int[size][];

        for (int i = 0; i < size; i++) {
            if (i == exceptRow1 || i == exceptRow2) {
                t[i] = Arrays.copyOf(tiles[i], size);
            } else {
                t[i] = tiles[i];
            }
        }

        return t;
    }

    /**
     * Copies the underlying array representing tiles on the board.
     * @param tiles original tiles
     * @return a deep copy of original tiles
     */
    private static int[][] copy(int[][] tiles) {
        int size = tiles.length;
        int[][] t = new int[size][];

        for (int i = 0; i < size; i++) {
            t[i] = Arrays.copyOf(tiles[i], size);
        }

        return t;
    }

    /**
     * Gets estimated distance to the goal with Hamming distance.
     * @return number of tiles in the wrong position
     */
    public int hamming() {
        if (hDist < 0) {
            int dist = 0;
            int size = size();

            for (int r = 0; r < size; r++) {
                for (int c = 0; c < size; c++) {
                    if (tileAt(r, c) != BLANK) {
                        dist += hamming(r, c);
                    }
                }
            }
            hDist = dist;
        }

        return hDist;
    }

    /**
     * Gets the Hamming distance at the given position.
     * @param row index of row
     * @param col index of column
     * @return 1 if the tile at the wrong position,
     *         0 otherwise
     */
    private int hamming(int row, int col) {
        int size = size();
        int expected = size * row + col + 1;
        int actual = tileAt(row, col);

        return actual == expected ? 0 : 1;
    }

    /**
     * Gets estimated distance to the goal with Manhattan distance.
     * @return The sum of the Manhattan distances (sum of the vertical
     *         and horizontal distance) from the tiles to their goal positions
     */
    public int manhattan() {
        if (mDist < 0) {
            int dist = 0;
            int size = size();

            for (int r = 0; r < size; r++) {
                for (int c = 0; c < size; c++) {
                    if (tileAt(r, c) != BLANK) {
                        dist += manhattan(r, c);
                    }
                }
            }
            mDist = dist;
        }

        return mDist;
    }

    /**
     * Gets the Manhattan distance at the given position.
     * @param row index of row
     * @param col index of column
     * @return sum of the vertical and horizontal distance to its goal position
     */
    private int manhattan(int row, int col) {
        int size = size();
        int value = tileAt(row, col) - 1;
        int rowExpected = value / size;
        int colExpected = value % size;

        return Math.abs(row - rowExpected) + Math.abs(col - colExpected);
    }

    /**
     * Gets distance estimate to the goal position.
     * @return distance estimate
     */
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    /**
     * Finds the position of blank.
     */
    private void findBlank() {
        if (blankRow >= 0 && blankCol >= 0) {
            return;
        }
        int N = size();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tileAt(i, j) == BLANK) {
                    blankRow = i;
                    blankCol = j;
                    return;
                }
            }
        }
    }

    /**
     * Indicates whether this board is equal to another.
     * @param y another board
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        } else if (!(y instanceof Board)) {
            return false;
        }

        Board other = (Board) y;
        // Check size
        if (this.size() != other.size()) {
            return false;
        }
        if (this.tiles == other.tiles) {
            return true;
        }
        // Check underlying array
        for (int i = 0, size = size(); i < size; i++) {
            if (!Arrays.equals(this.tiles[i], other.tiles[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Get hash code.
     * @return hash code
     */
    @Override
    public int hashCode() {
        if (!hashed) {
            int h = 1;
            for (int[] column : tiles) {
                h = h * 31 + Arrays.hashCode(column);
            }
            hash = h;
            hashed = true;
        }
        return hash;
    }

    /**
     * Returns the string representation of the board.
     * @return a string representing this board
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }
}
