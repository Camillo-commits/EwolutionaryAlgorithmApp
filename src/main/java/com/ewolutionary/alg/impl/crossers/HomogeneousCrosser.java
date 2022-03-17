package com.ewolutionary.alg.impl.crossers;

import com.ewolutionary.alg.impl.Entity;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class HomogeneousCrosser extends Crosser {

    @Override
    protected List<Entity> cross(Entity first, Entity second) {
        int chromosomeLength = first.getChromosome().length;
        byte[] template = generateTemplate(chromosomeLength);

        byte[] firstNew = new byte[chromosomeLength];
        byte[] secondNew = new byte[chromosomeLength];
        byte[] firstBinary = first.getChromosome();
        byte[] secondBinary = second.getChromosome();

        for (int i = 0; i < chromosomeLength; ++i) {
            if (template[i] == 0) {
                firstNew[i] = firstBinary[i];
                secondNew[i] = secondBinary[i];
            } else if (template[i] == 1) {
                firstNew[i] = secondBinary[i];
                secondNew[i] = firstBinary[i];
            }
        }

        return Arrays.asList(new Entity(first.getStart(), first.getStop(), firstNew),
                new Entity(second.getStart(), second.getStop(), secondNew));
    }

    private byte[] generateTemplate(int chromosomeLength) {
        byte[] template = new byte[chromosomeLength];
        for (int i = 0; i < chromosomeLength; ++i) {
            template[i] = (byte) rnd.nextInt(2);
        }
        return template;
    }

    public HomogeneousCrosser() {
    }

    public HomogeneousCrosser(Random random) {
        this.rnd = random;
    }

}
