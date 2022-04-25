public class Body {
  
  private final static double G = 6.67e-11;
  
  /* Current x position */
  public double xxPos;
  /* Current y position */
  public double yyPos;
  /* Current velocity in the x direction */
  public double xxVel;
  /* Current velocity in the y direction */
  public double yyVel;
  /* Mass */
  public double mass;
  /* The name of the file that corresponds to the image that depicts the body */
  public String imgFileName;
  
  public Body(double xP, double yP, double xV, double yV, double m, String img) {
    this.xxPos = xP;
    this.yyPos = yP;
    this.xxVel = xV;
    this.yyVel = yV;
    this.mass = m;
    this.imgFileName = img;
  }
  
  public Body(Body b) {
    this.xxPos = b.xxPos;
    this.yyPos = b.yyPos;
    this.xxVel = b.xxVel;
    this.yyVel = b.yyVel;
    this.mass = b.mass;
    this.imgFileName = b.imgFileName;
  }
  
  public double calcDistance(final Body body) {
    final double xDist = body.xxPos - this.xxPos;
    final double yDist = body.yyPos - this.yyPos;
    final double distance = xDist * xDist + yDist * yDist;
    return Math.sqrt(distance);
  }
  
  public double calcForceExertedBy(final Body body) {
    final double distance = calcDistance(body);
    return G * this.mass * body.mass / (distance * distance);
  }
  
  public double calcForceExertedByX(final Body body) {
    final double distance = calcDistance(body);
    final double force = calcForceExertedBy(body);
    return force * (body.xxPos - this.xxPos) / distance;
  }

  public double calcForceExertedByY(final Body body) {
    final double distance = calcDistance(body);
    final double force = calcForceExertedBy(body);
    return force * (body.yyPos - this.yyPos) / distance;
  }
  
  public double calcNetForceExertedByX(final Body[] bodies) {
    double netForce = 0;
    for (final Body body : bodies) {
      if (this.equals(body)) {
        continue;
      }
      netForce += calcForceExertedByX(body);
    }
    return netForce;
  }

  public double calcNetForceExertedByY(final Body[] bodies) {
    double netForce = 0;
    for (final Body body : bodies) {
      if (this.equals(body)) {
        continue;
      }
      netForce += calcForceExertedByY(body);
    }
    return netForce;
  }
  
  public void update(final double dt, final double forceX, final double forceY) {
    final double ax = forceX / this.mass;
    final double ay = forceY / this.mass;
    this.xxVel += ax * dt;
    this.yyVel += ay * dt;
    this.xxPos += this.xxVel * dt;
    this.yyPos += this.yyVel * dt;
  }
  
  public void draw() {
    StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
  }
}
