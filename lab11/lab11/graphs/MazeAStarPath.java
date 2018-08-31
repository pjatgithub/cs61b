package lab11.graphs;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        int vX = maze.toX(v), vY = maze.toY(v);
        int tX = maze.toX(t), tY = maze.toY(t);
        return Math.abs(tX - vX) + Math.abs(tY - vY);
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        Queue<Stop> queue = new PriorityQueue<>();
        queue.offer(new Stop(s, s, 0, h(s)));

        while (!queue.isEmpty()) {
            Stop stop = queue.poll();

            if (!marked[stop.to]) {
                marked[stop.to] = true;
                edgeTo[stop.to] = stop.from;
                distTo[stop.to] = stop.dist;
                announce();
            } else {
                continue;
            }

            if (stop.to == t) {
                break;
            }
            // Relax
            for (int neighbor : maze.adj(stop.to)) {
                int dist = distTo[stop.to] + stop.weight;
                if (distTo[neighbor] > dist) {
                    queue.offer(new Stop(stop.to, neighbor, dist, h(neighbor)));
                }
            }
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

    static class Stop implements Comparable<Stop> {
        int from, to, dist, h, weight;

        public Stop(int from, int to, int dist, int h) {
            this.from = from;
            this.to = to;
            this.dist = dist;
            this.h = h;
            weight = 1;
        }

        @Override
        public int compareTo(Stop o) {
            return Integer.compare(this.dist + this.h, o.dist + o.h);
        }
    }
}

