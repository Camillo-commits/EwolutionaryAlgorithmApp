package com.ewolutionary.alg.impl.mutators;

import com.ewolutionary.alg.impl.Entity;

import java.util.List;
import java.util.Random;

public class TwoPointMutator implements Mutator {
    private Random rnd;

    public TwoPointMutator() {
        rnd = new Random();
    }

    @Override
    public void mutate(List<Entity> individuals, double mutationPropability) {

        for(Entity entity: individuals) {
            if (Math.random() > mutationPropability) continue;
            int chromosomeLength = entity.getChromosome().length;
            int firstMutationPoint = rnd.nextInt(chromosomeLength);
            int secondMutationPoint = rnd.nextInt(chromosomeLength);

            byte[] binary = entity.getChromosome();
            binary[firstMutationPoint] = (byte) (binary[firstMutationPoint] == 1 ? 0 : 1);
            binary[secondMutationPoint] = (byte) (binary[secondMutationPoint] == 1 ? 0 : 1);

            entity.getChromosome2().setBinary(binary);
        }


    }
}
