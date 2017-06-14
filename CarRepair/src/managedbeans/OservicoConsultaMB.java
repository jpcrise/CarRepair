package managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import dao.OservicoDao;
import dao.OservicoDaoImp;
import model.OrdemServico;
import model.Peca;

@ManagedBean
@RequestScoped
public class OservicoConsultaMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2412642514748928698L;
	private OrdemServico osAtual = new OrdemServico();
	private OservicoDao os = new OservicoDaoImp();
	private List<OrdemServico> oss = new ArrayList<OrdemServico>();
	private double totalOs = 0;

	public double getTotalOs() {
		totalOs = 0;
		for (Peca pc : osAtual.getPecas()) {
			totalOs += pc.getValor() * pc.getQtde();
		}
		return totalOs;
	}

	public void setTotalOs(double totalOs) {
		this.totalOs = totalOs;
	}

	public List<OrdemServico> getOss() {
		return oss;
	}

	public void setOss(List<OrdemServico> oss) {
		this.oss = oss;
	}

	private boolean mostrarVeiculo = false;
	private boolean mostrarPecas = false;
	private boolean mostrarOs = false;

	public OrdemServico getOsAtual() {
		return osAtual;
	}

	public void setOsAtual(OrdemServico osAtual) {
		this.osAtual = osAtual;
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
		return "";
	}

	public boolean isMostrarOs() {
		return mostrarOs;
	}

	public void setMostrarOs(boolean mostrarOs) {
		this.mostrarOs = mostrarOs;
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
				mostrarPecas = false;
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

	public String listarPorPlaca() {

		try {
			if (!osAtual.getVeiculo().getPlaca().isEmpty()) {
				oss = os.listarPorPlaca(osAtual.getVeiculo().getPlaca());

				if (oss != null) {

					mostrarOs = true;

				} else {
					mostrarOs = false;
					FacesContext fc = FacesContext.getCurrentInstance();
					fc.addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Não existe ordem de serviço para este veículo!", ""));
				}
			}
		} catch (Exception e) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao buscar ordem de serviço!", ""));
			e.printStackTrace();
		}
		return "";
	}

}
