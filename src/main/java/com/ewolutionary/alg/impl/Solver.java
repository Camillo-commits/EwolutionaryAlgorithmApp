package com.ewolutionary.alg.impl;

import com.ewolutionary.alg.function.Functions;
import com.ewolutionary.alg.impl.crossers.Crosser;
import com.ewolutionary.alg.impl.crossers.CrosserOption;
import com.ewolutionary.alg.impl.crossers.CrosserProvider;
import com.ewolutionary.alg.impl.mutators.Mutator;
import com.ewolutionary.alg.impl.mutators.MutatorOption;
import com.ewolutionary.alg.impl.mutators.MutatorProvider;
import com.ewolutionary.alg.impl.selectors.Selector;
import com.ewolutionary.alg.impl.selectors.SelectorOption;
import com.ewolutionary.alg.impl.selectors.SelectorProvider;
import com.ewolutionary.alg.impl.utils.EntityUtils;
import org.apache.commons.lang3.time.StopWatch;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Solver {
    private final Selector selector;
    private final Mutator mutator;
    private final Crosser crosser;
    private final Configuration configuration;
    private static String function;

    public Solver(MutatorOption mutatorOption, CrosserOption crosserOption, SelectorOption selectorOption, String function, Configuration configuration) {
        this.mutator = MutatorProvider.getMutator(mutatorOption);
        this.crosser = CrosserProvider.getCrosser(crosserOption);
        this.selector = SelectorProvider.getSelector(selectorOption);
        this.configuration = configuration;
        Solver.function = function;
    }

    public Solution solve() throws IOException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<Entity> bestSolutions = new ArrayList<>();
        Population population = new Population(configuration.getSizeOfPopulation(), configuration.getPrecision(),
                configuration.getStart(), configuration.getStop(), configuration.getXVariableCount());
        boolean precisionMet = false;
        int numberOfIterations = 0;
        Entity bestPreviousSolution = null;
        Entity bestSolution = population.getEntities().get(0);
        PrintWriter out = new PrintWriter("src/main/resources/out/aimInIteration.txt");
        PrintWriter out2 = new PrintWriter("src/main/resources/out/bestAim.txt");
//        PrintWriter average = new PrintWriter("src/main/resources/out/average.txt");
//        PrintWriter sd = new PrintWriter("src/main/resources/out/sd.txt");
        out.println("iteration, x");
        out2.println("iteration, x");

        while (!precisionMet) {
            ++numberOfIterations;
            List<Entity> selected = selector.select(population, configuration.isEliteStrategy(),
                    configuration.getPercentOfBestToNextCentury(), configuration.getSelectorConfiguration());
            crosser.cross(selected, configuration.getCrossingProbability(), configuration.getSizeOfPopulation());
            mutator.mutate(selected, configuration.getMutationProbability());

            Entity bestCurrentSolution;
            if(Functions.MINIMUM) {
                bestCurrentSolution = EntityUtils.findMinBestSolution(selected);
            } else {
                bestCurrentSolution = EntityUtils.findMaxBestSolution(selected);
            }
            population.setEntities(selected);
            precisionMet = isStopArgumentsMet(numberOfIterations, configuration.getMaxIterations(),
                    bestCurrentSolution, bestPreviousSolution);
            bestPreviousSolution = bestCurrentSolution;
            bestSolutions.add(bestPreviousSolution);
            if (!Functions.MINIMUM && bestCurrentSolution.getFitness() > bestSolution.getFitness()) {
                bestSolution = bestCurrentSolution;
            } else if (Functions.MINIMUM && bestCurrentSolution.getFitness() < bestSolution.getFitness()) {
                bestSolution = bestCurrentSolution;
            }
            out.println(numberOfIterations + ", " + bestCurrentSolution.getFitness());
            out2.println(numberOfIterations + ", " + bestSolution.getFitness());
//            average.println(population.getEntities().stream().mapToDouble(Entity::getFitness).sum()/population.getEntities().size());
//            sd.println(calculateSD(population.getEntities().stream().mapToDouble(Entity::getFitness).toArray()));
        }
        stopWatch.stop();
        out.close();
        out2.close();
//        average.close();
//        sd.close();

        return Solution.builder()
                .bestEntity(bestSolution)
                .bestEntityInEachIteration(bestSolutions)
                .timeMilis(stopWatch.getTime(TimeUnit.MILLISECONDS))
                .numberOfIterations(numberOfIterations)
                .build();
    }

    private double calculateSD(double[] numArray)
    {
        double sum = 0.0, standardDeviation = 0.0;
        int length = numArray.length;

        for(double num : numArray) {
            sum += num;
        }

        double mean = sum/length;

        for(double num: numArray) {
            standardDeviation += Math.pow(num - mean, 2);
        }

        return Math.sqrt(standardDeviation/length);
    }

    private boolean isStopArgumentsMet(long numberOfIterations, long maxIterations, Entity bestSolution, Entity bestPreviousSolution) {
        if (numberOfIterations >= configuration.getMaxIterations())
            return true;
        //TODO calculate real precision
        return false;
    }

    public static String functionToSolve() {
        return function;
    }

    // only for tests
    public static void overrideFunctionToSolve(String function) {
        Solver.function = function;
    }

}
