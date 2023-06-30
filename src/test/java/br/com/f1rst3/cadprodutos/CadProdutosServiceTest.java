package br.com.f1rst3.cadprodutos;

import br.com.f1rst3.cadprodutos.model.ProdutoModel;
import br.com.f1rst3.cadprodutos.service.ProdutoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CadProdutosServiceTest {

    @Autowired
    private ProdutoService produtoService;

    @Test
    @DisplayName("Test - Inserir produto - OK!")
    void inserir_produto_ok() {

        ProdutoModel produtoModel = new ProdutoModel();
        produtoModel.setNome("sabao");
        produtoModel.setQuantidade(Integer.valueOf("8"));

        ProdutoModel produtoModelSaved = produtoService.salva(produtoModel);

        Assertions.assertNotNull(produtoModelSaved.getId());
        Assertions.assertEquals(produtoModel.getNome(), produtoModelSaved.getNome());
        Assertions.assertEquals(produtoModel.getQuantidade(), produtoModelSaved.getQuantidade());

    }

    @Test
    void excluir_produto() {

        ProdutoModel produtoModel = new ProdutoModel();
        produtoModel.setNome("pao");
        produtoModel.setQuantidade(Integer.valueOf("4"));

        ProdutoModel produtoModelSaved = produtoService.salva(produtoModel);

        Long idProduto = produtoModelSaved.getId();

        produtoService.excluir(idProduto);

        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            ProdutoModel produtoModelDeleted = produtoService.getById(idProduto);
        });

        Assertions.assertEquals("ID nao encontrado", exception.getMessage());

    }

    @Test
    void get_id_invalido() {

        Exception exception = Assertions.assertThrows(Exception.class, () -> {

            ProdutoModel produtoModel = produtoService.getById(100);

        });

        Assertions.assertEquals("ID nao encontrado", exception.getMessage());

    }

    @Test
    void quantidade_obrigatorio() {

        ProdutoModel produtoModel = new ProdutoModel();
        produtoModel.setNome("manteiga");

        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            ProdutoModel produtoModelSaved = produtoService.salva(produtoModel);
        });

        Assertions.assertEquals("A quantidade é obrigatoria", exception.getMessage());

    }

    @Test
    void nome_obrigatorio() {

        ProdutoModel produtoModel = new ProdutoModel();
        produtoModel.setQuantidade(Integer.valueOf("16"));

        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            ProdutoModel produtoModelSaved = produtoService.salva(produtoModel);
        });

        Assertions.assertEquals("Nome é obrigatório", exception.getMessage());

    }

    @Test
    void get_by_id() {

        ProdutoModel produtoModel = new ProdutoModel();
        produtoModel.setNome("batata");
        produtoModel.setQuantidade(Integer.valueOf("12"));

        ProdutoModel produtoModelSaved = produtoService.salva(produtoModel);

        Long idProduto = produtoModelSaved.getId();

        ProdutoModel produtoModelGet = produtoService.getById(idProduto);

        Assertions.assertEquals(produtoModelSaved.getId(), produtoModelGet.getId());
        Assertions.assertEquals(produtoModelSaved.getNome(), produtoModelGet.getNome());
        Assertions.assertEquals(produtoModelSaved.getQuantidade(), produtoModelGet.getQuantidade());
    }
}
