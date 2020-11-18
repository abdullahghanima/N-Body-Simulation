public class NBody {
    public static void main(String[] args) {

        // this reads the total time and time increment from the command line
        double totalTime = Double.parseDouble(args[0]);
        double timeIncrement = Double.parseDouble(args[1]);

        // this initializes the counter for time
        double currentTime = 0.0;

        // this reads the number of planets and the size of the window from a text file
        int n = StdIn.readInt();
        double radius = StdIn.readDouble();

        // this initializes all of the arrays
        double[] xPosition = new double[n];
        double[] yPosition = new double[n];
        double[] xVelocity = new double[n];
        double[] yVelocity = new double[n];
        double[] masses = new double[n];
        String[] images = new String[n];

        // this stores the data from the text file into the arrays
        for (int i = 0; i < n; i++) {
            xPosition[i] = StdIn.readDouble();
            yPosition[i] = StdIn.readDouble();
            xVelocity[i] = StdIn.readDouble();
            yVelocity[i] = StdIn.readDouble();
            masses[i] = StdIn.readDouble();
            images[i] = StdIn.readString();
        }

        // this creates a window with the specified size, with (0,0) being the center
        StdDraw.setXscale(-radius, radius);
        StdDraw.setYscale(-radius, radius);
        StdDraw.enableDoubleBuffering();

        // this plays the background music
        StdAudio.play("2001.wav");


        while (currentTime < totalTime) {
            // System.out.println(currentTime);

            // this incrememts the time
            currentTime += timeIncrement;

            // this draws the background, which is "resetting" the screen
            StdDraw.picture(0, 0, "starfield.jpg");

            // this declares and initializes the arrays for the forces
            double[] fx = new double[n];
            double[] fy = new double[n];
            for (int i = 0; i < n; i++) {
                fx[i] = 0.0;
                fy[i] = 0.0;
            }

            // this nested loop calculates the net forces of each planet on
            // every other planet
            for (int j = 0; j < n; j++) {
                for (int i = 0; i < n; i++) {
                    if (i != j) {

                        // this calculates the x and y distances, which can be used
                        // (with the mass) to calculate the net forces
                        double xDistance = xPosition[i] - xPosition[j];
                        double yDistance = yPosition[i] - yPosition[j];
                        double distance = Math.sqrt(Math.pow(xDistance, 2) +
                                Math.pow(yDistance, 2));
                        double netForce = (6.67e-11 * masses[i] * masses[j]) /
                                Math.pow(distance, 2);
                        fx[j] += netForce * (xDistance / distance);
                        fy[j] += netForce * (yDistance / distance);
                    }
                }
            }

            // this loop appropriately changes the velocities, which
            // change the position of the planets.
            // changing the position of an image at a certain rate
            // will give the impression of movement
            for (int i = 0; i < n; i++) {
                double xAcceleration = fx[i] / masses[i];
                double yAcceleration = fy[i] / masses[i];
                xVelocity[i] = xVelocity[i] + (timeIncrement * xAcceleration);
                yVelocity[i] = yVelocity[i] + (timeIncrement * yAcceleration);
                xPosition[i] = xPosition[i] + (timeIncrement * xVelocity[i]);
                yPosition[i] = yPosition[i] + (timeIncrement * yVelocity[i]);

                StdDraw.picture(xPosition[i], yPosition[i], images[i]);
            }

            // this shows the images and their positions (which change)
            StdDraw.show();
            StdDraw.pause(20);
        }


        // this prints all of the stored data in the specified format
        System.out.println(n);
        System.out.printf("%5.2e\n", radius);
        for (int i = 0; i < n; i++) {
            System.out.printf("%11.4e", xPosition[i]);
            System.out.printf("%12.4e", yPosition[i]);
            System.out.printf("%12.4e", xVelocity[i]);
            System.out.printf("%12.4e", yVelocity[i]);
            System.out.printf("%12.4e", masses[i]);
            System.out.printf("%13s", images[i]);
            System.out.println();
        }

    }
}
