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

    public Entity solve() {
        Population population = new Population(configuration.getSizeOfPopulation(), configuration.getPrecision(), configuration.getStart(), configuration.getStop());
        boolean precisionMet = false;
        long numberOfIterations = 0;
        Entity bestPreviousSolution = null;
        while (!precisionMet) {
            ++numberOfIterations;
            List<Entity> selected = selector.select(population, configuration.isEliteStrategy());
            crosser.cross(selected, configuration.getCrossingProbability());
            mutator.mutate(selected);
            inverter.ifPresent(inv -> inv.invert(selected));
            Entity bestCurrentSolution = selector.findBestSolution(selected);
            precisionMet = isStopArgumentsMet(numberOfIterations, configuration.getMaxIterations(), bestCurrentSolution, bestPreviousSolution);
            bestPreviousSolution = bestCurrentSolution;
        }
        return bestPreviousSolution;
    }

    private boolean isStopArgumentsMet(long numberOfIterations, long maxIterations, Entity bestSolution, Entity bestPreviousSolution) {
        if(numberOfIterations >= configuration.getMaxIterations()) return false;
        //TODO calculate real precision
        return true;
    }

}