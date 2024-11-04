package shrowd.crossover;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UniformCrossover implements CrossoverStrategy {

    @Override
    public List<String> crossoverMethod(String parent1, String parent2) {
        Random random = new Random();
        int length = parent1.length();

        StringBuilder pattern = new StringBuilder();
        for (int i = 0; i < length; i++) {
            pattern.append(random.nextInt(2));
        }

        StringBuilder child1 = new StringBuilder();
        StringBuilder child2 = new StringBuilder();

        for (int i = 0; i < length; i++) {
            if (pattern.charAt(i) == '0') {
                child1.append(parent1.charAt(i));
                child2.append(parent2.charAt(i));
            } else {
                child1.append(parent2.charAt(i));
                child2.append(parent1.charAt(i));
            }
        }

        List<String> result = new ArrayList<>();
        result.add(child1.toString());
        result.add(child2.toString());

        return result;
    }
}
