package com.soulsoftware.sisgep.nota.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "lancamento")
public class LancamentoTributo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Tomador tomador;
	private Prestador prestador;
	private Date mesReferencia;
	private Date dataEmissao;
	private Date dataVencimento;
	private String descricaoServicos;
	private String observacoes;
	private BigDecimal liquido;
	private BigDecimal brutot;
	private BigDecimal bruto = new BigDecimal(0.00);
	private BigDecimal iss = new BigDecimal(0.00);
	private BigDecimal irrf = new BigDecimal(0.00);
	private String aliquotaIss;
	private String aliquotaIrrf;
	
	//Com Base de Cálculo (em R$)  para o IRPF2017 já com 5% de aumento
	// lancamento liquido base de calculo proporcional a parcela do IR (calculo excel)
	BigDecimal vLiquid1 = new BigDecimal(1892.09);
	BigDecimal vLiquid2 = new BigDecimal(2729.19);
	BigDecimal vLiquid3 = new BigDecimal(3491.62);
	BigDecimal vLiquid4 = new BigDecimal(4175.46);
	
	// Pacela a deduzir do IR
	BigDecimal parcela1 = new BigDecimal(149.94);
	BigDecimal parcela2 = new BigDecimal(372.54);
	BigDecimal parcela3 = new BigDecimal(667.94);
	BigDecimal parcela4 = new BigDecimal(912.83);
	
	// base de calculo
	BigDecimal baseCalc1 = new BigDecimal(1999.18);
	BigDecimal baseCalc2 = new BigDecimal(2967.98);
	BigDecimal baseCalc3 = new BigDecimal(3938.60);
	BigDecimal baseCalc4 = new BigDecimal(4897.91);
	
	// Aliquota%
	BigDecimal aliquota1 = new BigDecimal(0.05);
	BigDecimal aliquota2 = new BigDecimal(0.075);
	BigDecimal aliquota3 = new BigDecimal(0.15);
	BigDecimal aliquota4 = new BigDecimal(0.225);
	BigDecimal aliquota5 = new BigDecimal(0.275);

	private BigDecimal liquidoT;
	private BigDecimal irrft;

	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++==
	public LancamentoTributo() {
		this.calculoValorBruto();
	}

	public void calculoValorBruto() {
		if (this.getLiquido() != null)

			if ((this.getLiquido().compareTo(vLiquid1) == -1) || (this.getLiquido().compareTo(vLiquid1) == 0)) {
				this.brutot = ((this.getLiquido().multiply(new BigDecimal(100 / 95)).setScale(2,
						RoundingMode.HALF_DOWN)));

			} else if ((this.getLiquido().compareTo(vLiquid2) == -1) || (this.getLiquido().compareTo(vLiquid2) == 0)) {
				this.liquidoT = (this.getLiquido().subtract(parcela1));
				this.brutot = (this.liquidoT.multiply(new BigDecimal(100 / 87.5)).setScale(2, RoundingMode.HALF_DOWN));

			} else if ((this.getLiquido().compareTo(vLiquid3) == -1) || (this.getLiquido().compareTo(vLiquid2) == 0)) {
				this.liquidoT = (this.getLiquido().subtract(parcela2));
				this.brutot = (this.liquidoT.multiply(new BigDecimal(100 / 80.0)).setScale(2, RoundingMode.HALF_DOWN));

			} else if ((this.getLiquido().compareTo(vLiquid4) == -1) || (this.getLiquido().compareTo(vLiquid2) == 0)) {
				this.liquidoT = (this.getLiquido().subtract(parcela3));
				this.brutot = (this.liquidoT.multiply(new BigDecimal(100 / 72.7)).setScale(2, RoundingMode.HALF_DOWN));

			} else {
				this.liquidoT = (this.getLiquido().subtract(parcela4));
				this.brutot = (this.liquidoT.multiply(new BigDecimal(100 / 67.5)).setScale(2, RoundingMode.HALF_DOWN));

			}

		this.calculaIRRF();
		this.calculaISS();
		this.cauculoB();
	}

	public void calculaIRRF() {
		if (this.brutot != null)
			if (this.brutot.compareTo(baseCalc1) == -1) {
				this.setIrrf(new BigDecimal(00));
				this.setAliquotaIrrf("0.0%");
			} else if (this.brutot.compareTo(baseCalc2) == -1) {
				this.irrft = this.brutot.multiply(aliquota2);
				this.setIrrf(irrft.subtract(parcela1).setScale(2, RoundingMode.HALF_DOWN));
				this.setAliquotaIrrf("7.5%");
			} else if (this.brutot.compareTo(baseCalc3) == -1) {
				this.irrft = this.brutot.multiply(aliquota3);
				this.setIrrf(irrft.subtract(parcela2).setScale(2, RoundingMode.HALF_DOWN));
				this.setAliquotaIrrf("15%");
			} else if (this.brutot.compareTo(baseCalc4) == -1) {
				this.irrft = this.brutot.multiply(aliquota4);
				this.setIrrf(irrft.subtract(parcela3).setScale(2, RoundingMode.HALF_DOWN));
				this.setAliquotaIrrf("22.5%");
			} else {
				this.irrft = this.brutot.multiply(aliquota5);
				this.setIrrf(irrft.subtract(parcela4).setScale(2, RoundingMode.HALF_DOWN));

				this.setAliquotaIrrf("27.5%");
			}

	}

	public void calculaISS() {

		if (this.brutot != null) {

			this.setIss(this.brutot.multiply(new BigDecimal(0.05)).setScale(2, RoundingMode.HALF_DOWN));
			// this.setISS(this.getBruto().subtract(getLiquido()));
			this.setAliquotaIss("5.0%");

		}
	}

	public void cauculoB() {
		if (getLiquido() != null) {
			this.setBruto(getLiquido().add(getIss()).add(getIrrf()));
		}
	}

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "tomador_id", nullable = false)
	public Tomador getTomador() {
		return tomador;
	}

	public void setTomador(Tomador tomador) {
		this.tomador = tomador;
	}

	@NotNull
	@ManyToOne
	@JoinColumn(name = "prestador_id", nullable = false)
	public Prestador getPrestador() {
		return prestador;
	}

	public void setPrestador(Prestador prestador) {
		this.prestador = prestador;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "mes_referente", nullable = false)
	public Date getMesReferencia() {
		return mesReferencia;
	}

	public void setMesReferencia(Date mesReferencia) {
		this.mesReferencia = mesReferencia;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "data_emissao", nullable = false)
	public Date getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "data_vencimento", nullable = false)
	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	@Column(name = "decricao", length = 255, nullable = false)
	public String getDescricaoServicos() {
		return descricaoServicos;
	}

	public void setDescricaoServicos(String descricaoServicos) {
		this.descricaoServicos = descricaoServicos;
	}

	@Column(columnDefinition = "text")
	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	@NotNull
	@Column(name = "valor_liquido", nullable = false, precision = 10, scale = 2)
	public BigDecimal getLiquido() {
		return liquido;
	}

	public void setLiquido(BigDecimal liquido) {
		this.liquido = liquido;
	}

	@Column(name = "valor_bruto", precision = 10, scale = 2)
	public BigDecimal getBruto() {
		return bruto;
	}

	public void setBruto(BigDecimal bruto) {
		this.bruto = bruto;
	}

	@Column(name = "iss", nullable = false, precision = 10, scale = 2)
	public BigDecimal getIss() {
		return iss;
	}

	public void setIss(BigDecimal iss) {
		this.iss = iss;
	}

	@Column(name = "irrf", nullable = false, precision = 10, scale = 2)
	public BigDecimal getIrrf() {
		return irrf;
	}

	public void setIrrf(BigDecimal irrf) {
		this.irrf = irrf;
	}

	@Column(name = "aliquota_iss", length = 9)
	public String getAliquotaIss() {
		return aliquotaIss;
	}

	public void setAliquotaIss(String aliquotaIss) {
		this.aliquotaIss = aliquotaIss;
	}

	@Column(name = "aliquota_irrf", length = 9)
	public String getAliquotaIrrf() {
		return aliquotaIrrf;
	}

	public void setAliquotaIrrf(String aliquotaIrrf) {
		this.aliquotaIrrf = aliquotaIrrf;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LancamentoTributo other = (LancamentoTributo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
