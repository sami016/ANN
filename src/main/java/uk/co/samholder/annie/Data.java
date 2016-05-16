/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.samholder.annie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author sam
 */
public class Data {

    public static List<Double> row(double... values) {
        List<Double> list = new ArrayList<>();
        for (double val : values) {
            list.add(val);
        }
        return list;
    }

    public static List<List<Double>> set(List<Double>... rows) {
        List<List<Double>> list = new ArrayList<>();
        for (List<Double> row : rows) {
            list.add(row);
        }
        return list;
    }

    public static String toString(List<Double> inputs) {
        return Arrays.toString(inputs.toArray());
    }
}
