package crudJava17.controller;

import crudJava17.model.Fornecedor;
import crudJava17.model.ItemNota;
import crudJava17.model.NotaFiscal;
import crudJava17.model.Produto;
import crudJava17.service.FornecedorService;
import crudJava17.service.NotaFiscalService;
import crudJava17.service.ProdutoService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Path("/notasfiscais")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NotaFiscalController {

    @Inject
    NotaFiscalService notaFiscalService;

    @GET
    public List<NotaFiscal> listar() {
        return notaFiscalService.listar();
    }

    @GET
    @Path("/{id}")
    public Response buscarNotaFiscalPorId(@PathParam("id") UUID id) {

        try {
            NotaFiscal nota = NotaFiscal.findById(id);

            if (nota == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Nota fiscal não encontrada com o ID: " + id)
                        .build();
            }
            return Response.ok(nota).build();

        } catch (WebApplicationException e) {
            return Response.status(e.getResponse().getStatus()).entity(e.getMessage()).build();
        }
    }


    @POST
    public Response salvarNotaFiscal(NotaFiscal notaF) {

        try {
            Fornecedor fornecedor = Fornecedor.findById(notaF.fornecedor.id);

            if (fornecedor == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Fornecedor não encontrado!")
                        .build();
            }

            NotaFiscal nota = new NotaFiscal();
            Random random = new Random();
            nota.numeroNota = "NF-" + random.nextInt(1000000);
            nota.dataEHoraEmissao = LocalDateTime.now();
            nota.fornecedor = fornecedor;
            nota.endereco = notaF.endereco;
            nota.valorTotalNota = notaF.valorTotalNota;

            for (ItemNota item : notaF.itens) {

                if (item.produto != null && item.produto.id != null) {
                    Produto produto = Produto.findById(item.produto.id);
                    if (produto == null) {
                        return Response.status(Response.Status.BAD_REQUEST)
                                .entity("Produto não encontrado com ID: " + item.produto.id)
                                .build();
                    }
                    item.produto = produto;
                    item.notaFiscal = nota;
                } else {
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity("Produto ID é obrigatório para o item")
                            .build();
                }

            }

            nota.itens.addAll(notaF.itens);

            notaFiscalService.salvar(nota);

            return Response.status(Response.Status.CREATED).entity(nota).build();
        } catch (WebApplicationException e) {
            return Response.status(e.getResponse().getStatus()).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response editarNotaFiscal(@PathParam("id") UUID id, NotaFiscal notaF) {
        try {
            NotaFiscal notaExistente = NotaFiscal.findById(id);
            if (notaExistente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Nota fiscal não encontrada com o ID: " + id)
                        .build();
            }

            Fornecedor fornecedor = Fornecedor.findById(notaF.fornecedor.id);
            if (fornecedor == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Fornecedor não encontrado!")
                        .build();
            }

            notaExistente.numeroNota = notaF.numeroNota != null ? notaF.numeroNota : notaExistente.numeroNota;
            notaExistente.dataEHoraEmissao = notaF.dataEHoraEmissao != null ? notaF.dataEHoraEmissao : notaExistente.dataEHoraEmissao;
            notaExistente.fornecedor = fornecedor;
            notaExistente.endereco = notaF.endereco != null ? notaF.endereco : notaExistente.endereco;
            notaExistente.valorTotalNota = notaF.valorTotalNota != null ? notaF.valorTotalNota : notaExistente.valorTotalNota;

            for (ItemNota item : notaF.itens) {
                if (item.produto != null && item.produto.id != null) {
                    Produto produto = Produto.findById(item.produto.id);
                    if (produto == null) {
                        return Response.status(Response.Status.BAD_REQUEST)
                                .entity("Produto não encontrado com ID: " + item.produto.id)
                                .build();
                    }
                    item.produto = produto;
                    item.notaFiscal = notaExistente; // Associar o item à nota fiscal existente
                } else {
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity("Produto ID é obrigatório para o item")
                            .build();
                }
            }

            notaExistente.itens.clear();
            notaExistente.itens.addAll(notaF.itens);
            notaExistente.persist();

            return Response.status(Response.Status.OK).entity(notaExistente).build();
        } catch (WebApplicationException e) {
            return Response.status(e.getResponse().getStatus()).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response excluirNotaFiscal(@PathParam("id") UUID id) {
        try {

            NotaFiscal nota = NotaFiscal.findById(id);
            if (nota == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Nota fiscal não encontrada com o ID: " + id)
                        .build();
            }

            if (nota.itens != null) {
                for (ItemNota item : nota.itens) {
                    item.delete();
                }
            }
            nota.delete();
            return Response.status(Response.Status.NO_CONTENT).build();

        } catch (WebApplicationException e) {
            return Response.status(e.getResponse().getStatus()).entity(e.getMessage()).build();
        }
    }
}
