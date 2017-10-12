/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pokerws.consumirTest;

import br.com.pokerws.dao.ConnectionFactory;
import br.com.pokerws.dao.PerfilDao;
import br.com.pokerws.entity.EntidadeDominio;
import br.com.pokerws.entity.Perfil;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PauLinHo
 */
public class ConnectionTest {

    public static Connection connection = null;

    public static void main(String[] args) {
        
        connection = ConnectionFactory.getConnection();

        List<EntidadeDominio> lista = new ArrayList<EntidadeDominio>();
        lista = new PerfilDao().findAll();
        
        for(EntidadeDominio e : lista){
            
            if(e instanceof Perfil){
                System.out.println(e);
            }
        }
        
        Perfil p = new Perfil(3);
        new PerfilDao().delete(p);
            
        
        if (connection == null) {
            System.out.println("Erro ao conectar");
        } else {
            System.out.println("Conectado com sucesso");
        }
    }

}
