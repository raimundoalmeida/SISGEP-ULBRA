package com.soulsoftware.sisgep.nota.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import com.soulsoftware.sisgep.nota.model.LancamentoTributo;
import com.soulsoftware.sisgep.nota.repository.filter.LancamentoFilter;
import com.soulsoftware.sisgep.util.exception.NegocioException;
import com.soulsoftware.sisgep.util.jpa.Transactional;

public class LancamentosTributos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	// Salvar no banco de dados
	public LancamentoTributo guardar(LancamentoTributo lancamentoTributo) {
		return manager.merge(lancamentoTributo);

	}
	@Transactional
	public void remover(LancamentoTributo lancamento) throws NegocioException{
		try{
			lancamento = porId(lancamento.getId());
			manager.remove(lancamento);
			manager.flush();
		}catch (PersistenceException e) {
			throw new NegocioException("Lançamento não pode ser excluido.");
		}
		
		
	}
	@Transactional
	public void codigo(LancamentoTributo codigo)throws NegocioException{
		try{
		codigo = porId(codigo.getId());
		manager.flush();
		}catch (PersistenceException ne) {
			throw new NegocioException("Nullo");
		}
	}

	public List<Predicate>  criarPredicatesParaFiltro(LancamentoFilter filtro, 
			Root<LancamentoTributo>lancamentoRoot,From<?, ?>tomadorJoin, From<?, ?>prestadorJoin) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		List<Predicate>predicates = new ArrayList<>();
		
		if (filtro.getDataCriacaoDe() != null){
			//Data deve ser maior ou igual (ge = greater or equals) a filtro.dataCriacaoDe
			predicates.add(builder.greaterThanOrEqualTo(lancamentoRoot.get("dataEmissao"), filtro.getDataCriacaoDe()));
		}
		if(filtro.getDataCriacaoAte() != null){
			//Data deve ser maior ou igual (ge = greater or equals) a filtro.dataCriacaoDe
			predicates.add(builder.lessThanOrEqualTo(lancamentoRoot.get("dataEmissao"), filtro.getDataCriacaoAte()));
		}
		if(StringUtils.isNotBlank(filtro.getNomeTomador())){
			predicates.add(builder.like(tomadorJoin.get("nome"), "%" + filtro.getNomeTomador()+"%"));
		}
		if(StringUtils.isNotBlank(filtro.getNomePrestador())){
			predicates.add(builder.like(prestadorJoin.get("nome"), "%" + filtro.getNomePrestador()+"%"));
		}
		
		return predicates;
	
	}
	
	public List<LancamentoTributo> filtrados(LancamentoFilter filtro) {
		
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<LancamentoTributo>criteriaQuery = builder.createQuery(LancamentoTributo.class);
		
		Root<LancamentoTributo>lancamentoRoot = criteriaQuery.from(LancamentoTributo.class);
		From<?, ?>tomadorJoin = (From<?, ?>) lancamentoRoot.fetch("tomador", JoinType.INNER);
		From<?, ?>prestadorJoin = (From<?, ?>) lancamentoRoot.fetch("prestador", JoinType.INNER);
		
		List<Predicate>predicates = 
				criarPredicatesParaFiltro(filtro, lancamentoRoot, tomadorJoin, prestadorJoin);
		
		criteriaQuery.select(lancamentoRoot);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		criteriaQuery.orderBy(builder.asc(lancamentoRoot.get("dataEmissao")));
		
		TypedQuery<LancamentoTributo>query = manager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	/*@SuppressWarnings("unchecked")
	public List<LancamentoTributo> filtrados(LancamentoFilter filtro) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(LancamentoTributo.class)
				//fazemos uma associação (join) com tomador e nomeamos como "t"
				.createAlias("tomador", "t")
				//fazemos uma associação (join) com prestador e nomeamos como "p"
				.createAlias("prestador", "p");
		
		if (filtro.getDataCriacaoDe() != null){
			//Data deve ser maior ou igual (ge = greater or equals) a filtro.dataCriacaoDe
			criteria.add(Restrictions.ge("dataEmissao", filtro.getDataCriacaoDe()));
		}
		if(filtro.getDataCriacaoAte() != null){
			//Data deve ser maior ou igual (ge = greater or equals) a filtro.dataCriacaoDe
			criteria.add(Restrictions.le("dataEmissao", filtro.getDataCriacaoAte()));
		}
		if(StringUtils.isNotBlank(filtro.getNomeTomador())){
			criteria.add(Restrictions.ilike("t.nome", filtro.getNomeTomador(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(filtro.getNomePrestador())){
			criteria.add(Restrictions.ilike("p.nome", filtro.getNomePrestador(), MatchMode.ANYWHERE));
		}
				
		return criteria.addOrder(Order.asc("id")).list();
	}*/

	public LancamentoTributo porId(Long id) {
		return manager.find(LancamentoTributo.class, id);
	}

}
