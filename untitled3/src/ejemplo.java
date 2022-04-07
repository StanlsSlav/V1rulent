import java.io.BufferedReader;
import java.io.FileReader;

public class ejemplo {
    public static void main(String[] args) {

        double[] newCoord = scaleCoords(1222, 277, 720);
        System.out.println(newCoord[0] + " " + newCoord[1]);

    }

    public static double[] scaleCoords(double x, double y, double resolution) {

        double[] coords = new double[2];
        coords[0] = x;
        coords[1] = y;
        if (resolution != 1080) {

            double scale = calcul(resolution);

            if (resolution > 1080) {

                coords[0] *= scale;
                coords[1] *= scale;

            } else {

                coords[0] /= scale;
                coords[1] /= scale;

            }


        } else {


        }

        coords[0] += 8;
        coords[1] += 30;

        return coords;
    }

    public static double calcul(double resolution) {

        double newY;


        if (resolution > 1080) {

            newY = resolution / 1080;

        } else {

            newY = 1080 / resolution;

        }

        System.out.println(newY);

        return newY;

    }
}
