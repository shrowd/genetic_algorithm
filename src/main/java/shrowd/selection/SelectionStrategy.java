package shrowd.selection;

import shrowd.Chromosome;

import java.util.List;

public interface SelectionStrategy {

    List<Double> selectionMethod(String selectionMode, List<Chromosome> chromosomes);
}
