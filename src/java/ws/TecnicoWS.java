/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dao.TecnicoDAO;
import java.lang.reflect.Type;
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
import vo.TecnicoVO;

/**
 * REST Web Service
 *
 * @author etecja
 */
@Path("tecnico")
public class TecnicoWS {

    TecnicoDAO tecnicoDAO = new TecnicoDAO();
    Gson gson = new Gson();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Tecnico
     */
    public TecnicoWS() {
    }

    /**
     * Retrieves representation of an instance of ws.Tecnico
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        return gson.toJson(tecnicoDAO.read());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("criarTecnico/")
    public boolean criarTecnico(String content) {
        Type tecnicoType = new TypeToken<TecnicoVO>() {
        }.getType();

        TecnicoVO tecnicoVO = (TecnicoVO) gson.fromJson(content, tecnicoType);

        return tecnicoDAO.create(tecnicoVO);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("updateTecnico/")
    public int updateTecnico(String content) {
         Type tecnicoType = new TypeToken<TecnicoVO>() {
        }.getType();

        TecnicoVO tecnicoVO = (TecnicoVO) gson.fromJson(content, tecnicoType);

        return  tecnicoDAO.update(tecnicoVO);
    }
}
