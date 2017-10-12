/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pokerws;

import br.com.pokerws.dao.IDao;
import br.com.pokerws.dao.JogadorDao;
import br.com.pokerws.entity.Jogador;
import br.com.pokerws.entity.Partida;
import br.com.pokerws.util.ServiceUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
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
@Path("/jogador")
public class JogadorWS {

    private Gson gson;
    private JogadorDao jogadorDao;
    private IDao dao;
    private ServiceUtil serviceUtil;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PokerWS
     */
    public JogadorWS() {
        jogadorDao = new JogadorDao();
        serviceUtil = new ServiceUtil();
    }

    /**
     *
     * @param jogador
     * @return
     */
    @POST
    @Path("/save")
    @Consumes("application/json")
    @Produces("application/json")
    public String save(Jogador jogador) {


        jogador.setId(jogadorDao.save(jogador));
        gson = new Gson();

        return gson.toJson(jogador.getId());
    }

    /**
     *
     * @param jogador
     * @return
     */
    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/update")
    public String update(Jogador jogador) {

        try {

            jogadorDao.edite(jogador);
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

        Jogador jogador = new Jogador(id);

        gson = new Gson();
        return gson.toJson(jogadorDao.delete(jogador));

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

        Jogador jogador = new Jogador(id);
        gson = new Gson();

        return gson.toJson(jogadorDao.find(jogador));
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

        gson = new Gson();
        return gson.toJson(jogadorDao.findAll());
    }

    /**
     *
     * @param jogador
     * @return
     */
    @POST
    @Path("/login")
    @Consumes("application/json")
    @Produces("application/json")
    public String login(String dados) {

       Jogador j = new Jogador();
        gson = new Gson();

        Type tipo = new TypeToken<Jogador>() {
        }.getType();

        j = gson.fromJson(dados, tipo);

        j = (Jogador) jogadorDao.login(j);
        System.out.println(j);
        gson = new Gson();

        return gson.toJson(j);
    }

    /**
     *
     * @param nome
     * @return
     */
    @GET
    @Path("/findbynome/{nome}")
    @Consumes("application/json")
    @Produces("application/json")
    public String findByNome(@PathParam("nome") String nome) {

        Jogador jogador = new Jogador();
        jogador.setNome(nome);
        gson = new Gson();

        return gson.toJson(jogadorDao.findByNome(jogador));
    }

    /**
     *
     * @param id
     * @return
     */
    @GET
    @Path("/findAllPartidasAnalitica/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public String findAllPartidasAnalitica(@PathParam("id") Integer id) {

        Jogador jogador = new Jogador(id);
        gson = new Gson();

        return gson.toJson(jogadorDao.findAllPartidasAnalitica(jogador));
    }

     /**
     *
     * @param id
     * @return
     */
    @GET
    @Path("/findLastPartida/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Partida findLastPartida(@PathParam("id") Integer id) {

        Jogador jogador = new Jogador(id);
        gson = new Gson();

        return (Partida) jogadorDao.findLastPartida(jogador);
    }
}
