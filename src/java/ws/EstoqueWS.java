
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dao.EstoqueDAO;
import java.lang.reflect.Type;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import vo.EstoqueVO;
import vo.UsuarioVO;

/**
 * REST Web Service
 *
 * @author gyova
 */
@Path("estoque")
public class EstoqueWS {

    Gson gson = new Gson();
    Type estoqueType = new TypeToken<EstoqueVO>() {
    }.getType();
    EstoqueDAO estoqueDAO = new EstoqueDAO();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of EstoqueWS
     */
    public EstoqueWS() {
    }

    /**
     * Retrieves representation of an instance of ws.EstoqueWS
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        return "estoque";
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("pesquisaEstoquea/")
    public String pesquisaEstoqueA(String content) {
        EstoqueVO estoqueVO = gson.fromJson(content, estoqueType);
        return gson.toJson(estoqueDAO.pesquisaEstoqueA(estoqueVO));
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("pesquisaEstoquead/")
    public String pesquisaEstoqueAD(String content) {
        EstoqueVO estoqueVO = gson.fromJson(content, estoqueType);
        return gson.toJson(estoqueDAO.pesquisaEstoqueAD(estoqueVO));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("dadosNosCampos/")
    public String dadosNosCampos(String content) {
        EstoqueVO estoqueVO = gson.fromJson(content, estoqueType);

        return gson.toJson(estoqueDAO.dadosNosCampos(estoqueVO));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("adcionarEstoque/")
    public String adcionarEstoque(String content) {
        EstoqueVO estoqueVO = gson.fromJson(content, estoqueType);

        return gson.toJson(estoqueDAO.adcionarEstoque(estoqueVO));
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("alterarEstoque/")
    public String alterarEstoque(String content) {
        EstoqueVO estoqueVO = gson.fromJson(content, estoqueType);

        return gson.toJson(estoqueDAO.alterarEstoque(estoqueVO));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("existerMaterialAtivadoInsert/")
    public String existerMaterialAtivado(String content) {
        EstoqueVO estoqueVO = gson.fromJson(content, estoqueType);

        return gson.toJson(estoqueDAO.existerMaterialAtivadoInsert(estoqueVO));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("existerMaterialDesativadoInsert/")
    public String existerMaterialDesativado(String content) {
        EstoqueVO estoqueVO = gson.fromJson(content, estoqueType);

        return gson.toJson(estoqueDAO.existerMaterialDesativadoInsert(estoqueVO));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("existerMaterialAtivadoUpdate/")
    public String existerMaterialAtivadoUpdate(String content) {
        EstoqueVO estoqueVO = gson.fromJson(content, estoqueType);

        return gson.toJson(estoqueDAO.existerMaterialAtivadoUpdate(estoqueVO));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("existerMaterialDesativadoUpdate/")
    public String existerMaterialDesativadoUpdate(String content) {
        EstoqueVO estoqueVO = gson.fromJson(content, estoqueType);

        return gson.toJson(estoqueDAO.existerMaterialDesativadoUpdate(estoqueVO));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("statusMaterial/")
    public String statusMaterial(String content) {
        EstoqueVO estoqueVO = gson.fromJson(content, estoqueType);

        return gson.toJson(estoqueDAO.statusMaterial(estoqueVO));
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("delete/{id}")
    public String delete(@PathParam("id") int id) {
        EstoqueVO estoqueVO = new EstoqueVO();
        estoqueVO.setCodigo(id);
        return gson.toJson(estoqueDAO.delete(estoqueVO));
    }

    /**
     * PUT method for updating or creating an instance of EstoqueWS
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
