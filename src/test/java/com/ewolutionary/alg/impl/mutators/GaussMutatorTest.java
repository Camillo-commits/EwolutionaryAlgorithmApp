package com.ewolutionary.alg.impl.mutators;

import com.ewolutionary.alg.impl.Entity;
import org.jsoup.nodes.Entities;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

public class GaussMutatorTest {

    @Test
    public void shouldMutateEntities() {

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

        GaussMutator gm = new GaussMutator();
        gm.mutate(entities, 0.2);

        assertNotEquals(ch1Value, entities.get(0).getChromosomesValues().get(0), 0.0);
        assertNotEquals(ch2Value, entities.get(0).getChromosomesValues().get(1), 0.0);
    }

}