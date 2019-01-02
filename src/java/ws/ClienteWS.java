/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import com.google.gson.Gson;
import java.lang.reflect.Type;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import vo.ClientVO;
import com.google.gson.reflect.*;
import dao.ClienteDAO;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;

/**
 * REST Web Service
 *
 * @author etecja
 */
@Path("cliente")
public class ClienteWS {

    Type clienteType = new TypeToken<ClientVO>() {
    }.getType();
    Gson gson = new Gson();
    ClienteDAO clienteDAO = new ClienteDAO();
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ClienteResource
     */
    public ClienteWS() {
    }

    /**
     * Retrieves representation of an instance of ws.ClienteResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("listagemCliente/")
    public String listagemCliente(String content) {

        ClientVO clientVO = gson.fromJson(content, clienteType);

        return gson.toJson(clienteDAO.pesquisaCliente(clientVO));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("passarDados/")
    public String passarDados(String content) {

        ClientVO clientVO = new ClientVO();

        clientVO = gson.fromJson(content, clienteType);
        return gson.toJson(clienteDAO.passarDados(clientVO));
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
