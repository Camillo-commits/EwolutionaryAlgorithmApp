package com.ewolutionary.alg.impl.crossers;

import com.ewolutionary.alg.impl.Entity;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TwoPointCrosser extends Crosser {

    @Override
    protected List<Entity> cross(Entity first, Entity second) {
        int chromosomeLength = first.getChromosome().length;
        int bytePosition1 = rnd.nextInt(chromosomeLength);
        int bytePosition2 = rnd.nextInt(chromosomeLength);

        while (bytePosition1 == bytePosition2) {
            bytePosition2 = rnd.nextInt(chromosomeLength);
        }

        if(bytePosition2 < bytePosition1) {
            int tmp = bytePosition1;
            bytePosition1 = bytePosition2;
            bytePosition2 = tmp;
        }

        byte[] firstNew = new byte[chromosomeLength];
        byte[] secondNew = new byte[chromosomeLength];
        byte[] firstBinary = first.getChromosome();
        byte[] secondBinary = second.getChromosome();

        for (int j = 0; j < bytePosition1; ++j) {
            firstNew[j] = firstBinary[j];
            secondNew[j] = secondBinary[j];
        }
        for (int j = bytePosition1; j < bytePosition2; ++j) {
            firstNew[j] = secondBinary[j];
            secondNew[j] = firstBinary[j];
        }
        for (int j = bytePosition2; j < chromosomeLength; ++j) {
            firstNew[j] = firstBinary[j];
            secondNew[j] = secondBinary[j];
        }
        return Arrays.asList(new Entity(first.getStart(), first.getStop(), firstNew),
                new Entity(second.getStart(), second.getStop(), secondNew));
    }


    public TwoPointCrosser() {
    }

    public TwoPointCrosser(Random random) {
        this.rnd = random;
    }
}
