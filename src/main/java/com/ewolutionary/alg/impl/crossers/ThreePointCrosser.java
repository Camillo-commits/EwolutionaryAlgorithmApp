package com.ewolutionary.alg.impl.crossers;

import com.ewolutionary.alg.impl.Entity;

import java.util.Arrays;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.Random;
import java.util.stream.IntStream;

public class ThreePointCrosser extends Crosser {
    @Override
    protected List<Entity> cross(Entity first, Entity second) {
        int chromosomeLength = first.getChromosome().length;
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
        for (int j = bytePosition2; j < bytePosition3; ++j) {
            firstNew[j] = firstBinary[j];
            secondNew[j] = secondBinary[j];
        }
        for (int j = bytePosition3; j < chromosomeLength; ++j) {
            firstNew[j] = secondBinary[j];
            secondNew[j] = firstBinary[j];
        }
        return Arrays.asList(new Entity(first.getStart(), first.getStop(), firstNew),
                new Entity(second.getStart(), second.getStop(), secondNew));
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
