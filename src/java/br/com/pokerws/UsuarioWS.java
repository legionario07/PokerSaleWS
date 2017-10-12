/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pokerws;

import br.com.pokerws.dao.IDao;
import br.com.pokerws.dao.UsuarioDao;
import br.com.pokerws.entity.Usuario;
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
@Path("/usuario")
public class UsuarioWS {

    private Gson gson;
    private UsuarioDao usuarioDao;
    private IDao dao;
    private ServiceUtil serviceUtil;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PokerWS
     */
    public UsuarioWS() {
        usuarioDao = new UsuarioDao();
        serviceUtil= new ServiceUtil();
    }

   /**
    * 
    * @param usuario
    * @return 
    */
        @POST
    @Path("/save")
    @Consumes("application/json")
    @Produces("application/json")
    public String save(Usuario usuario) {

        usuario.setId(usuarioDao.save(usuario));
        gson = new Gson();

        return gson.toJson(usuario.getId());
    }

    /**
     * 
     * @param usuario
     * @return 
     */
    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/update")
    public String update(Usuario usuario) {

        try {

            usuarioDao.edite(usuario);
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
        
        Usuario usuario = new Usuario(id);

        gson = new Gson();
        return gson.toJson(usuarioDao.delete(usuario));

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

        Usuario usuario = new Usuario(id);
        gson = new Gson();
        
        return gson.toJson(usuarioDao.find(usuario));
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
        return gson.toJson(usuarioDao.findAll());
    }
    
        @PUT
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/updateNoKey")
    public String updateNoKey(Usuario usuario) {

        try {

            usuarioDao.editeNoKey(usuario);
            gson = new Gson();

            return gson.toJson("Registro alterado com sucesso!");

        } catch (Exception e) {

            return gson.toJson("Erro ao alterar o registro " + e.getMessage());

        }

    }

}
