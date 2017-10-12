/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pokerws;

import br.com.pokerws.dao.IDao;
import br.com.pokerws.dao.ColocacaoDao;
import br.com.pokerws.entity.Colocacao;
import br.com.pokerws.util.ServiceUtil;
import com.google.gson.Gson;
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
@Path("/colocacao")
public class ColocacaoWS {

    private Gson gson;
    private ColocacaoDao colocacaoDao;
    private IDao dao;
    private ServiceUtil serviceUtil;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PokerWS
     */
    public ColocacaoWS() {
        colocacaoDao = new ColocacaoDao();
        serviceUtil= new ServiceUtil();
    }

   /**
    * 
    * @param colocacao
    * @return 
    */
    @POST
    @Path("/save")
    @Consumes("application/json")
    @Produces("application/json")
    public String save(Colocacao colocacao) {

        colocacao.setId(colocacaoDao.save(colocacao));
        gson = new Gson();

        return gson.toJson(colocacao.getId());
    }

    /**
     * 
     * @param colocacao
     * @return 
     */
    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/update")
    public String update(Colocacao colocacao) {

        try {

            colocacaoDao.edite(colocacao);
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
        
        Colocacao colocacao = new Colocacao(id);

        gson = new Gson();
        return gson.toJson(colocacaoDao.delete(colocacao));

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

        Colocacao colocacao = new Colocacao(id);
        gson = new Gson();
        
        return gson.toJson(colocacaoDao.find(colocacao));
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
        return gson.toJson(colocacaoDao.findAll());
    }

}
