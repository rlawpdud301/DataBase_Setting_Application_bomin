package database_setting_application;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import database_setting_application.jdbc.ConnectionProvider;
import database_setting_application.jdbc.LogUtil;

public class ConnectionProviderTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println();
		LogUtil.prnLog("START ConnectionProviderTest");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println();
		LogUtil.prnLog("END ConnectionProviderTest");
	}

	@Before
	public void setUp() throws Exception {
		System.out.println();
	}

	@Test
	public void test() {
		try(Connection connection = ConnectionProvider.getConnection("db.properties")){
			LogUtil.prnLog(connection.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
