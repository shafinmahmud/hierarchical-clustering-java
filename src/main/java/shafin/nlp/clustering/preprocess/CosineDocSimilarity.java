package shafin.nlp.clustering.preprocess;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math3.linear.*;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.Version;

/*
 * CREDIT : Mark H. Butler
 * https://gist.github.com/butlermh/4672977/eb5e5a9dd367ec2125c3805cb0a1896220f3d386
 */
public class CosineDocSimilarity {

	public static final String CONTENT = "Content";

	private final Set<String> terms = new HashSet<>();
	private final RealVector v1;
	private final RealVector v2;

	CosineDocSimilarity(String s1, String s2) throws IOException {
		Directory directory = createIndex(s1, s2);
		IndexReader reader = DirectoryReader.open(directory);
		Map<String, Integer> f1 = getTermFrequencies(reader, 0);
		Map<String, Integer> f2 = getTermFrequencies(reader, 1);
		reader.close();
		v1 = toRealVector(f1);
		v2 = toRealVector(f2);
	}

	Directory createIndex(String s1, String s2) throws IOException {
		Directory directory = new RAMDirectory();
		Analyzer analyzer = new SimpleAnalyzer(Version.LUCENE_40);
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_40, analyzer);
		IndexWriter writer = new IndexWriter(directory, iwc);
		addDocument(writer, s1);
		addDocument(writer, s2);
		writer.close();
		return directory;
	}

	void addDocument(IndexWriter writer, String content) throws IOException {
		Document doc = new Document();
		doc.add(new VecTextField(CONTENT, content, Store.YES));
		writer.addDocument(doc);
	}

	double getCosineSimilarity() {
		return (v1.dotProduct(v2)) / (v1.getNorm() * v2.getNorm());
	}

	public static double getCosineSimilarity(String s1, String s2) throws IOException {
		return new CosineDocSimilarity(s1, s2).getCosineSimilarity();
	}

	Map<String, Integer> getTermFrequencies(IndexReader reader, int docId) throws IOException {
		Terms vector = reader.getTermVector(docId, CONTENT);
		TermsEnum termsEnum = null;
		termsEnum = vector.iterator(termsEnum);
		Map<String, Integer> frequencies = new HashMap<>();
		BytesRef text = null;
		while ((text = termsEnum.next()) != null) {
			String term = text.utf8ToString();
			int freq = (int) termsEnum.totalTermFreq();
			frequencies.put(term, freq);
			terms.add(term);
		}
		return frequencies;
	}

	RealVector toRealVector(Map<String, Integer> map) {
		RealVector vector = new ArrayRealVector(terms.size());
		int i = 0;
		for (String term : terms) {
			int value = map.containsKey(term) ? map.get(term) : 0;
			vector.setEntry(i++, value);
		}
		return (RealVector) vector.mapDivide(vector.getL1Norm());
	}
}
