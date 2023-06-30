package br.com.f1rst3.cadprodutos.service;

import br.com.f1rst3.cadprodutos.exception.NotFoundException;
import br.com.f1rst3.cadprodutos.exception.ValidationException;
import br.com.f1rst3.cadprodutos.model.ProdutoModel;
import br.com.f1rst3.cadprodutos.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
    public class ProdutoService {
        final ProdutoRepository produtoRepository;
        public ProdutoService(ProdutoRepository produtoRepository) {
            this.produtoRepository = produtoRepository;
        }
        public ProdutoModel salva(ProdutoModel produtoModel) {
            if (produtoModel.getNome() == null) {
                throw new ValidationException("Nome é obrigatório");
            }
            if (produtoModel.getQuantidade() == null) {
                throw new ValidationException("A quantidade é obrigatoria");
            } //senao
            return produtoRepository.save(produtoModel);
        }

        public void excluir(Long idProduto) {
            produtoRepository.deleteById(idProduto);
        }

        public ProdutoModel getById(long idProduto) {
            ProdutoModel produtoModel = produtoRepository.findById(idProduto).orElse(null);
            if (produtoModel == null) {
                throw new NotFoundException("ID nao encontrado");
            }
            return produtoModel;
        }

        public List<ProdutoModel> listarTodos() {
            return produtoRepository.findAll();
        }
    }

