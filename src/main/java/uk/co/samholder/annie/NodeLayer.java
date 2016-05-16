/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.samholder.annie;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author sam
 */
public class NodeLayer implements Iterable<Node> {

    private int id;
    private List<Node> nodes;

    public NodeLayer(int id, List<Node> nodes) {
        this.id = id;
        this.nodes = nodes;
    }

    public NodeLayer(int id, Node... nodes) {
        this(id);
        for (Node node : nodes) {
            this.nodes.add(node);
        }
    }

    public NodeLayer(int id) {
        this(id, new ArrayList<>());
    }

    public int getId() {
        return id;
    }

    public int size() {
        return nodes.size();
    }

    public Node get(int i) {
        return nodes.get(i);
    }

    public Set<Node> get(Set<Integer> errorNodes) {
        return errorNodes.stream().map(id -> get(id)).collect(Collectors.toSet());
    }

    public NodeLayer getUpstream(NodeNetwork network) {
        return network.getLayer(id - 1);
    }

    public NodeLayer getDownstream(NodeNetwork network) {
        return network.getLayer(id + 1);
    }

    public NodeLayer clone() {
        List<Node> nodesCopy = new ArrayList<>(nodes);
        return new NodeLayer(id, nodesCopy);
    }

    @Override
    public Iterator<Node> iterator() {
        return nodes.iterator();
    }

}
