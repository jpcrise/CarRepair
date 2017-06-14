package dao;

import java.sql.SQLException;

import model.Usuario;

public interface UsuarioDao {

	public Usuario logar(Usuario usuario) throws SQLException, ClassNotFoundException;

}
