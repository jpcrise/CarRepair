package managedbeans;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import dao.OservicoDao;
import dao.OservicoDaoImp;
import dao.VeiculoDao;
import dao.VeiculoDaoImp;
import model.OrdemServico;
import model.Peca;
import model.Veiculo;
import servicos.EnumStatusVeiculo;

@ManagedBean
@SessionScoped
public class OservicoMB implements Serializable {

	private static final long serialVersionUID = -8234673211445524887L;
	private OrdemServico osAtual = new OrdemServico();
	private OservicoDao os = new OservicoDaoImp();
	private List<OrdemServico> oss = new ArrayList<OrdemServico>();
	private boolean mostrarVeiculo = false;
	private boolean mostrarPecas = false;
	private boolean abrirOs = false;
	private double totalOs = 0;

	public double getTotalOs() {
		totalOs = 0;
		for (Peca pc : osAtual.getPecas()) {
			totalOs += pc.getValor() * pc.getQtde();
		}
		return totalOs;
	}

	public OrdemServico getOsAtual() {
		return osAtual;
	}

	public void setOsAtual(OrdemServico osAtual) {
		this.osAtual = osAtual;
	}

	public List<OrdemServico> getOss() {
		return oss;
	}

	public void setOss(List<OrdemServico> oss) {
		this.oss = oss;
	}

	public boolean getMostrarVeiculo() {
		return mostrarVeiculo;
	}

	public void setMostrarVeiculo(boolean mostrarVeiculo) {
		this.mostrarVeiculo = mostrarVeiculo;
	}

	public boolean isMostrarPecas() {
		return mostrarPecas;
	}

	public void setMostrarPecas(boolean mostrarPecas) {
		this.mostrarPecas = mostrarPecas;
	}

	public String refresh() {
		osAtual = new OrdemServico();
		mostrarVeiculo = false;
		mostrarPecas = false;
		abrirOs = false;
		return "";
	}

	public String abrir() {

		VeiculoDao veic = new VeiculoDaoImp();
		Veiculo v = null;
		try {
			v = veic.buscarPorPlaca(osAtual.getVeiculo().getPlaca());
		} catch (ClassNotFoundException | SQLException e1) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao abrir a ordem de servico!", ""));
			e1.printStackTrace();
		}

		if (v != null) {
			try {
				os.abrir(osAtual);
				VeiculoDao veicDao = new VeiculoDaoImp();
				veicDao.alterarStatus(osAtual.getVeiculo().getPlaca(), EnumStatusVeiculo.MANUTENÇÃO.toString());
				buscarPorNumero();
				FacesContext fc = FacesContext.getCurrentInstance();
				fc.addMessage("",
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Ordem Serviço aberta com sucesso!", ""));
			} catch (Exception e) {
				FacesContext fc = FacesContext.getCurrentInstance();
				fc.addMessage("",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao abrir a ordem de servico!", ""));
				e.printStackTrace();
			}

		} else {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Placa não cadastrada! Cadastre veículo primeiro!", ""));
		}

		return "";
	}

	public String atualizar() {
		try {
			os.atualizar(osAtual);
			refresh();
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Ordem Serviço atualizada com sucesso!", ""));
		} catch (ClassNotFoundException | SQLException e) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao atualizar a ordem de servico!", ""));
			e.printStackTrace();
		}
		return "";
	}

	public String fechar() {

		try {
			os.fechar(osAtual);
			VeiculoDao veicDao = new VeiculoDaoImp();
			veicDao.alterarStatus(osAtual.getVeiculo().getPlaca(), EnumStatusVeiculo.PRONTO.toString());
			refresh();
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "Ordem Serviço fechada com sucesso!", ""));
		} catch (Exception e) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao fechar a ordem de servico!", ""));
			e.printStackTrace();
		}
		return "";
	}

	public String buscarPorNumero() {
		try {

			OrdemServico o = os.buscarPorNumero(osAtual.getNumero());
			if (o != null) {
				osAtual = o;
				mostrarVeiculo = true;
				if (osAtual.getPecas().size() > 0) {
					mostrarPecas = true;
				} else {
					mostrarPecas = false;
				}
			} else {
				mostrarVeiculo = false;
				FacesContext fc = FacesContext.getCurrentInstance();
				fc.addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "Ordem de Serviço não cadastrada!", ""));
			}
		} catch (Exception e) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao buscar a ordem de servico!", ""));
			e.printStackTrace();
		}
		return "";

	}

	public String buscarAbertaPorPlaca() {
		try {

			OrdemServico o = os.buscarAbertaPorPlaca(osAtual.getVeiculo().getPlaca());
			if (o != null) {
				osAtual = o;
				if (osAtual.getPecas().size() > 0) {
					mostrarPecas = true;
				} else {
					mostrarPecas = false;
				}
			} else {
				abrirOs = true;
				FacesContext fc = FacesContext.getCurrentInstance();
				fc.addMessage("",
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Veículo sem Ordem de Serviço aberta!", ""));
			}
		} catch (Exception e) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao buscar a ordem de servico2!", ""));
			e.printStackTrace();
		}
		return "";

	}

	public String buscarPorPlaca() {
		VeiculoDao veicDao = new VeiculoDaoImp();
		try {
			if (!osAtual.getVeiculo().getPlaca().isEmpty()) {
				Veiculo v = veicDao.buscarPorPlaca(osAtual.getVeiculo().getPlaca());

				if (v != null) {
					osAtual.setVeiculo(v);
					mostrarVeiculo = true;
					buscarAbertaPorPlaca();
				} else {
					mostrarVeiculo = false;
					FacesContext fc = FacesContext.getCurrentInstance();
					fc.addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "Veículo não cadastrado!", ""));
				}
			}
		} catch (Exception e) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao buscar veículo!", ""));
			e.printStackTrace();
		}
		return "";
	}

	public boolean isAbrirOs() {
		return abrirOs;
	}

	public void setAbrirOs(boolean abrirOs) {
		this.abrirOs = abrirOs;
	}

}
