package shafin.nlp.clustering.controller;

import java.io.IOException;

import shafin.nlp.clustering.preprocess.CosineDocSimilarity;

public class SimilarityExample {

	public static void main(String[] args) throws IOException {
		String a = "my name is shafin";
		String b = "my name is shafin";
		double val = CosineDocSimilarity.getCosineSimilarity(a, b);
		System.out.println(val);
	}
}
