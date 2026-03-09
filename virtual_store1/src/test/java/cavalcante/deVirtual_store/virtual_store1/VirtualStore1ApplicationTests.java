package cavalcante.deVirtual_store.virtual_store1;

import cavalcante.deVirtual_store.virtual_store1.controllers.AcessoController;
import cavalcante.deVirtual_store.virtual_store1.models.Acesso;
import cavalcante.deVirtual_store.virtual_store1.repositoryes.AcessoRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@SpringBootTest(classes = VirtualStore1Application.class)
public class VirtualStore1ApplicationTests extends TestCase {

	@Autowired
	private AcessoController acessoController; // Controller para manipulação dos acessos

	@Autowired
	private AcessoRepository acessoRepository; // Repositório para acesso ao banco de dados

	@Autowired
	private WebApplicationContext wac; // Contexto da aplicação usado para simular requisições

	/**
	 * Testa o cadastro de um acesso via API REST.
	 */
	@Test
	public void testRestApiCadastroAcesso() throws Exception {
		// Configuração do MockMvc para simular requisições REST
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		// Cria um objeto Acesso com uma descrição
		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_COMPRADOR");

		// Serializa o objeto Acesso para JSON
		ObjectMapper objectMapper = new ObjectMapper();

		// Simula a requisição POST para salvar o acesso
		ResultActions retornoApi = mockMvc.perform(MockMvcRequestBuilders.post("/salvarAcesso")
				.content(objectMapper.writeValueAsString(acesso))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON));

		// Exibe o retorno da API no console
		System.out.printf("retorno da API  %s", retornoApi.andReturn().getResponse().getContentAsString());

		// Deserializa o retorno da API para um objeto Acesso
		Acesso objetoRetorno = objectMapper.readValue(retornoApi.andReturn().getResponse().getContentAsString(), Acesso.class);

		// Verifica se a descrição do objeto salvo é a mesma do objeto retornado
		assertEquals(acesso.getDescricao(), objetoRetorno.getDescricao());
	}

	/**
	 * Testa a exclusão de um acesso via API REST.
	 */
	@Test
	public void testRestApiDeleteAcesso() throws Exception {
		// Configuração do MockMvc para simular requisições REST
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		// Cria e salva um objeto Acesso no banco
		Acesso acesso = new Acesso();
		acesso.setDescricao("RELE_TESTE_DELETE");
		acessoRepository.save(acesso);

		// Serializa o objeto Acesso para JSON
		ObjectMapper objectMapper = new ObjectMapper();

		// Simula a requisição DELETE para excluir o acesso
		ResultActions retornoApi = mockMvc.perform(MockMvcRequestBuilders.delete("/deleteAcesso")
				.content(objectMapper.writeValueAsString(acesso))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON));

		// Exibe o retorno da API no console
		System.out.printf("retorno da API  %s", retornoApi.andReturn().getResponse().getContentAsString());

		// Verifica se o status da resposta é 200 (OK)
		assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
	}

	/**
	 * Testa a exclusão de um acesso por ID via API REST.
	 */
	@Test
	public void testRestApiDeletePorIdAcesso() throws Exception {
		// Configuração do MockMvc para simular requisições REST
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		// Cria e salva um objeto Acesso no banco
		Acesso acesso = new Acesso();
		acesso.setDescricao("RELE_TESTE_DELETE_ID");
		acessoRepository.save(acesso);

		// Serializa o objeto Acesso para JSON
		ObjectMapper objectMapper = new ObjectMapper();

		// Simula a requisição DELETE para excluir o acesso por ID
		ResultActions retornoApi = mockMvc.perform(MockMvcRequestBuilders.delete("/deleteAcessoPorId/" + acesso.getId())
				.content(objectMapper.writeValueAsString(acesso))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON));

		// Exibe o retorno da API no console
		System.out.printf("retorno da API  %s", retornoApi.andReturn().getResponse().getContentAsString());

		// Verifica se o status da resposta é 200 (OK)
		assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
	}

	/**
	 * Testa o retorno de um acesso por ID via API REST.
	 */
	@Test
	public void testRestApiRetornoPorIdAcesso() throws Exception {
		// Configuração do MockMvc para simular requisições REST
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		// Cria e salva um objeto Acesso no banco
		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_TESTE_RETORNO");
		acesso = acessoRepository.save(acesso);

		// Serializa o objeto Acesso para JSON
		ObjectMapper objectMapper = new ObjectMapper();

		// Simula a requisição GET para buscar o acesso por ID
		ResultActions retornoApi = mockMvc.perform(MockMvcRequestBuilders.get("/retornoAcessoPorId/" + acesso.getId())
				.content(objectMapper.writeValueAsString(acesso))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON));

		// Verifica se o status da resposta é 200 (OK)
		assertEquals(200, retornoApi.andReturn().getResponse().getStatus());

		// Deserializa o retorno da API para um objeto Acesso
		Acesso acessoRetorno = objectMapper.readValue(retornoApi.andReturn().getResponse().getContentAsString(), Acesso.class);

		// Exibe a descrição do objeto retornado
		System.out.println("Retorno da API   :   " + acessoRetorno.getDescricao());
	}

	/**
	 * Testa o retorno de um acesso por descrição via API REST.
	 */
	@Test
	public void testRestApiRetornoPorDesc() throws Exception {
		// Configuração do MockMvc para simular requisições REST
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		// Cria e salva um objeto Acesso no banco
		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_TESTE_RETORNO_DESC");
		acesso = acessoRepository.save(acesso);

		// Serializa o objeto Acesso para JSON
		ObjectMapper objectMapper = new ObjectMapper();

		// Simula a requisição GET para buscar o acesso por descrição
		ResultActions retornoApi = mockMvc.perform(MockMvcRequestBuilders.get("/retornoAcessoDesc/" + acesso.getDescricao())
				.content(objectMapper.writeValueAsString(acesso))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON));

		// Verifica se o status da resposta é 200 (OK)
		assertEquals(200, retornoApi.andReturn().getResponse().getStatus());

		// Deserializa o retorno da API para uma lista de objetos Acesso
		List<Acesso> retornoApiList = objectMapper.readValue(retornoApi.andReturn().getResponse().getContentAsString(), new TypeReference<List<Acesso>>() {});

		// Verifica se a lista contém apenas um elemento e se os dados correspondem
		assertEquals(1, retornoApiList.size());
		assertEquals(acesso.getDescricao(), retornoApiList.get(0).getDescricao());

		// Exclui o objeto Acesso do banco
		acessoRepository.deleteById(acesso.getId());
	}

	/**
	 * Testa o cadastro e manipulação de acessos diretamente via controller e repositório.
	 */
	@Test
	public void TestCadastraAcesso() {
		// Cria um novo objeto Acesso
		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_ADMIN4");

		// Salva o objeto via controller
		acessoController.salvarAcesso(acesso).getBody();

		// Verifica se o ID


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
