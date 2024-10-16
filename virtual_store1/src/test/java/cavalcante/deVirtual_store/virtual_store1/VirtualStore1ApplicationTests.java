package cavalcante.deVirtual_store.virtual_store1;

import cavalcante.deVirtual_store.virtual_store1.controllers.AcessoController;
import cavalcante.deVirtual_store.virtual_store1.models.Acesso;
import cavalcante.deVirtual_store.virtual_store1.repositoryes.AcessoRepository;
import cavalcante.deVirtual_store.virtual_store1.services.AcessoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = VirtualStore1Application.class)
public class VirtualStore1ApplicationTests {


	@Autowired
	private AcessoController acessoController;



	@Test
	public void TestCadastraAcesso() {

		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_ADMIN4");


		acessoController.salvarAcesso(acesso);



	}

}
