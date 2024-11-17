package shrowd;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static shrowd.Chromosome.generateChromosomes;
import static shrowd.Chromosome.getUniqueChromosomes;
import static shrowd.Constants.EPOCHS_NUMBER;
import static shrowd.Constants.POPULATION_SIZE;
import static shrowd.Inversion.inverse;
import static shrowd.Mutation.mutate;
import static shrowd.PopulationUtils.*;
import static shrowd.Utils.computeBinaryLengths;
import static shrowd.Utils.computeNumberOfValues;

public record GeneticAlgorithm(double[] a, double[] b, int[] d) {

    private List<Chromosome> generatePopulation() {
        int[] binaryLengths = computeBinaryLengths(a, b, d);
        int[] numberOfValues = computeNumberOfValues(a, b, d);

        List<String> population = createPopulation(binaryLengths, numberOfValues);
        List<Double> ratedPopulation = ratePopulation(a, b, population, binaryLengths);

        return generateChromosomes(population, ratedPopulation);
    }

    public void trivialSuccession(String selectionMethod,
                                  String selectionMode,
                                  String crossoverMethod) {

        int[] binaryLengths = computeBinaryLengths(a, b, d);
        List<Chromosome> population = generatePopulation();

        Selection selection = new Selection(selectionMethod);
        Crossover crossover = new Crossover(crossoverMethod);

        for (int i = 0; i < EPOCHS_NUMBER; i++) {
            population = selection.performSelection(selectionMode, population);
            population = mutate(population);
            population = inverse(population);
            population = crossover.performCrossover(population);
            recalculateRastriginValue(a, b, population, binaryLengths);
        }

        System.out.println("Results for " + selectionMode + " optimization:");
        for (Chromosome c : population) {
            System.out.println(c);
        }
    }

    public void partialSuccession(String selectionMethod,
                                  String selectionMode,
                                  String crossoverMethod,
                                  String successionMode) {

        int[] binaryLengths = computeBinaryLengths(a, b, d);
        List<Chromosome> population = generatePopulation();

        Random random = new Random();
        Selection selection = new Selection(selectionMethod);
        Crossover crossover = new Crossover(crossoverMethod);

        for (int i = 0; i < EPOCHS_NUMBER; i++) {
            List<Chromosome> resultsSelection = selection.performSelection(selectionMode, population);
            List<Chromosome> resultsMutation = mutate(resultsSelection);
            List<Chromosome> resultsInversion = inverse(resultsSelection);
            List<Chromosome> resultsCrossover = crossover.performCrossover(resultsSelection);

            recalculateRastriginValue(a, b, resultsMutation, binaryLengths);
            recalculateRastriginValue(a, b, resultsInversion, binaryLengths);
            recalculateRastriginValue(a, b, resultsCrossover, binaryLengths);

            resultsMutation = getUniqueChromosomes(population, resultsMutation);
            resultsInversion = getUniqueChromosomes(population, resultsInversion);
            resultsCrossover = getUniqueChromosomes(population, resultsCrossover);


            List<Chromosome> combinedResults = new ArrayList<>();
            combinedResults.addAll(population);
            combinedResults.addAll(resultsMutation);
            combinedResults.addAll(resultsInversion);
            combinedResults.addAll(resultsCrossover);


            if (successionMode.equals("elite")) {
                if (selectionMode.equals("max")) {
                    combinedResults.sort(Comparator.comparing(Chromosome::getRastriginValue).reversed());
                } else if (selectionMode.equals("min")) {
                    combinedResults.sort(Comparator.comparing(Chromosome::getRastriginValue));
                }
                combinedResults = combinedResults.subList(0, POPULATION_SIZE);
            } else if (successionMode.equals("random")) {
                while (combinedResults.size() > POPULATION_SIZE) {
                    int randomIndex = random.nextInt(combinedResults.size());
                    combinedResults.remove(randomIndex);
                }
            }

            population = combinedResults;
        }

        System.out.println("Results for " + selectionMode + " optimization:");
        for (Chromosome c : population) {
            System.out.println(c);
        }

    }
}
