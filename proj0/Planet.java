public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    public static final double G = 6.67e-11;

    /**
     *  Initializes a planet with the given position, velocity, mass and the
     *  name image file depicting the planet.
     *
     *  @param xxPos x component of the position
     *  @param yyPos y component of the position
     *  @param xxVel x component of the velocity
     *  @param yyVel x component of the velocity
     *  @param mass the mass of the planet
     *  @param imgFileName the name of an image file representing the planet
     */
    public Planet(double xxPos, double yyPos, double xxVel,
                  double yyVel, double mass, String imgFileName) {
        this.xxPos = xxPos;
        this.yyPos = yyPos;
        this.xxVel = xxVel;
        this.yyVel = yyVel;
        this.mass = mass;
        this.imgFileName = imgFileName;
    }

    /**
     *  Makes a copy of the given {@code Planet}
     *
     *  @param p the planet to be copied
     */
    public Planet(Planet p) {
        this.xxPos = p.xxPos;
        this.yyPos = p.yyPos;
        this.xxVel = p.xxVel;
        this.yyVel = p.yyVel;
        this.mass = p.mass;
        this.imgFileName = p.imgFileName;
    }

    /**
     *  Calculates the distance between {@code this} and {@code other}.
     *
     *  @param other another {@code Planet}
     *  @return the distance
     */
    public double calcDistance(Planet other) {
        double xDiff = this.xxPos - other.xxPos;
        double yDiff = this.yyPos - other.yyPos;

        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    /**
     *  Calculates the gravitational force by {@code other}.
     *
     *  @param other another {@code Planet}
     *  @return the gravitational force exerted by {@code other}
     */
    public double calcForceExertedBy(Planet other) {
        if (this == other) {
            return 0.0;
        }

        double distance = this.calcDistance(other);
        return G * this.mass * other.mass / distance / distance;
    }

    /**
     *  Calculates x component of the gravitational force by {@code other}.
     *
     *  @param other another {@code Planet}
     *  @return x component of the gravitational force exerted by {@code other}
     */
    public double calcForceExertedByX(Planet other) {
        if (this == other) {
            return 0.0;
        }

        double force = this.calcForceExertedBy(other);
        double distance = this.calcDistance(other);
        double xDiff = other.xxPos - this.xxPos;
        return xDiff / distance * force;
    }

    /**
     *  Calculates y component of the gravitational force by {@code other}.
     *
     *  @param other another {@code Planet}
     *  @return y component of the gravitational force exerted by {@code other}
     */
    public double calcForceExertedByY(Planet other) {
        if (this == other) {
            return 0.0;
        }

        double force = this.calcForceExertedBy(other);
        double distance = this.calcDistance(other);
        double yDiff = other.yyPos - this.yyPos;
        return yDiff / distance * force;
    }

    /**
     *  Calculates x components of the gravitational forces exerted by an array
     *  of {@code Planet}.
     *
     *  @param planets an array of {@code Planet}
     *  @return x component of the gravitational forces
     */
    public double calcNetForceExertedByX(Planet[] planets) {
        double xForce = 0.0;

        for (Planet p : planets) {
            xForce += this.calcForceExertedByX(p);
        }

        return xForce;
    }

    /**
     *  Calculates y components of the gravitational forces exerted by an array
     *  of {@code Planet}.
     *
     *  @param planets an array of {@code Planet}
     *  @return y component of the gravitational forces
     */
    public double calcNetForceExertedByY(Planet[] planets) {
        double yForce = 0.0;

        for (Planet p : planets) {
            yForce += this.calcForceExertedByY(p);
        }

        return yForce;
    }

    /**
     *  Updates the position and velocity according to the given gravitational
     *  force.
     *
     *  @param dt a small period time spanned
     *  @param fX x component of the gravitational force
     *  @param fY y component of the gravitational force
     */
    public void update(double dt, double fX, double fY) {
        // Acceleration
        double aX = fX / this.mass;
        double aY = fY / this.mass;

        // Velocity
        double dvX = aX * dt;
        double dvY = aY * dt;
        this.xxVel += dvX;
        this.yyVel += dvY;

        // Position
        double dpX = this.xxVel * dt;
        double dpY = this.yyVel * dt;
        this.xxPos += dpX;
        this.yyPos += dpY;
    }
}
