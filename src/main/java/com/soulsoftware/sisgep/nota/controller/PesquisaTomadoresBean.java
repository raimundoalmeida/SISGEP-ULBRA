package com.soulsoftware.sisgep.nota.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.soulsoftware.sisgep.nota.model.Tomador;
import com.soulsoftware.sisgep.nota.repository.Tomadores;
import com.soulsoftware.sisgep.nota.repository.filter.TomadorFilter;
import com.soulsoftware.sisgep.util.exception.NegocioException;
import com.soulsoftware.sisgep.util.jsf.FacesUtil;

@Named
@ViewScoped

public class PesquisaTomadoresBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Tomadores tomadores;

	private TomadorFilter filtro;

	private List<Tomador> tomadoresFiltrados;

	private Tomador tomadorSelecionado;

	public PesquisaTomadoresBean() {
		filtro = new TomadorFilter();

	}

	public void pesquisar() {
		tomadoresFiltrados = tomadores.filtrados(filtro);
	}

	public void excluir() {
		try {
			tomadores.remover(tomadorSelecionado);
			tomadoresFiltrados.remove(tomadorSelecionado);
			FacesUtil.addInfoMessage("Tomador " + tomadorSelecionado.getId() + " excluido com sucesso.");
		} catch (NegocioException ne) {
			FacesUtil.addErrorMessage(ne.getMessage());
		}
	}

	public List<Tomador> getTomadoresFiltrados() {
		return tomadoresFiltrados;
	}

	public TomadorFilter getFiltro() {
		return filtro;
	}

	public Tomador getTomadorSelecionado() {
		return tomadorSelecionado;
	}

	public void setTomadorSelecionado(Tomador tomadorSelecionado) {
		this.tomadorSelecionado = tomadorSelecionado;
	}

}
