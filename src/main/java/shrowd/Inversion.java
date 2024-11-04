package shrowd;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static shrowd.Constants.INVERSION_PROB;

public class Inversion {

    public static List<String> inverse(List<String> chromosomes) {
        List<String> result = new ArrayList<>();
        Random random = new Random();

        for (String chromosome : chromosomes) {
            double r = random.nextDouble();

            if (r < INVERSION_PROB) {
                int m = chromosome.length();
                int a = random.nextInt(m);
                int b = random.nextInt(m);

                if (a > b) {
                    int temp = a;
                    a = b;
                    b = temp;
                }

                char[] genes = chromosome.toCharArray();
                while (a < b) {
                    char temp = genes[a];
                    genes[a] = genes[b];
                    genes[b] = temp;
                    a++;
                    b--;
                }
                result.add(new String(genes));
            } else {
                result.add(chromosome);
            }
        }

        return result;
    }
}
