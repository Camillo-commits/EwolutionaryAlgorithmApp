package com.ewolutionary.alg.function;

import com.ewolutionary.alg.impl.parser.ExpressionSolver;

import java.util.List;

public class Functions {

    public static double function(String name, List<Double> x) {
        if(name == null) {
            throw new IllegalArgumentException("Function cannot be null!!!");
        }
        switch (name) {
            case "2x0^2+5":
                return functionPow2xPlus5(x);
            case "x0^2+x1":
                return functionX1PowPlusX2(x);
            case "BealFunction":
                return bealFunction(x);
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

    private static double bealFunction(List<Double> x) {
        double x1 = x.get(0);
        double x2 = x.get(1);
        return Math.pow(1.5 - x1 + x1 * x2, 2) + Math.pow(2.25 - x1 + x1 * x2*x2, 2) + Math.pow(2.625 - x1 + x1 * x2*x2*x2, 2);
    }

}
