package crudJava17.controller;

import crudJava17.model.Produto;
import crudJava17.service.ProdutoService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;

@Path("/produtos")
@Tag(name = "produto")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutoController {

    @Inject
    ProdutoService produtoService;

    @GET
    public List<Produto> listar() {
        return produtoService.listar();
    }

    @GET
    @Path("/{id}")
    @Operation(description = "Busca produto por ID", summary = "Busca produto pelo id cadastrado no sistema")
    public Response buscarPorId(@PathParam("id") UUID id) {
        try {
            Produto produto = produtoService.buscarPorId(id);
            if (produto != null) {
                return Response.ok(produto).build();
            }
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (WebApplicationException e) {
            return Response.status(e.getResponse().getStatus()).entity(e.getMessage()).build();
        }
    }

    @POST
    public Response salvar(Produto produto) {
        try {
            Produto produtoSalvo = produtoService.salvar(produto);
            return Response.status(Response.Status.CREATED).entity(produtoSalvo).build();
        } catch (WebApplicationException e) {
            return Response.status(e.getResponse().getStatus()).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response editar(@PathParam("id") UUID id, Produto produto) {
        try {
            Produto produtoEditado = produtoService.editar(id, produto);
            if (produtoEditado != null) {
                return Response.ok(produtoEditado).build();
            }
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (WebApplicationException e) {
            return Response.status(e.getResponse().getStatus()).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response excluir(@PathParam("id") UUID id) {
        try {
            boolean sucesso = produtoService.excluir(id);

            if (sucesso) {
                return Response.noContent().build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("Produto não pode ser excluído. Esta vinculado a uma nota fiscal.").build();
        } catch (WebApplicationException e) {
            return Response.status(e.getResponse().getStatus()).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/busca")
    public List<Produto> buscarProdutos(@QueryParam("descricao") String descricao) {
        if (descricao != null && !descricao.isEmpty()) {
            return Produto.find("descricao like ?1", "%" + descricao + "%").list();
        } else {
            return Produto.listAll();
        }
    }
}
