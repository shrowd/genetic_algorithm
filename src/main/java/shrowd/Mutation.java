package shrowd;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static shrowd.Constants.MUTATION_PROB;

public class Mutation {

    public static List<Chromosome> mutate(List<Chromosome> chromosomes) {
        List<Chromosome> result = new ArrayList<>();
        Random random = new Random();

        for (Chromosome chromosome : chromosomes) {
            char[] genes = chromosome.getGenotype().toCharArray();

            for (int i = 0; i < genes.length; i++) {
                double prob = random.nextDouble();
                if (prob < MUTATION_PROB) {
                    genes[i] = (genes[i] == '0') ? '1' : '0';
                }
            }

            String mutatedGenotype = new String(genes);
            Chromosome mutatedChromosome = new Chromosome(mutatedGenotype, chromosome.getRastriginValue());

            result.add(mutatedChromosome);
        }

        return result;
    }
}
