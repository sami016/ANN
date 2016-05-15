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
public class LinearActivationFunction implements ActivationFunction {

    @Override
    public double apply(double in) {
        return in;
    }

    @Override
    public double applyDerivative(double in) {
        return 1f;
    }

}
