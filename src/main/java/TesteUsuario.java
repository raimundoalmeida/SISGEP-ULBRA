import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.soulsoftware.sisgep.nota.model.Grupo;
import com.soulsoftware.sisgep.nota.model.Usuario;

public class TesteUsuario {

	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("SisgepPU");
		EntityManager manager = factory.createEntityManager();
		
		EntityTransaction trx = manager.getTransaction();
		trx.begin();
		
		Usuario usuario = new Usuario();
		usuario.setNome("Rai Almeida");
		usuario.setEmail("rai.almeida7@hotmail.com");
		usuario.setSenha("1234");
		
		Grupo grupo  = new Grupo();
		grupo.setNome("Adminstradores");
		grupo.setDescricao("Administradores do sistema");
		
		usuario.getGrupos().add(grupo);
		
		manager.persist(usuario);
		trx.commit();

	}

}
