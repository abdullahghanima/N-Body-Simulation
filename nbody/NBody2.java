public class NBody2 {
    public static void main(String[] args) {

        // this reads the number of particles and the radius of the window
        int n = StdIn.readInt();
        double radius = StdIn.readDouble();

        // this reads the total time and time increment from the command line
        double totalTime = Double.parseDouble(args[0]);
        double timeIncrement = Double.parseDouble(args[1]);

        // this initializes the counter for time
        double currentTime = 0.0;


        StdDraw.setXscale(-radius, radius);
        StdDraw.setYscale(-radius, radius);

        double[] xPosition = new double[n];
        double[] yPosition = new double[n];
        double[] xVelocity = new double[n];
        double[] yVelocity = new double[n];
        double[] masses = new double[n];
        String[] images = new String[n];


        double centerMass = 0.0;

        for (int i = 0; i < n; i++) {
            xPosition[i] = StdIn.readDouble();
            yPosition[i] = StdIn.readDouble();
            xVelocity[i] = StdIn.readDouble();
            yVelocity[i] = StdIn.readDouble();
            masses[i] = StdIn.readDouble();
            images[i] = StdIn.readString();
            if (xPosition[i] == 0.0 && yPosition[i] == 0.0) {
                centerMass = masses[i];
            }

        }

        StdDraw.enableDoubleBuffering();
        StdAudio.play("2001.wav");

        while (currentTime < totalTime) {
            currentTime += timeIncrement;

            for (int i = 0; i < n; i++) {

                double xDistance = 0 - xPosition[i];
                double yDistance = 0 - yPosition[i];
                double xForce = 0;
                double yForce = 0;
                if (yDistance != 0 && xDistance != 0) {
                    double distance = Math.sqrt(Math.pow(xDistance, 2)
                            + Math.pow(yDistance, 2));
                    double netForce = (6.67e-11 * masses[i] * centerMass)
                            / Math.pow(distance, 2);
                    xForce = netForce * (xDistance / distance);
                    yForce = netForce * (yDistance / distance);
                }
                double xAcceleration = xForce / masses[i];
                double yAcceleration = yForce / masses[i];

                xVelocity[i] = xVelocity[i] + (timeIncrement * xAcceleration);
                yVelocity[i] = yVelocity[i] + (timeIncrement * yAcceleration);

                xPosition[i] = xPosition[i] + (timeIncrement * xVelocity[i]);
                yPosition[i] = yPosition[i] + (timeIncrement * yVelocity[i]);


                StdDraw.picture(xPosition[i], yPosition[i], images[i]);
            }
            StdDraw.show();
            StdDraw.pause(20);
            StdDraw.clear();
        }

    }
}

