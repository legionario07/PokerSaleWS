/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pokerws;

import br.com.pokerws.dao.IDao;
import br.com.pokerws.dao.PerfilDao;
import br.com.pokerws.entity.Perfil;
import br.com.pokerws.util.ServiceUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
@Path("/perfil")
public class PerfilWS {

    private Gson gson;
    private PerfilDao perfilDao;
    private IDao dao;
    private ServiceUtil serviceUtil;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PokerWS
     */
    public PerfilWS() {
        perfilDao = new PerfilDao();
        serviceUtil = new ServiceUtil();
    }

    /**
     *
     * @param perfil
     * @return
     */
    @POST
    @Path("/save")
    @Consumes("application/json")
    @Produces("application/json")
    public String save(Perfil perfil) {


        System.out.println(perfil.getPerfil());
        perfil.setId(perfilDao.save(perfil));

        return gson.toJson(perfil.getId());
    }

    /**
     *
     * @param perfil
     * @return
     */
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/update")
    public String update(Perfil perfil) {

        try {
            perfilDao.edite(perfil);
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

        Perfil perfil = new Perfil(id);

        gson = new Gson();
        return gson.toJson(perfilDao.delete(perfil));

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

        Perfil perfil = new Perfil(id);
        gson = new Gson();

        return gson.toJson(perfilDao.find(perfil));
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
        return gson.toJson(perfilDao.findAll());
    }

}
