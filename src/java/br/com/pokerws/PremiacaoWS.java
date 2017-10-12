/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pokerws;

import br.com.pokerws.dao.IDao;
import br.com.pokerws.dao.PremiacaoDao;
import br.com.pokerws.entity.Premiacao;
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
@Path("/premiacao")
public class PremiacaoWS {

    private Gson gson;
    private PremiacaoDao premiacaoDao;
    private IDao dao;
    private ServiceUtil serviceUtil;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PokerWS
     */
    public PremiacaoWS() {
        premiacaoDao = new PremiacaoDao();
        serviceUtil= new ServiceUtil();
    }

   /**
    * 
    * @param premiacao
    * @return 
    */
        @POST
    @Path("/save")
    @Consumes("application/json")
    @Produces("application/json")
    public String save(Premiacao premiacao) {

        
        
        premiacao.setId(premiacaoDao.save(premiacao));
        gson = new Gson();

        return gson.toJson(premiacao.getId());
    }

    /**
     * 
     * @param premiacao
     * @return 
     */
    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/update")
    public String update(Premiacao premiacao) {

        try {

            premiacaoDao.edite(premiacao);
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
        
        Premiacao premiacao = new Premiacao(id);

        gson = new Gson();
        return gson.toJson(premiacaoDao.delete(premiacao));

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

        Premiacao premiacao = new Premiacao(id);
        gson = new Gson();
        
        return gson.toJson(premiacaoDao.find(premiacao));
    }
    
    

    /**
     * 
     * @return 
     */
    @GET
    @Path("/findAll/")
    @Consumes("application/json")
    @Produces("application/json")
    public String findAll(){

        gson = new Gson();
        return gson.toJson(premiacaoDao.findAll());
    }

}
