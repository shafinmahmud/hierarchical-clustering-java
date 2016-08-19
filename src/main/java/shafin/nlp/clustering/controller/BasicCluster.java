package shafin.nlp.clustering.controller;

import java.io.IOException;
import java.util.List;

import shafin.nlp.clustering.AverageLinkageStrategy;
import shafin.nlp.clustering.Cluster;
import shafin.nlp.clustering.ClusteringAlgorithm;
import shafin.nlp.clustering.DefaultClusteringAlgorithm;
import shafin.nlp.clustering.preprocess.DistanceMeasurement;
import shafin.nlp.clustering.visualization.DendrogramPanel;
import shafin.nlp.util.FileHandler;

public class BasicCluster {

	final List<String> DOCUMENTS;

	public BasicCluster(String path) {
		this.DOCUMENTS = FileHandler.readFile(path);
	}

	public Cluster getCluster() throws IOException {
		String[] names = new String[DOCUMENTS.size()];
		names =  DOCUMENTS.toArray(names);
		double[][] distances = DistanceMeasurement.getCosineDistanceMatrix(DOCUMENTS);

		ClusteringAlgorithm alg = new DefaultClusteringAlgorithm();
		Cluster cluster = alg.performClustering(distances, names, new AverageLinkageStrategy());
		return cluster;
	}

	public void visualizeCluster() throws IOException {
		DendrogramPanel.visualize(getCluster());
	}
	
	public static void main(String[] args) throws IOException {
		String path = "D:/title.txt";
		BasicCluster bc = new BasicCluster(path);
		bc.visualizeCluster();
	}
}
