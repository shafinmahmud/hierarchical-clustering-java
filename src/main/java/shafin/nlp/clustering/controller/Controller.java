package shafin.nlp.clustering.controller;

import shafin.nlp.clustering.AverageLinkageStrategy;
import shafin.nlp.clustering.Cluster;
import shafin.nlp.clustering.ClusteringAlgorithm;
import shafin.nlp.clustering.DefaultClusteringAlgorithm;
import shafin.nlp.clustering.visualization.DendrogramPanel;

public class Controller {

	public static void main(String[] args) {
		String[] names = new String[] { "O1", "O2", "O3", "O4", "O5", "O6" };
		double[][] distances = new double[][] { 
		    { 0, 1, 9, 7, 11, 14 },
		    { 1, 0, 4, 3, 8, 10 }, 
		    { 9, 4, 0, 9, 2, 8 },
		    { 7, 3, 9, 0, 6, 13 }, 
		    { 11, 8, 2, 6, 0, 10 },
		    { 14, 10, 8, 13, 10, 0 }};

		ClusteringAlgorithm alg = new DefaultClusteringAlgorithm();
		Cluster cluster = alg.performClustering(distances, names,
		    new AverageLinkageStrategy());
		
		DendrogramPanel.visualize(cluster);
	}
}
