package managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import dao.ClienteDaoImp;
import dao.ClienteDao;
import model.Cliente;

@ManagedBean
@SessionScoped
public class ClienteMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4609510732091535660L;
	private Cliente cliAtual = new Cliente();
	private ClienteDao cliDAO = new ClienteDaoImp();
	private List<Cliente> cliente = new ArrayList<Cliente>();
	private boolean btnAtualizar = true;
	private boolean btnAdicionar = true;

	public Cliente getCliAtual() {
		return cliAtual;
	}

	public void setCliAtual(Cliente cliAtual) {
		this.cliAtual = cliAtual;
	}

	public List<Cliente> getclientes() {
		return cliente;
	}

	public void setClientes(List<Cliente> clientes) {
		this.cliente = clientes;
	}

	public void refresh() {
		cliAtual = new Cliente();
		setBtnAdicionar(true);
		setBtnAtualizar(true);

	}

	public String adicionar() {

		try {
			cliDAO.adicionar(cliAtual);
			refresh();
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente adicionado com sucesso!", ""));
		} catch (Exception e) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao adicionar o Cliente!", ""));
			e.printStackTrace();
		}
		return "";
	}

	public String atualizar() {
		try {
			cliDAO.alterar(cliAtual);
			refresh();
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente atualizado com sucesso!", ""));
		} catch (Exception e) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao atualizar o Cliente!", ""));
			e.printStackTrace();
		}
		return "";
	}

	public String remover(Cliente v) {
		try {
			cliDAO.remover(v);
			refresh();
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente excluido com sucesso!", ""));
		} catch (Exception e) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao excluir o Cliente!", ""));
			e.printStackTrace();
		}
		return "";
	}

	public String listarTodos() {
		try {
			cliente = cliDAO.listar();
		} catch (Exception e) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao listar Clientes!", ""));
			e.printStackTrace();
		}
		return "";
	}

	public String buscarPorCnpj() {
		try {

			if (!cliAtual.getCnpj().isEmpty()) {
				Cliente f = cliDAO.buscarPorCnpj(cliAtual.getCnpj());
				if (f != null) {
					cliAtual = f;
					setBtnAtualizar(false);
				} else {
					FacesContext fc = FacesContext.getCurrentInstance();
					fc.addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente não cadastrado!", ""));
					setBtnAdicionar(false);
				}
			}
		} catch (Exception e) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao buscar Cliente!", ""));
			e.printStackTrace();
		}
		return "";
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

}
