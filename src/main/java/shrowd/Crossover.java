package shrowd;

import shrowd.crossover.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static shrowd.Constants.CROSSOVER_PROB;

public class Crossover {

    private final CrossoverStrategy crossover;

    public Crossover(String method, int numPoints) {
        this.crossover = createCrossoverStrategy(method, numPoints);
    }

    private List<String> crossbreedingChoosing(List<String> chromosomes) {
        Random random = new Random();

        List<String> chosenChromosomes = chromosomes.stream()
                .filter(chromosome -> random.nextDouble() < CROSSOVER_PROB)
                .collect(Collectors.toList());

        if (chosenChromosomes.size() < 2) {
            return Collections.emptyList();
        }

        if (chosenChromosomes.size() % 2 != 0) {
            int index = random.nextInt(chosenChromosomes.size());
            chosenChromosomes.remove(index);
        }

        Collections.shuffle(chosenChromosomes);

        return chosenChromosomes;
    }


    private CrossoverStrategy createCrossoverStrategy(String method, int numPoints) {
        return switch (method) {
            case "single" -> new SinglePointCrossover();
            case "double" -> new TwoPointCrossover();
            case "multi" -> new MultiPointCrossover(numPoints);
            case "uniform" -> new UniformCrossover();
            default -> throw new IllegalArgumentException("Unknown crossover method: " + method);
        };
    }

    public List<String> performCrossover(List<String> chromosomes) {
        List<String> pairs = crossbreedingChoosing(chromosomes);
        List<String> result = new ArrayList<>(chromosomes);

        if (pairs.size() < 2) {
            return result;
        }

        for (int i = 0; i < pairs.size(); i += 2) {
            String parent1 = pairs.get(i);
            String parent2 = pairs.get(i + 1);

            int index1 = chromosomes.indexOf(parent1);
            int index2 = chromosomes.indexOf(parent2);

            List<String> offspring = crossover.crossoverMethod(parent1, parent2);

            String child1 = offspring.get(0);
            String child2 = offspring.get(1);

            result.set(index1, child1);
            result.set(index2, child2);
        }

        return result;
    }

}
