package crudJava17.service;

import crudJava17.model.Fornecedor;
import crudJava17.model.ItemNota;
import crudJava17.model.NotaFiscal;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class FornecedorService {
    @Transactional
    public Fornecedor salvar(Fornecedor fornecedor) {
        Fornecedor fornecedorExistente = Fornecedor.find("cnpj", fornecedor.cnpj).firstResult();

        if (fornecedorExistente != null) {
            throw new WebApplicationException("Já existe um fornecedor com o CNPJ: " + fornecedor.cnpj, Response.Status.BAD_REQUEST);
        }

        fornecedor.persist();
        return fornecedor;
    }

    @Transactional
    public Fornecedor editar(UUID id, Fornecedor fornecedorAtualizado) {
        Fornecedor fornecedor = Fornecedor.findById(id);
        if (fornecedor != null) {
            fornecedor.razaoSocial = fornecedorAtualizado.razaoSocial;
            fornecedor.email = fornecedorAtualizado.email;
            fornecedor.telefone = fornecedorAtualizado.telefone;
            fornecedor.cnpj = fornecedorAtualizado.cnpj;
            fornecedor.dataBaixa = fornecedorAtualizado.dataBaixa;
            fornecedor.situacaoFornecedor = fornecedorAtualizado.situacaoFornecedor;
            fornecedor.persist();
        }
        return fornecedor;
    }

    @Transactional
    public boolean excluir(UUID id) {
        Fornecedor fornecedor = Fornecedor.findById(id);
        if (fornecedor != null) {

            long count = NotaFiscal.count("fornecedor = ?1", fornecedor);
            if (count > 0) {
                return false;
            }
            fornecedor.delete();
            return true;  //
        }
        return false;
    }


    public List<Fornecedor> listar() {
        return Fornecedor.listAll();
    }


    public Fornecedor buscarPorId(UUID id) {
        return Fornecedor.findById(id);
    }
}
