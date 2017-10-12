/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pokerws.consumirTest;

import br.com.pokerws.entity.Jogador;

/**
 *
 * @author PauLinHo
 */
public class JogadorTest implements ITest {

    private static final String URL_SAVE = "http://localhost:8080/PokerWS/webresources/jogador/save";
    private static final String URL_DELETE = "http://localhost:8080/PokerWS/webresources/jogador/delete";
    private static final String URL_FIND = "http://localhost:8080/PokerWS/webresources/jogador/find";
    private static final String URL_FIND_ALL = "http://localhost:8080/PokerWS/webresources/jogador/findAll";
    private static final String URL_UPDATE = "http://localhost:8080/PokerWS/webresources/jogador/update";
    private static final String URL_LOGIN = "http://localhost:8080/PokerWS/webresources/jogador/login";
    private static final String URL_FIND_NOME = "http://localhost:8080/PokerWS/webresources/jogador/findbynome";
    private static final String URL_FIND_ALL_PARTIDA_ANALITICA = "http://localhost:8080/PokerWS/webresources/jogador/findAllPartidasAnalitica";
    private static final String URL_FIND_ALL_PARTIDAS = "http://localhost:8080/PokerWS/webresources/jogadorpartida/findAllPartidas";
    private static final String URL_FIND_BY_PARTIDA = "http://localhost:8080/PokerWS/webresources/jogadorpartida/findByPartida";

    private CrudWSTest crudWSTest;

    public JogadorTest() {

        crudWSTest = new CrudWSTest();

    }

    public static void main(String[] args) {

        JogadorTest jogadorTest = new JogadorTest();
        jogadorTest.findByPartidaTest();

    }

    @Override
    public void saveTest() {

        Jogador jogador = new Jogador();
        jogador.setNome("Teste");

        crudWSTest.saveTest(URL_SAVE, jogador);

    }

    @Override
    public void updateTest() {

        Jogador jogador = new Jogador();
        jogador.setId(2);
        jogador.setNome("Teste 2");

        crudWSTest.updateTest(URL_UPDATE, jogador);
    }

    @Override
    public void deleteTest() {

        Jogador jogador = new Jogador(3);

        crudWSTest.deleteTest(URL_DELETE, jogador.getId());

    }

    @Override
    public void findTest() {
        Jogador jogador = new Jogador(1);

        crudWSTest.findTest(URL_FIND, jogador.getId());

    }

    @Override
    public void findAllTest() {
        crudWSTest.findAllTest(URL_FIND_ALL);
    }

    public void loginTest() {
        Jogador jogador = new Jogador();
        jogador.getUsuario().setLogin("PAULINHO");
        jogador.getUsuario().setSenha("123");

        crudWSTest.login(URL_LOGIN, jogador);
    }

    public void findByNomeTest() {
        Jogador jogador = new Jogador();
        jogador.setNome("PAULINHO");

        crudWSTest.findByNomeTest(URL_FIND_NOME, jogador.getNome());
    }

    public void findAllPartidasAnaliticaTest() {
        Jogador jogador = new Jogador();
        jogador.setId(1);

        crudWSTest.findAllPartidasAnalitica(URL_FIND_ALL_PARTIDA_ANALITICA, jogador.getId());
    }
    
    public void findAllPartidasTest() {
        crudWSTest.findAllPartidasTest(URL_FIND_ALL_PARTIDAS);
    }
    
    public void findByPartidaTest() {
        Jogador jogador = new Jogador();
        jogador.setId(24);

        crudWSTest.findByPartidaTest(URL_FIND_BY_PARTIDA, jogador.getId());
    }

}
