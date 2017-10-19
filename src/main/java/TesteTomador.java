
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.soulsoftware.sisgep.nota.model.Tomador;

public class TesteTomador {

	public static void main(String[] args) {

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("SisgepPU");
		EntityManager manager = factory.createEntityManager();

		EntityTransaction trx = manager.getTransaction();
		trx.begin();

		Tomador tomador = new Tomador();
		tomador.setNome("Prefeitura Municipal de Maranhãozinho");
		tomador.setEmail("pref_maz@hotmail.com");
		tomador.setDocumentoReceitaFederal("888.933.753-20");
		tomador.setTelefone("(98) 33251047");
		tomador.setBairro("Centro");
		tomador.setCep("65283-000");
		tomador.setCidade("Maranhãozinho");
		tomador.setComplemento("");
		tomador.setLogradouro("Rua Boa Vista");
		tomador.setNumero("03");
		tomador.setUf("MA");
		

		manager.persist(tomador);

		trx.commit();

	}

}
