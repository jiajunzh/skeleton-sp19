public class NBody {
  public static double readRadius(final String filename) {
    final In in = new In(filename);
    in.readInt();
    return in.readDouble();
  }
  
  public static Body[] readBodies(final String filename) {
    final In in = new In(filename);
    final int n = in.readInt();
    in.readDouble();
    final Body[] bodies = new Body[n];
    for (int i = 0; i < n; i++) {
      bodies[i] = new Body(
        in.readDouble(), 
        in.readDouble(),
        in.readDouble(), 
        in.readDouble(), 
        in.readDouble(), 
        in.readString()
      );
    }
    return bodies;
  }
  
  public static void main(String[] args) {
    if (args.length < 3) {
      throw new IllegalArgumentException("The number of argument is less than 3");
    }
    
    final double T = Double.parseDouble(args[0]);
    final double dt = Double.parseDouble(args[1]);
    final String filename = args[2];
    final Body[] bodies = readBodies(filename);
    final double radius = readRadius(filename);
    drawUniverse(radius, bodies, dt, T);
    printUniverse(radius, bodies);
  }
  
  private static void printUniverse(final double radius, final Body[] bodies) {
    StdOut.printf("%d\n", bodies.length);
    StdOut.printf("%.2e\n", radius);
    for (int i = 0; i < bodies.length; i++) {
      StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
        bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
        bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);
    }
  }
  
  private static void drawUniverse(final double radius, final Body[] bodies, final double dt, final double T) {
    /* Prevent flickering in the animation */
    StdDraw.enableDoubleBuffering();
    StdDraw.setScale(-radius, radius);
    /* Clears the drawing window. */
    StdDraw.clear();
    for (int time = 0; time <= T; time += dt) {
      double[] xForces = new double[bodies.length];
      double[] yForces = new double[bodies.length];

      for (int i = 0; i < bodies.length; i++) {
        xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
        yForces[i] = bodies[i].calcNetForceExertedByY(bodies);
      }
      
      for (int i = 0; i < bodies.length; i++) {
        bodies[i].update(dt, xForces[i], yForces[i]);
      }
      
      StdDraw.picture(0, 0, "images/starfield.jpg");
      
      for (final Body body : bodies) {
        body.draw();
      }

      StdDraw.show();
      StdDraw.pause(10);
    }
  }
}
