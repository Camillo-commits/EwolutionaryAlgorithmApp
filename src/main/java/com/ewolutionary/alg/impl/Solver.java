package com.ewolutionary.alg.impl;

import com.ewolutionary.alg.impl.crossers.Crosser;
import com.ewolutionary.alg.impl.crossers.CrosserOption;
import com.ewolutionary.alg.impl.crossers.CrosserProvider;
import com.ewolutionary.alg.impl.inwerters.Inverter;
import com.ewolutionary.alg.impl.inwerters.InverterImpl;
import com.ewolutionary.alg.impl.mutators.Mutator;
import com.ewolutionary.alg.impl.mutators.MutatorOption;
import com.ewolutionary.alg.impl.mutators.MutatorProvider;
import com.ewolutionary.alg.impl.selectors.Selector;
import com.ewolutionary.alg.impl.selectors.SelectorOption;
import com.ewolutionary.alg.impl.selectors.SelectorProvider;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Solver {
    private Selector selector;
    private Mutator mutator;
    private Optional<Inverter> inverter;
    private Crosser crosser;
    private Configuration configuration;

    public Solver(MutatorOption mutatorOption, CrosserOption crosserOption, SelectorOption selectorOption, Configuration configuration) {
        this.mutator = MutatorProvider.getMutator(mutatorOption);
        this.crosser = CrosserProvider.getCrosser(crosserOption);
        this.selector = SelectorProvider.getSelector(selectorOption);
        this.configuration = configuration;
        if (true) {
            this.inverter = Optional.of(new InverterImpl());
        }
    }

    public Solution solve() {
        List<Solution> population = generatePopulation();
        boolean precisionMet = false;
        long numberOfIterations = 0;
        Solution bestPreviousSolution = null;
        while (!precisionMet) {
            ++numberOfIterations;
            population.forEach(Solution::calculateValue);
            List<Solution> selected = selector.select(population, configuration.isEliteStrategy());
            crosser.cross(selected);
            mutator.mutate(selected);
            inverter.ifPresent(inv -> inv.invert(selected));
            Solution bestCurrentSolution = selector.findBestSolution(selected);
            precisionMet = isStopArgumentsMet(numberOfIterations, configuration.getMaxIterations(), bestCurrentSolution, bestPreviousSolution);
            bestPreviousSolution = bestCurrentSolution;
        }
        return bestPreviousSolution;
    }

    private List<Solution> generatePopulation() {
        //TODO generate population ( use sizeOfPopulation )
        return Collections.emptyList();
    }

    private boolean isStopArgumentsMet(long numberOfIterations, long maxIterations, Solution bestSolution, Solution bestPreviousSolution) {
        //TODO calculate real precision
        return true;
    }

}
