package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.HashMap;
import java.util.Map;

/**
 * {@code Solver} is used current solve puzzle problems.
 */
public class Solver {
    private Map<WorldState, WorldState> from;
    private Map<WorldState, Integer> total;
    private MinPQ<SearchNode> pq;
    private boolean solved = false;
    private Iterable<WorldState> moves;
    private int numMoves;
    private WorldState initial;
    /**
     * Creates an solver.
     * @param initial initial state of a puzzle
     */
    public Solver(WorldState initial) {
        this.initial = initial;
    }

    /**
     * Solves the puzzle.
     */
    private void solve() {
        from = new HashMap<>();
        total = new HashMap<>();
        pq = new MinPQ<>();
        pq.insert(new SearchNode(null, initial, 0, 0));
        total.put(initial, 0);

        while (!pq.isEmpty()) {
            SearchNode node = pq.delMin();

            if (node.current.isGoal()) {
                numMoves = node.total;
                moves = getMoves(node.current);
                break;
            }
            // Relaxes current node
            relax(node);
        }

        from = null;
        total = null;
        pq = null;
        solved = true;
    }

    /**
     * Relaxes a node.
     * @param node a search node
     */
    private void relax(SearchNode node) {
        for (WorldState neighbor : node.current.neighbors()) {
            int newTotal;
            if (!neighbor.equals(node.previous)
                    && total.getOrDefault(neighbor, Integer.MAX_VALUE) > (newTotal = node.total + 1)) {
                from.put(neighbor, node.current);
                total.put(neighbor, newTotal);
                pq.insert(new SearchNode(node.current, neighbor, newTotal, 1));
            }
        }
    }

    /**
     * Gets a sequence of moves from source to target.
     * @param wd target
     * @return a sequence of moves
     */
    private Iterable<WorldState> getMoves(WorldState wd) {
        Stack<WorldState> result = new Stack<>();

        while (wd != null) {
            result.push(wd);
            wd = from.get(wd);
        }
        return result;
    }

    private class SearchNode implements Comparable<SearchNode> {
        private final WorldState previous, current;
        private final int total, price, h;
        private final boolean isGoal;

        /**
         * Creates a node for searching.
         * @param previous previous node
         * @param current current node
         * @param total total cost from initial node to current node
         * @param price cost from previous node to current node
         */
        private SearchNode(WorldState previous, WorldState current, int total, int price) {
            this.previous = previous;
            this.current = current;
            this.total = total;
            this.price = price;
            this.h = current.estimatedDistanceToGoal();
            this.isGoal = current.isGoal();
        }

        /**
         * Compares with another node.
         * @param o another node
         * @return  positive integer if this node larger than another node,
         *          negative interger if this node less than another node,
         *          zero if two nodes are equal.
         */
        @Override
        public int compareTo(SearchNode o) {
            return Integer.compare(this.total + this.h, o.total + o.h);
        }
    }

    /**
     * Gets the minimum number of moves for solving the puzzle.
     * @return minimum number of moves
     */
    public int moves() {
        if (!solved) {
            solve();
        }

        return numMoves;
    }

    /**
     * Gets a sequence of moves current the solution.
     * @return a sequence of moves
     */
    public Iterable<WorldState> solution() {
        if (!solved) {
            solve();
        }

        return moves;
    }
}
