package com.soulsoftware.sisgep.nota.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.soulsoftware.sisgep.nota.model.Grupo;

public class Grupos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public List<Grupo> gruposRepository() {
		return manager.createQuery("from Grupo", Grupo.class).getResultList();
	}
	public Grupo porId(Long id) {
		return manager.find(Grupo.class, id);
	}

}
