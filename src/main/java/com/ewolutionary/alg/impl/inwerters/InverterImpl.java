package com.ewolutionary.alg.impl.inwerters;

import com.ewolutionary.alg.impl.Entity;
import org.apache.commons.lang3.ArrayUtils;

import java.util.List;
import java.util.Random;

public class InverterImpl implements Inverter {

    private Random rnd;

    public InverterImpl() {
        rnd = new Random();
    }

    @Override
    public void invert(List<Entity> individuals, double invertionProbability) {

        for(Entity entity: individuals) {
            if (Math.random() > invertionProbability) continue;
            int chromosomeLength = entity.getSize();
            byte[] binary = entity.getChromosomesBytes().get(0); //TODO
            int firstInvertionPoint;
            int secondInvertionPoint;

            do {
                 firstInvertionPoint = rnd.nextInt(chromosomeLength);
                 secondInvertionPoint = rnd.nextInt(chromosomeLength);

                if(secondInvertionPoint < firstInvertionPoint){
                    int tmp = secondInvertionPoint;
                    secondInvertionPoint = firstInvertionPoint;
                    firstInvertionPoint = tmp;
                }

            } while(firstInvertionPoint>=secondInvertionPoint);

            byte[] selectedBinary = new byte[secondInvertionPoint - firstInvertionPoint];
            int j = 0;
            for(int i = firstInvertionPoint; i <= secondInvertionPoint; i++){
                selectedBinary[j] = binary[i];
                j++;
            }

            ArrayUtils.reverse(selectedBinary);

            j = 0;
            for(int i = firstInvertionPoint; i <= secondInvertionPoint; i++){
               binary[i] = selectedBinary[j];
               j++;
            }
            entity.getChromosomes().get(1).setBinary(binary);
        }

    }
}
