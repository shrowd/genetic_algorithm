package shrowd.selection;

import shrowd.Chromosome;
import shrowd.Selection;

import java.util.*;

public class Tournament implements Selection {

    @Override
    public List<Double> selectionMethod(String cases, List<Chromosome> chromosomes) {
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
            if (cases.equals("max")) {
                bestIndividual = Collections.max(tournamentGroup,
                        Comparator.comparingDouble(Double::doubleValue));
            } else if (cases.equals("min")) {
                bestIndividual = Collections.min(tournamentGroup,
                        Comparator.comparingDouble(Double::doubleValue));
            }

            result.add(bestIndividual);
        }

        return result;
    }
}
