package lab11.graphs;

import edu.princeton.cs.algs4.Stack;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    public MazeCycles(Maze m) {
        super(m);
    }

    @Override
    public void solve() {
        dfs();
    }

    // Helper methods go here
    private void dfs() {
        int numV = maze.V();
        int[] edgeTo = new int[numV];
        Stack<Integer> stack = new Stack<>();
        stack.push(0);

        outer:
        while (!stack.isEmpty()) {
            int vertex = stack.pop();
            marked[vertex] = true;
            announce();

            for (int neighbor : maze.adj(vertex)) {
                if (!marked[neighbor]) {
                    edgeTo[neighbor] = vertex;
                    stack.push(neighbor);
                } else if (neighbor != edgeTo[vertex]) {
                    this.edgeTo[neighbor] = vertex;
                    for (int w = vertex; w != neighbor; w = edgeTo[w]) {
                        this.edgeTo[w] = edgeTo[w];
                    }

                    announce();
                    break outer;
                }
            }
        }
    }
}

