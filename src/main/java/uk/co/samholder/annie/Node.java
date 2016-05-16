/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.samholder.annie;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import uk.co.samholder.annie.activation.ActivationFunction;

/**
 *
 * @author sam
 */
public class Node {

    private int id;
    private Map<Integer, Double> connections;
    private double bias;
    private ActivationFunction activation;

    private Set<Integer> downstreamNodes;

    private double input;
    private double output;
    private double delta;
    private boolean inputNode;

    public Node(int id) {
        this.id = id;
        connections = new TreeMap<>();
        this.bias = 0f;
        downstreamNodes = new TreeSet<>();
    }

    public Node(int id, Map<Integer, Double> connections, double bias, ActivationFunction activation) {
        this.id = id;
        this.connections = connections;
        this.bias = bias;
        this.activation = activation;
        downstreamNodes = new TreeSet<>();
    }

    public int getId() {
        return id;
    }

    public int numLinks() {
        return connections.size();
    }

    public Set<Integer> links() {
        return connections.keySet();
    }

    public double getUpstreamLinkStrength(int linkNumber) {
        return connections.get(linkNumber);
    }

    public double getUpstreamLinkStrength(Node node) {
        return getUpstreamLinkStrength(node.getId());
    }

    public double getBias() {
        return bias;
    }

    public Node clone() {
        Map con = new TreeMap(connections);
        return new Node(id, con, bias, activation);
    }

    public ActivationFunction getActivation() {
        return activation;
    }

    public void setActivation(ActivationFunction activation) {
        this.activation = activation;
    }

    public double getInput() {
        return input;
    }

    public double getOutput() {
        return output;
    }

    public boolean isInputNode() {
        return inputNode;
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    public void setInputNode(double input) {
        this.input = input;
        this.output = input;
        this.inputNode = true;
    }

    public void setHiddenNode(double sum, double activation) {
        this.input = sum;
        this.output = activation;
        this.inputNode = false;
    }

    public void registerDownstreamNode(int i) {
        downstreamNodes.add(i);
    }

    /**
     * Get the downstream nodes. i.e. the nodes which this node feeds into.
     *
     * @param network
     * @param layer
     * @return
     */
    public Set<Node> downstreamNodes(NodeNetwork network, NodeLayer layer) {
        return layer.getDownstream(network).get(downstreamNodes);
    }

    /**
     * Get the upstream nodes. i.e. the nodes which feed into this node.
     *
     * @param network
     * @param layer
     * @return
     */
    public Set<Node> upstreamNodes(NodeNetwork network, NodeLayer layer) {
        return layer.getUpstream(network).get(connections.keySet());
    }

    public void offsetBias(double offset) {
        bias += offset;
    }

    public void offsetWeight(int linkNumber, double offset) {
        connections.put(linkNumber, connections.get(linkNumber) + offset);
    }

    public Map<Integer, Double> getConnections() {
        return connections;
    }

}
