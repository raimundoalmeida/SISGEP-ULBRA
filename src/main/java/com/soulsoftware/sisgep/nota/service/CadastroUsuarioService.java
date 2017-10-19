package com.soulsoftware.sisgep.nota.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.soulsoftware.sisgep.nota.model.Usuario;
import com.soulsoftware.sisgep.nota.repository.UsuarioRepository;
import com.soulsoftware.sisgep.util.exception.NegocioException;
import com.soulsoftware.sisgep.util.jpa.Transactional;

public class CadastroUsuarioService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioRepository usuarioRepository;

	@Transactional
	public Usuario salvar(Usuario usuario) throws NegocioException {
		Usuario usuarioExitente = this.usuarioRepository.porEmail(usuario.getEmail());

		if (usuarioExitente != null && !usuarioExitente.equals(usuario)) {
			throw new NegocioException("Já existe um usuario com E-mail informado.");

		}

		return usuarioRepository.guardar(usuario);
	}

}
