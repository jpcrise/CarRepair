package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.mysql.jdbc.Statement;
import model.OrdemServico;
import model.Veiculo;
import servicos.EnumStatusOservico;

public class OservicoDaoImp implements OservicoDao {

	@Override
	public void abrir(OrdemServico o) throws ClassNotFoundException, SQLException {

		Connection con = DatabaseConnection.getConnection();

		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO tb_servico (data_servico, descricao, km_servico, placa, status) ");
		sb.append("VALUES (?, ?, ?, ?, ?)");

		PreparedStatement st = con.prepareStatement(sb.toString(), Statement.RETURN_GENERATED_KEYS);
		st.setDate(1, dataBanco(new Date()));
		st.setString(2, o.getDescricao());
		st.setInt(3, o.getKmServico());
		st.setString(4, o.getVeiculo().getPlaca());
		st.setString(5, EnumStatusOservico.ABERTA.toString());

		st.executeUpdate();
		ResultSet rs = st.getGeneratedKeys();
		if (rs.next()) {
			int id = rs.getInt(1);
			o.setNumero(id);
		}

		con.close();

	}

	@Override
	public void fechar(OrdemServico o) throws ClassNotFoundException, SQLException {
		Connection con = DatabaseConnection.getConnection();
		String sql = "UPDATE tb_servico SET status = ? WHERE id_os = ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, EnumStatusOservico.FECHADA.toString());
		st.setInt(2, o.getNumero());
		st.executeUpdate();
		con.close();

	}

	@Override
	public void atualizar(OrdemServico o) throws ClassNotFoundException, SQLException {
		Connection con = DatabaseConnection.getConnection();
		String sql = "UPDATE tb_servico SET descricao = ?, km_servico = ? WHERE id_os = ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, o.getDescricao());
		st.setInt(2, o.getKmServico());
		st.setInt(3, o.getNumero());
		st.executeUpdate();
		con.close();

	}

	@Override
	public void remover(OrdemServico o) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public OrdemServico buscarPorNumero(int n) throws ClassNotFoundException, SQLException {
		OrdemServico o = null;

		Connection con = DatabaseConnection.getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT s.data_servico, s.descricao, s.km_servico, s.placa, s.id_os, s.status, ");
		sb.append("v.marca, v.modelo, v.ano_fab, v.ano_mod, v.status as status_veic ");
		sb.append("FROM tb_servico s ");
		sb.append("INNER JOIN tb_veiculo v ON s.placa = v.placa ");
		sb.append("WHERE id_os = ? ");

		PreparedStatement st = con.prepareStatement(sb.toString());
		st.setInt(1, n);
		ResultSet rs = st.executeQuery();
		if (rs.next()) {
			o = new OrdemServico();
			o.setData(rs.getDate("data_servico"));
			o.setDescricao(rs.getString("descricao"));
			o.setKmServico(rs.getInt("km_servico"));
			o.setNumero(rs.getInt("id_os"));
			o.setStatus(rs.getString("status"));
			Veiculo v = new Veiculo();
			v.setPlaca(rs.getString("placa"));
			v.setAnoFabricacao(rs.getString("ano_fab"));
			v.setAnoModelo(rs.getString("ano_mod"));
			v.setMarca(rs.getString("marca"));
			v.setModelo(rs.getString("modelo"));
			v.setStatus(rs.getString("status_veic"));
			o.setVeiculo(v);

		}

		con.close();
		if (o != null) {
			PecaDao p = new PecaDaoImp();
			o.setPecas(p.listarPorId(n));
		}
		return o;
	}

	@Override
	public OrdemServico buscarAbertaPorPlaca(String placa) throws ClassNotFoundException, SQLException {
		OrdemServico o = null;

		Connection con = DatabaseConnection.getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT s.data_servico, s.descricao, s.km_servico, s.placa, s.id_os, s.status, ");
		sb.append("v.marca, v.modelo, v.ano_fab, v.ano_mod, v.status as status_veic ");
		sb.append("FROM tb_servico s ");
		sb.append("INNER JOIN tb_veiculo v ON s.placa = v.placa ");
		sb.append("WHERE s.placa = ? and s.status = 'ABERTA'");

		PreparedStatement st = con.prepareStatement(sb.toString());
		st.setString(1, placa);
		ResultSet rs = st.executeQuery();
		if (rs.next()) {
			o = new OrdemServico();
			o.setData(rs.getDate("data_servico"));
			o.setDescricao(rs.getString("descricao"));
			o.setKmServico(rs.getInt("km_servico"));
			o.setNumero(rs.getInt("id_os"));
			o.setStatus(rs.getString("status"));
			Veiculo v = new Veiculo();
			v.setPlaca(rs.getString("placa"));
			v.setAnoFabricacao(rs.getString("ano_fab"));
			v.setAnoModelo(rs.getString("ano_mod"));
			v.setMarca(rs.getString("marca"));
			v.setModelo(rs.getString("modelo"));
			v.setStatus(rs.getString("status_veic"));
			o.setVeiculo(v);
			PecaDao p = new PecaDaoImp();
			o.setPecas(p.listarPorId(o.getNumero()));
		}

		con.close();

		return o;
	}

	@Override
	public List<OrdemServico> listarPorPlaca(String placa) throws ClassNotFoundException, SQLException {
		List<OrdemServico> oss = new ArrayList<OrdemServico>();
		OrdemServico o;
		Connection con = DatabaseConnection.getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT data_servico, descricao, km_servico, placa, id_os, status ");
		sb.append("FROM tb_servico WHERE placa = ?");

		PreparedStatement st = con.prepareStatement(sb.toString());
		st.setString(1, placa);
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			o = new OrdemServico();
			o.setData(rs.getDate("data_servico"));
			o.setDescricao(rs.getString("descricao"));
			o.setKmServico(rs.getInt("km_servico"));
			o.setNumero(rs.getInt("id_os"));
			o.setStatus(rs.getString("status"));
			oss.add(o);
		}
		con.close();

		return oss;
	}

	@Override
	public List<OrdemServico> listar() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	private java.sql.Date dataBanco(Date data) {
		try {
			return new java.sql.Date(data.getTime());
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<OrdemServico> listarPorStatus(String t) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
