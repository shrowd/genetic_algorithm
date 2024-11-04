package shrowd.crossover;

import java.util.List;

public interface CrossoverStrategy {

    List<String> crossoverMethod(String parent1, String parent2);
}
