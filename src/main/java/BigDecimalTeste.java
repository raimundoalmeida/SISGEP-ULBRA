import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalTeste {

	BigDecimal cinPorcento = new BigDecimal(0.0);

	public static void main(String args[]) {
		BigDecimal porc = new BigDecimal(0.0);
		

		BigDecimal bruto = new BigDecimal(2000);
		
		porc = (bruto.multiply(new BigDecimal(0.275)).setScale(2, RoundingMode.DOWN));

		System.out.println("5% procentos de R$ " + bruto + " = " + bruto.multiply(new BigDecimal(0.650)).setScale(2, RoundingMode.DOWN));
		System.out.println("7,5% procentos de R$ " + bruto + " = "	+ bruto.multiply(new BigDecimal(0.075)).setScale(2, RoundingMode.DOWN));
		System.out.println("10% procentos de R$ " + bruto + " = "	+ bruto.multiply(new BigDecimal(0.10)).setScale(2, RoundingMode.DOWN));
		System.out.println("27,5% procentos de R$ "  + bruto + " = " + porc);

	}

}
