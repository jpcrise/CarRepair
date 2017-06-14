package dao;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.DatabaseConnection;
import model.Cliente;


public class ClienteDaoImp implements ClienteDao {

	@Override
	public void adicionar(Cliente f) throws ClassNotFoundException, SQLException {

		Connection con = DatabaseConnection.getConnection();

		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO tb_cliente (cnpj_cliente, nome, cep, rua, numero, complemento, bairro, cidade, uf, ");
		sb.append("fone1, fone2, contato, observacao, dt_Cadastro ) ");
		sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		PreparedStatement st = con.prepareStatement(sb.toString());

		st.setString(1, f.getCnpj());
		st.setString(2, f.getNome());
		st.setString(3, f.getCep());
		st.setString(4, f.getRua());
		st.setString(5, f.getNum());
		st.setString(6, f.getCompl());
		st.setString(7, f.getBairro());
		st.setString(8, f.getCidade());
		st.setString(9, f.getUf());
		st.setString(10, f.getFone1());
		st.setString(11, f.getFone2());
		st.setString(12, f.getContato());
		st.setString(13, f.getObservacao());
		st.setDate(14, dataBanco(new Date()));

		st.executeUpdate();
		con.close();

	}

	@Override
	public void alterar(Cliente f) throws ClassNotFoundException, SQLException {

		Connection con = DatabaseConnection.getConnection();

		StringBuffer sb = new StringBuffer();
		sb.append(
				"UPDATE tb_cliente SET nome = ?, cep = ?, rua = ?, numero = ?, complemento = ?, bairro = ?, cidade = ?, uf = ?, ");
		sb.append("fone1 = ?, fone2 = ?, contato = ?, observacao = ? ");
		sb.append("WHERE  cnpj_cliente = ?");

		PreparedStatement st = con.prepareStatement(sb.toString());
		st.setString(1, f.getNome());
		st.setString(2, f.getCep());
		st.setString(3, f.getRua());
		st.setString(4, f.getNum());
		st.setString(5, f.getCompl());
		st.setString(6, f.getBairro());
		st.setString(7, f.getCidade());
		st.setString(8, f.getUf());
		st.setString(9, f.getFone1());
		st.setString(10, f.getFone2());
		st.setString(11, f.getContato());
		st.setString(12, f.getObservacao());
		st.setString(13, f.getCnpj());
		st.executeUpdate();
		con.close();

	}

	@Override
	public void remover(Cliente f) throws ClassNotFoundException, SQLException {

		Connection con = DatabaseConnection.getConnection();
		String sql = "DELETE FROM tb_cliente WHERE cnpj_cliente = ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, f.getCnpj());
		st.executeUpdate();
		con.close();

	}

	@Override
	public List<Cliente> listar() throws ClassNotFoundException, SQLException {

		List<Cliente> lista = new ArrayList<Cliente>();

		Connection con = DatabaseConnection.getConnection();

		StringBuffer sb = new StringBuffer();

		sb.append("SELECT cnpj_cliente, nome, cep, rua, numero, complemento, bairro, cidade, uf, ");
		sb.append("fone1, fone2, contato, observacao, dt_Cadastro ");
		sb.append("FROM tb_cliente");
		PreparedStatement st = con.prepareStatement(sb.toString());

		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			Cliente f = new Cliente();
			f.setCnpj(rs.getString("cnpj_cliente"));
			f.setNome(rs.getString("nome"));
			f.setCep(rs.getString("cep"));
			f.setRua(rs.getString("rua"));
			f.setNum(rs.getString("numero"));
			f.setCompl(rs.getString("complemento"));
			f.setBairro(rs.getString("bairro"));
			f.setCidade(rs.getString("cidade"));
			f.setUf(rs.getString("uf"));
			f.setFone1(rs.getString("fone1"));
			f.setFone2(rs.getString("fone2"));
			f.setContato(rs.getString("contato"));
			f.setObservacao(rs.getString("observacao"));
			f.setDtCadastro(rs.getDate("dt_cadastro"));

			lista.add(f);
		}
		con.close();

		return lista;
	}

	@Override
	public Cliente buscarPorCnpj(String t) throws ClassNotFoundException, SQLException {

		Connection con = DatabaseConnection.getConnection();
		Cliente f = null;
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT cnpj_cliente, nome, cep, rua, numero, complemento, bairro, cidade, uf, ");
		sb.append("fone1, fone2, contato, observacao, dt_Cadastro ");
		sb.append("FROM tb_cliente WHERE cnpj_cliente = ?");
		PreparedStatement st = con.prepareStatement(sb.toString());
		st.setString(1, t);
		ResultSet rs = st.executeQuery();
		if (rs.next()) {
			f = new Cliente();
			f.setCnpj(rs.getString("cnpj_cliente"));
			f.setNome(rs.getString("nome"));
			f.setCep(rs.getString("cep"));
			f.setRua(rs.getString("rua"));
			f.setNum(rs.getString("numero"));
			f.setCompl(rs.getString("complemento"));
			f.setBairro(rs.getString("bairro"));
			f.setCidade(rs.getString("cidade"));
			f.setUf(rs.getString("uf"));
			f.setFone1(rs.getString("fone1"));
			f.setFone2(rs.getString("fone2"));
			f.setContato(rs.getString("contato"));
			f.setObservacao(rs.getString("observacao"));
			f.setDtCadastro(rs.getDate("dt_cadastro"));

		}
		con.close();

		return f;
	}

	@Override
	public List<Cliente> listarPorRazao(String t) throws ClassNotFoundException, SQLException {
		List<Cliente> lista = new ArrayList<Cliente>();

		Connection con = DatabaseConnection.getConnection();

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT cnpj_cliente, nome, cep, rua, numero, complemento, bairro, cidade, uf, ");
		sb.append("fone1, fone2, contato, observacao, dt_Cadastro ");
		sb.append("FROM tb_cliente WHERE nome like ?");
		PreparedStatement st = con.prepareStatement(sb.toString());
		st.setString(1, "%" + t + "%");
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			Cliente f = new Cliente();
			f.setCnpj(rs.getString("cnpj_cliente"));
			f.setNome(rs.getString("nome"));
			f.setCep(rs.getString("cep"));
			f.setRua(rs.getString("rua"));
			f.setNum(rs.getString("numero"));
			f.setCompl(rs.getString("complemento"));
			f.setBairro(rs.getString("bairro"));
			f.setCidade(rs.getString("cidade"));
			f.setUf(rs.getString("uf"));
			f.setFone1(rs.getString("fone1"));
			f.setFone2(rs.getString("fone2"));
			f.setContato(rs.getString("contato"));
			f.setObservacao(rs.getString("observacao"));
			f.setDtCadastro(rs.getDate("dt_cadastro"));

			lista.add(f);
		}
		con.close();

		return lista;
	}

	private java.sql.Date dataBanco(Date data) {
		try {
			return new java.sql.Date(data.getTime());
		} catch (Exception e) {
			return null;
		}
	}
	
	
}
