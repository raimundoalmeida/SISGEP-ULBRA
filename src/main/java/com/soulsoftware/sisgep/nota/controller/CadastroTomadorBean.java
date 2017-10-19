package com.soulsoftware.sisgep.nota.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.soulsoftware.sisgep.nota.model.Tomador;
import com.soulsoftware.sisgep.nota.service.CadastroTomadorService;
import com.soulsoftware.sisgep.util.exception.NegocioException;
import com.soulsoftware.sisgep.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroTomadorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroTomadorService cadastroTomadorService;

	private Tomador tomador;

	public CadastroTomadorBean() {
		limpar();

	}

	public void limpar() {
		tomador = new Tomador();
	}

	public void salvar() {
		try {
			this.tomador = cadastroTomadorService.salvar(this.tomador);
			limpar();
			FacesUtil.addInfoMessage("Tomador salvo com sucesso.");
		} catch (NegocioException ne) {
			FacesUtil.addErrorMessage(ne.getMessage());
		}

	}

	public Tomador getTomador() {
		return tomador;
	}

	public void setTomador(Tomador tomador) {
		this.tomador = tomador;
	}

	public boolean isEditando() {
		return this.tomador.getId() != null;
	}

}
