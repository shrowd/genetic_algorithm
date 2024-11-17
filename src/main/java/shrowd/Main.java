package shrowd;

public class Main {

    public static void main(String[] args) {
        double[] a = {-1, 0, -1, -1};
        double[] b = {1, 3, 2, 1};
        int[] d = {2, 1, 2, 1};

        GeneticAlgorithm algorithm = new GeneticAlgorithm(a, b, d);
        algorithm.partialSuccession("roulette",
                "min",
                "multi",
                "random");

//        algorithm.trivialSuccession("tournament",
//                "min",
//                "uniform");
    }
}
