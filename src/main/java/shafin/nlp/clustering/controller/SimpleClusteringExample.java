package shafin.nlp.clustering.controller;

import shafin.nlp.clustering.AverageLinkageStrategy;
import shafin.nlp.clustering.Cluster;
import shafin.nlp.clustering.ClusteringAlgorithm;
import shafin.nlp.clustering.DefaultClusteringAlgorithm;
import shafin.nlp.clustering.visualization.DendrogramPanel;

public class SimpleClusteringExample {

	public static void main(String[] args) {
		String[] names = new String[] { "B", "C", "D", "E", "F", "G" };
		double[][] distances = new double[][] { 
		    { 0,  6,  2,  4, 10, 14 },
		    { 6,  0,  1,  9,  8, 13 }, 
		    { 2,  1,  0, 12,  3,  9 },
		    { 4,  9, 12,  0,  2,  7 }, 
		    { 10, 8,  3,  2,  0,  5 },
		    { 14, 13, 9,  7,  5,  0 }};

		ClusteringAlgorithm alg = new DefaultClusteringAlgorithm();
		Cluster cluster = alg.performClustering(distances, names, new AverageLinkageStrategy());
		
		DendrogramPanel.visualize(cluster);
	}
}
