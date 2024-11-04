package shrowd.crossover;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SinglePointCrossover implements CrossoverStrategy {

    @Override
    public List<String> crossoverMethod(String parent1, String parent2) {
        Random random = new Random();
        int point = random.nextInt(parent1.length());

        String child1 = parent1.substring(0, point) + parent2.substring(point);
        String child2 = parent2.substring(0, point) + parent1.substring(point);

        List<String> result = new ArrayList<>();
        result.add(child1);
        result.add(child2);

        return result;
    }
}
