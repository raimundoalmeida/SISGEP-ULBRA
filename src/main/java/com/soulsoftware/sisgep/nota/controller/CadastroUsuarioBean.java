package com.soulsoftware.sisgep.nota.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.soulsoftware.sisgep.nota.model.Grupo;
import com.soulsoftware.sisgep.nota.model.Usuario;
import com.soulsoftware.sisgep.nota.repository.Grupos;
import com.soulsoftware.sisgep.nota.service.CadastroUsuarioService;
import com.soulsoftware.sisgep.util.exception.NegocioException;
import com.soulsoftware.sisgep.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuario;
	@Inject
	private CadastroUsuarioService cadastroUsuarioService;

	@Inject
	private Grupos grupos;

	private Grupo grupo;
	private List<Grupo> listGrupo;// listBox

	public CadastroUsuarioBean() {
		limpar();
	}

	private void limpar() {
		this.usuario = new Usuario();
		this.grupo = null;

	}

	public void inicilizar() {
		if (FacesUtil.isNotPostback()) {
			this.listGrupo = grupos.gruposRepository();

		}
	}

	public void adicionarGrupo() {
		if (this.grupo == null) {
			FacesUtil.addErrorMessage("Selcione um grupo!");
		} else {
			this.usuario.getGrupos().add(this.grupo);
		}
	}

	public void salvar() {
		try {
			this.usuario = cadastroUsuarioService.salvar(this.usuario);
			limpar();
			FacesUtil.addInfoMessage("Usuario salvo com sucesso!");
		} catch (NegocioException ne) {
			FacesUtil.addErrorMessage(ne.getMessage());
		}
	}

	public void excluirGrupo() {
		this.usuario.getGrupos().remove(this.grupo);
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Grupo> getListGrupo() {
		return listGrupo;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public boolean isEditando() {
		return this.usuario.getId() != null;
	}

}
