package com.ewolutionary.alg.impl.crossers;

import com.ewolutionary.alg.impl.Entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class OnePointCrosser extends Crosser {

    @Override
    protected List<Entity> cross(Entity first, Entity second) {
        int chromosomeLength = first.getSize();
        int bytePosition = rnd.nextInt(chromosomeLength);

        List<byte[]> firstBinaries = first.getChromosomesBytes();
        List<byte[]> secondBinaries = second.getChromosomesBytes();

        List<byte[]> firstBinariesNew = new ArrayList<>();
        List<byte[]> secondBinariesNew = new ArrayList<>();

        byte[] firstNew = new byte[chromosomeLength];
        byte[] secondNew = new byte[chromosomeLength];

        for(int k = 0; k < firstBinaries.size(); k++) {
            for (int j = 0; j < bytePosition; ++j) {
                firstNew[j] = firstBinaries.get(k)[j];
                secondNew[j] = secondBinaries.get(k)[j];
            }
            for (int j = bytePosition; j < chromosomeLength; ++j) {
                firstNew[j] = secondBinaries.get(k)[j];
                secondNew[j] = firstBinaries.get(k)[j];
            }
            firstBinariesNew.add(firstNew);
            secondBinariesNew.add(secondNew);
        }

        return Arrays.asList(new Entity(first.getStart(), first.getStop(), firstBinariesNew),
                new Entity(second.getStart(), second.getStop(), secondBinariesNew));
    }


    public OnePointCrosser() {
    }

    public OnePointCrosser(Random random) {
        this.rnd = random;
    }
}
