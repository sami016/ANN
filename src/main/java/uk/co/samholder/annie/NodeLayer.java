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
public class NodeLayer {

    private List<Node> nodes;

    public NodeLayer(List<Node> nodes) {
        this.nodes = nodes;
    }

    public NodeLayer() {
        this(new ArrayList<>());
    }

    public int size() {
        return nodes.size();
    }

    public Node get(int i) {
        return nodes.get(i);
    }

    public NodeLayer clone() {
        List<Node> nodesCopy = new ArrayList<>(nodes);
        return new NodeLayer(nodesCopy);
    }

}
