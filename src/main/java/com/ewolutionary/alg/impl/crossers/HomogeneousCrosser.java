package com.ewolutionary.alg.impl.crossers;

import com.ewolutionary.alg.impl.Entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class HomogeneousCrosser extends Crosser {

    @Override
    protected List<Entity> cross(Entity first, Entity second) {
        int chromosomeLength = first.getSize();
        byte[] template = generateTemplate(chromosomeLength);

        List<byte[]> firstBinaries = first.getChromosomesBytes();
        List<byte[]> secondBinaries = second.getChromosomesBytes();

        List<byte[]> firstBinariesNew = new ArrayList<>();
        List<byte[]> secondBinariesNew = new ArrayList<>();

        byte[] firstNew = new byte[chromosomeLength];
        byte[] secondNew = new byte[chromosomeLength];

        for(int k = 0;  k < firstBinaries.size(); k++) {
            for (int i = 0; i < chromosomeLength; ++i) {
                if (template[i] == 0) {
                    firstNew[i] = firstBinaries.get(k)[i];
                    secondNew[i] = secondBinaries.get(k)[i];
                } else if (template[i] == 1) {
                    firstNew[i] = secondBinaries.get(k)[i];
                    secondNew[i] = firstBinaries.get(k)[i];
                }
            }
            firstBinariesNew.add(firstNew);
            secondBinariesNew.add(secondNew);
        }

        return Arrays.asList(new Entity(first.getStart(), first.getStop(), firstBinariesNew),
                new Entity(second.getStart(), second.getStop(), secondBinariesNew));
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
