package lab11.graphs;

import java.util.LinkedList;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int source;
    private int target;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        source = maze.xyTo1D(sourceX, sourceY);
        target = maze.xyTo1D(targetX, targetY);
        distTo[source] = 0;
        edgeTo[source] = source;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(source);

        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            marked[vertex] = true;
            announce();

            if (vertex == target) {
                break;
            }

            for (int neighbor : maze.adj(vertex)) {
                if (!marked[neighbor]) {
                    edgeTo[neighbor] = vertex;
                    distTo[neighbor] = distTo[vertex] + 1;
                    queue.offer(neighbor);
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

