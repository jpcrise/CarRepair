package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Usuario;

public class UsuarioDaoImp implements UsuarioDao{

	@Override
	public Usuario logar(Usuario usuario) throws SQLException, ClassNotFoundException{
		Usuario user = null;
		Connection con = DatabaseConnection.getConnection();
		
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT login, nome FROM tb_usuario WHERE login = ? and senha = ?");
		PreparedStatement st = con.prepareStatement(sb.toString());
		st.setString(1, usuario.getLogin());
		st.setString(2, usuario.getSenha());
		ResultSet rs = st.executeQuery();
		if (rs.next()) {
			user= new Usuario();
			user.setNome(rs.getString("nome"));
			user.setLogin(rs.getString("login"));			

		}
		con.close();
		return user;
	}
}
