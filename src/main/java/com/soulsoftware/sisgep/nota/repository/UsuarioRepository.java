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

import com.soulsoftware.sisgep.nota.model.Usuario;
import com.soulsoftware.sisgep.nota.repository.filter.UsuarioFilter;
import com.soulsoftware.sisgep.util.exception.NegocioException;
import com.soulsoftware.sisgep.util.jpa.Transactional;

public class UsuarioRepository implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Usuario guardar(Usuario usuario) {
		return manager.merge(usuario);
	}

	@Transactional
	public void remover(Usuario usuario) throws NegocioException {
		try {
			usuario = porId(usuario.getId());
			manager.remove(usuario);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("O usuário não pode ser excluido");
		}
	}

	public Usuario porEmail(String email) {
		Usuario usuario = null;
		try {
			return manager.createQuery("from Usuario where upper(email) = :email", Usuario.class)
					.setParameter("email", email.toUpperCase()).getSingleResult();
		} catch (NoResultException e) {
			// nenhum usuario encontrado
		}
		return usuario;
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> filtrados(UsuarioFilter filtro) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Usuario.class);

		if (StringUtils.isNotBlank(filtro.getNome())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}
		return criteria.addOrder(Order.asc("nome")).list();

	}

	public List<Usuario> vendedores() {
		// TODO filtrar apenas vendedores (por um grupo especifico)
		return this.manager.createQuery("from Usuario", Usuario.class).getResultList();
	}

	public Usuario porId(Long id) {
		return manager.find(Usuario.class, id);
	}

}
