package br.com.f1rst3.cadprodutos.repository;

import br.com.f1rst3.cadprodutos.model.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<ProdutoModel, Long> {

}
