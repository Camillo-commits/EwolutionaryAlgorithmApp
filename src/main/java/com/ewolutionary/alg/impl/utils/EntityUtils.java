package com.ewolutionary.alg.impl.utils;

import com.ewolutionary.alg.impl.Entity;

import java.util.Comparator;
import java.util.List;

public class EntityUtils {

    public static Entity findMaxBestSolution(List<Entity> population) {
        return population.stream().max(Comparator.comparingDouble(Entity::getFitness)).get();
    }

    public static Entity findMinBestSolution(List<Entity> population) {
        return population.stream().min(Comparator.comparingDouble(Entity::getFitness)).get();
    }

}
