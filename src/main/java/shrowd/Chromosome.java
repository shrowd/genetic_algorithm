package shrowd;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Chromosome {

    private String genotype;
    private double rastriginValue;

    public static List<Chromosome> generateChromosomes(List<String> population, List<Double> rastriginValues) {
        List<Chromosome> chromosomes = new ArrayList<>();

        for (int i = 0; i < population.size(); i++) {
            String chromosome = population.get(i);
            double rastriginValue = rastriginValues.get(i);

            chromosomes.add(new Chromosome(chromosome, rastriginValue));
        }

        return chromosomes;
    }

    @Override
    public String toString() {
        return "Chromosome: " + genotype + ", Rastrigin Value: " + rastriginValue;
    }
}
