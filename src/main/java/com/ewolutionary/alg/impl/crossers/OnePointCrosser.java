package com.ewolutionary.alg.impl.crossers;

import com.ewolutionary.alg.impl.Entity;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class OnePointCrosser extends Crosser {

    @Override
    protected List<Entity> cross(Entity first, Entity second) {
        int chromosomeLength = first.getChromosome().length;
        int bytePosition = rnd.nextInt(chromosomeLength);

        byte[] firstNew = new byte[chromosomeLength];
        byte[] secondNew = new byte[chromosomeLength];
        byte[] firstBinary = first.getChromosome();
        byte[] secondBinary = second.getChromosome();

        for (int j = 0; j < bytePosition; ++j) {
            firstNew[j] = firstBinary[j];
            secondNew[j] = secondBinary[j];
        }
        for (int j = bytePosition; j < chromosomeLength; ++j) {
            firstNew[j] = secondBinary[j];
            secondNew[j] = firstBinary[j];
        }

        return Arrays.asList(new Entity(first.getStart(), first.getStop(), firstNew),
                new Entity(second.getStart(), second.getStop(), secondNew));
    }


    public OnePointCrosser() {
    }

    public OnePointCrosser(Random random) {
        this.rnd = random;
    }
}
