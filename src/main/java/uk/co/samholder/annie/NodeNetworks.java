/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.samholder.annie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import uk.co.samholder.annie.activation.ActivationFunction;

/**
 *
 * @author sam
 */
public class NodeNetworks {

    private static Random random = new Random();

    private static Node fullyConnect(int index, NodeLayer previousLayer, ActivationFunction activation) {
        Map<Integer, Double> connections = new TreeMap<>();
        for (int i = 0; i < previousLayer.size(); i++) {
            connections.put(i, (random.nextDouble() - 0.5) * 4.0);
        }
        return new Node(index, connections, random.nextFloat(), activation);
    }

    public static NodeNetwork simpleNetwork(int inputs, int hiddens, int outputs) {
        List<NodeLayer> layers = new ArrayList<>();
        // Input layer.
        List<Node> inputNodes = new ArrayList<>();
        for (int i = 0; i < inputs; i++) {
            inputNodes.add(new Node(i));
        }
        layers.add(new NodeLayer(inputNodes));
        // Construct the hidden layer.
        List<Node> hiddenNodes = new ArrayList<>();
        for (int i = 0; i < hiddens; i++) {
            hiddenNodes.add(fullyConnect(i, layers.get(0), ActivationFunction.LINEAR));
        }
        layers.add(new NodeLayer(hiddenNodes));
        // Construct the output layer.
        List<Node> outputNodes = new ArrayList<>();
        for (int i = 0; i < outputs; i++) {
            ActivationFunction activation = ActivationFunction.LOGISTIC;//(i == outputs - 1) ?
            outputNodes.add(fullyConnect(i, layers.get(1), activation));
        }
        layers.add(new NodeLayer(outputNodes));
        // Output layer.
        NodeNetwork network = new NodeNetwork(layers);
        return network;
    }

    public static void main(String[] args) {
        NodeNetwork net = simpleNetwork(2, 2, 1);
        net.getOutputLayer().get(0).setActivation(ActivationFunction.LINEAR);

        List<List<Double>> training_in = new ArrayList<>();
        List<List<Double>> training_labels = new ArrayList<>();
        training_in.add(Arrays.asList(new Double[]{1.0, 1.0}));
        training_labels.add(Arrays.asList(new Double[]{0.0}));
        training_in.add(Arrays.asList(new Double[]{0.0, 0.0}));
        training_labels.add(Arrays.asList(new Double[]{0.0}));
        training_in.add(Arrays.asList(new Double[]{0.0, 1.0}));
        training_labels.add(Arrays.asList(new Double[]{1.0}));
        training_in.add(Arrays.asList(new Double[]{1.0, 0.0}));
        training_labels.add(Arrays.asList(new Double[]{1.0}));

        double learningRate = 0.01;
        double error = 0;
        Random r = new Random();
        System.out.println("epoch error");
        for (int epoch = 0; epoch < 500; epoch++) {
            for (int j = 0; j < training_in.size(); j++) {
                int example = r.nextInt(training_in.size());
                net.trainExample(training_in.get(example), training_labels.get(example), learningRate);
            }
            error = net.checkErrorSet(training_in, training_labels);
            if (epoch % 100 == 0) {
                System.out.println(epoch + " " + error);
            }
        }

        net.printStructure(true);

        List<Double> output = net.evaluate(Arrays.asList(new Double[]{1.0, 1.0}), false);
        System.out.println("1.0, 1.0 ->" + Arrays.toString(output.toArray()));
        output = net.evaluate(Arrays.asList(new Double[]{0.0, 1.0}), false);
        System.out.println("0.0, 1.0 ->" + Arrays.toString(output.toArray()));
        output = net.evaluate(Arrays.asList(new Double[]{1.0, 0.0}), false);
        System.out.println("1.0, 0.0 ->" + Arrays.toString(output.toArray()));
        output = net.evaluate(Arrays.asList(new Double[]{0.0, 0.0}), false);
        System.out.println("0.0, 0.0 ->" + Arrays.toString(output.toArray()));

        System.out.println("final error : " + error);

    }
}
