/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dao.StatusDAO;
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
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import vo.StatusVO;
import vo.TecnicoVO;

/**
 * REST Web Service
 *
 * @author etecja
 */
@Path("status")
public class StatusWS {

    StatusDAO statusDAO = new StatusDAO();
    Gson gson = new Gson();
    Type statusType = new TypeToken<StatusVO>() {
    }.getType();

    public StatusWS(UriInfo context) {
        this.context = context;
    }

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of StatusResource
     */
    public StatusWS() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        String status = gson.toJson(statusDAO.readChamado());
        return status;
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("statusNoCampoEstoque/")
    public String statusNoCampoEstoque(String filtro) {

        StatusVO statusVO = gson.fromJson(filtro, statusType);

        return gson.toJson(statusDAO.statusNoCampoEstoque(statusVO));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("listagemStatusChamado/")
    public String statusNoCampoChamado(String filtro) {

        StatusVO statusVO = gson.fromJson(filtro, statusType);

        return gson.toJson(statusDAO.readChamado());
    }

     @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("listagemStatusChamadodesktop/")
    public String statusNoCampoChamadoDesktop(String filtro) {

        StatusVO statusVO = gson.fromJson(filtro, statusType);

        return gson.toJson(statusDAO.readChamadoDesktop());
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON )
    @Path("listagemStatusEstoque/")
    public String statusNoCampoEstoque() {


        return gson.toJson(statusDAO.readEstoque());
    }

}
