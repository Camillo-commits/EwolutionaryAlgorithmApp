package com.ewolutionary.alg.impl.crossers;

import com.ewolutionary.alg.impl.Entity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.mockito.ArgumentMatchers.anyInt;

@RunWith(MockitoJUnitRunner.class)
public class HomogeneousCrosserTest {

    private Random rnd;

    @Before
    public void setUp() {
        rnd = Mockito.mock(java.util.Random.class);
    }

    @Test
    public void shouldSwapBytesCorrectly() {
        Crosser crosser = new HomogeneousCrosser(rnd);
        byte[] chrom1 = {0, 0, 0};
        byte[] chrom2 = {1, 1, 1};
        Mockito.when(rnd.nextInt(anyInt())).thenReturn(0, 1, 0, 0, 1);

        //when
        List<Entity> solutionList = new ArrayList<>(Arrays.asList(new Entity(0, 0, List.of(chrom1)),
                new Entity(0, 0, List.of(chrom2))));
        crosser.cross(solutionList, 1.0);

        //then
        Entity solution1 = solutionList.get(0);
        Entity solution2 = solutionList.get(1);

        Assert.assertSame(0, (int) solution1.getChromosomesBytes().get(0)[0]);
        Assert.assertSame(0, (int) solution1.getChromosomesBytes().get(0)[1]);
        Assert.assertSame(1, (int) solution1.getChromosomesBytes().get(0)[2]);

        Assert.assertSame(1, (int) solution2.getChromosomesBytes().get(0)[0]);
        Assert.assertSame(1, (int) solution2.getChromosomesBytes().get(0)[1]);
        Assert.assertSame(0, (int) solution2.getChromosomesBytes().get(0)[2]);
    }
}
