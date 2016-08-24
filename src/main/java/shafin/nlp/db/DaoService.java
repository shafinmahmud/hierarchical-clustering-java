package shafin.nlp.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import shafin.nlp.util.FileHandler;

public class DaoService {

	public static List<Jobs> getAllJobs() {
		try {
			DaoInterface<Jobs> dao = new JobsDaoImpl(new DBConn());
			return (List<Jobs>) dao.findAll();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<Jobs> getAllDevJobs() {
		try {
			JobsDaoImpl dao = new JobsDaoImpl(new DBConn());
			return (List<Jobs>) dao.findAllDev();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void writeAllJobsTitleToFile(String filePath) {
		List<Jobs> alljobs = getAllJobs();
		List<String> outputLines = new ArrayList<String>();
		for (Jobs jobs : alljobs) {
			outputLines.add(jobs.getJobTitle());
			System.out.println("ARRANGING: "+jobs.getJobTitle());
		}
		FileHandler.writeListToFile(filePath, outputLines);
	}
	
	public static void writeDevJobsTitleToFile(String filePath) {
		List<Jobs> alljobs = getAllDevJobs();
		List<String> outputLines = new ArrayList<String>();
		for (Jobs jobs : alljobs) {
			outputLines.add(jobs.getJobTitle());
			System.out.println("ARRANGING: "+jobs.getJobTitle());
		}
		FileHandler.writeListToFile(filePath, outputLines);
	}
	
	public static void main(String[] args) {
		/*String path = "D:/job_title.txt";
		writeAllJobsTitleToFile(path);*/
		String path = "D:/cse_dept_job.txt";
		writeDevJobsTitleToFile(path);
	}
}
