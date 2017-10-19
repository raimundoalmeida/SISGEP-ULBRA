
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.soulsoftware.sisgep.nota.model.Prestador;
import com.soulsoftware.sisgep.nota.model.TipoPessoa;

public class TestePrestador {

	public static void main(String[] args) {

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("SisgepPU");
		EntityManager manager = factory.createEntityManager();

		EntityTransaction trx = manager.getTransaction();
		trx.begin();

		Prestador prestador = new Prestador();
		prestador.setNome("Raimundo Almeida");
		prestador.setEmail("rai.almeida@hotmail.com");
		prestador.setDocumentoReceitaFederal("888.933.753-20");
		prestador.setTelefone("(98) 987371236");
		prestador.setTipo(TipoPessoa.FISICA);
		prestador.setLogradouro("Rua Valdinar monteiro");
		prestador.setNumero("1213");
		prestador.setComplemento("");
		prestador.setCidade("Maranhãozinho");
		prestador.setUf("MA");
		prestador.setCep("65283-000");
		prestador.setBairro("Centro");

		manager.persist(prestador);

		trx.commit();

	}

}
