package com.ewolutionary.alg.impl.parser;

//import com.bgsoftware.superiorskyblock.scripts.RhinoScript;
import com.bgsoftware.superiorskyblock.scripts.RhinoScript;
import com.ewolutionary.alg.impl.Entity;

import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.List;

public class ExpressionSolver {

    private ExpressionSolver() {}

    public static double solve(String expression, List<Double> values) {
        expression = fillExpressionWithEntityValues(expression, values);

        if(expression.indexOf("pow(")>expression.indexOf("sqrt(")){
            while(expression.contains("pow")) {
                expression = resolvePowerFunction(expression);
            }
            while(expression.contains("sqrt")) {
                expression = resolveSqrtFunction(expression);
            }
        } else {
            while(expression.contains("sqrt")) {
                expression = resolveSqrtFunction(expression);
            }
            while(expression.contains("pow")) {
                expression = resolvePowerFunction(expression);
            }
        }

        RhinoScript rs = RhinoScript.getInstance();
        String strResult;

        try {
            strResult = rs.eval(expression).toString();
            return Double.parseDouble(strResult);
        } catch (ScriptException e) {
            e.printStackTrace();
        }

        return 0.0;
    }

    private static String fillExpressionWithEntityValues(String expression, List<Double> values) {
        for(int i=0; i<values.size(); i++) {
            expression = expression.replace("x"+i, Double.toString(values.get(i)));
        }
        return expression;
    }

    private static String resolvePowerFunction(String expression) {
        int powIndex = expression.indexOf("pow(")+4;
        int powEndIndex =  expression.indexOf(')', powIndex);
        String str = expression.substring(powIndex, powEndIndex);
        while(str.contains("sqrt")) {
            str = resolveSqrtFunction(str);
        }
        List<Double> d = new ArrayList<>();
        for(String s: str.split(",")) {
            d.add(Double.parseDouble(s));
        }
        double power = Math.pow(d.get(0), d.get(1));
        String powerReplace = expression.substring(powIndex-4, powEndIndex+1);

        return expression.replace(powerReplace, String.valueOf(power));
    }

    private static String resolveSqrtFunction(String expression) {
        int startIndex = expression.indexOf("sqrt(")+5;
        int endIndex =  expression.indexOf(')', startIndex);
        String ss = expression.substring(startIndex, endIndex);
        while(ss.contains("pow")) {
            ss = resolvePowerFunction(ss);
        }
        double doub = Double.parseDouble(ss);
        double power = Math.sqrt(doub);
        String powerReplace = expression.substring(startIndex-5, endIndex+1);

        return expression.replace(powerReplace, String.valueOf(power));
    }

}
