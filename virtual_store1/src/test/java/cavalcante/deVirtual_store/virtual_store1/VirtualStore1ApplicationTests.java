package cavalcante.deVirtual_store.virtual_store1;

import cavalcante.deVirtual_store.virtual_store1.controllers.AcessoController;
import cavalcante.deVirtual_store.virtual_store1.models.Acesso;
import cavalcante.deVirtual_store.virtual_store1.repositoryes.AcessoRepository;
import cavalcante.deVirtual_store.virtual_store1.services.AcessoService;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Locale;

@SpringBootTest(classes = VirtualStore1Application.class)
public class VirtualStore1ApplicationTests extends TestCase {



	@Autowired
	private AcessoController acessoController;

	@Autowired
	private AcessoRepository acessoRepository;


	@Test
	public void TestCadastraAcesso() {

		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_ADMIN4");


        // Gravou no banco de dados
		acessoController.salvarAcesso(acesso).getBody();

		// Teste se gerou id
		assertEquals(true,acesso.getId() > 0);

		// testa se dados foram salvos corretamente
        assertEquals("ROLE_ADMIN4",acesso.getDescricao());



		Acesso acesso2 = acessoRepository.findById(acesso.getId()).get();

		// Teste de carregamento
		assertEquals(acesso.getId(),acesso2.getId());

		//Teste e delete
         acessoRepository.delete(acesso2);

         acessoRepository.flush();


		 Acesso acesso3 = acessoRepository.findById(acesso2.getId()).orElse(null);

		 assertEquals(true,acesso3 == null);


         // Teste de query
		Acesso acesso1 = new Acesso();
		acesso1.setDescricao("ROLE_ALUNO");

		acesso1 = acessoController.salvarAcesso(acesso1).getBody();

		List<Acesso> acessos = acessoRepository.buscarAcessoDesc("ALUNO".trim().toUpperCase());

		assertEquals(1,acessos.size());

		acessoRepository.deleteById(acesso1.getId());







	}

}
