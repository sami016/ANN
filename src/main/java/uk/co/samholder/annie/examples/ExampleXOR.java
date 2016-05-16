/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.samholder.annie.examples;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import uk.co.samholder.annie.Data;
import uk.co.samholder.annie.NetworkTrainer;
import uk.co.samholder.annie.NodeNetwork;
import uk.co.samholder.annie.NodeNetworks;
import uk.co.samholder.annie.activation.ActivationFunction;

/**
 *
 * @author sam
 */
public class ExampleXOR {

    public static void main(String[] args) {

        Random random = new Random();
        // Create network.
        NodeNetwork net = NodeNetworks.simpleNetwork(2, 2, 1);
        net.getLayer(1).get(0).setActivation(ActivationFunction.LINEAR);
        net.getLayer(1).get(1).setActivation(ActivationFunction.LINEAR);
        net.getOutputLayer().get(0).setActivation(ActivationFunction.LINEAR);

        // Create training data.
        List<List<Double>> training_in = new ArrayList<>();
        List<List<Double>> training_out = new ArrayList<>();

        training_in.add(Data.row(0, 0));
        training_out.add(Data.row(0));

        training_in.add(Data.row(1, 0));
        training_out.add(Data.row(1));

        training_in.add(Data.row(0, 1));
        training_out.add(Data.row(1));

        training_in.add(Data.row(1, 1));
        training_out.add(Data.row(0));

        // Train the network.
        NetworkTrainer trainer = new NetworkTrainer();
        List<Double> errors = trainer.train(net, training_in, training_out, 0.2, 1000, true);

        net.printStructure(true);

        for (double d : errors) {
            System.out.println(d);
        }

        for (int j = 0; j < training_in.size(); j++) {
            System.out.println(Data.toString(training_in.get(j)) + " -> " + net.evaluate(training_in.get(j), false));
        }
    }
}
