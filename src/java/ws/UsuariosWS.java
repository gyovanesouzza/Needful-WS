/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dao.UsuarioDAO;
import java.lang.reflect.Type;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import vo.ChamadosVO;
import vo.TecnicoVO;
import vo.UsuarioVO;

/**
 * REST Web Service
 *
 * @author gyova
 */
@Path("usuarios")
public class UsuariosWS {

    UsuarioDAO usuarioDAO = new UsuarioDAO();
    Gson gson = new Gson();
    Type usuarioType = new TypeToken<UsuarioVO>() {
    }.getType();
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of UsuariosWS
     */
    public UsuariosWS() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        List<UsuarioVO> lista = usuarioDAO.readTablea();
        return gson.toJson(lista);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("pesquisaUsuarioAD/")
    public String pesquisaUsuarioAD() {

        return gson.toJson(usuarioDAO.readTableAD());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("pesquisaUsuarioa/")
    public String pesquisaUsuarioa() {

        return gson.toJson(usuarioDAO.readTablea());
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("pesquisaUsuario/")
    public String pesquisaUsuario(String content) {
        UsuarioVO filtro = (UsuarioVO) gson.fromJson(content, usuarioType);

        UsuarioVO retorno = usuarioDAO.pesquisaUsuario(filtro);

        return gson.toJson(retorno);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("pesquisaTipoUsuario/")
    public String pesquisaTipoUsuarioa() {

        return gson.toJson(usuarioDAO.readTipoUsuario());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("criarusuario/")
    public boolean criarusuario(String content) {
        UsuarioVO usuarioVO = gson.fromJson(content, UsuarioVO.class);
        return usuarioDAO.create(usuarioVO);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("usuarioAD/")
    public boolean usuarioAD(String content) {
        UsuarioVO usuarioVO = gson.fromJson(content, UsuarioVO.class);
        return usuarioDAO.usuarioAD(usuarioVO);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("existeEmailLogin/")
    public String existeEmailLogin(String content) {
        UsuarioVO usuarioVO = (UsuarioVO) gson.fromJson(content, usuarioType);

        return gson.toJson(usuarioDAO.existeEmailLogin(usuarioVO));

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("listadeAdm/")
    public String listadeAdm(String content) {
        return gson.toJson(usuarioDAO.listaadm());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("existerContaa/")
    public String existerContaa(String content) {
        UsuarioVO usuarioVO = (UsuarioVO) gson.fromJson(content, usuarioType);

        return gson.toJson(usuarioDAO.existerContaa(usuarioVO));

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("existerContaAD/")
    public String existerContaAD(String content) {
        UsuarioVO usuarioVO = (UsuarioVO) gson.fromJson(content, usuarioType);

        return gson.toJson(usuarioDAO.existerContaAD(usuarioVO));

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("permissaoAlterar")
    public String permissaoAlterar(String content) {

        UsuarioVO usuarioVO = (UsuarioVO) gson.fromJson(content, usuarioType);

        return gson.toJson(usuarioDAO.permissaoAlterar(usuarioVO));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("checkLogin/")
    public String checkLogin(String content) {
        UsuarioVO usuarioVO = gson.fromJson(content, usuarioType);

        return gson.toJson(UsuarioDAO.checkLogin(usuarioVO));

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("permissaoDaAreaRestrita/")
    public String permissaoDaAreaRestrita(String content) {
        UsuarioVO usuarioVO = gson.fromJson(content, usuarioType);

        return gson.toJson(usuarioDAO.permissaoDaAreaRestrita(usuarioVO));

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("permissaoDeLogin/")
    public String permissaoDeLogin(String content) {
        UsuarioVO usuarioVO = gson.fromJson(content, usuarioType);

        return gson.toJson(usuarioDAO.permissaoDeLogin(usuarioVO));

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("buscarEmailLogin/")
    public String buscarEmail(String content) {
        UsuarioVO usuarioVO = gson.fromJson(content, UsuarioVO.class);

        return gson.toJson(usuarioDAO.buscarEmailLogin(usuarioVO));

    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("alterarSenha/")
    public String alterarSenha(String content) {
        UsuarioVO usuarioVO = gson.fromJson(content, UsuarioVO.class);
        return gson.toJson(usuarioDAO.alterarSenha(usuarioVO));
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("alterarconta/")
    public String alterarConta(String content) {
        UsuarioVO usuarioVO = gson.fromJson(content, UsuarioVO.class);
        return gson.toJson(usuarioDAO.alterarConta(usuarioVO));
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("statusConta/")
    public String statusConta(String content) {
        UsuarioVO usuarioVO = gson.fromJson(content, UsuarioVO.class);
        return gson.toJson(usuarioDAO.statusConta(usuarioVO));
    }
}
