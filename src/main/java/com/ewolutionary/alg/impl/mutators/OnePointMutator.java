package com.ewolutionary.alg.impl.mutators;

import com.ewolutionary.alg.impl.Chromosome;
import com.ewolutionary.alg.impl.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OnePointMutator implements Mutator {

    private Random rnd;

    public OnePointMutator() {
        rnd = new Random();
    }

    @Override
    public void mutate(List<Entity> individuals, double mutationPropability) {

        for(Entity entity: individuals) {
            for(Chromosome chromosome: entity.getChromosomes()) {
                if (Math.random() > mutationPropability) continue;
                int chromosomeLength = entity.getSize();

                int mutationPoint = rnd.nextInt(chromosomeLength);
                byte[] binary = chromosome.getBinary();
                binary[mutationPoint] = (byte) (binary[mutationPoint] == 1 ? 0 : 1);
                chromosome.setBinary(binary);
            }
        }
    }
}
