package shrowd;

import shrowd.selection.Ranking;
import shrowd.selection.Roulette;
import shrowd.selection.SelectionStrategy;
import shrowd.selection.Tournament;

import java.util.List;

public class Selection {

    private final SelectionStrategy selection;

    public Selection(String method) {
        this.selection = createSelectionStrategy(method);
    }

    private SelectionStrategy createSelectionStrategy(String method) {
        return switch (method) {
            case "ranking" -> new Ranking();
            case "tournament" -> new Tournament();
            case "roulette" -> new Roulette();
            default -> throw new IllegalArgumentException("Unknown selection method: " + method);
        };
    }

    public List<Double> performSelection(String selectionMode, List<Chromosome> chromosomes) {

        return selection.selectionMethod(selectionMode, chromosomes);
    }
}
