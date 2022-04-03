package com.ewolutionary.alg.impl.selectors;

import com.ewolutionary.alg.impl.Entity;
import com.ewolutionary.alg.impl.Population;
import com.ewolutionary.alg.impl.Solver;
import com.ewolutionary.alg.impl.selectors.configuration.BestSelectorConfiguration;
import junitx.util.PrivateAccessor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class BestSelectorTest {

    @Test
    public void shouldSelectBest() throws Throwable {
        List<Entity> entityList = new ArrayList<>(Arrays.asList());

        for (double i = 0; i < 100; ++i) {
            Entity entity = new Entity(0, 1, 0, 1);
            Class[] classes = {Double.class};
            Object[] objects = {i};
            PrivateAccessor.invoke(entity, "setFitness", classes, objects);
            entityList.add(entity);
        }

        Solver.overrideFunctionToSolve("2x0^2+5");

        Population population = new Population(100,0,0,1,1);
        population.setEntities(entityList);

        Selector selector = SelectorProvider.getSelector(SelectorOption.BEST);
        List<Entity> result = selector.select(population, false, 0, new BestSelectorConfiguration(0.35));
        result.forEach(entity -> Assert.assertTrue(entity.getFitness() >= 65) );
    }
}
