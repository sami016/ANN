/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.samholder.annie;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author sam
 */
public class NetworkTrainer {

    private Random random = new Random();

    public List<Double> train(NodeNetwork network, List<List<Double>> trainingInput, List<List<Double>> trainingLabels, double learningRate, int epochs, boolean verbose) {
        List<Double> errorLog = new ArrayList<>();
        for (int i = 0; i < epochs; i++) {
            for (int j = 0; j < trainingInput.size(); j++) {
                int example = random.nextInt(trainingInput.size());
                network.trainExample(trainingInput.get(example), trainingLabels.get(example), learningRate);
            }
            if (verbose) {
                double error = network.checkErrorSet(trainingInput, trainingLabels);
                errorLog.add(error);
                System.out.println(i + " " + error);
            }

        }
        return errorLog;
    }

}
