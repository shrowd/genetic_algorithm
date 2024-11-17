package shrowd.crossover;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MultiPointCrossover implements CrossoverStrategy {

    @Override
    public List<String> crossoverMethod(String parent1, String parent2) {
        Random random = new Random();
        List<Integer> points = new ArrayList<>();

        int numPoints = random.nextInt(parent1.length());

        while (points.size() < numPoints) {
            int point = random.nextInt(parent1.length());
            if (!points.contains(point)) {
                points.add(point);
            }
        }

        points.sort(Integer::compareTo);
        StringBuilder child1 = new StringBuilder();
        StringBuilder child2 = new StringBuilder();

        boolean swap = false;
        int lastPoint = 0;
        for (int point : points) {
            child1.append(swap ? parent2.substring(lastPoint, point) : parent1.substring(lastPoint, point));
            child2.append(swap ? parent1.substring(lastPoint, point) : parent2.substring(lastPoint, point));
            swap = !swap;
            lastPoint = point;
        }

        child1.append(swap ? parent2.substring(lastPoint) : parent1.substring(lastPoint));
        child2.append(swap ? parent1.substring(lastPoint) : parent2.substring(lastPoint));

        List<String> result = new ArrayList<>();
        result.add(child1.toString());
        result.add(child2.toString());

        return result;
    }
}
