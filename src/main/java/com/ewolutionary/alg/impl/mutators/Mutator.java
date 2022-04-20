package com.ewolutionary.alg.impl.mutators;

import com.ewolutionary.alg.impl.Entity;

import java.util.List;

public interface Mutator {
    public void mutate(List<Entity> individualism, double mutationProbability);
}
