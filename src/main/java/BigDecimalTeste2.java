import java.math.BigDecimal;

public class BigDecimalTeste2 {
	
	public static void main(String args[])
    {
        BigDecimal numero = new BigDecimal(20.005);
        int valorRetorno;
        
        
        //Repostas x.compareTo(y):
        //1  x maior que y
        //0  x igual a y 
        //-1 x menor que y
        
        System.out.println("\n\n");        
        System.out.println("Comparações");
        
        valorRetorno = numero.compareTo(new BigDecimal(20.001));
        System.out.println("Caso 1 (MaiorQue): " + valorRetorno);

        valorRetorno = numero.compareTo(new BigDecimal(20.006));
        System.out.println("Caso 1 (MenorQue): " + valorRetorno);
        
        valorRetorno = numero.compareTo(numero);
        System.out.println("Caso 1 (Igual):" + valorRetorno);
        
        
    }
	

}
