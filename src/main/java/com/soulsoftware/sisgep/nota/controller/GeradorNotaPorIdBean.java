package com.soulsoftware.sisgep.nota.controller;

import java.awt.Image;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import org.hibernate.Session;

import com.soulsoftware.sisgep.nota.model.LancamentoTributo;
import com.soulsoftware.sisgep.nota.repository.LancamentosTributos;
import com.soulsoftware.sisgep.nota.repository.filter.LancamentoFilter;
import com.soulsoftware.sisgep.util.jsf.FacesUtil;
import com.soulsoftware.sisgep.util.report.ExecutorRelatorio;

@Named
@ViewScoped
public class GeradorNotaPorIdBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id_nota;
	private Long id_dam;
	private Long id_darf;

	// ++++++++++++++++++++++++++++++++++++++++++++++
	@Inject
	private FacesContext facesContext;
	@Inject
	private HttpServletResponse response;
	@Inject
	private EntityManager manager;
	// +++++++++++++++++++++++++++++++++++++++++++++++++
	@Inject
	private LancamentosTributos lancamentosTributos; // repository lancamentos
	private LancamentoFilter filtro;
	private List<LancamentoTributo> lancamentosFiltrados;
	private LancamentoTributo lancamentoSelecionnado;

	public GeradorNotaPorIdBean() {
		id_nota = null;
		filtro = new LancamentoFilter();
		lancamentosFiltrados = new ArrayList<>();
	}

	public void emitirNota(Long id_nota) {
		Map<String, Object> parametros = new HashMap<>();
		Image logo = new ImageIcon(getClass().getResource("/relatorios/LogoTributoAlma.png")).getImage();
		parametros.put("id_nota", this.id_nota = id_nota);
		parametros.put("logo", logo);

		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/nota_fiscal_id.jasper", response, parametros,
				"Nota_Fiscal_" + lancamentoSelecionnado.getId() + ".pdf");

		Session session = manager.unwrap(Session.class);
		session.doWork(executor);

		if (executor.isRelatorioGerado()) {
			facesContext.responseComplete();
		} else {
			FacesUtil.addErrorMessage("Nenhuma nota emitida");
		}
	}

	public void emitirDam(Long id_nota) {
		Map<String, Object> parametros = new HashMap<>();
		Image logoDam = new ImageIcon(getClass().getResource("/relatorios/LogoTributoAlma.png"))
						.getImage();
		parametros.put("id_dam", this.id_dam = id_nota);
		parametros.put("logoDam", logoDam);

		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/dam_por_id.jasper", response, parametros,
				"DAM_" + lancamentoSelecionnado.getId() + ".pdf");

		Session session = manager.unwrap(Session.class);
		session.doWork(executor);

		if (executor.isRelatorioGerado()) {
			facesContext.responseComplete();
		} else {
			FacesUtil.addErrorMessage("Nenhuma DAM emitida");
		}
	}

	public void emitirDarf(Long id_nota) {
		Map<String, Object> parametros = new HashMap<>();
		Image logoDarf = new ImageIcon(getClass().getResource("/relatorios/LogonRepublica.png")).getImage();
		parametros.put("id_darf", this.id_darf = id_nota);
		parametros.put("logoDarf", logoDarf);

		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/darf_por_id.jasper", response, parametros,
				"DARF_" + lancamentoSelecionnado.getId() + ".pdf");

		Session session = manager.unwrap(Session.class);
		session.doWork(executor);

		if (executor.isRelatorioGerado()) {
			facesContext.responseComplete();
		} else {
			FacesUtil.addErrorMessage("Nenhuma DARF emitida");
		}
	}

	public void gerarNota() {
		emitirNota(lancamentoSelecionnado.getId());

	}

	public void gerarDam() {
		emitirDam(lancamentoSelecionnado.getId());

	}

	public void gerarDarf() {
		emitirDarf(lancamentoSelecionnado.getId());

	}

	public void pesquisar() {
		lancamentosFiltrados = lancamentosTributos.filtrados(filtro);
	}

	public LancamentoFilter getFiltro() {
		return filtro;
	}

	public void setFiltro(LancamentoFilter filtro) {
		this.filtro = filtro;
	}

	public LancamentosTributos getLancamentosTributos() {
		return lancamentosTributos;
	}

	public List<LancamentoTributo> getLancamentosFiltrados() {
		return lancamentosFiltrados;
	}

	public LancamentoTributo getLancamentoSelecionnado() {
		return lancamentoSelecionnado;
	}

	public void setLancamentoSelecionnado(LancamentoTributo lancamentoSelecionnado) {
		this.lancamentoSelecionnado = lancamentoSelecionnado;
	}

	public Long getId_nota() {
		return id_nota;
	}

	public void setId_nota(Long id_nota) {
		this.id_nota = id_nota;
	}

	public Long getId_dam() {
		return id_dam;
	}

	public void setId_dam(Long id_dam) {
		this.id_dam = id_dam;
	}

	public Long getId_darf() {
		return id_darf;
	}

	public void setId_darf(Long id_darf) {
		this.id_darf = id_darf;
	}

}
