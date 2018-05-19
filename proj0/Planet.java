public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

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
}
