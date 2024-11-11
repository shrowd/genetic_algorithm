package shrowd;

import java.util.List;
import java.util.stream.Collectors;

import static shrowd.Chromosome.generateChromosomes;
import static shrowd.Inversion.inverse;
import static shrowd.Mutation.mutate;
import static shrowd.PopulationUtils.*;
import static shrowd.Utils.computeBinaryLengths;
import static shrowd.Utils.computeNumberOfValues;

public record GeneticAlgorithm(double[] a, double[] b, int[] d) {


    public void optimize(String selectionMethod,
                         String selectionMode,
                         String crossoverMethod,
                         int crossoverNumPoints) {
        int[] binaryLengths = computeBinaryLengths(a, b, d);
        int[] numberOfValues = computeNumberOfValues(a, b, d);
        List<String> population = createPopulation(binaryLengths, numberOfValues);
        List<Double> results = ratePopulation(a, b, population, binaryLengths);
        List<Chromosome> chromosomes = generateChromosomes(population, results);

        Selection selection = new Selection(selectionMethod);
        List<Chromosome> resultsSelection = selection.performSelection(selectionMode, chromosomes);

        List<Chromosome> resultsMutation = mutate(resultsSelection);
        List<Chromosome> resultsInversion = inverse(resultsMutation);

        Crossover crossover = new Crossover(crossoverMethod, crossoverNumPoints);
        List<String> chromosomesStrings = resultsInversion.stream()
                .map(Chromosome::getGenotype)
                .collect(Collectors.toList());
        List<String> resultsCrossover = crossover.performCrossover(chromosomesStrings);

        System.out.println("Chromosomes and rastrigin function value:");
        for (Chromosome c : chromosomes) {
            System.out.println(c);
        }

        System.out.println("\n" + selectionMethod + " selection(" + selectionMode + "):");
        for (Chromosome c : resultsSelection) {
            System.out.println(c);
        }

        recalculateRastriginValue(a, b, resultsMutation, binaryLengths);
        System.out.println("\nMutation results:");
        for (Chromosome c : resultsMutation) {
            System.out.println(c);
        }

        recalculateRastriginValue(a, b, resultsInversion, binaryLengths);
        System.out.println("\nInversion results:");
        for (Chromosome c : resultsInversion) {
            System.out.println(c);
        }

        System.out.println("\nCrossover results:");
        System.out.println(resultsCrossover);

//        System.out.println("\nChromosomes before operations: " + chromosomesStrings +
//                "\nChromosomes after mutation:    " + resultsMutation +
//                "\nChromosomes after inversion:   " + resultsInversion);
//        System.out.println("Crossover: " + crossoverResult);
    }
}
