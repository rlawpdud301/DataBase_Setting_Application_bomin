package database_setting_application.service;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import database_setting_application.jdbc.ConnectionProvider;
import database_setting_application.jdbc.MyDataSource;

public class ImportService {
	public void service(String dirPath) {
		loadData(dirPath);
	}

	private void loadData(String dirPath) {
		try(Connection con = ConnectionProvider.getConnection("db.properties");
				Statement stmt = con.createStatement()){
			stmt.addBatch("use " + MyDataSource.getInstance("db.properties").getProperties().getProperty("dbname"));
			stmt.addBatch("SET FOREIGN_KEY_CHECKS = 0");
			String path = null;
			String fileName = null;
			String sql = null;
			File sqlDir = new File(dirPath);
			
			for(File sqlFile : sqlDir.listFiles()) {
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
