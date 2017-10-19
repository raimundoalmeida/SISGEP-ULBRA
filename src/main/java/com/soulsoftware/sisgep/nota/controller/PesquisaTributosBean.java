package com.soulsoftware.sisgep.nota.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.soulsoftware.sisgep.nota.model.LancamentoTributo;
import com.soulsoftware.sisgep.nota.model.Prestador;
import com.soulsoftware.sisgep.nota.repository.LancamentosTributos;
import com.soulsoftware.sisgep.nota.repository.Prestadores;
import com.soulsoftware.sisgep.nota.repository.filter.LancamentoFilter;
import com.soulsoftware.sisgep.util.exception.NegocioException;
import com.soulsoftware.sisgep.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaTributosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private LancamentosTributos lancamentosTributos; // repository lancamentos

	private LancamentoFilter filtro;
	private List<LancamentoTributo> lancamentosFiltrados;

	@Inject
	private Prestadores prestadores;

	private LancamentoTributo lancamentoSelecionnado;

	public PesquisaTributosBean() {
		filtro = new LancamentoFilter();
		lancamentosFiltrados = new ArrayList<>();

	}

	public void excluir() {
		try {
			lancamentosTributos.remover(lancamentoSelecionnado);
			lancamentosFiltrados.remove(lancamentoSelecionnado);

			FacesUtil.addInfoMessage("Lancçamento " + lancamentoSelecionnado.getId() + " excluido com sucesso.");
		} catch (NegocioException ne) {
			FacesUtil.addErrorMessage(ne.getMessage());
		}

	}

	public void pesquisar() {
		lancamentosFiltrados = lancamentosTributos.filtrados(filtro);
	}

	public List<Prestador> completarPrestador(String nome) {
		return this.prestadores.porNome(nome);
	}

	public List<LancamentoTributo> getLancamentosFiltrados() {
		return lancamentosFiltrados;
	}

	public LancamentoFilter getFiltro() {
		return filtro;
	}

	public LancamentoTributo getLancamentoSelecionnado() {
		return lancamentoSelecionnado;
	}

	public void setLancamentoSelecionnado(LancamentoTributo lancamentoSelecionnado) {
		this.lancamentoSelecionnado = lancamentoSelecionnado;
	}

}
