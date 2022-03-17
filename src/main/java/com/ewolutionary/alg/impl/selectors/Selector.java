package com.ewolutionary.alg.impl.selectors;

import com.ewolutionary.alg.impl.Entity;
import com.ewolutionary.alg.impl.Population;

import java.util.List;

public interface Selector {
    public List<Entity> select(Population population, boolean isEliteStrategy);

    public Entity findBestSolution(List<Entity> population);
}
