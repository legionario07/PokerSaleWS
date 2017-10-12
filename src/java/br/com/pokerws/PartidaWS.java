/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pokerws;

import br.com.pokerws.dao.IDao;
import br.com.pokerws.dao.PartidaDao;
import br.com.pokerws.entity.Partida;
import br.com.pokerws.util.GsonUtil;
import br.com.pokerws.util.ServiceUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;

/**
 * REST Web Service
 *
 * @author PauLinHo
 */
@Path("/partida")
public class PartidaWS {

    private Gson gson;
    private PartidaDao partidaDao;
    private IDao dao;
    private ServiceUtil serviceUtil;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PokerWS
     */
    public PartidaWS() {
        partidaDao = new PartidaDao();
        serviceUtil = new ServiceUtil();
    }

    /**
     *
     * @param partida
     * @return
     */
    @POST
    @Path("/save")
    @Consumes("application/json")
    @Produces("application/json")
    public String save(String dados) {

        Type tipo = new TypeToken<Partida>() {
        }.getType();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new GsonUtil(sdf));

        Gson gson = gsonBuilder.create();

        Partida partida = new Partida();
        partida = gson.fromJson(dados, tipo);

        partida.setId(partidaDao.save(partida));
        gson = new Gson();

        return gson.toJson(partida.getId());
    }

    /**
     *
     * @param partida
     * @return
     */
    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/update")
    public String update(String dados) {

        try {

            Type tipo = new TypeToken<Partida>() {
            }.getType();

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Date.class, new GsonUtil(sdf));

            Gson gson = gsonBuilder.create();

            Partida partida = new Partida();
            partida = gson.fromJson(dados, tipo);

            partidaDao.edite(partida);
            gson = new Gson();

            return gson.toJson("Registro alterado com sucesso!");

        } catch (Exception e) {

            return gson.toJson("Erro ao alterar o registro " + e.getMessage());

        }

    }

    /**
     *
     * @param id
     * @return
     */
    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/delete/{id}")
    public String delete(@PathParam("id") Integer id) {

        Partida partida = new Partida(id);

        gson = new Gson();
        return gson.toJson(partidaDao.delete(partida));

    }

    /**
     *
     * @param id
     * @return
     */
    @GET
    @Path("/find/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public String find(@PathParam("id") Integer id) {

        Partida partida = new Partida(id);
        gson = new Gson();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new GsonUtil(sdf));

        Gson gson = gsonBuilder.create();

        return gson.toJson(partidaDao.find(partida));
    }

    /**
     *
     * @return
     */
    @GET
    @Path("/findAll/")
    @Consumes("application/json")
    @Produces("application/json")
    public String findAll() {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new GsonUtil(sdf));

        Gson gson = gsonBuilder.create();
        gson = new Gson();
        return gson.toJson(partidaDao.findAll());
    }

}
