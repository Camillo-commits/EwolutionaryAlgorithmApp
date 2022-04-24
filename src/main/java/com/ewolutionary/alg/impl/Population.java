package com.ewolutionary.alg.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Population {

    private final int size;
    private final int xVariablesCount;
    private List<Entity> entities = new ArrayList<>();

    public Population(int size, int start, int stop, int xVariablesCount) {
        this.size = size;
        this.xVariablesCount = xVariablesCount;
        generateEntities(start, stop);
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        if(entities.size() == size)
            this.entities = entities;
    }

    private void generateEntities(int start, int stop) {
        for(int i=0; i<size; i++) {
            entities.add(new Entity(start, stop, xVariablesCount));
        }
    }

    @Override
    public String toString() {
        return "Population: \n" + entities.toString();
    }

}
