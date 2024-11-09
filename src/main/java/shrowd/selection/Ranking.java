package shrowd.selection;

import shrowd.Chromosome;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Ranking implements SelectionStrategy {

    @Override
    public List<Chromosome> selectionMethod(String selectionMode, List<Chromosome> chromosomes) {
        List<Chromosome> result = new ArrayList<>();
        int N = chromosomes.size();
        Random rnd = new Random();

        if (selectionMode.equals("max")) {
            chromosomes.sort(Comparator.comparing(Chromosome::getRastriginValue).reversed());
        } else if (selectionMode.equals("min")) {
            chromosomes.sort(Comparator.comparing(Chromosome::getRastriginValue));
        }

        for (int i = 0; i < N; i++) {
            int randomNumber = rnd.nextInt(N) + 1;
            result.add(chromosomes.get(rnd.nextInt(randomNumber)));
        }

        return result;
    }
}
