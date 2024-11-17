package shrowd;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static shrowd.Constants.INVERSION_PROB;

public class Inversion {

    public static List<Chromosome> inverse(List<Chromosome> chromosomes) {
        List<Chromosome> result = new ArrayList<>();
        Random random = new Random();

        for (Chromosome chromosome : chromosomes) {
            String genotype = chromosome.getGenotype();
            double r = random.nextDouble();

            if (r < INVERSION_PROB) {
                int m = genotype.length();
                int a = random.nextInt(m);
                int b = random.nextInt(m);

                if (a > b) {
                    int temp = a;
                    a = b;
                    b = temp;
                }

                char[] genes = genotype.toCharArray();
                while (a < b) {
                    char temp = genes[a];
                    genes[a] = genes[b];
                    genes[b] = temp;
                    a++;
                    b--;
                }
                result.add(new Chromosome(new String(genes)));
            } else {
                result.add(chromosome);
            }
        }

        return result;
    }
}
