package com.ewolutionary.alg.impl.selectors;

import com.ewolutionary.alg.impl.Entity;
import com.ewolutionary.alg.impl.Population;
import com.ewolutionary.alg.impl.Solver;
import com.ewolutionary.alg.impl.selectors.configuration.RouletteSelectorConfiguration;
import org.bouncycastle.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;

public class RouletteSelectorTest {

    @Test
    public void doubleChromosome() {
        RouletteSelector rs = new RouletteSelector();
        RouletteSelectorConfiguration rsc = new RouletteSelectorConfiguration(0.50);

        Population p = getMinPopulation();
        System.out.println(p);

        Solver.overrideFunctionToSolve("x0^2+x1");
        List<Entity> selected = rs.select(p, false, 0, rsc);
        System.out.println(selected);

    }


    @Test
    public void shouldSelectProperlyEntitiesForElite() {

        RouletteSelector rs = new RouletteSelector();
        Population p = getPopulation();
        RouletteSelectorConfiguration rsc = new RouletteSelectorConfiguration(0.20);

        Solver.overrideFunctionToSolve("2x0^2+5");
        List<Entity> selected = rs.select(p, true, 1, rsc);


        for(int i = 0; i < 100; i++) {
            List<Entity> dd = p.getEntities().subList(20, 100);
            dd.addAll(selected);
            p.setEntities(dd);
            selected = rs.select(p, false, 0, rsc);
        }

//        System.out.println(selected);
        assertEquals(selected.size(), 20);
        Optional<Entity> en = selected.stream().filter(e -> e.getValue().get(0) != 3.937007874015748).findAny();
        assertFalse(en.isPresent());
    }

    @Test
    public void shouldSelectProperlyEntitiesNotElite() {

        RouletteSelector rs = new RouletteSelector();
        Population p = getPopulation();
        RouletteSelectorConfiguration rsc = new RouletteSelectorConfiguration(0.20);

        Solver.overrideFunctionToSolve("2x0^2+5");

        List<Entity> selected = rs.select(p, false, 0, rsc);

        for(int i = 0; i < 100; i++) {
            List<Entity> dd = p.getEntities().subList(20, 100);
            dd.addAll(selected);
            p.setEntities(dd);
            selected = rs.select(p, false, 0, rsc);
        }

        assertEquals(selected.size(), 20);
        Optional<Entity> en = selected.stream().filter(e -> e.getValue().get(0) == 3.937007874015748).findAny();
        assertTrue(en.isPresent());
    }

    private Population getMinPopulation() {
        Population p = new Population(10, 2, 0, 1, 2);

        int chromosomeBinary = 0;
        for(int i=0; i<10; i++) {
            p.getEntities().get(i).setChromosomesBytes(List.of(decimalToBinary(chromosomeBinary++), decimalToBinary(chromosomeBinary++)));
        }

        return p;
    }

    private Population getPopulation() {
        Population p = new Population(100, 2, 0, 1, 1);
        int chromosomeBinary = 1;

        for(int i=0; i<99; i++) {
            p.getEntities().get(i).setChromosomesBytes(List.of(decimalToBinary(chromosomeBinary)));
            chromosomeBinary++;
        }
        p.getEntities().get(99).setChromosomesBytes(List.of(decimalToBinary(500)));

        return p;
    }

    public byte[] decimalToBinary(int num)
    {
        byte[] binary = new byte[10];
        int id = 0;

        while (num > 0) {
            binary[id++] = (byte) ((byte) num % 2);
            num = num / 2;
        }

        return Arrays.reverse(binary);
    }
}