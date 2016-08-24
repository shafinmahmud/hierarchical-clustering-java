package shafin.nlp.clustering.preprocess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DistanceMeasurement {

	public static double[][] getCosineDistanceMatrix(List<String> documents) throws IOException {
		int SIZE = documents.size();
		double[][] distanceMatrix = new double[SIZE][SIZE];

		for (int i = 0; i < SIZE; i++) {
			for (int j = i + 1; j < SIZE; j++) {
				String DOCA = documents.get(i);
				String DOCB = documents.get(j);
				System.out.println(DOCA+" <-> "+DOCB);
				double distance = 1 - CosineDocSimilarity.getCosineSimilarity(DOCA, DOCB);
				distanceMatrix[i][j] = distance;
				distanceMatrix[i][i] = 0;
				distanceMatrix[j][i] = distance;
			}
		}
		return distanceMatrix;
	}

	public static void printMatrix(double matrix[][]) {
		for (double[] row : matrix) {
			for (double i : row) {
				System.out.format("%f ", i);
			}
			System.out.println();
		}
	}

	public static void main(String[] args) throws IOException {

		List<String> docs = new ArrayList<>();
		docs.add("My name is Shafin");
		docs.add("Shafin Mahmud is my name");
		docs.add("Mahmud is the surname");
		docs.add("Sheikh Shafin Mahmud");

		printMatrix(getCosineDistanceMatrix(docs));
	}
}
