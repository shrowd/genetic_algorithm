package shrowd;

public class Main {

    public static void main(String[] args) {
        double[] a = {-1, -1, -1};
        double[] b = {1, 1, 1};
        int[] d = {1, 1, 1};
        int N = 10;

        GeneticAlgorithm algorithm = new GeneticAlgorithm(a, b, d, N);
        algorithm.optimize("tournament","max","uniform", 4);
    }
}
