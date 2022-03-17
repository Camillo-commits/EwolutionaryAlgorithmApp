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
public class OnePointCrosserTest {

    private Random rnd;

    @Before
    public void setUp() {
        rnd = Mockito.mock(Random.class);

    }

    @Test
    public void shouldSwapBytesCorrectly() {
        //given
        Crosser crosser = new OnePointCrosser(rnd);
        byte[] chrom1 = {0, 0, 0, 0};
        byte[] chrom2 = {1, 1, 1, 1};
        Mockito.when(rnd.nextInt(anyInt())).thenReturn(0, 1, 2);

        //when
        List<Entity> solutionList = new ArrayList<>(Arrays.asList(new Entity(0, 0, chrom1),
                new Entity(0, 0, chrom2)));
        crosser.cross(solutionList, 1.0);

        //then
        Entity solution1 = solutionList.get(0);
        Entity solution2 = solutionList.get(1);

        Assert.assertSame(0, (int) solution1.getChromosome()[0]);
        Assert.assertSame(0, (int) solution1.getChromosome()[1]);
        Assert.assertSame(1, (int) solution1.getChromosome()[2]);
        Assert.assertSame(1, (int) solution1.getChromosome()[3]);

        Assert.assertSame(1, (int) solution2.getChromosome()[0]);
        Assert.assertSame(1, (int) solution2.getChromosome()[1]);
        Assert.assertSame(0, (int) solution2.getChromosome()[2]);
        Assert.assertSame(0, (int) solution2.getChromosome()[3]);
    }

    @Test
    public void shouldSwapBytesCorrectlyWhenNotEven() {
        //given
        Crosser crosser = new OnePointCrosser(rnd);
        byte[] chrom1 = {0, 0, 0, 0};
        byte[] chrom2 = {1, 1, 1, 1};
        byte[] chrom3 = {0, 1, 1, 0};
        Mockito.when(rnd.nextInt(anyInt())).thenReturn(0, 1, 2, 0, 2, 2);

        //when
        List<Entity> solutionList = new ArrayList<>(Arrays.asList(
                new Entity(0, 0, chrom1),
                new Entity(0, 0, chrom2),
                new Entity(0, 0, chrom3))
        );
        crosser.cross(solutionList, 1.0);

        //then
        Entity solution1 = solutionList.get(0);
        Entity solution2 = solutionList.get(1);
        Entity solution3 = solutionList.get(2);

        Assert.assertSame(0, (int) solution1.getChromosome()[0]);
        Assert.assertSame(0, (int) solution1.getChromosome()[1]);
        Assert.assertSame(1, (int) solution1.getChromosome()[2]);
        Assert.assertSame(1, (int) solution1.getChromosome()[3]);

        Assert.assertSame(1, (int) solution2.getChromosome()[0]);
        Assert.assertSame(1, (int) solution2.getChromosome()[1]);
        Assert.assertSame(0, (int) solution2.getChromosome()[2]);
        Assert.assertSame(0, (int) solution2.getChromosome()[3]);

        Assert.assertSame(0, (int) solution3.getChromosome()[0]);
        Assert.assertSame(0, (int) solution3.getChromosome()[1]);
        Assert.assertSame(1, (int) solution3.getChromosome()[2]);
        Assert.assertSame(0, (int) solution3.getChromosome()[3]);
    }
}
