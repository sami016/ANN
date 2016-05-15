/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.samholder.annie.activation;

/**
 *
 * @author sam
 */
public class LogisticActivationFunction implements ActivationFunction {

    @Override
    public double apply(double in) {
        return 1.0 / (1.0 + Math.exp(-in));
    }

    @Override
    public double applyDerivative(double in) {
        double sig = apply(in);
        return sig * (1.0 - sig);
    }
}
