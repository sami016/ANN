/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.samholder.annie.examples;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;
import uk.co.samholder.annie.Data;
import uk.co.samholder.annie.NetworkTrainer;
import uk.co.samholder.annie.Node;
import uk.co.samholder.annie.NodeLayer;
import uk.co.samholder.annie.NodeNetwork;
import uk.co.samholder.annie.activation.ActivationFunction;

/**
 *
 * @author sam
 */
public class ExampleSingle {

    public static void main(String[] args) {

        Random random = new Random();
        // Create network.
        NodeLayer input = new NodeLayer(0, new Node(0));
        NodeLayer output = new NodeLayer(1,
                new Node(0,
                        new TreeMap<Integer, Double>() {
                    {
                        put(0, (random.nextDouble() - 0.5) * 4.0);
                    }
                },
                        (random.nextDouble() - 0.5) * 4.0,
                        ActivationFunction.LINEAR)
        );
        NodeNetwork net = new NodeNetwork(input, output);

        // Create training data.
        List<List<Double>> training_in = new ArrayList<>();
        List<List<Double>> training_out = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            training_in.add(Data.row(i));
            training_out.add(Data.row(i * 0.5 + 2));
        }

        // Train the network.
        NetworkTrainer trainer = new NetworkTrainer();
        List<Double> errors = trainer.train(net, training_in, training_out, 0.001, 3000, true);

        double res;
        res = net.evaluate(Data.row(0), true).get(0);
        System.out.println("0 -> " + res);

        net.printStructure(true);

        for (double error : errors) {
            System.out.println(error);
        }

    }

}
