package com.soulsoftware.sisgep.nota.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.soulsoftware.sisgep.nota.model.Prestador;
import com.soulsoftware.sisgep.nota.repository.Prestadores;
import com.soulsoftware.sisgep.util.exception.NegocioException;
import com.soulsoftware.sisgep.util.jpa.Transactional;

public class CadastroPrestadorService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Prestadores prestadores;

	@Transactional
	public Prestador salvar(Prestador prestador) throws NegocioException {
		Prestador prestadorExistente = prestadores.porDocumentoReceitaFederal(prestador.getDocumentoReceitaFederal());

		if (prestadorExistente != null && !prestadorExistente.equals(prestador)) {
			throw new NegocioException("Já existe um prestador o CPF/CNPJ informado.");
		}

		return prestadores.guardar(prestador);
	}

}
