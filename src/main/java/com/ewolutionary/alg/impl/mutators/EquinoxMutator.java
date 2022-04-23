package com.ewolutionary.alg.impl.mutators;

import com.ewolutionary.alg.impl.Chromosome;
import com.ewolutionary.alg.impl.Entity;

import java.util.List;
import java.util.Random;

public class EquinoxMutator implements Mutator {

    private final Random rnd;

    public EquinoxMutator() {
        rnd = new Random();
    }

    public EquinoxMutator(Random rnd) {
        this.rnd = rnd;
    }

    @Override
    public void mutate(List<Entity> individuals, double mutationProbability) {

        for(Entity entity: individuals) {
            if (rnd.nextDouble() > mutationProbability) continue;
            List<Chromosome> entityChromosomes = entity.getChromosomes();
            int chromosomeLength = entity.getChromosomesValues().size();
            int mutationPoint = rnd.nextInt(chromosomeLength);
            entityChromosomes.get(mutationPoint).setNextValue();
        }
    }

}
