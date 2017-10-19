import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class CalcularBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal liquidoT;
	private BigDecimal liquido;
	private BigDecimal bruto;
	private BigDecimal brutot;
	private BigDecimal ISS;
	private BigDecimal IRRF;
	private BigDecimal irrf;
	private String aliquotaIss;
	private String aliquotaIrrf;

	// lancamento liquido base de calculo proporcional a parcela do IR

	BigDecimal vLiquid1 = new BigDecimal(1808.79);
	BigDecimal vLiquid2 = new BigDecimal(2616.13);
	BigDecimal vLiquid3 = new BigDecimal(3355.65);
	BigDecimal vLiquid4 = new BigDecimal(4018.02);
	// Pacela a deduzir do IR

	BigDecimal parcela1 = new BigDecimal(142.8);
	BigDecimal parcela2 = new BigDecimal(354.8);
	BigDecimal parcela3 = new BigDecimal(636.13);
	BigDecimal parcela4 = new BigDecimal(869.36);
	// base de calculo
	BigDecimal baseCalc1 = new BigDecimal(1903.98);
	BigDecimal baseCalc2 = new BigDecimal(2826.65);
	BigDecimal baseCalc3 = new BigDecimal(3751.05);
	BigDecimal baseCalc4 = new BigDecimal(4664.68);
	// Aliquota
	BigDecimal aliquota1 = new BigDecimal(0.05);
	BigDecimal aliquota2 = new BigDecimal(0.075);
	BigDecimal aliquota3 = new BigDecimal(0.15);
	BigDecimal aliquota4 = new BigDecimal(0.225);
	BigDecimal aliquota5 = new BigDecimal(0.275);

	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++==
	public CalcularBean() {
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
				this.setIRRF(new BigDecimal(00));
				this.setAliquotaIrrf("0.0");
			} else if (this.brutot.compareTo(baseCalc2) == -1) {
				this.irrf = this.brutot.multiply(aliquota2);
				this.setIRRF(irrf.subtract(parcela1).setScale(2, RoundingMode.HALF_DOWN));
				this.setAliquotaIrrf("7.5%");
			} else if (this.brutot.compareTo(baseCalc3) == -1) {
				this.irrf = this.brutot.multiply(aliquota3);
				this.setIRRF(irrf.subtract(parcela2).setScale(2, RoundingMode.HALF_DOWN));
				this.setAliquotaIrrf("15%");
			} else if (this.brutot.compareTo(baseCalc4) == -1) {
				this.irrf = this.brutot.multiply(aliquota4);
				this.setIRRF(irrf.subtract(parcela3).setScale(2, RoundingMode.HALF_DOWN));
				this.setAliquotaIrrf("22.5%");
			} else {
				this.irrf = this.brutot.multiply(aliquota5);
				this.setIRRF(irrf.subtract(parcela4).setScale(2, RoundingMode.HALF_DOWN));

				this.setAliquotaIrrf("27.5%");
			}

	}

	public void calculaISS() {

		if (this.brutot != null) {

			this.setISS(this.brutot.multiply(new BigDecimal(0.05)).setScale(2, RoundingMode.HALF_DOWN));
			// this.setISS(this.getBruto().subtract(getLiquido()));
			this.setAliquotaIss("5.0%");

		}
	}
	public void cauculoB(){
		if(getLiquido() != null){
			this.setBruto(getLiquido().add(getISS()).add(getIRRF()));
			
		}
		
	}

	public BigDecimal getLiquido() {
		return liquido;
	}

	public void setLiquido(BigDecimal liquido) {
		this.liquido = liquido;
	}

	public BigDecimal getBruto() {
		return bruto;
	}

	public void setBruto(BigDecimal bruto) {
		this.bruto = bruto;
	}

	public BigDecimal getISS() {
		return ISS;
	}

	public void setISS(BigDecimal iSS) {
		ISS = iSS;
	}

	public BigDecimal getIRRF() {
		return IRRF;
	}

	public void setIRRF(BigDecimal iRRF) {
		IRRF = iRRF;
	}

	public String getAliquotaIss() {
		return aliquotaIss;
	}

	public void setAliquotaIss(String aliquotaIss) {
		this.aliquotaIss = aliquotaIss;
	}

	public String getAliquotaIrrf() {
		return aliquotaIrrf;
	}

	public void setAliquotaIrrf(String aliquotaIrrf) {
		this.aliquotaIrrf = aliquotaIrrf;
	}

}
