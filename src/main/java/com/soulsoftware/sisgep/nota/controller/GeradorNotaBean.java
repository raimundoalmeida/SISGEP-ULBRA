package com.soulsoftware.sisgep.nota.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.hibernate.Session;

import com.soulsoftware.sisgep.nota.model.LancamentoTributo;
import com.soulsoftware.sisgep.util.jsf.FacesUtil;
import com.soulsoftware.sisgep.util.report.ExecutorRelatorio;

@Named
@RequestScoped
public class GeradorNotaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	
	private Date dataInicial;
	private Date dataFinal;
	@Inject
	private LancamentoTributo lancamennto;
	@Inject
	private FacesContext facesContext;
	@Inject
	private HttpServletResponse response;
	@Inject
	private EntityManager manager;

	
	public void emitir() {
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("data_inicial", this.dataInicial );
		parametros.put("data_final", this.dataFinal );

		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/nota_fiscal.jasper", response, parametros,
				"NTFSavemitida.pdf");

		Session session = manager.unwrap(Session.class);
		session.doWork(executor);

		if (executor.isRelatorioGerado()) {
			facesContext.responseComplete();
		} else {
			FacesUtil.addErrorMessage("Nenhuma nota emitida");
		}
	}


	@NotNull
	public Date getDataInicial() {
		return dataInicial;
	}


	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	@NotNull
	public Date getDataFinal() {
		return dataFinal;
	}


	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}


	public LancamentoTributo getLancamennto() {
		return lancamennto;
	}
	

}
