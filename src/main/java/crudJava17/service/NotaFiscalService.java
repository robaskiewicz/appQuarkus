package crudJava17.service;

import crudJava17.model.NotaFiscal;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class NotaFiscalService {
    @Transactional
    public NotaFiscal salvar(NotaFiscal notaFiscal) {
        notaFiscal.persist();
        return notaFiscal;
    }

//    @Transactional
//    public NotaFiscal editar(NotaFiscal nota) {
//        if (nota != null) {
//            nota.persist();
//        }
//        return nota;
//    }

//    @Transactional
//    public boolean excluir(UUID id) {
//        NotaFiscal notaFiscal = NotaFiscal.findById(id);
//        if (notaFiscal != null) {
//
//            long count = ItemNota.count("notaFiscal = ?1", notaFiscal);
//            if (count > 0) {
//                return false;
//            }
//            notaFiscal.delete();
//            return true;  //
//        }
//        return false;
//    }


    public List<NotaFiscal> listar() {
        return NotaFiscal.listAll();
    }


    public NotaFiscal buscarPorId(UUID id) {
        return NotaFiscal.findById(id);
    }
}
