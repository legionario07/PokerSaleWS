/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pokerws;

import br.com.pokerws.dao.IDao;
import br.com.pokerws.dao.LoggerDao;
import br.com.pokerws.util.GsonUtil;
import br.com.pokerws.util.ServiceUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Date;
import br.com.pokerws.util.Logger;
import java.text.SimpleDateFormat;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * REST Web Service
 *
 * @author PauLinHo
 */
@Path("/logger")
public class LoggerWS {

    private Gson gson;
    private LoggerDao loggerDao;
    private IDao dao;
    private ServiceUtil serviceUtil;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PokerWS
     */
    public LoggerWS() {
        loggerDao = new LoggerDao();
        serviceUtil = new ServiceUtil();
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
    public String save(String dados) {

        Type tipo = new TypeToken<Logger>() {
        }.getType();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new GsonUtil(sdf));

        Gson gson = gsonBuilder.create();

        Logger logger = new Logger();
        logger = gson.fromJson(dados, tipo);
        
        logger.setId(loggerDao.save(logger));
        gson = new Gson();

        return gson.toJson(logger.getId());
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

        return null;

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
        return gson.toJson(loggerDao.findAll());
    }

}
