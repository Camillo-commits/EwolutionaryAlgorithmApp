package com.ewolutionary.alg.impl.crossers;

import com.ewolutionary.alg.impl.Entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TwoPointCrosser extends Crosser {

    @Override
    protected List<Entity> cross(Entity first, Entity second) {
        int chromosomeLength = first.getSize();
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

        List<byte[]> firstBinaries = first.getChromosomesBytes();
        List<byte[]> secondBinaries = second.getChromosomesBytes();

        List<byte[]> firstBinariesNew = new ArrayList<>();
        List<byte[]> secondBinariesNew = new ArrayList<>();

        byte[] firstNew = new byte[chromosomeLength];
        byte[] secondNew = new byte[chromosomeLength];

        for(int k = 0; k < firstBinaries.size(); k++) {
            for (int j = 0; j < bytePosition1; ++j) {
                firstNew[j] = firstBinaries.get(k)[j];
                secondNew[j] = secondBinaries.get(k)[j];
            }
            for (int j = bytePosition1; j < bytePosition2; ++j) {
                firstNew[j] = secondBinaries.get(k)[j];
                secondNew[j] = firstBinaries.get(k)[j];
            }
            for (int j = bytePosition2; j < chromosomeLength; ++j) {
                firstNew[j] = firstBinaries.get(k)[j];
                secondNew[j] = secondBinaries.get(k)[j];
            }
            firstBinariesNew.add(firstNew);
            secondBinariesNew.add(secondNew);
        }

        return Arrays.asList(new Entity(first.getStart(), first.getStop(), firstBinariesNew),
                new Entity(second.getStart(), second.getStop(), secondBinariesNew));
    }


    public TwoPointCrosser() {
    }

    public TwoPointCrosser(Random random) {
        this.rnd = random;
    }
}
