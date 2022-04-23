package com.ewolutionary.alg.impl.crossers;

import com.ewolutionary.alg.impl.Entity;

import java.util.Arrays;
import java.util.List;

public class BlxABCrosser extends Crosser {
    @Override
    protected List<Entity> cross(Entity first, Entity second) {
        double alpha = rnd.nextDouble();
        double beta = rnd.nextDouble();

        Entity newFirst = new Entity(first.getStart(), first.getStop(), first.getXVariableCount());
        Entity newSecond = new Entity(first.getStart(), first.getStop(), first.getXVariableCount());

        for (int i = 0; i < first.getChromosomes().size(); ++i) {
            double value1 = first.getChromosomesValues().get(i);
            double value2 = second.getChromosomesValues().get(i);
            double d = Math.abs(value1 - value2);
            double min = Math.min(value1, value2);
            double max = Math.max(value1, value2);

            newFirst.getChromosomes()
                    .get(i).setValue(genRandomBetween(min - alpha * d, max + beta * d));
            newSecond.getChromosomes()
                    .get(i).setValue(genRandomBetween(min - alpha * d, max + beta * d));
        }
        return Arrays.asList(newFirst, newSecond);
    }
}
