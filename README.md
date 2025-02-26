# Genetic Algorithm for Rastrigin Function Optimization

This repository contains an implementation of a genetic algorithm for optimizing the Rastrigin function. The implementation supports various selection, crossover, and mutation strategies, enabling flexible experimentation.

## Table of Contents
* [About](#about)
* [Getting Started](#getting-started)
   * [Prerequisites](#prerequisites)
   * [Installation](#installation)
* [Algorithm Components](#algorithm-components)
   * [Selection Strategies](#selection-strategies)
   * [Crossover Strategies](#crossover-strategies)
   * [Mutation and Inversion](#mutation-and-inversion)
* [Configuration](#configuration)

## About
This project implements a genetic algorithm for optimizing the Rastrigin function, commonly used in multi-dimensional optimization problems. The Rastrigin function is defined as:

$$f(\mathbf{x}) = A * n + \sum_{i=1}^{n} [x_i^2 - A * \cos(\omega * x_i)]$$

Where:
- $A$ = 10 (control constant)
- $\omega$ = 20 * pi (frequency parameter)
- $n$ = number of dimensions
- $x_i$ = coordinates of a point in n-dimensional space

The genetic algorithm uses various selection, crossover, and mutation strategies to find the optimal solution to this function.

## Getting Started

### Prerequisites

* Java JDK 17
* Maven 3.6.0 or higher
* Git

### Installation

1. Clone the repository:
   ```sh
   git clone https://github.com/shrowd/genetic_algorithm.git
   ```
   
2. Navigate to the project directory
   ```sh
   cd genetic_algorithm
   ```
   
3. Build the project:
   ```sh
   mvn clean install
   ```

4. Run the application:
   ```sh
   mvn exec:java -Dexec.mainClass="shrowd.Main"
   ```
   Alternatively, you can run Main.java directly through your IDE (IntelliJ IDEA, Eclipse, etc.).

## Algorithm Components

The genetic algorithm operates on a population of chromosomes that represent potential solutions to the optimization problem. The optimization process includes the following steps:

1. Population initialization within boundaries defined by vectors `a` and `b`
2. Selection of the best chromosomes according to the selected method
3. Crossover to create new individuals based on the chosen strategy
4. Mutation to introduce random changes to genes
5. Inversion to reverse parts of chromosomes
6. Succession to select the population for the next generation
7. Repetition of the process for a specified number of epochs

### Selection Strategies

Three selection methods are implemented:

- **Ranking** (`Ranking.java`) - Sorts chromosomes by fitness and selects based on rank
- **Roulette** (`Roulette.java`) - Probabilistic selection where better individuals have higher chances
- **Tournament** (`Tournament.java`) - Randomly selects groups and chooses the best from each group

### Crossover Strategies

Four crossover methods are implemented:

- **Single-point** (`SinglePointCrossover.java`) - Exchanges genetic material at a single point
- **Two-point** (`TwoPointCrossover.java`) - Exchanges genetic material between two points
- **Multi-point** (`MultiPointCrossover.java`) - Exchanges genetic material at multiple points
- **Uniform** (`UniformCrossover.java`) - Exchanges genes based on a randomly generated binary mask

### Mutation and Inversion

- **Mutation** (`Mutation.java`) - Makes random changes to individual genes
- **Inversion** (`Inversion.java`) - Reverses the order of a subsequence of genes

## Configuration

The algorithm can be configured through constants in `Constants.java`:

```java
public interface Constants {
    double A = 10.0; // Rastrigin function constant
    double W = 20 * Math.PI; // Oscillation frequency coefficient
    int POPULATION_SIZE = 10; // Population size
    double CROSSOVER_PROB = 0.6; // Crossover probability
    double MUTATION_PROB = 0.2; // Mutation probability
    double INVERSION_PROB = 0.2; // Inversion probability
    int EPOCHS_NUMBER = 1000; // Number of evolution epochs
}
```

The algorithm can be run using the `trivialSuccession` or `partialSuccession` methods with the following parameters:

```java
public class Main {
    public static void main(String[] args) {
        double[] a = {-1, 0, -1, -1}; // Lower bounds
        double[] b = {1, 3, 2, 1}; // Upper bounds
        int[] d = {2, 1, 2, 1}; // Number of decimal places for each dimension(defines precision)

        GeneticAlgorithm algorithm = new GeneticAlgorithm(a, b, d);
        algorithm.partialSuccession("roulette", "min", "multi", "elite");
        
        // Alternative method:
        // algorithm.trivialSuccession("tournament", "min", "uniform");
    }
}
```

Parameters include:
- Selection method (`ranking`, `tournament`, `roulette`)
- Selection mode (`min`, `max`)
- Crossover method (`single`, `double`, `multi`, `uniform`)
- Succession mode (`elite`, `random`) - only for `partialSuccession`
