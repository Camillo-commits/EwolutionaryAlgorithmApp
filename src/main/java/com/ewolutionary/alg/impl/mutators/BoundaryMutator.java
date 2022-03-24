package com.ewolutionary.alg.impl.mutators;

import com.ewolutionary.alg.impl.Chromosome;
import com.ewolutionary.alg.impl.Entity;

import java.util.List;
import java.util.Random;

public class BoundaryMutator implements Mutator {

    private Random rnd;

    public BoundaryMutator() {
        rnd = new Random();
    }

    @Override
    public void mutate(List<Entity> individuals, double mutationPropability) {

        for(Entity entity: individuals) {
            for(Chromosome chromosome: entity.getChromosomes()) { // TODO nwm czy na 100% dobrze rozumiem
                if (Math.random() > mutationPropability) continue;
                int chromosomeLength = entity.getSize();
                byte[] binary = chromosome.getBinary();
                int mutationPoint = Math.random() < 0.50 ? 0 : chromosomeLength-1;
                binary[mutationPoint] = (byte) (binary[mutationPoint] == 1 ? 0 : 1);
                chromosome.setBinary(binary);
            }
        }
    }
}
