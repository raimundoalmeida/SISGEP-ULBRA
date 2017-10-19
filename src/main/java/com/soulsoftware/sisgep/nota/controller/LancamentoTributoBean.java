package com.soulsoftware.sisgep.nota.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.soulsoftware.sisgep.nota.model.LancamentoTributo;
import com.soulsoftware.sisgep.nota.model.Prestador;
import com.soulsoftware.sisgep.nota.model.Tomador;
import com.soulsoftware.sisgep.nota.repository.Prestadores;
import com.soulsoftware.sisgep.nota.repository.Tomadores;
import com.soulsoftware.sisgep.nota.service.CadastroTributoService;
import com.soulsoftware.sisgep.util.jsf.FacesUtil;

@Named
@ViewScoped
public class LancamentoTributoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Prestadores prestadores;
	@Inject
	private Tomadores tomadores;

	@Inject
	private CadastroTributoService cadastroTributoService;

	private LancamentoTributo lancamento;
	private Prestador prestador;
	private Tomador tomador;

	// lista que recebe do repository e carregar os tomadores na pagina xhtml
	private List<Tomador> tomadors;
	// lista que recebe do repository e carregar os prestadores na pagina xhtml
	private List<Prestador> prestadors;
	
	public LancamentoTributoBean() {
		this.limpar();

	}

	public void inicializar() {

		if (FacesUtil.isNotPostback()) {
			// ao abrir a pagina xhtml incializa as lista de:
			tomadors = tomadores.tomadoresRepository();
			prestadors = prestadores.prestadoresRepository();
		}
	}

	public void limpar() {
		tomadors = new ArrayList<>();
		prestadors = new ArrayList<>();
		lancamento = new LancamentoTributo();

	}

	public void salvar() {
		this.lancamento = cadastroTributoService.salvar(this.lancamento);
		this.limpar();
		FacesUtil.addInfoMessage("Lançamento efetuado com sucesso.");
	}
	
	public void calcularTributos(){
		this.lancamento.calculoValorBruto();
	}

	public LancamentoTributo getLancamento() {
		return lancamento;
	}

	public void setLancamento(LancamentoTributo lancamento) {
		this.lancamento = lancamento;
		if(this.lancamento == null){
			this.lancamento = new LancamentoTributo();
		
		}
	}
	
	public List<Tomador> getTomadors() {
		return tomadors;
	}

	public List<Prestador> getPrestadors() {
		return prestadors;
	}

	public Prestador getPrestador() {
		return prestador;
	}

	public void setPrestador(Prestador prestador) {
		this.prestador = prestador;
	}

	public Tomador getTomador() {
		return tomador;
	}

	public void setTomador(Tomador tomador) {
		this.tomador = tomador;
	}
	public boolean isEditando() {
		return this.lancamento.getId() != null;
	}

	

}
