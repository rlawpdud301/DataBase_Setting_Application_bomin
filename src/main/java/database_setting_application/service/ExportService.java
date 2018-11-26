package database_setting_application.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database_setting_application.jdbc.ConnectionProvider;
import database_setting_application.jdbc.MyDataSource;

public class ExportService {
	public void service(String dirPath) {
		checkBackupDir(dirPath);
		List<String> tables = getTables();
		File sqlDir = new File(dirPath);
		File sqlFile = null;
		for (String tblName : tables) {
			sqlFile = new File(sqlDir, tblName);
			exportData("select * from " + tblName, sqlFile.getAbsolutePath().replace("\\", "/"));
		}
	}

	private void exportData(String sql, String exportPath) {
		StringBuilder sb = new StringBuilder();
		try (Connection con = ConnectionProvider.getConnection("db.properties");
				Statement stmt = con.createStatement();) {
			stmt.execute("use " + MyDataSource.getInstance("db.properties").getProperties().getProperty("dbname"));

			try (ResultSet rs = stmt.executeQuery(sql)) {
				int colCnt = rs.getMetaData().getColumnCount();
				while (rs.next()) {
					for (int i = 1; i <= colCnt; i++) {
						sb.append(rs.getObject(i) + ",");
					}
					sb.replace(sb.length() - 1, sb.length(), "");
					sb.append("\n");
				}
				backupFileWrite(sb.toString(), exportPath);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void backupFileWrite(String str, String exportPath)
			throws UnsupportedEncodingException, FileNotFoundException, IOException {
		try (OutputStreamWriter dos = new OutputStreamWriter(new FileOutputStream(exportPath), "UTF-8")) {
			dos.write(str);
		}

	}

	private List<String> getTables() {
		List<String> list = new ArrayList<>();
		try (Connection con = ConnectionProvider.getConnection("db.properties");
				Statement stmt = con.createStatement()) {
			stmt.execute("use " + MyDataSource.getInstance("db.properties").getProperties().getProperty("dbname"));
			try (ResultSet rs = stmt.executeQuery("show tables");) {
				while (rs.next()) {
					list.add(rs.getString(1));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	private void checkBackupDir(String dirPath) {
		File backupDir = new File(dirPath);
		if (backupDir.exists()) {
			for (File file : backupDir.listFiles()) {
				file.delete();
			}
		} else {
			backupDir.mkdir();
		}

	}
}
