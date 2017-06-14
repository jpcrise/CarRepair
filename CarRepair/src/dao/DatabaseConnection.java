package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

	private static Connection con;

	public static Connection getConnection() throws ClassNotFoundException, SQLException {

		String server = "bd_carrepair.mysql.dbaas.com.br/bd_carrepair";
		String user = "bd_carrepair";
		String pwd = "carrepair123";

		if (con == null) {

			Class.forName("com.mysql.jdbc.Driver");
		}

		con = DriverManager.getConnection("jdbc:mysql://" + server, user, pwd);
		return con;
	}

}
