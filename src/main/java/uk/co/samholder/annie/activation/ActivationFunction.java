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
public interface ActivationFunction {

    public static final ActivationFunction LINEAR = new LinearActivationFunction();
    public static final ActivationFunction LOGISTIC = new LogisticActivationFunction();

    public double apply(double in);

    public double applyDerivative(double in);

}
