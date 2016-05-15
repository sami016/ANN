/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.samholder.annie;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sam
 */
public class NodeNetwork {

    private List<NodeLayer> layers;

    public NodeNetwork() {
    }

    public NodeNetwork(List<NodeLayer> layers) {
        this.layers = layers;
    }

    public NodeNetwork clone() {
        List<NodeLayer> layersCopy = new ArrayList<>(layers);
        return new NodeNetwork(layersCopy);
    }

    public NodeLayer getInputLayer() {
        return layers.get(0);
    }

    public NodeLayer getOutputLayer() {
        return layers.get(layers.size() - 1);
    }

    private void setInputWeights(List<Double> inputs) {
        NodeLayer inputLayer = getInputLayer();
        if (inputs.size() < inputLayer.size()) {
            throw new IllegalArgumentException("Not enough inputs");
        }
        for (int i = 0; i < inputs.size(); i++) {
            Node node = inputLayer.get(i);
            node.setInputNode(inputs.get(i));
        }
    }

    /**
     * Get the output values from the final layer of the network.
     *
     * @return
     */
    private List<Double> getOutputWeights() {
        NodeLayer outputLayer = getOutputLayer();
        List<Double> outputs = new ArrayList<>(outputLayer.size());
        for (int i = 0; i < outputLayer.size(); i++) {
            outputs.add(outputLayer.get(i).getOutput());
        }
        return outputs;
    }

    /**
     * Performs a forwards pass evaluation.
     *
     * @param inputs
     * @return list of outputs.
     */
    public List<Double> evaluate(List<Double> inputs, boolean training) {
        // Set the weights in the first layer.
        setInputWeights(inputs);
        // Iterate through non-input layers one by one and evaluate.
        NodeLayer previousLayer = layers.get(0);
        for (int layerIndex = 1; layerIndex < layers.size(); layerIndex++) {
            NodeLayer layer = layers.get(layerIndex);
            for (int nodeIndex = 0; nodeIndex < layer.size(); nodeIndex++) {
                Node node = layer.get(nodeIndex);
                evaluateNode(node, previousLayer, training);
            }
            previousLayer = layer;
        }

        return getOutputWeights();
    }

    private void evaluateNode(Node node, NodeLayer previousLayer, boolean training) {
        double sum = node.getBias();
        // Create sum from all connected nodes.
        for (int link : node.links()) {
            if (training) {
                previousLayer.get(link).addErrorNode(node.getId());
            }
            sum += node.getLinkStrength(link) * previousLayer.get(link).getOutput();
        }
        // apply the activation function.
        double activation = node.getActivation().apply(sum);
        node.setHiddenNode(sum, activation);
    }

    private static float norm(List<Double> correct, List<Double> predicted) {
        float total = 0f;
        for (int i = 0; i < correct.size(); i++) {
            total += (float) Math.pow(correct.get(i) - predicted.get(i), 2);
        }
        return (float) Math.sqrt(total);
    }

    protected void backPropogate(List<Double> correct) {
        //float error = norm(correct, getOutputWeights());
        // Final layer error.
        NodeLayer outputLayer = getOutputLayer();
        List<Double> output = getOutputWeights();
        for (int i = 0; i < outputLayer.size(); i++) {
            // Calculate error on the ith output.
            double error = correct.get(i) - output.get(i);
            // Set the delta to the error in dimension i multiplied by the activation derivative of the input.
            Node node = outputLayer.get(i);
            node.setDelta(error * node.getActivation().applyDerivative(node.getInput()));
        }
        // Recursive layer errors.
        for (int l = layers.size() - 2; l > 0; l--) {
            NodeLayer layer = layers.get(l);
            // Iterate over nodes in a layer.
            for (int i = 0; i < layer.size(); i++) {
                Node node = layer.get(i);
                float nodeError = 0;
                // Accumulate error from all errors the node contributes to downstream.
                for (int errorSourceIndex : node.errorNodes()) {
                    Node downstreamNode = layers.get(l + 1).get(errorSourceIndex);
                    // Add the error from the downstream node, times the connection strength to this node.
                    nodeError += downstreamNode.getDelta() * downstreamNode.getLinkStrength(i);
                }
                // Multiply by the activation derivative of the input.
                nodeError *= node.getActivation().applyDerivative(node.getInput());
                node.setDelta(nodeError);
            }
        }
    }

    private void updateParameters(double learningRate) {
        for (int layerIndex = 1; layerIndex < layers.size(); layerIndex++) {
            NodeLayer layer = layers.get(layerIndex);

            for (int nodeIndex = 1; nodeIndex < layer.size(); nodeIndex++) {
                Node node = layer.get(nodeIndex);
                double delta = node.getDelta();
                node.offsetBias(delta * learningRate);

                for (int link : node.links()) {
                    Node sourceNode = layers.get(layerIndex - 1).get(link);
                    node.offsetWeight(link, delta * sourceNode.getOutput() * learningRate);
                }
            }
        }
    }

    public void trainExample(List<Double> inputs, List<Double> correct, double learningRate) {
        evaluate(inputs, true);
        backPropogate(correct);

        updateParameters(learningRate);
    }

    public void trainExampleSet(List<List<Double>> inputSet, List<List<Double>> labelSet, double learningRate) {
        for (int i = 0; i < inputSet.size(); i++) {
            trainExample(inputSet.get(i), labelSet.get(i), learningRate);
        }
    }

    public float checkError(List<Double> inputs, List<Double> correct) {
        evaluate(inputs, false);
        return norm(correct, getOutputWeights());
    }

    public float checkErrorSet(List<List<Double>> inputSet, List<List<Double>> correctSet) {
        float errorSum = 0f;
        for (int i = 0; i < inputSet.size(); i++) {
            errorSum += checkError(inputSet.get(i), correctSet.get(i));
        }
        return errorSum / (float) inputSet.size();
    }

    public void printStructure(boolean showRunDetails) {
        for (int layerNo = 0; layerNo < layers.size(); layerNo++) {
            System.out.println("Layer " + layerNo + (layerNo == 0 ? "(input)" : ""));
            NodeLayer layer = layers.get(layerNo);
            for (int nodeNo = 0; nodeNo < layer.size(); nodeNo++) {
                Node node = layer.get(nodeNo);
                System.out.println(nodeNo + ": " + node.getConnections() + " bias:" + node.getBias());
                System.out.println("\tin:" + node.getInput() + " out:" + node.getOutput() + "     delta:" + node.getDelta());
            }
        }
    }

}
