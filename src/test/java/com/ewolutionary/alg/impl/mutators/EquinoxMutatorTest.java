package com.ewolutionary.alg.impl.mutators;

import com.ewolutionary.alg.impl.Entity;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.testng.Assert.*;

public class EquinoxMutatorTest {

    @Test
    public void shouldMutateEntities() {

        Random rnd = Mockito.mock(java.util.Random.class);
        Mockito.when(rnd.nextDouble()).thenReturn(0.1, 0.9, 0.2, 0.23, 0.19);

        List<Entity> entities = new ArrayList<>();
        Entity e1 = new Entity(0,5,2,2);
        entities.add(e1);
        double ch1Value = e1.getChromosomesValues().get(0);
        double ch2Value = e1.getChromosomesValues().get(1);
        entities.add(new Entity(0, 5, 2, 2));
        entities.add(new Entity(0, 5, 2, 2));
        entities.add(new Entity(0, 5, 2, 2));
        entities.add(new Entity(0, 5, 2, 2));
        entities.add(new Entity(0, 5, 2, 2));

        EquinoxMutator em = new EquinoxMutator(rnd);
        em.mutate(entities, 0.2);

        assertTrue(ch1Value != entities.get(0).getChromosomesValues().get(0) || ch2Value != entities.get(0).getChromosomesValues().get(1));
    }

}