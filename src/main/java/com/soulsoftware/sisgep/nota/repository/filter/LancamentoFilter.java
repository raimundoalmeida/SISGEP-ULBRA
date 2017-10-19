package com.soulsoftware.sisgep.nota.repository.filter;

import java.io.Serializable;
import java.util.Date;

public class LancamentoFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	
	private Date dataCriacaoDe;
	private Date dataCriacaoAte;
	private String nomePrestador;
	private String nomeTomador;
	public Date getDataCriacaoDe() {
		return dataCriacaoDe;
	}
	public void setDataCriacaoDe(Date dataCriacaoDe) {
		this.dataCriacaoDe = dataCriacaoDe;
	}
	public Date getDataCriacaoAte() {
		return dataCriacaoAte;
	}
	public void setDataCriacaoAte(Date dataCriacaoAte) {
		this.dataCriacaoAte = dataCriacaoAte;
	}
	public String getNomePrestador() {
		return nomePrestador;
	}
	public void setNomePrestador(String nomePrestador) {
		this.nomePrestador = nomePrestador;
	}
	public String getNomeTomador() {
		return nomeTomador;
	}
	public void setNomeTomador(String nomeTomador) {
		this.nomeTomador = nomeTomador;
	}

	
	
	

	

}
