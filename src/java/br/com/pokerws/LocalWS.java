/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pokerws;

import br.com.pokerws.dao.IDao;
import br.com.pokerws.dao.LocalDao;
import br.com.pokerws.entity.Local;
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
@Path("/local")
public class LocalWS {

    private Gson gson;
    private LocalDao localDao;
    private IDao dao;
    private ServiceUtil serviceUtil;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PokerWS
     */
    public LocalWS() {
        localDao = new LocalDao();
        serviceUtil= new ServiceUtil();
    }

   /**
    * 
    * @param local
    * @return 
    */
        @POST
    @Path("/save")
    @Consumes("application/json")
    @Produces("application/json")
    public String save(Local local) {

        local.setId(localDao.save(local));
        gson = new Gson();

        return gson.toJson(local.getId());
    }

    /**
     * 
     * @param local
     * @return 
     */
    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/update")
    public String update(Local local) {

        try {

            localDao.edite(local);
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
        
        Local local = new Local(id);

        gson = new Gson();
        return gson.toJson(localDao.delete(local));

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

        Local local = new Local(id);
        gson = new Gson();
        
        return gson.toJson(localDao.find(local));
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
        return gson.toJson(localDao.findAll());
    }

}
