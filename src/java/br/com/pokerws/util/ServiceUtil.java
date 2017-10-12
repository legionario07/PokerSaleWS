/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pokerws.util;

import br.com.pokerws.dao.ColocacaoDao;
import br.com.pokerws.dao.IDao;
import br.com.pokerws.dao.JogadorDao;
import br.com.pokerws.dao.LocalDao;
import br.com.pokerws.dao.PartidaDao;
import br.com.pokerws.dao.PerfilDao;
import br.com.pokerws.dao.PremiacaoDao;
import br.com.pokerws.dao.UsuarioDao;
import br.com.pokerws.entity.Colocacao;
import br.com.pokerws.entity.Despesa;
import br.com.pokerws.entity.EntidadeDominio;
import br.com.pokerws.entity.Jogador;
import br.com.pokerws.entity.Local;
import br.com.pokerws.entity.Partida;
import br.com.pokerws.entity.Perfil;
import br.com.pokerws.entity.Premiacao;
import br.com.pokerws.entity.Usuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author PauLinHo
 */
public class ServiceUtil {

    private Map<String, IDao> mapDao;
    private Map<String, EntidadeDominio> mapEntity;
    private List<EntidadeDominio> classes;

    public ServiceUtil() {

        mapDao = new HashMap<String, IDao>();
        mapEntity = new HashMap<String, EntidadeDominio>();
        classes = new ArrayList<EntidadeDominio>();

        mapDao.put(Colocacao.class.getSimpleName(), new ColocacaoDao());
        mapDao.put(Jogador.class.getSimpleName(), new JogadorDao());
        mapDao.put(Local.class.getSimpleName(), new LocalDao());
        mapDao.put(Partida.class.getSimpleName(), new PartidaDao());
        mapDao.put(Perfil.class.getSimpleName(), new PerfilDao());
        mapDao.put(Premiacao.class.getSimpleName(), new PremiacaoDao());
        mapDao.put(Usuario.class.getSimpleName(), new UsuarioDao());

        mapEntity.put(Colocacao.class.getSimpleName(), new Colocacao());
        mapEntity.put(Jogador.class.getSimpleName(), new Jogador());
        mapEntity.put(Local.class.getSimpleName(), new Local());
        mapEntity.put(Despesa.class.getSimpleName(), new Despesa());
        mapEntity.put(Partida.class.getSimpleName(), new Partida());
        mapEntity.put(Perfil.class.getSimpleName(), new Perfil());
        mapEntity.put(Premiacao.class.getSimpleName(), new Premiacao());
        mapEntity.put(Usuario.class.getSimpleName(), new Usuario());

        classes.add(new Colocacao());
        classes.add(new Jogador());
        classes.add(new Local());
        classes.add(new Despesa());
        classes.add(new Partida());
        classes.add(new Perfil());
        classes.add(new Premiacao());
        classes.add(new Usuario());

    }

    /**
     * @return the mapDao
     */
    public Map<String, IDao> getMapDao() {
        return mapDao;
    }

    /**
     * @param mapDao the mapDao to set
     */
    public void setMapDao(Map<String, IDao> mapDao) {
        this.mapDao = mapDao;
    }

    /**
     * @return the mapEntity
     */
    public Map<String, EntidadeDominio> getMapEntity() {
        return mapEntity;
    }

    /**
     * @param mapEntity the mapEntity to set
     */
    public void setMapEntity(Map<String, EntidadeDominio> mapEntity) {
        this.mapEntity = mapEntity;
    }

    /**
     * @return the classes
     */
    public List<EntidadeDominio> getClasses() {
        return classes;
    }

    /**
     * @param classes the classes to set
     */
    public void setClasses(List<EntidadeDominio> classes) {
        this.classes = classes;
    }


}
