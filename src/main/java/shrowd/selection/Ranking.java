package shrowd.selection;

import shrowd.Chromosome;
import shrowd.Selection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Ranking implements Selection {

    @Override
    public List<Double> selectionMethod(String cases, List<Chromosome> chromosomes) {
        int N = chromosomes.size();
        List<Double> result = new ArrayList<>();
        Random rnd = new Random();
        List<Double> rastriginValue = chromosomes.stream()
                .map(Chromosome::getRastriginValue)
                .collect(Collectors.toList());

        if (cases.equals("max")) {
            rastriginValue.sort(Comparator.reverseOrder());
        } else if (cases.equals("min")) {
            rastriginValue.sort(Comparator.naturalOrder());
        }

        for (int i = 0; i < N; i++) {
            int randomNumber = rnd.nextInt(N) + 1;
            result.add(rastriginValue.get(rnd.nextInt(randomNumber)));
        }

        return result;
    }
}
