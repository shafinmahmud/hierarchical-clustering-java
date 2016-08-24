package shafin.nlp.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shafin.nlp.util.FileHandler;
import shafin.nlp.util.ListUtil;
import shafin.nlp.util.MapUtil;

public class ClusterFileIO {

	public static Map<String, List<String>> readClusterFromFile(String path) {
		Map<String, List<String>> map = new HashMap<>();
		List<String> lines = FileHandler.readFile(path);

		for (int i = 0; i < lines.size() - 1; i++) {
			String clusterName = "";
			List<String> titles = null;

			if (lines.get(i).contains("-----")) {
				clusterName = lines.get(i).replaceAll("-", "").trim();
				titles = new ArrayList<>();

				for (i = i + 1; i < lines.size() && !lines.get(i).startsWith("--"); i++) {
					if (!lines.get(i).trim().equals("")) {
						titles.add(lines.get(i));
					}
				}
				i--;
			}
			map.put(clusterName, titles);
		}
		return map;
	}

	public static void main(String[] args) {
		Map<String, List<String>> map = readClusterFromFile("D:/home/job_cluster/clus_dev.txt");
		List<String> alls = FileHandler.readFile("D:/cse_dept_job.txt");
		// MapUtil.printMapListValue(map);
		
		List<String> values = MapUtil.getListValuesAsList(map);
		System.out.println("alls :"+alls.size()+" / val: "+values.size());
		ListUtil.printList(ListUtil.AminusB(alls, values));
	}
}
