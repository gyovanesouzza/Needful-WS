/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dao.ChamadosDAO;
import dao.TesteDAO;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import vo.ChamadosVO;

/**
 * REST Web Service
 *
 * @author aluno14
 */
@Path("TesteRESTful")
public class TesteRESTfulResource {

    Gson gson = new Gson();
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of TesteRESTfulResource
     */
    public TesteRESTfulResource() {
    }

    /**
     * Retrieves representation of an instance of ws.TesteRESTfulResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {

        return "r";
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("listagemfiltropost/")
    public String getListagemDeChamadoFiltraPost(String filtro) {
        Type chamadoType = new TypeToken<ChamadosVO>() {
        }.getType();
        ChamadosVO chamadosVO = gson.fromJson(filtro, chamadoType);

        List<ChamadosVO> lista = new ArrayList<>();
        lista = new TesteDAO().lista(chamadosVO);
        return gson.toJson(lista);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("up/")
    public String upd(String filtro
    ) {
        Type chamadoType = new TypeToken<ChamadosVO>() {
        }.getType();
        ChamadosVO chamadosVO = gson.fromJson(filtro, chamadoType);

        return gson.toJson(new TesteDAO().alterarChamadoInstalacao(chamadosVO));
    }

    /**
     * PUT method for updating or creating an instance of TesteRESTfulResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content
    ) {
    }
}
