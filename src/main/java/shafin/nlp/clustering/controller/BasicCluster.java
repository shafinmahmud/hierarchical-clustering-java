package shafin.nlp.clustering.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
		List<String> docList = FileHandler.readFile(path);
		Set<String> docSets = new HashSet<>(docList);
		this.DOCUMENTS = new ArrayList<>(docSets);
	}

	public Cluster getCluster() throws IOException {
		System.out.println(DOCUMENTS.size());
		String[] names = new String[DOCUMENTS.size()];
		names = DOCUMENTS.toArray(names);
		double[][] distances = DistanceMeasurement.getCosineDistanceMatrix(DOCUMENTS);

		ClusteringAlgorithm alg = new DefaultClusteringAlgorithm();
		Cluster cluster = alg.performClustering(distances, names, new AverageLinkageStrategy());
		// traverseTreeLevels(cluster, 0, 10);
		// int level = 12;
		for (int i = 0; i < 20; i++) {
			HashMap<String, List<String>> map = getTreeLevelsAsMap(cluster, 0, i, new HashMap<String, List<String>>());
			writeMappedClusterToFile(i, map, "D:/home/job_cluster/cse");
		}
		return cluster;
	}

	public void visualizeCluster() throws IOException {
		DendrogramPanel.visualize(getCluster());
	}

	public List<String> getClusterAsList(Cluster cluster, List<String> clusterNames) {
		List<Cluster> clusters = cluster.getChildren();
		for (Cluster c : clusters) {
			getClusterAsList(c, clusterNames);
		}

		if (cluster.isLeaf()) {
			String clusterName = cluster.getName();
			clusterNames.add(clusterName);
			System.out.println(clusterName);
		}
		return clusterNames;
	}

	public HashMap<String, List<String>> getTreeLevelsAsMap(Cluster cluster, int initLevel, int level,
			HashMap<String, List<String>> map) {
		List<Cluster> clusters = cluster.getChildren();
		initLevel++;
		for (Cluster c : clusters) {
			if (initLevel < level) {
				getTreeLevelsAsMap(c, initLevel, level, map);
			}
			if (initLevel == level || c.isLeaf()) {
				map.put(c.getName(), getClusterAsList(c, new ArrayList<String>()));
			}
		}
		return map;
	}

	public void writeMappedClusterToFile(int level, HashMap<String, List<String>> map, String folder_path) {

		FileHandler.deleteFile(folder_path + "/" + level + ".txt");

		List<String> singleTones = new ArrayList<>();
		for (String key : map.keySet()) {
			List<String> names = map.get(key);

			if (names.size() > 1) {
				FileHandler.appendFile(folder_path + "/" + level + ".txt", "\n\n" + key + "\n");
				FileHandler.appendFile(folder_path + "/" + level + ".txt",
						"----------------------------------------------" + "\n");
				for (String s : names) {
					FileHandler.appendFile(folder_path + "/" + level + ".txt", s + "\n");
				}
				// FileHandler.writeListToFile(folder_path + "/" + key + ".txt",
				// names);
			} else {
				for (String s : names) {
					singleTones.add(s);
				}
			}
		}
		FileHandler.appendFile(folder_path + "/" + level + ".txt", "\n\n Non Cluster \n");
		FileHandler.appendFile(folder_path + "/" + level + ".txt",
				"----------------------------------------------" + "\n");
		for (String s : singleTones) {
			FileHandler.appendFile(folder_path + "/" + level + ".txt", s + "\n");
		}
		// FileHandler.writeListToFile(folder_path + "/singleTone.txt",
		// singleTones);
		// System.out.println("WRITTING: " + folder_path + "/singleTone.txt");
	}

	public static void main(String[] args) throws IOException {
		String path = "D:/cse_r.txt";
		BasicCluster bc = new BasicCluster(path);
		bc.visualizeCluster();
	}
}
