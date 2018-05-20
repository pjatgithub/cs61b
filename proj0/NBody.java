/**
 *  {@code NBody} is intended to simulate the universe.
 */
public class NBody {
    public static final String BACKGROUND_FILE_NAME = "images/starfield.jpg";
    public static final int PAUSE_INTERVAL = 10;

    /**
     *  Reads the radius of the universe.
     *
     *  @param dataFileName name of the file representing the universe
     *  @return radius of the universe
     */
    public static double readRadius(String dataFileName) {
        In in = new In(dataFileName);
        double radius;

        in.readInt(); // skip size
        radius = in.readDouble();
        in.close();

        return radius;
    }

    /**
     *  Reads an array of {@ Planet} of the universe.
     *
     *  @param dataFileName name of the file representing the universe
     *  @return an array of {@code Planet}
     */
    public static Planet[] readPlanets(String dataFileName) {
        int size;
        In in = new In(dataFileName);
        Planet[] planets;

        size = in.readInt();
        in.readDouble(); // skip radius
        planets = new Planet[size];

        for (int i = 0; i < size; i++) {
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            String imgFileName = in.readString();

            planets[i] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
        }

        return planets;
    }

    public static void main(String[] args) {
        double T, dt;
        String dataFileName;
        double radius;
        Planet[] planets;

        T = Double.parseDouble(args[0]);
        dt = Double.parseDouble(args[1]);
        dataFileName = args[2];
        radius = readRadius(dataFileName);
        planets = readPlanets(dataFileName);

        StdOut.printf("T = %f, dt = %f%n", T, dt);
        StdOut.printf("A universe with %d planets and the radius of %f%n",
                          planets.length, radius);

        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-radius, radius);
        drawAll(planets);

        for (double t = 0.0; t < T; t += dt) {
            updateAll(planets, dt);
            drawAll(planets);
            StdDraw.pause(PAUSE_INTERVAL);
        }

        StdOut.printf("%d%n", planets.length);
        StdOut.printf("%.2e%n", radius);

        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s%n",
                          planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                          planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }

    // Draws the background image and planets.
    private static void drawAll(Planet[] planets) {
        StdDraw.clear();
        StdDraw.picture(0, 0, BACKGROUND_FILE_NAME);

        for (Planet p : planets) {
            p.draw();
        }

        StdDraw.show();
    }

    // Updates states of all planets.
    private static void updateAll(Planet[] planets, double dt) {
        int size = planets.length;
        double[] xForces = new double[size];
        double[] yForces = new double[size];

        for (int i = 0; i < size; i++) {
            xForces[i] = planets[i].calcNetForceExertedByX(planets);
            yForces[i] = planets[i].calcNetForceExertedByY(planets);
        }

        for (int i = 0; i < size; i++) {
            planets[i].update(dt, xForces[i], yForces[i]);
        }
    }
}
