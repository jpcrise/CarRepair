package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import javax.swing.JOptionPane;
import dao.DatabaseConnection;

public class Teste {

	public static void main(String[] args) throws ParseException, ClassNotFoundException, SQLException {

		try {
			Connection con = DatabaseConnection.getConnection();
			if (con != null) {
				JOptionPane.showMessageDialog(null, "Conexão Efetuada com sucesso!");
			}

		}

		catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}

		catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Erro na conexão!");
			ex.printStackTrace();
		}

	}
}
