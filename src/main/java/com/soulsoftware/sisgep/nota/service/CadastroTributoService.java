package com.soulsoftware.sisgep.nota.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.soulsoftware.sisgep.nota.model.LancamentoTributo;
import com.soulsoftware.sisgep.nota.repository.LancamentosTributos;
import com.soulsoftware.sisgep.util.jpa.Transactional;

public class CadastroTributoService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private LancamentosTributos lancamentosTributos;
	
	@Transactional
	public LancamentoTributo salvar(LancamentoTributo lancamentoTributo){
		
		return lancamentosTributos.guardar(lancamentoTributo);
	}

}
