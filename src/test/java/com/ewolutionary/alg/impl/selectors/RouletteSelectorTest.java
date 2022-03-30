package com.ewolutionary.alg.impl.selectors;

import com.ewolutionary.alg.impl.Entity;
import com.ewolutionary.alg.impl.Population;
import com.ewolutionary.alg.impl.selectors.configuration.RouletteSelectorConfiguration;
import org.bouncycastle.util.Arrays;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.ewolutionary.alg.impl.selectors.configuration.SelectorKeys.BEST_PERCENTAGE;
import static org.testng.Assert.*;

public class RouletteSelectorTest {

    @Test
    public void shouldSelectProperlyEntitiesNotElite() {
        //function 2*x*x+5
        RouletteSelector rs = new RouletteSelector();
        Population p = getPopulation();
        RouletteSelectorConfiguration rsc = new RouletteSelectorConfiguration(0.20);

        List<Entity> selected = rs.select(p, false, 0, rsc);


        for(int i = 0; i < 100; i++) {
            List<Entity> dd = p.getEntities().subList(20, 100);
            dd.addAll(selected);
            p.setEntities(dd);
            selected = rs.select(p, false, 0, rsc);
        }

        System.out.println(selected);
        assertEquals(selected.size(), 20);
    }

    private Population getPopulation() {
        Population p = new Population(100, 2, 0, 1, 1);
        System.out.println(p);

        int chromosomeBinary = 1;
        List<byte[]> binaries = new ArrayList<>();

        for(int i=0; i<100; i++) {
            p.getEntities().get(i).setChromosomesBytes(List.of(decimalToBinary(chromosomeBinary)));
            chromosomeBinary++;
        }

        System.out.println(p);

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