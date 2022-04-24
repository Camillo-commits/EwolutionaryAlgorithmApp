package com.ewolutionary.alg.impl.crossers;

import com.ewolutionary.alg.impl.Entity;
import com.ewolutionary.alg.impl.Solver;
import junitx.util.PrivateAccessor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class BlxABCrosserTest {

    private Random rnd;

    private final BlxABCrosser crosser = new BlxABCrosser();

    @Before
    public void setUp() {
        rnd = Mockito.mock(Random.class);
    }

    @Test
    public void shouldCrossCorrectly() throws Throwable {
        //given
        Entity first = new Entity(-5, 5, 1);
        first.setChromosomesValues(Collections.singletonList(4.0));
        Entity second = new Entity(-5, 5, 1);
        second.setChromosomesValues(Collections.singletonList(2.0));

        Solver.overrideFunctionToSolve("x0");

        Mockito.when(rnd.nextDouble()).thenReturn(0.5);

        Class[] classes = {Random.class};
        Object[] objects = {rnd};
        PrivateAccessor.invoke(crosser, "setRnd", classes, objects);

        //when
        List<Entity> result = crosser.cross(first, second);

        //then
        Assert.assertEquals(2, result.size());
        result.forEach(entity -> assertEquals(3, entity.getChromosomesValues().get(0)));

    }
}