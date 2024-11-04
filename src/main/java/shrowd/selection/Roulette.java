package shrowd.selection;

import shrowd.Chromosome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Roulette implements SelectionStrategy {

    @Override
    public List<Double> selectionMethod(String selectionMode, List<Chromosome> chromosomes) {
        List<Double> q = new ArrayList<>();
        List<Double> result = new ArrayList<>();
        List<Double> temp = new ArrayList<>();
        Random rnd = new Random();
        double sum = 0.0;

        List<Double> rastriginValue = chromosomes.stream()
                .map(Chromosome::getRastriginValue)
                .collect(Collectors.toList());

        if (selectionMode.equals("min")) {
            for (Double r : rastriginValue) {
                double inverseValue = 1.0 / r;
                temp.add(inverseValue);
                sum += inverseValue;
            }
        } else if (selectionMode.equals("max")) {
            temp = rastriginValue;
            sum = rastriginValue.stream()
                    .mapToDouble(Double::doubleValue)
                    .sum();
        }

        double cumulativeProb = 0.0;
        for (Double r : temp) {
            cumulativeProb += r / sum;
            q.add(cumulativeProb);
        }

        for (int i = 0; i < chromosomes.size(); i++) {
            double randomNumber = rnd.nextDouble();
            for (int j = 0; j < q.size(); j++) {
                if (randomNumber <= q.get(j)) {
                    result.add(rastriginValue.get(j));
                    break;
                }
            }
        }

        return result;
    }
}
