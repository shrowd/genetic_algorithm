package shrowd.selection;

import shrowd.Chromosome;

import java.util.*;

public class Tournament implements SelectionStrategy {

    @Override
    public List<Chromosome> selectionMethod(String selectionMode, List<Chromosome> chromosomes) {
        Random rnd = new Random();
        List<Chromosome> result = new ArrayList<>();

        for (int i = 0; i < chromosomes.size(); i++) {
            Set<Integer> indices = new HashSet<>();
            List<Chromosome> tournamentGroup = new ArrayList<>();

            while (indices.size() < 2) {
                int randomIndex = rnd.nextInt(chromosomes.size());
                if (indices.add(randomIndex)) {
                    tournamentGroup.add(chromosomes.get(randomIndex));
                }
            }

            Chromosome bestIndividual = null;
            if (selectionMode.equals("max")) {
                bestIndividual = Collections.max(tournamentGroup,
                        Comparator.comparing(Chromosome::getRastriginValue));
            } else if (selectionMode.equals("min")) {
                bestIndividual = Collections.min(tournamentGroup,
                        Comparator.comparing(Chromosome::getRastriginValue));
            }

            result.add(bestIndividual);
        }

        return result;
    }
}
