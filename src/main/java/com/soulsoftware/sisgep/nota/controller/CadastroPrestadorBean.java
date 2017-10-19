package com.soulsoftware.sisgep.nota.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.soulsoftware.sisgep.nota.model.Prestador;
import com.soulsoftware.sisgep.nota.model.TipoPessoa;
import com.soulsoftware.sisgep.nota.service.CadastroPrestadorService;
import com.soulsoftware.sisgep.util.exception.NegocioException;
import com.soulsoftware.sisgep.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroPrestadorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroPrestadorService cadastroPrestadorService;

	private Prestador prestador;

	public CadastroPrestadorBean() {
		limppar();

	}

	public void limppar() {
		prestador = new Prestador();

	}

	public void salvar() {
		try {
			this.prestador = cadastroPrestadorService.salvar(this.prestador);

			limppar();
			FacesUtil.addInfoMessage("Prestador salvo com sucesso.");
		} catch (NegocioException ne) {
			FacesUtil.addErrorMessage(ne.getMessage());
		}

	}

	public TipoPessoa[] getTipos() {
		return TipoPessoa.values();

	}

	public Prestador getPrestador() {
		return prestador;
	}

	public void setPrestador(Prestador prestador) {
		this.prestador = prestador;
	}

	public boolean isEditando() {
		return this.prestador.getId() != null;
	}

}
