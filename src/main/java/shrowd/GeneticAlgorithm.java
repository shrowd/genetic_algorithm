package shrowd;

import java.util.*;
import java.util.stream.Collectors;

import static shrowd.Constants.POPULATION_SIZE;
import static shrowd.Inversion.inverse;
import static shrowd.Mutation.mutate;
import static shrowd.Utils.*;

public record GeneticAlgorithm(double[] a, double[] b, int[] d) {

    private List<String> createPopulation(int[] binaryLengths, int[] numberOfValues) {
        List<String> population = new ArrayList<>();
        List<List<String>> binaryGroups = new ArrayList<>();
        Random rnd = new Random();

        for (int i = 0; i < binaryLengths.length; i++) {
            int length = binaryLengths[i];
            int maxValue = numberOfValues[i];
            Set<String> individuals = new HashSet<>();

            while (individuals.size() < POPULATION_SIZE) {
                int randomNumber = rnd.nextInt(maxValue);

                String binaryString = String.format("%" + length + "s",
                        Integer.toBinaryString(randomNumber)).replace(' ', '0');

                individuals.add(binaryString);
            }

            binaryGroups.add(new ArrayList<>(individuals));
        }

        for (int i = 0; i < POPULATION_SIZE; i++) {
            StringBuilder chromosome = new StringBuilder();
            for (List<String> group : binaryGroups) {
                chromosome.append(group.get(i));
            }
            population.add(chromosome.toString());
        }

        return population;
    }

    private List<Double> ratePopulation(List<String> population, int[] binaryLengths) {
        List<List<Double>> ratedPopulations = new ArrayList<>();

        for (String chromosome : population) {
            List<Double> normalizedValues = new ArrayList<>();
            int currentIndex = 0;

            for (int i = 0; i < binaryLengths.length; i++) {
                int length = binaryLengths[i];
                String subChromosome = chromosome.substring(currentIndex, currentIndex + length);
                currentIndex += length;

                double decimalValue = Integer.parseInt(subChromosome, 2);

                double normalizedValue = a[i] + ((b[i] - a[i]) * decimalValue) / (Math.pow(2, length) - 1);
                normalizedValues.add(normalizedValue);
            }

            ratedPopulations.add(normalizedValues);
        }

        return ratedPopulations.stream()
                .map(individual -> rastrigin(individual.stream()
                        .mapToDouble(Double::doubleValue)
                        .toArray()))
                .collect(Collectors.toList());
    }

    private List<Chromosome> generateChromosomes(List<String> population, List<Double> rastriginValues) {
        List<Chromosome> chromosomes = new ArrayList<>();

        for (int i = 0; i < population.size(); i++) {
            String chromosome = population.get(i);
            double rastriginValue = rastriginValues.get(i);

            chromosomes.add(new Chromosome(chromosome, rastriginValue));
        }

        return chromosomes;
    }

    public void optimize(String selectionMethod,
                         String selectionMode,
                         String crossoverMethod,
                         int crossoverNumPoints) {
        int[] binaryLengths = computeBinaryLengths(a, b, d);
        int[] numberOfValues = computeNumberOfValues(a, b, d);
        List<String> population = createPopulation(binaryLengths, numberOfValues);
        List<Double> results = ratePopulation(population, binaryLengths);
        List<Chromosome> chromosomes = generateChromosomes(population, results);

        Selection selection = new Selection(selectionMethod);
        List<Chromosome> resultsSelection = selection.performSelection(selectionMode, chromosomes);

        List<Chromosome> resultsMutation = mutate(resultsSelection);
        //List<String> resultsInversion = inverse(resultsMutation);

        Crossover crossover = new Crossover(crossoverMethod, crossoverNumPoints);
        //List<List<String>> crossoverResult = crossover.performCrossover(resultsInversion);

        System.out.println("Chromosomes and rastrigin function value:");
        for (Chromosome c : chromosomes) {
            System.out.println(c);
        }

        System.out.println("\n" + selectionMethod + " selection(" + selectionMode + "):");
        for (Chromosome c : resultsSelection) {
            System.out.println(c);
        }

        System.out.println("\nMutation results:");
        for (Chromosome c : resultsMutation) {
            System.out.println(c);
        }

//        System.out.println("\nChromosomes before operations: " + chromosomesStrings +
//                "\nChromosomes after mutation:    " + resultsMutation +
//                "\nChromosomes after inversion:   " + resultsInversion);
//        System.out.println("Crossover: " + crossoverResult);
    }
}
