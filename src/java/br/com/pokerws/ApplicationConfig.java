/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pokerws;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author PauLinHo
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(br.com.pokerws.ColocacaoWS.class);
        resources.add(br.com.pokerws.JogadorPartidaWS.class);
        resources.add(br.com.pokerws.JogadorWS.class);
        resources.add(br.com.pokerws.LocalWS.class);
        resources.add(br.com.pokerws.LoggerWS.class);
        resources.add(br.com.pokerws.PartidaWS.class);
        resources.add(br.com.pokerws.PerfilWS.class);
        resources.add(br.com.pokerws.PremiacaoWS.class);
        resources.add(br.com.pokerws.UsuarioWS.class);
    }
    
}
