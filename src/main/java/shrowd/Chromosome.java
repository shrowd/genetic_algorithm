package shrowd;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Chromosome {

    private String genotype;
    private double rastriginValue;

    public Chromosome(String genotype) {
        this.genotype = genotype;
    }

    public Chromosome(String genotype, double rastriginValue) {
        this.genotype = genotype;
        this.rastriginValue = rastriginValue;
    }

    public static List<Chromosome> generateChromosomes(List<String> population, List<Double> rastriginValues) {
        List<Chromosome> chromosomes = new ArrayList<>();

        for (int i = 0; i < population.size(); i++) {
            String chromosome = population.get(i);
            double rastriginValue = rastriginValues.get(i);

            chromosomes.add(new Chromosome(chromosome, rastriginValue));
        }

        return chromosomes;
    }

    public static List<Chromosome> getUniqueChromosomes(List<Chromosome> originalList, List<Chromosome> resultList) {
        List<Chromosome> originalSet = new ArrayList<>(originalList);
        List<Chromosome> uniqueChromosomes = new ArrayList<>();

        for (Chromosome chromosome : resultList) {
            if (!originalSet.contains(chromosome)) {
                uniqueChromosomes.add(chromosome);
            }
        }

        return uniqueChromosomes;
    }

    @Override
    public String toString() {
        return "Chromosome: " + genotype + ", Rastrigin Value: " + rastriginValue;
    }
}
