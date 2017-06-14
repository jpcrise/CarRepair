package managedbeans;

import java.io.IOException;
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import dao.UsuarioDao;
import dao.UsuarioDaoImp;
import model.Usuario;

@ManagedBean
@SessionScoped
public class UsuarioMB {
	UsuarioDao usuarioDao = new UsuarioDaoImp();
	private Usuario usuario = new Usuario();
	private boolean userLogado = false;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isUserLogado() {
		return userLogado;
	}

	public void setUserLogado(boolean userLogado) {
		this.userLogado = userLogado;
	}

	public String logar() {
		
		
		try {
			Usuario usuarioLogado = usuarioDao.logar(usuario);
			if (usuarioLogado!=null) {
				try {
					usuario = usuarioLogado;
					setUserLogado(true);
					FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");

				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				FacesMessage msg = new FacesMessage("Usuário ou senha inválido!");
				FacesContext.getCurrentInstance().addMessage("erro", msg);
			}
		} catch (ClassNotFoundException | SQLException e1) {
			FacesMessage msg = new FacesMessage("Erro ao tentar verificar credenciais! Sem Conexão!");
			FacesContext.getCurrentInstance().addMessage("erro", msg);
			e1.printStackTrace();
		}

		return "";

	}

	public String sair() {
		if (isUserLogado()) {
			this.usuario = new Usuario();
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			setUserLogado(false);
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml?faces-redirect=true");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return "";
	}

	public String getNomeUsuario() throws IOException {
		if (isUserLogado()) {
			return usuario.getNome();
		}
		FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
		return "";
	}

}
