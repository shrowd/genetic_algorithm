package shrowd;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static shrowd.Constants.MUTATION_PROB;

public class Mutation {

    public static List<String> mutate(List<String> chromosomes) {
        List<String> result = new ArrayList<>();
        Random random = new Random();

        for (String chromosome : chromosomes) {
            char[] genes = chromosome.toCharArray();

            for (int i = 0; i < genes.length; i++) {
                double prob = random.nextDouble();
                if (prob < MUTATION_PROB) {
                    genes[i] = (genes[i] == '0') ? '1' : '0';
                }
            }
            result.add(new String(genes));
        }

        return result;
    }
}
