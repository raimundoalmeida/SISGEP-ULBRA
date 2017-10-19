package com.soulsoftware.sisgep.nota.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.soulsoftware.sisgep.nota.model.Tomador;
import com.soulsoftware.sisgep.nota.repository.Tomadores;
import com.soulsoftware.sisgep.util.exception.NegocioException;
import com.soulsoftware.sisgep.util.jpa.Transactional;

public class CadastroTomadorService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Tomadores tomadores;

	@Transactional
	public Tomador salvar(Tomador tomador) throws NegocioException {

		Tomador tomadorExistente = tomadores.porDocumentoReceitaFederal(tomador.getDocumentoReceitaFederal());
		if (tomadorExistente != null && !tomadorExistente.equals(tomador)) {
			throw new NegocioException("Já existe um prestador esse cnpj informado.");
		}

		return tomadores.guardar(tomador);
	}

}
