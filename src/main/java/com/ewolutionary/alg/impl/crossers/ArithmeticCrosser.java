package com.ewolutionary.alg.impl.crossers;

import com.ewolutionary.alg.impl.Entity;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class ArithmeticCrosser extends Crosser {
    @Override
    protected List<Entity> cross(Entity first, Entity second) {
        double k = rnd.nextDouble();

        List<Double> firstValues = new ArrayList<>();
        List<Double> secondValues = new ArrayList<>();

        for(int i = 0; i < first.getChromosomesValues().size(); i++) {
            firstValues.add(k * first.getChromosomesValues().get(i) + (1 - k) * second.getChromosomesValues().get(i));
            secondValues.add(k * second.getChromosomesValues().get(i) + (1 - k) * first.getChromosomesValues().get(i));
        }

        Entity newFirst = new Entity(first.getStart(), first.getStop(), first.getXVariableCount());
        Entity newSecond = new Entity(first.getStart(), first.getStop(), first.getXVariableCount());

        newFirst.setChromosomesValues(firstValues);
        newSecond.setChromosomesValues(secondValues);

        return asList(newFirst, newSecond);
    }
}
