package com.ewolutionary.alg.impl.crossers;

import com.ewolutionary.alg.impl.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Crosser {
    public static int minProbability = 0;
    public static int maxProbability = 1;
    protected Random rnd = new Random();

    protected int calcFullSize(List<Entity> list1, List<Entity> list2) {
        return list1.size() + list2.size();
    }

    protected abstract List<Entity> cross(Entity first, Entity second);

    public final void cross(List<Entity> individuals, double probability, int sizeOfPopulation) {
        List<Entity> result = new ArrayList<>();
        List<Entity> unmodifiedResult = new ArrayList<>();
        double iterations = (double) sizeOfPopulation / 2;

        for (int i = 0; i < Math.ceil(iterations); ++i) {
            int firstIndex = rnd.nextInt(individuals.size());
            int secondIndex = rnd.nextInt(individuals.size());

            boolean willBeCrossed = Math.floor(Math.random() * (maxProbability - minProbability + 1) + minProbability) <= probability;
            Entity first = individuals.get(firstIndex);
            Entity second = individuals.get(secondIndex);
            if (willBeCrossed) {
                result.addAll(cross(first, second));
            } else {
                unmodifiedResult.add(first);
                unmodifiedResult.add(second);
            }
        }

        individuals.clear();
        while (sizeOfPopulation != calcFullSize(result, unmodifiedResult)) {
            result.remove(result.size() - 1);
        }
        individuals.addAll(result);
        individuals.addAll(unmodifiedResult);
    }
}
