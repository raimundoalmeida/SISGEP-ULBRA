package com.soulsoftware.sisgep.nota.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.soulsoftware.sisgep.nota.model.Prestador;
import com.soulsoftware.sisgep.nota.repository.Prestadores;
import com.soulsoftware.sisgep.nota.repository.filter.PrestadorFilter;
import com.soulsoftware.sisgep.util.exception.NegocioException;
import com.soulsoftware.sisgep.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaPrestadoresBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Prestadores prestadores;

	private PrestadorFilter filtro;

	private List<Prestador> prestadoresFiltrados;

	private Prestador prestadorSelecionado;

	public PesquisaPrestadoresBean() {
		filtro = new PrestadorFilter();
	}

	public void pesquisar() {
		prestadoresFiltrados = prestadores.filtrados(filtro);

	}

	public void excluir() {
		try {
			prestadores.remover(prestadorSelecionado);
			prestadoresFiltrados.remove(prestadorSelecionado);

			FacesUtil.addInfoMessage("Prestador " + prestadorSelecionado.getId() + " excluido com sucesso.");
		} catch (NegocioException ne) {
			FacesUtil.addErrorMessage(ne.getMessage());
		}

	}

	public List<Prestador> getPrestadoresFiltrados() {
		return prestadoresFiltrados;
	}

	public PrestadorFilter getFiltro() {
		return filtro;
	}

	public Prestador getPrestadorSelecionado() {
		return prestadorSelecionado;
	}

	public void setPrestadorSelecionado(Prestador prestadorSelecionado) {
		this.prestadorSelecionado = prestadorSelecionado;
	}

}
