package com.ewolutionary.alg.impl.selectors;

import com.ewolutionary.alg.impl.Entity;
import com.ewolutionary.alg.impl.Population;
import com.ewolutionary.alg.impl.selectors.configuration.BestSelectorConfiguration;
import com.ewolutionary.alg.impl.selectors.configuration.SelectorConfiguration;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.ewolutionary.alg.impl.selectors.configuration.SelectorKeys.BEST_PERCENTAGE;

public class BestSelector implements Selector {

    @Override
    public List<Entity> select(Population population, boolean isEliteStrategy, int percentOfBestToNextCentury, SelectorConfiguration configuration) {
        if (!(configuration instanceof BestSelectorConfiguration)) {
            throw new IllegalArgumentException("WrongConfiguration");
        }
        int amount2Select = (int) (population.getEntities().size() * (double) configuration.getConfigurations().get(BEST_PERCENTAGE));
        List<Entity> entities = population
                .getEntities()
                .stream()
                .sorted(Comparator.comparingDouble(Entity::getFitness))
                .collect(Collectors.toList());

        return entities.subList(0, amount2Select);
    }
}
