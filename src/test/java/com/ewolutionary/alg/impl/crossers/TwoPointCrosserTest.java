package com.ewolutionary.alg.impl.crossers;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Random;

@RunWith(MockitoJUnitRunner.class)
public class TwoPointCrosserTest {
    private Random rnd;

    @Before
    public void setUp() {
        rnd = Mockito.mock(java.util.Random.class);
    }


}
