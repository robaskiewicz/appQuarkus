package crudJava17.controller;

import crudJava17.model.Fornecedor;
import crudJava17.service.FornecedorService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

@Path("/fornecedores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FornecedorController {

    @Inject
    FornecedorService fornecedorService;

    @GET
    public List<Fornecedor> listar() {
        return fornecedorService.listar();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") UUID id) {
        try {
            Fornecedor fornecedor = fornecedorService.buscarPorId(id);
            if (fornecedor != null) {
                return Response.ok(fornecedor).build();
            }
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (WebApplicationException e) {
            return Response.status(e.getResponse().getStatus()).entity(e.getMessage()).build();
        }
    }

    @POST
    public Response salvar(Fornecedor fornecedor) {
        try {
            Fornecedor fornecedorSalvo = fornecedorService.salvar(fornecedor);
            return Response.status(Response.Status.CREATED).entity(fornecedorSalvo).build();

        } catch (WebApplicationException e) {
            return Response.status(e.getResponse().getStatus()).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response editar(@PathParam("id") UUID id, Fornecedor fornecedor) {
        try {
            Fornecedor fornecedorEditado = fornecedorService.editar(id, fornecedor);
            if (fornecedorEditado != null) {
                return Response.ok(fornecedorEditado).build();
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
            boolean sucesso = fornecedorService.excluir(id);

            if (sucesso) {
                return Response.noContent().build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("Fornecedor não pode ser excluído. Verifique se há vínculos com notas fiscais.").build();
        } catch (WebApplicationException e) {
            return Response.status(e.getResponse().getStatus()).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/busca")
    public List<Fornecedor> buscarFornecedores(@QueryParam("razaosocial") String razaoSocial) {
        if (razaoSocial != null && !razaoSocial.isEmpty()) {
            return Fornecedor.find("razaoSocial like ?1", "%" + razaoSocial + "%").list();
        } else {
            return Fornecedor.listAll();
        }
    }
}
