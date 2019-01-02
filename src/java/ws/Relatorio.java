/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import com.google.gson.Gson;
import dao.ChamadosDAO;
import dao.RelatorioDAO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import vo.ChamadosVO;

/**
 * REST Web Service
 *
 * @author GYOVANEPEREIRADESOUZ
 */
@Path("relatorio")
public class Relatorio {

    private RelatorioDAO relatorioDAO = new RelatorioDAO();
    @Context
    private UriInfo context;
    private Gson gson = new Gson();

    /**
     * Creates a new instance of Relatorio
     */
    public Relatorio() {
    }

    /**
     * Retrieves representation of an instance of ws.Relatorio
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("relatorioWS/")
    public String pesquisaTipoDeChamado(String json) {
        ChamadosVO chamadosVO = gson.fromJson(json, ChamadosVO.class);
        return gson.toJson(relatorioDAO.relatorioTeste());

    }

    /**
     * PUT method for updating or creating an instance of Relatorio
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
