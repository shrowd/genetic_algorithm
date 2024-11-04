package shrowd;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import shrowd.selection.Ranking;
import shrowd.selection.Roulette;
import shrowd.selection.Tournament;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import static shrowd.Inversion.inverse;
import static shrowd.Mutation.mutate;
import static shrowd.Utils.*;

@Getter
@Setter
@AllArgsConstructor
public class GeneticAlgorithm {

    private final double[] a;
    private final double[] b;
    private final int[] d;
    private final int N;


    private List<Set<String>> createPopulation(int[] binaryLengths, int[] numberOfValues) {
        List<Set<String>> population = new ArrayList<>();
        Random rnd = new Random();

        for (int i = 0; i < binaryLengths.length; i++) {
            int length = binaryLengths[i];
            int maxValue = numberOfValues[i];
            Set<String> individuals = new HashSet<>();

            while (individuals.size() < N) {
                int randomNumber = rnd.nextInt(maxValue);

                String binaryString = String.format("%" + length + "s",
                        Integer.toBinaryString(randomNumber)).replace(' ', '0');

                individuals.add(binaryString);
            }

            population.add(individuals);
        }

        return population;
    }

    private List<Chromosome> generateChromosomes(List<Set<String>> population, List<Double> rastriginValues) {
        List<Chromosome> chromosomes = new ArrayList<>();

        for (int i = 0; i < N; i++) {

            StringBuilder individual = new StringBuilder();

            for (Set<String> gene : population) {
                List<String> list = new ArrayList<>(gene);
                individual.append(list.get(i % list.size()));
            }


            chromosomes.add(new Chromosome(individual.toString(), rastriginValues.get(i)));
        }

        return chromosomes;
    }

    private List<Double> ratePopulation(List<Set<String>> populations, int[] binaryLengths) {
        List<List<Double>> ratedPopulations = new ArrayList<>();

        for (int index = 0; index < populations.size(); index++) {
            Set<String> population = populations.get(index);
            double a = this.a[index];
            double b = this.b[index];
            int m = binaryLengths[index];

            List<Double> decimalValues = new ArrayList<>();
            for (String individual : population) {
                decimalValues.add((double) Integer.parseInt(individual, 2));
            }

            List<Double> normalizedValues = new ArrayList<>();
            for (Double d : decimalValues) {
                normalizedValues.add(a + ((b - a) * d) / (Math.pow(2, m) - 1));
            }
            ratedPopulations.add(normalizedValues);
        }

        List<List<Double>> transposedPopulations = transpose(ratedPopulations);

        List<Double> evaluationResults = new ArrayList<>();
        transposedPopulations.stream()
                .map(individual -> rastrigin(individual.stream()
                        .mapToDouble(Double::doubleValue)
                        .toArray()))
                .map(result -> new BigDecimal(result)
                        .setScale(2, RoundingMode.HALF_UP)
                        .doubleValue())
                .forEach(evaluationResults::add);

        return evaluationResults;
    }

    public final void optimize(String cases) {
        int[] binaryLengths = computeBinaryLengths(a, b, d);
        int[] numberOfValues = computeNumberOfValues(a, b, d);
        List<Set<String>> population = createPopulation(binaryLengths, numberOfValues);
        List<Double> results = ratePopulation(population, binaryLengths);
        List<Chromosome> chromosomes = generateChromosomes(population, results);
        List<String> chromosomesStrings = chromosomes.stream()
                .map(Chromosome::getChromosome)
                .collect(Collectors.toList());
        Selection t = new Tournament();
        Selection r = new Ranking();
        Selection ro = new Roulette();
        List<Double> resultsTournament = t.selectionMethod(cases, chromosomes);
        List<Double> resultsRanking = r.selectionMethod(cases, chromosomes);
        List<Double> resultsRoulette = ro.selectionMethod(cases, chromosomes);
        List<String> resultsMutation = mutate(chromosomesStrings);
        List<String> resultsInversion = inverse(resultsMutation);

        System.out.println("Chromosomes and rastrigin function value:");
        for (Chromosome c : chromosomes) {
            System.out.println(c);
        }

        System.out.println("\nSelection methods:");
        System.out.println("Tournament method: " + resultsTournament
                + "\nRanking method:    " + resultsRanking
                + "\nRoulette method:   " + resultsRoulette);

        System.out.println("\nChromosomes before operations: " + chromosomesStrings +
                "\nChromosomes after mutation:    " + resultsMutation +
                "\nChromosomes after inversion:   " + resultsInversion);

    }

    public static void main(String[] args) {
        double[] a = {-1, -1, -1};
        double[] b = {1, 1, 1};
        int[] d = {1, 1, 1};
        int N = 5;

        GeneticAlgorithm algorithm = new GeneticAlgorithm(a, b, d, N);
        algorithm.optimize("max");
    }
}
