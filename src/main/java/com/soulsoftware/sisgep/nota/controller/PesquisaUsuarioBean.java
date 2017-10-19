package com.soulsoftware.sisgep.nota.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.soulsoftware.sisgep.nota.model.Grupo;
import com.soulsoftware.sisgep.nota.model.Usuario;
import com.soulsoftware.sisgep.nota.repository.UsuarioRepository;
import com.soulsoftware.sisgep.nota.repository.filter.UsuarioFilter;
import com.soulsoftware.sisgep.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaUsuarioBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioRepository usuarioRepository;

	private UsuarioFilter filtro;

	private List<Usuario> usuariosFiltrados;

	private Usuario usuarioSelecionado;

	private Grupo grupo;

	public PesquisaUsuarioBean() {
		filtro = new UsuarioFilter();
		this.grupo = null;
	}

	public void excluir() {
		try {
			usuarioRepository.remover(usuarioSelecionado);
			usuariosFiltrados.remove(usuarioSelecionado);

			FacesUtil.addInfoMessage("Usuario " + usuarioSelecionado.getNome() + " excluido com sucesso.");
		} catch (Exception ne) {
			FacesUtil.addErrorMessage(ne.getMessage());
		}
	}

	public void pesquisar() {
		usuariosFiltrados = usuarioRepository.filtrados(filtro);
	}

	public List<Usuario> getUsuariosFiltrados() {
		return usuariosFiltrados;
	}

	public UsuarioFilter getFiltro() {
		return filtro;
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

}
