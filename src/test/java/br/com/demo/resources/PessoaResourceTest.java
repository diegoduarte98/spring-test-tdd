package br.com.demo.resources;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.equalTo;

import java.util.Arrays;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import br.com.demo.ApplicationTests;
import br.com.demo.modelo.Pessoa;
import br.com.demo.modelo.Telefone;
import br.com.demo.repository.filtro.PessoaFiltro;
import io.restassured.http.ContentType;

public class PessoaResourceTest extends ApplicationTests {
	
	@Test
	public void deve_procurar_pessoa_pelo_ddd_e_numero_do_telefone() {
		given()
			.pathParam("ddd", "86")
			.pathParam("numero", "35006330")
		.get("/pessoas/{ddd}/{numero}")
		.then()
			.log().body().and()
			.statusCode(HttpStatus.OK.value())
			.body("codigo", equalTo(3), "nome", equalTo("Cauê"), "cpf", equalTo("38767897100"));
	}
	
    @Test
    public void deve_retornar_erro_nao_encontrado_quando_buscar_pessoa_por_telefone_inexistente() throws Exception {
        given()
                .pathParam("ddd", "99")
                .pathParam("numero", "987654321")
        .get("/pessoas/{ddd}/{numero}")
        .then()
                .log().body().and()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("erro", equalTo("Não existe pessoa com o telefone (99)987654321"));
    }
    
    @Test
    public void deve_salvar_nova_pessoa_no_sistema() {
    	Telefone telefone = Telefone.builder().ddd("11").numero("999898989").build();
    	Pessoa pesssoa = Pessoa.builder().nome("Diego Duarte").cpf("88888888888").telefones(Arrays.asList(telefone)).build();
    	
    	 given()
    	 	.accept(ContentType.JSON)
    	 	.contentType(ContentType.JSON)
    	 	.body(pesssoa)
    	 .when()
    	 	.post("/pessoas")
    	 .then()
    	 	.log().headers()
    	 .and()
    	 	.log().body()
    	 .and() 
    	 	.statusCode(HttpStatus.CREATED.value())
    	 .and()
    	 	.header("Location", equalTo("http://localhost:"+porta+"/pessoas/11/999898989"))
    	 	.body("codigo", equalTo(6));
    	 	
    }
    
    @Test
    public void deve_salvar_suas_pessoas_com_o_mesmo_cpf() {
    	Telefone telefone = Telefone.builder().ddd("11").numero("999898989").build();
    	Pessoa pesssoa = Pessoa.builder().nome("Diego Duarte").cpf("72788740417").telefones(Arrays.asList(telefone)).build();
    	
    	 given()
    	 	.accept(ContentType.JSON)
    	 	.contentType(ContentType.JSON)
    	 	.body(pesssoa)
    	 .when()
    	 	.post("/pessoas")
    	 .then()
    	 	.log().headers()
    	 .and()
    	 	.log().body()
    	 .and() 
    	 	.statusCode(HttpStatus.BAD_REQUEST.value())
    	 .and()
    	 	.body("erro", equalTo("Já existe pessoa cadastrada com o CPF 72788740417"));
    	 	
    }
    
    @Test
    public void deve_salvar_suas_pessoas_com_o_mesmo_telefone() {
    	Telefone telefone = Telefone.builder().ddd("41").numero("999570146").build();
    	Pessoa pesssoa = Pessoa.builder().nome("Diego Duarte").cpf("444444444444").telefones(Arrays.asList(telefone)).build();
    	
    	 given()
    	 	.accept(ContentType.JSON)
    	 	.contentType(ContentType.JSON)
    	 	.body(pesssoa)
    	 .when()
    	 	.post("/pessoas")
    	 .then()
    	 	.log().headers()
    	 .and()
    	 	.log().body()
    	 .and() 
    	 	.statusCode(HttpStatus.BAD_REQUEST.value())
    	 .and()
    	 	.body("erro", equalTo("Já existe pessoa cadastrada com o Telefone (41)999570146"));
    	 	
    }
    
    @Test
    public void deve_fitrar_pessoas_pelo_nome() {
    	PessoaFiltro filtro = PessoaFiltro.builder().nome("a").build();
    	
    	given()
    		.accept(ContentType.JSON)
    	 	.contentType(ContentType.JSON)
    	 	.body(filtro)
    	 .when()
    	 .post("/pessoas/filtrar")
    	 .then()
    	 	.log().body()
    	 		.and()
    	 			.statusCode(HttpStatus.OK.value())
    	 			.body("nome", hasItem("Thiago"));
    }
}
