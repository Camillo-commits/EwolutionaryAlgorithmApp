package com.ewolutionary.alg.function;

import com.ewolutionary.alg.impl.parser.ExpressionSolver;

import java.util.List;

public class Functions {

    public static double function(String name, List<Double> x) {
        if(name == null) {
            //TODO get input function
            return 0;
        }
        switch(name) {
            case "2x^2+5":
                return functionPow2xPlus5(x);
            case "x1^2+x2":
                return functionX1PowPlusX2(x);
            default:
                return ExpressionSolver.solve(name, x);
        }

    }

    private static double functionPow2xPlus5(List<Double> x) {
        double x1 = x.get(0);
        return 2 * x1 * x1 + 5;
    }

    private static double functionX1PowPlusX2(List<Double> x) {
        double x1 = x.get(0);
        double x2 = x.get(1);
        return x1 * x1 + x2;
    }

}
