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

import com.soulsoftware.sisgep.nota.model.Prestador;
import com.soulsoftware.sisgep.nota.repository.filter.PrestadorFilter;
import com.soulsoftware.sisgep.util.exception.NegocioException;
import com.soulsoftware.sisgep.util.jpa.Transactional;

public class Prestadores implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	// Salvar no banco de dados
	public Prestador guardar(Prestador prestador) {
		return manager.merge(prestador);
	}
	@Transactional
	public void remover(Prestador prestador) throws NegocioException{ //excluir prestador
		try{
		prestador = porId(prestador.getId());
		manager.remove(prestador);
		manager.flush();
		}catch (PersistenceException e) {
			throw new NegocioException("Prestador não pode ser excluido, pois esta vinculado a uma NTF");
		}
	}
	// Consulta LPQL para comparar cpf/cnpj, evitar cadastrar dois documentos
	// iguais.
	public Prestador porDocumentoReceitaFederal(String documentoReceitaFederal) {
		try {
			return manager
					.createQuery("from Prestador where upper(documentoReceitaFederal) = :documentoReceitaFederal",
							Prestador.class)
					.setParameter("documentoReceitaFederal", documentoReceitaFederal.toUpperCase()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	@SuppressWarnings("unchecked") //pesquisa
	public List<Prestador> filtrados(PrestadorFilter filtro){
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Prestador.class);
		
		if(StringUtils.isNotBlank(filtro.getDocumentoReceitaFederal())){
			criteria.add(Restrictions.eq("documentoReceitaFederal", filtro.getDocumentoReceitaFederal()));
		}
		if(StringUtils.isNotBlank(filtro.getNome())){
			criteria.add(Restrictions.ilike("nome", filtro.getNome(),MatchMode.ANYWHERE));
		}
		return criteria.addOrder(Order.asc("nome")).list();
	}
	
	public Prestador porId(Long id) {
		return manager.find(Prestador.class, id);

	}

	public List<Prestador> prestadoresRepository() { //carregar lista para lançamento
		return manager.createQuery("from Prestador", Prestador.class).getResultList();
	}
	public List<Prestador> porNome(String nome) {
		return this.manager.createQuery("from Cliente " +
				"where upper(nome) like :nome", Prestador.class)
				.setParameter("nome", nome.toUpperCase() + "%")
				.getResultList();
	}
	
}
