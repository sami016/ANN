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
import java.util.TreeMap;
import org.junit.Assert;
import org.junit.Test;
import uk.co.samholder.annie.activation.ActivationFunction;

/**
 *
 * @author sam
 */
public class NodeNetworksTest {

    @Test
    public void testMain() {
        Node node = new Node(0);
        List<Node> nodes = new ArrayList<>();
        nodes.add(node);
        NodeLayer layer = new NodeLayer(nodes);
        List<NodeLayer> layers = new ArrayList<>();
        layers.add(layer);
        NodeNetwork network = new NodeNetwork(layers);

        double output1 = network.evaluate(Arrays.asList(new Double[]{1.0}), false).get(0);
        Assert.assertTrue(output1 == 1f);
        double output0 = network.evaluate(Arrays.asList(new Double[]{0.0}), false).get(0);
        Assert.assertTrue(output0 == 0f);
    }

    @Test
    public void biasTest() {
        Node inNode, outNode;

        List<NodeLayer> layers = new ArrayList<>();
        {
            inNode = new Node(0);
            List<Node> nodes = new ArrayList<>();
            nodes.add(inNode);
            NodeLayer layer = new NodeLayer(nodes);
            layers.add(layer);
        }
        {
            Map<Integer, Double> con = new TreeMap<>();
            con.put(0, 1.0);
            outNode = new Node(0, con, 1, ActivationFunction.LINEAR);
            List<Node> nodes = new ArrayList<>();
            nodes.add(outNode);
            NodeLayer layer = new NodeLayer(nodes);
            layers.add(layer);
        }
        NodeNetwork network = new NodeNetwork(layers);

        double output1 = network.evaluate(Arrays.asList(new Double[]{1.0}), true).get(0);
        Assert.assertTrue(inNode.getOutput() == 1f);
        Assert.assertTrue(outNode.getInput() == 1f * 1f + 1f);

        network.backPropogate(Arrays.asList(new Double[]{0.5}));
        System.out.println("outnode error: " + outNode.getDelta());

        double output0 = network.evaluate(Arrays.asList(new Double[]{0.0}), true).get(0);
        Assert.assertTrue(inNode.getOutput() == 0f);
        Assert.assertTrue(outNode.getInput() == 0f * 1f + 1f);
        System.out.println(outNode.getOutput());
    }

}
