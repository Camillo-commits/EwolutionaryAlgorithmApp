package com.ewolutionary.alg.impl.crossers;

import com.ewolutionary.alg.impl.Entity;

import java.util.*;
import java.util.stream.IntStream;

public class ThreePointCrosser extends Crosser {
    @Override
    protected List<Entity> cross(Entity first, Entity second) {
        int chromosomeLength = first.getSize();
        int bytePosition1 = rnd.nextInt(chromosomeLength);
        int bytePosition2 = rnd.nextInt(chromosomeLength);
        int bytePosition3 = rnd.nextInt(chromosomeLength);

        while (arePositionsEqual(bytePosition1, bytePosition2, bytePosition3)) {
            bytePosition1 = rnd.nextInt(chromosomeLength);
            bytePosition2 = rnd.nextInt(chromosomeLength);
            bytePosition3 = rnd.nextInt(chromosomeLength);
        }

        if (arePositionsEqual(bytePosition1, bytePosition2, bytePosition3)) {
            List<Integer> newPositions = swapPositionIndexes(bytePosition1, bytePosition2, bytePosition3);
            bytePosition1 = newPositions.get(0);
            bytePosition2 = newPositions.get(1);
            bytePosition3 = newPositions.get(2);
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
            for (int j = bytePosition2; j < bytePosition3; ++j) {
                firstNew[j] = firstBinaries.get(k)[j];
                secondNew[j] = secondBinaries.get(k)[j];
            }
            for (int j = bytePosition3; j < chromosomeLength; ++j) {
                firstNew[j] = secondBinaries.get(k)[j];
                secondNew[j] = firstBinaries.get(k)[j];
            }
            firstBinariesNew.add(firstNew);
            secondBinariesNew.add(secondNew);
        }
        return Arrays.asList(new Entity(first.getStart(), first.getStop(), firstBinariesNew),
                new Entity(second.getStart(), second.getStop(), secondBinariesNew));
    }

    private boolean arePositionsEqual(int position1, int position2, int position3) {
        return position1 == position2 || position1 == position3 || position2 == position3;
    }

    private List<Integer> swapPositionIndexes(int position1, int position2, int position3) {
        PrimitiveIterator.OfInt positions = IntStream.of(position1, position2, position3).sorted().iterator();
        position1 = positions.nextInt();
        position2 = positions.nextInt();
        position3 = positions.nextInt();
        return Arrays.asList(position1, position2, position3);
    }

    public ThreePointCrosser() {
    }

    public ThreePointCrosser(Random random) {
        this.rnd = random;
    }
}
