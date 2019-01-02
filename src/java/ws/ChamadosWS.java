/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import dao.ChamadosDAO;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import vo.ChamadosVO;
import java.lang.reflect.Type;
import com.google.gson.reflect.*;
import java.text.DateFormat;
import java.util.Date;
import vo.GraficoVO;

/**
 * REST Web Service
 *
 * @author Gyovane Souzza
 */
@Path("chamados")
public class ChamadosWS {

    ChamadosDAO chamadosDAO = new ChamadosDAO();
    Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy hh:mm:ss").create();
    Type chamadoType = new TypeToken<ChamadosVO>() {
    }.getType();
    @Context
    private UriInfo context;

    public ChamadosWS() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    public String getJson() {
        List<ChamadosVO> lista = chamadosDAO.listagemMobile();
        return gson.toJson(lista);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("listagemfiltropost/")
    public String ListagemDeChamadoFiltraPost(String filtro) {
        Gson gson = new Gson();
        List<ChamadosVO> lista = new ArrayList<>();
        lista = chamadosDAO.listagemMobile();
        return gson.toJson(lista);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("listagem/")
    public String listagemdeChamadosDesktop(String filtro) {

        ChamadosVO chamadosVO = gson.fromJson(filtro, chamadoType);

        List<ChamadosVO> lista = chamadosDAO.listagemDesktop(chamadosVO);

        return gson.toJson(lista);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("carregarTelaEdicaoInstalacao/")
    public String carregarTelaEdicaoInstalacao(String content) {
        ChamadosVO chamadosVO = (ChamadosVO) gson.fromJson(content, chamadoType);
        return gson.toJson(chamadosDAO.carregarTelaEdicaoInstalacao(chamadosVO));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("carregarTelaEdicaoManutencao/")
    public String carregarTelaEdicaoManuntencao(String content) {
        ChamadosVO chamadosVO = (ChamadosVO) gson.fromJson(content, chamadoType);
        return gson.toJson(chamadosDAO.carregarTelaEdicaoManuntencao(chamadosVO));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("carregarDadosGrafico/")
    public List<GraficoVO> carregarDadosGrafico() {
        return chamadosDAO.carregarDadosGrafico();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("pesquisaTipoDeChamado/")
    public String pesquisaTipoDeChamado(String content) {
        ChamadosVO chamadosVO = (ChamadosVO) gson.fromJson(content, chamadoType);
        return chamadosDAO.pesquisaTipoDeChamado(chamadosVO);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Path("carregarTipoChamado/")
    public String carregarTipoChamado() {

        return gson.toJson(chamadosDAO.carregarTipoChamado());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Path("listagemdeChamadosMobile/")
    public String listagemdeChamadosMobile() {

        return gson.toJson(chamadosDAO.listagemMobile());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("abreChamadomanutencao/")
    public String abreChamadoManu(String content) {
        ChamadosVO chamadosVO = (ChamadosVO) gson.fromJson(content, chamadoType);
        return gson.toJson(chamadosDAO.abreChamadoManuntecao(chamadosVO));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("abreChamadoInstalacao/")
    public String abreChamadoInstalacao(String content) {
        ChamadosVO chamadosVO = (ChamadosVO) gson.fromJson(content, chamadoType);
        return gson.toJson(chamadosDAO.abreChamadoInstalacao(chamadosVO));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("abreChamadoInstalaca/")
    public String abreChamadoInstalac(String content) {

        ChamadosVO chamadosVO = (ChamadosVO) gson.fromJson(content, ChamadosVO.class);
        return gson.toJson(chamadosDAO.abreChamadoInstalacao(chamadosVO));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("listadeAdm/")
    public String listadeAdm(String content) {

        ChamadosVO chamadosVO = (ChamadosVO) gson.fromJson(content, ChamadosVO.class);
        return gson.toJson(chamadosDAO.abreChamadoInstalacao(chamadosVO));
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("fecharChamadomanutencao/")
    public String fecharChamadoManu(String content) {
        ChamadosVO chamadosVO = (ChamadosVO) gson.fromJson(content, chamadoType);
        return gson.toJson(chamadosDAO.fecharChamado(chamadosVO));
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("alteraChamadomanutencao/")
    public String alteraChamadomanuntecao(String content) {
        ChamadosVO chamadosVO = (ChamadosVO) gson.fromJson(content, chamadoType);
        return gson.toJson(chamadosDAO.alterarChamadoManuntencao(chamadosVO));
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("alteraChamadoinstalacao/")
    public String alteraChamadoInstalacao(String content) {
        ChamadosVO chamadosVO = (ChamadosVO) gson.fromJson(content, chamadoType);
        return gson.toJson(chamadosDAO.alterarChamadoInstalacao(chamadosVO));
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON + ";charset=ISO-8859-1")
    @Path("alterarStatusChamado/")
    public String alterarStatusChamado(String content) {
        ChamadosVO chamadosVO = (ChamadosVO) gson.fromJson(content, chamadoType);
        return gson.toJson(chamadosDAO.alterarStatusChamado(chamadosVO));
    }

}
