package com.ewolutionary.alg.impl.mutators;

import com.ewolutionary.alg.impl.Chromosome;
import com.ewolutionary.alg.impl.Entity;

import java.util.List;
import java.util.Random;

public class GaussMutator implements Mutator {

    private Random rnd;

    public GaussMutator() {
        this.rnd = new Random();
    }

    @Override
    public void mutate(List<Entity> individuals, double mutationProbability) {

        double newValue;
        for(Entity entity: individuals) {
            for(Chromosome chromosome: entity.getChromosomes()) {
                newValue = chromosome.getValue() + rnd.nextGaussian();
                while(newValue < entity.getStart() || newValue > entity.getStop()) {
                    newValue = chromosome.getValue() + rnd.nextGaussian();
                }
                chromosome.setValue(newValue);
            }
        }

    }
}
