package shrowd.selection;

import shrowd.Chromosome;

import java.util.*;

public class Tournament implements SelectionStrategy {

    @Override
    public List<Double> selectionMethod(String selectionMode, List<Chromosome> chromosomes) {
        Random rnd = new Random();
        List<Double> result = new ArrayList<>();

        for (int i = 0; i < chromosomes.size(); i++) {
            Set<Integer> indices = new HashSet<>();
            List<Double> tournamentGroup = new ArrayList<>();

            while (indices.size() < 2) {
                int randomIndex = rnd.nextInt(chromosomes.size());
                if (indices.add(randomIndex)) {
                    tournamentGroup.add(chromosomes.get(randomIndex).getRastriginValue());
                }
            }

            Double bestIndividual = 0.0;
            if (selectionMode.equals("max")) {
                bestIndividual = Collections.max(tournamentGroup,
                        Comparator.comparingDouble(Double::doubleValue));
            } else if (selectionMode.equals("min")) {
                bestIndividual = Collections.min(tournamentGroup,
                        Comparator.comparingDouble(Double::doubleValue));
            }

            result.add(bestIndividual);
        }

        return result;
    }
}
