package shrowd;

import java.util.*;
import java.util.stream.Collectors;

import static shrowd.Constants.POPULATION_SIZE;
import static shrowd.Utils.rastrigin;

public class PopulationUtils {

    public static List<String> createPopulation(int[] binaryLengths, int[] numberOfValues) {
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

    public static List<Double> ratePopulation(double[] a, double[] b, List<String> population, int[] binaryLengths) {
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

    public static void recalculateRastriginValue(double[] a, double[] b, List<Chromosome> chromosomes, int[] binaryLengths) {
        List<String> genotypes = chromosomes.stream()
                .map(Chromosome::getGenotype)
                .collect(Collectors.toList());

        List<Double> rastriginValues = ratePopulation(a, b, genotypes, binaryLengths);

        for (int i = 0; i < chromosomes.size(); i++) {
            chromosomes.get(i).setRastriginValue(rastriginValues.get(i));
        }
    }
}
