package shafin.nlp.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;


public class JobsDaoImpl implements DaoInterface<Jobs>{
	
	private final DBConn DB_CONN;
	
	public JobsDaoImpl(DBConn dbConn) {
		this.DB_CONN = dbConn;
	}
	
	@Override
	public Jobs findOne(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Jobs> findAll() {
		try {
			String query = "SELECT * FROM job_koi.jobs";
			PreparedStatement queryStatement = DB_CONN.connection.prepareStatement(query);
			
			Collection<Jobs> allJobs = new ArrayList<>();
			ResultSet rs = this.DB_CONN.retriveResultset(queryStatement);
			while(rs.next()) {
				Jobs job = new Jobs();
				job.setJobTitle(rs.getString("job_title"));
				allJobs.add(job);
				System.out.println("PULLING: "+job.getJobTitle());
			}
			return allJobs;

		} catch (IllegalStateException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Collection<Jobs> findAllDev() {
		try {
			String query = "SELECT * FROM job_koi.jobs where LOWER(jobs.job_title) LIKE '%computer science%' OR"
								 +" LOWER(jobs.prefered_degree) LIKE '%computer science%' OR"
								 +" LOWER(jobs.educational_requirements) LIKE '%computer science%' OR"
								 +" LOWER(jobs.position_requirements) LIKE '%computer science%' OR"
								 +" LOWER(jobs.job_description) LIKE '%computer science%'";
			PreparedStatement queryStatement = DB_CONN.connection.prepareStatement(query);
			
			Collection<Jobs> allJobs = new ArrayList<>();
			ResultSet rs = this.DB_CONN.retriveResultset(queryStatement);
			while(rs.next()) {
				Jobs job = new Jobs();
				job.setJobTitle(rs.getString("job_title"));
				allJobs.add(job);
				System.out.println("PULLING: "+job.getJobTitle());
			}
			return allJobs;

		} catch (IllegalStateException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean insertOne(Jobs object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateOne(Jobs object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteOne(Jobs object) {
		// TODO Auto-generated method stub
		return false;
	}

}
