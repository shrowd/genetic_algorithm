package shrowd;

import static shrowd.Constants.A;
import static shrowd.Constants.W;

public class Utils {

    public static double rastrigin(double[] x) {
        double sum = 0.0;
        for (double xi : x) {
            sum += Math.pow(xi, 2) - A * Math.cos(W * xi);
        }

        double result = A * x.length + sum;

        return Math.round(result * 100.0) / 100.0;
    }

    public static int[] computeBinaryLengths(double[] a, double[] b, int[] d) {
        int[] binaryLengths = new int[a.length];

        for (int i = 0; i < a.length; i++) {
            int m = 0;

            while (true) {
                double range = (b[i] - a[i]) * Math.pow(10, d[i]) + 1;

                if (Math.pow(2, m - 1) <= range && range <= Math.pow(2, m)) {
                    break;
                }
                m++;
            }
            binaryLengths[i] = m;
        }

        return binaryLengths;
    }

    public static int[] computeNumberOfValues(double[] a, double[] b, int[] d) {
        int[] numberOfValues = new int[a.length];

        for (int i = 0; i < a.length; i++) {
            numberOfValues[i] = (int) ((b[i] - a[i]) * Math.pow(10, d[i]) + 1);
        }

        return numberOfValues;
    }
}
