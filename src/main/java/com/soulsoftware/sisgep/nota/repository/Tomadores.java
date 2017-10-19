package com.soulsoftware.sisgep.nota.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.soulsoftware.sisgep.nota.model.Tomador;
import com.soulsoftware.sisgep.nota.repository.filter.TomadorFilter;
import com.soulsoftware.sisgep.util.exception.NegocioException;
import com.soulsoftware.sisgep.util.jpa.Transactional;

public class Tomadores implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	// Salvar no banco de dados
	public Tomador guardar(Tomador tomador) {
		return manager.merge(tomador);
	}
	@Transactional
	public void remover(Tomador tomador) throws NegocioException{
		try{
		tomador = porId(tomador.getId());
		manager.remove(tomador);
		manager.flush();
		}catch (PersistenceException e) {
			throw new NegocioException("Tomador não pode ser excluido, pois esta vinculado a uma NTF");
		}
	}

	// Consulta LPQL para comparar cpf/cnpj, evitar cadastrar dois documentos
	// iguais.
	public Tomador porDocumentoReceitaFederal(String documentoReceitaFederal) {
		try {
			return manager
					.createQuery("from Tomador where upper(documentoReceitaFederal) = :documentoReceitaFederal",
							Tomador.class)
					.setParameter("documentoReceitaFederal", documentoReceitaFederal.toUpperCase()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Tomador> filtrados(TomadorFilter filtro) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Tomador.class);

		if (StringUtils.isNotBlank(filtro.getDocumentoReceitaFederal())) {
			criteria.add(Restrictions.eq("documentoReceitaFederal", filtro.getDocumentoReceitaFederal()));
		}
		if (StringUtils.isNotBlank(filtro.getNome())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}
		return criteria.addOrder(Order.asc("nome")).list();
	}

	public Tomador porId(Long id) {
		return manager.find(Tomador.class, id);

	}

	public List<Tomador> tomadoresRepository() {
		return manager.createQuery("from Tomador", Tomador.class).getResultList();
	}

}
