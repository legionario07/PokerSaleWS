/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pokerws;

import br.com.pokerws.dao.IDao;
import br.com.pokerws.dao.JogadorPartidaDao;
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
@Path("/jogadorpartida")
public class JogadorPartidaWS {

    private Gson gson;
    private JogadorPartidaDao jogadorPartidaDao;
    private IDao dao;
    private ServiceUtil serviceUtil;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PokerWS
     */
    public JogadorPartidaWS() {
        jogadorPartidaDao = new JogadorPartidaDao();
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

        partida.setId(jogadorPartidaDao.save(partida));
        gson = new Gson();

        return gson.toJson(partida.getJogadores().get(0).getDespesa().getId());
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
    public String update(Partida partida) {

        try {

            jogadorPartidaDao.edite(partida);
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
        return gson.toJson(jogadorPartidaDao.delete(partida));

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
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new GsonUtil(sdf));

        Gson gson = gsonBuilder.create();

        return gson.toJson(jogadorPartidaDao.find(partida));
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

        Gson gson = new Gson();
        return gson.toJson(jogadorPartidaDao.findAll());
    }

    /**
     *
     * @param partida
     * @return
     */
    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/updateByJogador")
    public String updateByJogador(Partida partida) {

        try {

            jogadorPartidaDao.editeByJogador(partida);
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
    @Path("/deleteByPartida/{id}")
    public String deleteByPartida(@PathParam("id") Integer id) {

        Partida partida = new Partida(id);

        gson = new Gson();
        return gson.toJson(jogadorPartidaDao.deleteByPartida(partida));

    }

}
