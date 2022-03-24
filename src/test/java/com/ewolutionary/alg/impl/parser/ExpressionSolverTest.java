package com.ewolutionary.alg.impl.parser;

import com.ewolutionary.alg.impl.Entity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class) // add 'public' if you want to run these tests
class ExpressionSolverTest {

    @Test
    public void shouldParsePowerExpression() {
        assertEquals(9.0, ExpressionSolver.solve("pow(3,2)", null));
        assertEquals(25.0, ExpressionSolver.solve("pow(3,2)+pow(2,4)", null));
        assertEquals(1024.0, ExpressionSolver.solve("pow(2,10)", null));
        assertEquals(1049.0, ExpressionSolver.solve("pow(2,10)+pow(3,2)+pow(2,4)", null));
        assertEquals(35.0, ExpressionSolver.solve("pow(3,2)+pow(2,4)+2*5", null));
    }

    @Test
    public void shouldParseSqrtExpression() {
        assertEquals(2.0, ExpressionSolver.solve("sqrt(4)", null));
        assertEquals(5.0, ExpressionSolver.solve("sqrt(4)+sqrt(9)", null));
        assertEquals(15.0, ExpressionSolver.solve("sqrt(4)+2*5+sqrt(9)", null));
        assertEquals(32.0, ExpressionSolver.solve("sqrt(1024)", null));
    }

    @Test
    public void shouldParseSqrtWithPower() {
        assertEquals(83.0, ExpressionSolver.solve("sqrt(4) + pow(3,4)", null));
        assertEquals(4.0, ExpressionSolver.solve("sqrt(pow(2,4))", null));
        assertEquals(4.0, ExpressionSolver.solve("pow(2,sqrt(4))", null));
    }

    @Test
    public void shouldParseExpressionWithX() {
        Entity entity = mock(Entity.class);

    }

}