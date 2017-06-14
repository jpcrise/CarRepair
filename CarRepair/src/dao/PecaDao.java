package dao;

import java.sql.SQLException;
import java.util.List;

import model.Peca;

public interface PecaDao {

	public List<Peca> listarPorId(int id) throws ClassNotFoundException, SQLException;;

	public void adicionar(Peca p) throws ClassNotFoundException, SQLException;;

	public void remover(Peca p) throws ClassNotFoundException, SQLException;;

}
