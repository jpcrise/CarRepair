package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Peca;

public class PecaDaoImp implements PecaDao {

	@Override
	public void adicionar(Peca p) throws ClassNotFoundException, SQLException {
		Connection con = DatabaseConnection.getConnection();

		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO tb_peca (cod_peca, nome, nfiscal, valor, qtde, id_os, data_nf ) ");
		sb.append("VALUES (?, ?, ?, ?, ?, ?, ?)");

		PreparedStatement st = con.prepareStatement(sb.toString());
		st.setInt(1, p.getCodPeca());
		st.setString(2, p.getNome());
		st.setInt(3, p.getNfiscal());
		st.setDouble(4, p.getValor());
		st.setInt(5, p.getQtde());
		st.setInt(6, p.getId_os());
		st.setDate(7, dataBanco(p.getDtNota()));
		st.executeUpdate();
		con.close();

	}

	@Override
	public void remover(Peca p) throws ClassNotFoundException, SQLException {
		Connection con = DatabaseConnection.getConnection();
		String sql = "DELETE FROM tb_peca WHERE id_os = ? AND cod_peca = ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, p.getId_os());
		st.setInt(2, p.getCodPeca());
		st.executeUpdate();
		con.close();
	}

	@Override
	public List<Peca> listarPorId(int id) throws ClassNotFoundException, SQLException {
		List<Peca> listaPecas = new ArrayList<Peca>();

		Connection con = DatabaseConnection.getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT cod_peca, nome, nfiscal, valor, qtde, id_os, data_nf ");
		sb.append("FROM tb_peca WHERE id_os = ?");

		PreparedStatement st = con.prepareStatement(sb.toString());
		st.setInt(1, id);

		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			Peca p = new Peca();
			p.setCodPeca(rs.getInt("cod_peca"));
			p.setNome(rs.getString("nome"));
			p.setNfiscal(rs.getInt("nfiscal"));
			p.setValor(rs.getDouble("valor"));
			p.setQtde(rs.getInt("qtde"));
			p.setId_os(rs.getInt("id_os"));
			p.setDtNota(rs.getDate("data_nf"));

			listaPecas.add(p);
		}
		con.close();

		return listaPecas;
	}

	private java.sql.Date dataBanco(Date data) {
		try {
			return new java.sql.Date(data.getTime());
		} catch (Exception e) {
			return null;
		}
	}

}
