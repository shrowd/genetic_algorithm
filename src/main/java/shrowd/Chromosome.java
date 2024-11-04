package shrowd;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Chromosome {

    private String chromosome;
    private double rastriginValue;

    @Override
    public String toString() {
        return "Chromosome: " + chromosome + ", Rastrigin Value: " + rastriginValue;
    }
}
