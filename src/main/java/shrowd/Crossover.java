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

    public Crossover(String method) {
        this.crossover = createCrossoverStrategy(method);
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


    private CrossoverStrategy createCrossoverStrategy(String method) {
        return switch (method) {
            case "single" -> new SinglePointCrossover();
            case "double" -> new TwoPointCrossover();
            case "multi" -> new MultiPointCrossover();
            case "uniform" -> new UniformCrossover();
            default -> throw new IllegalArgumentException("Unknown crossover method: " + method);
        };
    }

    public List<Chromosome> performCrossover(List<Chromosome> chromosomes) {
        List<String> chromosomesStrings = chromosomes.stream()
                .map(Chromosome::getGenotype)
                .collect(Collectors.toList());

        List<String> pairs = crossbreedingChoosing(chromosomesStrings);
        List<Chromosome> result = new ArrayList<>(chromosomes);

        if (pairs.size() < 2) {
            return result;
        }

        for (int i = 0; i < pairs.size(); i += 2) {
            String parent1 = pairs.get(i);
            String parent2 = pairs.get(i + 1);

            int index1 = chromosomesStrings.indexOf(parent1);
            int index2 = chromosomesStrings.indexOf(parent2);

            List<String> offspring = crossover.crossoverMethod(parent1, parent2);

            Chromosome child1 = new Chromosome(offspring.get(0));
            Chromosome child2 = new Chromosome(offspring.get(1));

            result.set(index1, child1);
            result.set(index2, child2);
        }

        return result;
    }

}
