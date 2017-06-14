package dao;

import java.sql.SQLException;
import java.util.List;

import model.Cliente;

public interface ClienteDao {
	public void adicionar(Cliente f) throws ClassNotFoundException, SQLException;

	public void alterar(Cliente f) throws ClassNotFoundException, SQLException;

	public void remover(Cliente f) throws ClassNotFoundException, SQLException;;

	public Cliente buscarPorCnpj(String t) throws ClassNotFoundException, SQLException;;

	public List<Cliente> listarPorRazao(String t) throws ClassNotFoundException, SQLException;;

	public List<Cliente> listar() throws ClassNotFoundException, SQLException;;
}
