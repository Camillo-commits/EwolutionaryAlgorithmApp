package com.ewolutionary.alg.impl.crossers;

import com.ewolutionary.alg.impl.Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AverageCrosser extends Crosser {
    @Override
    protected List<Entity> cross(Entity first, Entity second) {

        List<Double> newValues = new ArrayList<>();

        for(int i=0; i<first.getChromosomesValues().size(); i++) {
            newValues.add((first.getChromosomesValues().get(i) + second.getChromosomesValues().get(i))/2);
        }

        first.setChromosomesValues(newValues);

        return Collections.singletonList(first);
    }
}
