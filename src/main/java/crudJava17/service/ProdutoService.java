package crudJava17.service;

import crudJava17.model.ItemNota;
import crudJava17.model.Produto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ProdutoService {
    @Transactional
    public Produto salvar(Produto produto) {
        produto.persist();
        return produto;
    }

    @Transactional
    public Produto editar(UUID id, Produto produtoAtualizado) {
        Produto produto = Produto.findById(id);
        if (produto != null) {
            produto.descricao = produtoAtualizado.descricao;
            produto.situacaoProduto = produtoAtualizado.situacaoProduto;
            produto.persist();
        }
        return produto;
    }

    @Transactional
    public boolean excluir(UUID id) {
        Produto produto = Produto.findById(id);
        if (produto != null) {

            long count = ItemNota.count("produto = ?1", produto);
            if (count > 0) {
                return false;
            }
            produto.delete();
            return true;  //
        }
        return false;
    }


    public List<Produto> listar() {
        return Produto.listAll();
    }


    public Produto buscarPorId(UUID id) {
        return Produto.findById(id);
    }
}
