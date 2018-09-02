package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.HashMap;
import java.util.Map;

/**
 * {@code Solver} is used current solve puzzle problems.
 *
 * @author Hongbin Jin
 */
public class Solver {
    /**
     * A map records edges.
     */
    private Map<WorldState, WorldState> from;

    /**
     * A map stores minimum moves from the initial state to a state.
     */
    private Map<WorldState, Integer> total;

    /**
     * A priority queue holds all states to be examined.
     */
    private MinPQ<SearchNode> pq;

    /**
     * A flag indicates whether this puzzle has been solved.
     */
    private boolean solved = false;

    /**
     * A sequence of moves from the initial state to the goal state.
     */
    private Iterable<WorldState> moves;

    /**
     * Number of moves from the initial state to the goal state.
     */
    private int numMoves;

    /**
     * Initial state.
     */
    private WorldState initial;

    /**
     * Creates an solver.
     * @param init init state of a puzzle
     */
    public Solver(WorldState init) {
        this.initial = init;
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
            int newTotal = node.total + 1;
            if (!neighbor.equals(node.previous)
                    && total.getOrDefault(neighbor, Integer.MAX_VALUE)
                        > newTotal) {
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

    /**
     * {@code SearchNode} represents a node to be searched.
     */
    private class SearchNode implements Comparable<SearchNode> {
        /**
         * Previous state.
         */
        private final WorldState previous;

        /**
         * Current state.
         */
        private final WorldState current;

        /**
         * Total number of moves from the initial state to the current state.
         */
        private final int total;

        /**
         * Number of moves from the previous state to the current state.
         * In almost all cases, this value is 1.
         */
        private final int price;

        /**
         * Cache for estimated number of moves from the current state to the
         * goal state.
         */
        private final int h;

        /**
         * Creates a node for searching.
         * @param prev previous node
         * @param curr current node
         * @param dist total cost from initial node to current node
         * @param weight cost from previous node to current node
         */
        private SearchNode(WorldState prev, WorldState curr,
                           int dist, int weight) {
            this.previous = prev;
            this.current = curr;
            this.total = dist;
            this.price = weight;
            this.h = curr.estimatedDistanceToGoal();
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
