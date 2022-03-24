package com.ewolutionary.alg.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Population {

    private final int size;
    private final int xCount;
    private List<Entity> entities = new ArrayList<>();

    public Population(int size, int precision, int start, int stop, int xCount) {
        this.size = size;
        this.xCount = xCount;
        generateEntities(start, stop, precision);
    }

    public List<Entity> getEntities() {
        return entities;
    }

    private void generateEntities(int start, int stop, int precision) {
        for(int i=0; i<size; i++) {
            entities.add(new Entity(start, stop, precision, xCount));
        }
    }

}
