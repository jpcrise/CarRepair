package managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import dao.VeiculoDaoImp;
import dao.VeiculoDao;
import model.Veiculo;
import servicos.EnumStatusVeiculo;

@ManagedBean
@RequestScoped
public class VeiculoMB implements Serializable {

	private static final long serialVersionUID = -7432112812752393705L;
	private Veiculo veicAtual = new Veiculo();
	private VeiculoDao veicDAO = new VeiculoDaoImp();
	private List<Veiculo> veiculos = new ArrayList<Veiculo>();
	private boolean btnAtualizar = true;
	private boolean btnAdicionar = true;

	public Veiculo getVeicAtual() {
		return veicAtual;
	}

	public void setVeicAtual(Veiculo veicAtual) {
		this.veicAtual = veicAtual;
	}

	public List<Veiculo> getVeiculos() {
		return veiculos;
	}

	public void setVeiculos(List<Veiculo> veiculos) {
		this.veiculos = veiculos;
	}

	public void refresh() {
		veicAtual = new Veiculo();
		setBtnAdicionar(true);
		setBtnAtualizar(true);
	}

	public boolean isBtnAtualizar() {
		return btnAtualizar;
	}

	public void setBtnAtualizar(boolean btnAtualizar) {
		this.btnAtualizar = btnAtualizar;
	}

	public boolean isBtnAdicionar() {
		return btnAdicionar;
	}

	public void setBtnAdicionar(boolean btnAdicionar) {
		this.btnAdicionar = btnAdicionar;
	}

	public String adicionar() {

		try {
			veicDAO.adicionar(veicAtual);
			refresh();
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "Veículo adicionado com sucesso!", ""));
		} catch (Exception e) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro ao adicionar o veículo! Verificar se cliente está cadastrado!", ""));
			e.printStackTrace();
		}
		return "";
	}

	public String atualizar() {
		try {
			veicDAO.alterar(veicAtual);
			refresh();
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "Veículo atualizado com sucesso!", ""));
		} catch (Exception e) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro ao atualizar o veículo! Verificar se cliente está cadastrado!", ""));
			e.printStackTrace();
		}
		return "";
	}

	public String remover(Veiculo v) {
		try {
			veicDAO.remover(v);
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "Veículo excluido com sucesso!", ""));
		} catch (Exception e) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao excluir o veículo!", ""));
			e.printStackTrace();
		}
		return "";
	}

	public String listarManutencao() {
		try {
			veiculos = veicDAO.listarPorStatus(EnumStatusVeiculo.MANUTENÇÃO.toString());
		} catch (Exception e) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao listar veículos!", ""));
			e.printStackTrace();
		}
		return "";
	}

	public String listarTodos() {
		try {
			veiculos = veicDAO.listar();
		} catch (Exception e) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao listar veículos!", ""));
			e.printStackTrace();
		}
		return "";
	}

	public String buscarPorPlaca() {
		try {
			if (!veicAtual.getPlaca().isEmpty()) {
				Veiculo v = veicDAO.buscarPorPlaca(veicAtual.getPlaca());
				if (v != null) {
					veicAtual = v;
					setBtnAtualizar(false);
				} else {
					FacesContext fc = FacesContext.getCurrentInstance();
					fc.addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "Veículo não cadastrado!", ""));
					setBtnAdicionar(false);
				}
			}
		} catch (Exception e) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao listar veículos!", ""));
			e.printStackTrace();
		}
		return "";
	}

}
