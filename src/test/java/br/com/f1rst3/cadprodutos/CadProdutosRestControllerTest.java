package br.com.f1rst3.cadprodutos;

import br.com.f1rst3.cadprodutos.dto.request.ProdutoSalvarRequestDto;
import br.com.f1rst3.cadprodutos.dto.response.ProdutoResponseDto;
import br.com.f1rst3.cadprodutos.dto.response.ProdutoSalvarResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CadProdutosRestControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void inserir_produto_rest() {

        ProdutoSalvarRequestDto requestDto = new ProdutoSalvarRequestDto();
        requestDto.setNome("arroz");
        requestDto.setQuantidade(Integer.valueOf("10"));

        ResponseEntity<ProdutoSalvarResponseDto> responseDto =
                restTemplate.exchange(
                        "/produtos",
                        HttpMethod.POST,
                        new HttpEntity<>(requestDto),
                        ProdutoSalvarResponseDto.class);

        Assertions.assertEquals(201, responseDto.getStatusCode().value());
        Assertions.assertNotNull(responseDto.getBody());
        Assertions.assertNotNull(responseDto.getBody().getId());
        Assertions.assertEquals(requestDto.getNome(), responseDto.getBody().getNome());
        Assertions.assertEquals(requestDto.getQuantidade(), responseDto.getBody().getQuantidade());

    }

    @Test
    void listar_todos_produtos() {

        ProdutoSalvarRequestDto requestDto = new ProdutoSalvarRequestDto();
        requestDto.setNome("oleo");
        requestDto.setQuantidade(Integer.valueOf("5"));

        ResponseEntity<ProdutoSalvarResponseDto> responseDto =
                restTemplate.exchange(
                        "/produtos",
                        HttpMethod.POST,
                        new HttpEntity<>(requestDto),
                        ProdutoSalvarResponseDto.class);

        Long idProduto1 = responseDto.getBody().getId();

        requestDto = new ProdutoSalvarRequestDto();
        requestDto.setNome("pao");
        requestDto.setQuantidade(Integer.valueOf("8"));

        responseDto =
                restTemplate.exchange(
                        "/produtos",
                        HttpMethod.POST,
                        new HttpEntity<>(requestDto),
                        ProdutoSalvarResponseDto.class);

        Long idProduto2 = responseDto.getBody().getId();

        ResponseEntity<List<ProdutoResponseDto>> responseGetDto =
                restTemplate.exchange(
                        "/produtos",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<ProdutoResponseDto>>() {});

        List<ProdutoResponseDto> produtosList = responseGetDto.getBody();

        Assertions.assertFalse(produtosList.isEmpty());

        boolean existsProduto1 = false;
        boolean existsProduto2 = false;
        for (ProdutoResponseDto produtoResponseDto : produtosList) {
            if (produtoResponseDto.getId().equals(idProduto1)) {
                existsProduto1 = true;
            } else if (produtoResponseDto.getId().equals(idProduto2)) {
                existsProduto2 = true;
            }
        }

        Assertions.assertTrue(existsProduto1);

        Assertions.assertTrue(existsProduto2);

        Assertions.assertTrue(
                produtosList.stream().anyMatch(produtoResponseDto -> produtoResponseDto.getId().equals(idProduto1))
        );

        Assertions.assertTrue(
                produtosList.stream().anyMatch(produtoResponseDto -> produtoResponseDto.getId().equals(idProduto2))
        );

    }

    @Test
    void get_produto_by_Id() {

        ProdutoSalvarRequestDto requestDto = new ProdutoSalvarRequestDto();
        requestDto.setNome("feijao");
        requestDto.setQuantidade(Integer.valueOf("14"));

        ResponseEntity<ProdutoSalvarResponseDto> responseDto =
                restTemplate.exchange(
                        "/produtos",
                        HttpMethod.POST,
                        new HttpEntity<>(requestDto),
                        ProdutoSalvarResponseDto.class);

        Long idProduto = responseDto.getBody().getId();

        ResponseEntity<ProdutoResponseDto> responseGetDto =
                restTemplate.exchange(
                        "/produtos/" + idProduto,
                        HttpMethod.GET,
                        null,
                        ProdutoResponseDto.class);

        ProdutoResponseDto responseBody = responseGetDto.getBody();

        Assertions.assertEquals(200, responseGetDto.getStatusCode().value()); // testa se a resposta foi 200
        Assertions.assertEquals(idProduto, responseBody.getId()); // compara se a resposta do id é igual
        Assertions.assertEquals(requestDto.getNome(), responseBody.getNome());
        Assertions.assertEquals(requestDto.getQuantidade(), responseBody.getQuantidade());
    }

    @Test
    void atualizar_produto() {

        ProdutoSalvarRequestDto requestDto = new ProdutoSalvarRequestDto();
        requestDto.setNome("arroz integral");
        requestDto.setQuantidade(Integer.valueOf("10"));

        ResponseEntity<ProdutoSalvarResponseDto> responseDto =
                restTemplate.exchange(
                        "/produtos",
                        HttpMethod.POST,
                        new HttpEntity<>(requestDto),
                        ProdutoSalvarResponseDto.class);

        Long idProduto = responseDto.getBody().getId();

        requestDto.setNome("arroz integral atualizado");
        requestDto.setQuantidade(Integer.valueOf("5"));

        ResponseEntity<ProdutoSalvarResponseDto> responsePutDto =
                restTemplate.exchange(
                        "/produtos/" + idProduto,
                        HttpMethod.PUT,
                        new HttpEntity<>(requestDto),
                        ProdutoSalvarResponseDto.class);


        Assertions.assertEquals(200, responsePutDto.getStatusCode().value());
        Assertions.assertEquals(idProduto, responsePutDto.getBody().getId());
        Assertions.assertEquals(requestDto.getNome(), responsePutDto.getBody().getNome());
        Assertions.assertEquals(requestDto.getQuantidade(), responsePutDto.getBody().getQuantidade());

    }

    @Test
    void excluir_produto() {

        ProdutoSalvarRequestDto requestDto = new ProdutoSalvarRequestDto();
        requestDto.setNome("ovos");
        requestDto.setQuantidade(Integer.valueOf("12"));

        ResponseEntity<ProdutoSalvarResponseDto> responseDto =
                restTemplate.exchange(
                        "/produtos",
                        HttpMethod.POST,
                        new HttpEntity<>(requestDto),
                        ProdutoSalvarResponseDto.class);

        Long idProduto = responseDto.getBody().getId();

        ResponseEntity<?> responseDeleteDto =
                restTemplate.exchange(
                        "/produtos/" + idProduto,
                        HttpMethod.DELETE,
                        null,
                        Object.class
                );

        Assertions.assertEquals(202, responseDeleteDto.getStatusCode().value());

        ResponseEntity<ProdutoResponseDto> responseGetDto =
                restTemplate.exchange(
                        "/produtos/" + idProduto,
                        HttpMethod.GET,
                        null,
                        ProdutoResponseDto.class);

        Assertions.assertEquals(404, responseGetDto.getStatusCode().value());
    }
}
