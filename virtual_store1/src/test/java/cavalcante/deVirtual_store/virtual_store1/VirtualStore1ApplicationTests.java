package cavalcante.deVirtual_store.virtual_store1;

import cavalcante.deVirtual_store.virtual_store1.controllers.AcessoController;
import cavalcante.deVirtual_store.virtual_store1.models.Acesso;
import cavalcante.deVirtual_store.virtual_store1.repositoryes.AcessoRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;
import netscape.javascript.JSException;
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
import java.util.Locale;

@SpringBootTest(classes = VirtualStore1Application.class)
public class VirtualStore1ApplicationTests extends TestCase {



	@Autowired
	private AcessoController acessoController;

	@Autowired
	private AcessoRepository acessoRepository;

    @Autowired
	private WebApplicationContext wac;



    @Test
	public void  testRestApiCadastroAcesso() throws Exception {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		Acesso acesso = new Acesso();

		acesso.setDescricao("ROLE_COMPRADOR");

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions retornoApi = mockMvc
				                 .perform(MockMvcRequestBuilders.post("/salvarAcesso")
								 .content(objectMapper.writeValueAsString(acesso))
								 .accept(MediaType.APPLICATION_JSON)
								 .contentType(MediaType.APPLICATION_JSON)

									);

		System.out.printf("retorno da API  "+ retornoApi.andReturn().getResponse().getContentAsString());

		Acesso objetoRetorno = objectMapper.readValue(retornoApi.andReturn().getResponse().getContentAsString(), Acesso.class);

		assertEquals(acesso.getDescricao(),objetoRetorno.getDescricao());




	}


	@Test
	public void  testRestApiDeleteAcesso() throws Exception {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		Acesso acesso = new Acesso();

		acesso.setDescricao("RELE_TESTE_DELETE");

		acessoRepository.save(acesso);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions retornoApi = mockMvc
				        .perform(MockMvcRequestBuilders.delete("/deleteAcesso")
						.content(objectMapper.writeValueAsString(acesso))
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)

				);

		System.out.printf("retorno da API  "+ retornoApi.andReturn().getResponse().getContentAsString() +"  ");

		assertEquals(200,retornoApi.andReturn().getResponse().getStatus() );


	}


	@Test
	public void  testRestApiDeletePorIdAcesso() throws Exception {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		Acesso acesso = new Acesso();

		acesso.setDescricao("RELE_TESTE_DELETE_ID");

		acessoRepository.save(acesso);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions retornoApi = mockMvc
				.perform(MockMvcRequestBuilders.delete("/deleteAcessoPorId/" + acesso.getId())
						.content(objectMapper.writeValueAsString(acesso))
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)

				);

		System.out.printf("retorno da API  "+ retornoApi.andReturn().getResponse().getContentAsString() +"  ");

		assertEquals(200,retornoApi.andReturn().getResponse().getStatus() );


	}


	@Test
	public void  testRestApiRetornoPorIdAcesso() throws Exception {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

        Acesso acesso = new Acesso();

		acesso.setDescricao("ROLE_TESTE_RETORNO");

		acesso = acessoRepository.save(acesso);


		ObjectMapper objectMapper = new ObjectMapper();


		ResultActions retornoApi = mockMvc
				.perform(MockMvcRequestBuilders.get("/retornoAcessoPorId/" + acesso.getId())
						.content(objectMapper.writeValueAsString(acesso))
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
				);




		assertEquals(200,retornoApi.andReturn().getResponse().getStatus() );

		Acesso acessoRetorno = objectMapper.readValue(retornoApi.andReturn().getResponse().getContentAsString(), Acesso.class);

		System.out.println("Retorno da API   :   "+acessoRetorno.getDescricao());


	}


	@Test
	public void  testRestApiRetornoPorDesc() throws Exception {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		Acesso acesso = new Acesso();

		acesso.setDescricao("ROLE_TESTE_RETORNO_DESC");

		acesso = acessoRepository.save(acesso);


		ObjectMapper objectMapper = new ObjectMapper();


		ResultActions retornoApi = mockMvc
				.perform(MockMvcRequestBuilders.get("/retornoAcessoDesc/" + acesso.getDescricao())
						.content(objectMapper.writeValueAsString(acesso))
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
				);




		assertEquals(200,retornoApi.andReturn().getResponse().getStatus() );

		List<Acesso> retornoApiList = objectMapper.readValue(retornoApi.andReturn().getResponse().getContentAsString(), new TypeReference<List<Acesso>>() {});

		assertEquals(1,retornoApiList.size());

		assertEquals(acesso.getDescricao(),retornoApiList.get(0).getDescricao());

		acessoRepository.deleteById(acesso.getId());


	}





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
