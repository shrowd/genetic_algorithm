package shrowd.crossover;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TwoPointCrossover implements CrossoverStrategy {

    @Override
    public List<String> crossoverMethod(String parent1, String parent2) {
        Random random = new Random();
        int a = random.nextInt(parent1.length());
        int b = random.nextInt(parent1.length());

        if (a > b) {
            int temp = a;
            a = b;
            b = temp;
        }

        String child1 = parent1.substring(0, a) + parent2.substring(a, b) + parent1.substring(b);
        String child2 = parent2.substring(0, a) + parent1.substring(a, b) + parent2.substring(b);

        List<String> result = new ArrayList<>();
        result.add(child1);
        result.add(child2);

        return result;
    }
}
