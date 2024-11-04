package shrowd;

import java.util.ArrayList;
import java.util.List;

import static shrowd.Constants.A;
import static shrowd.Constants.W;

public class Utils {

    public static double rastrigin(double[] x) {
        double sum = 0.0;
        for (double xi : x) {
            sum += Math.pow(xi, 2) - A * Math.cos(W * xi);
        }

        return A * x.length + sum;
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

    public static List<List<Double>> transpose(List<List<Double>> matrix) {
        List<List<Double>> transposed = new ArrayList<>();

        int numCols = matrix.get(0).size();

        for (int col = 0; col < numCols; col++) {
            List<Double> newRow = new ArrayList<>();
            for (List<Double> doubles : matrix) {
                newRow.add(doubles.get(col));
            }
            transposed.add(newRow);
        }

        return transposed;
    }
}
