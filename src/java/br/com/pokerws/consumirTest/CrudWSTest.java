/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pokerws.consumirTest;

import br.com.pokerws.entity.EntidadeDominio;
import br.com.pokerws.entity.Jogador;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class CrudWSTest {

    StringBuffer url = null;

    public void saveTest(String URL_SAVE, EntidadeDominio entidade) {
        Client client = Client.create();
        WebResource webResource = client.resource(URL_SAVE);

        
        ClientResponse response = webResource.type("application/json").post(ClientResponse.class, entidade);
        String output = response.getEntity(String.class);
        System.out.print(output);
    }

    public void findTest(String URL_FIND, Integer id) {
        Client client = Client.create();

        url = new StringBuffer();
        url.append(URL_FIND);
        url.append("/");
        url.append(id);

        WebResource webResource = client.resource(url.toString());

        ClientResponse response = webResource.type("application/json").get(ClientResponse.class);
        String output = response.getEntity(String.class);
        System.out.print(output);
    }

    public void findAllTest(String URL_FIND_ALL) {
        Client client = Client.create();
        WebResource webResource = client.resource(URL_FIND_ALL);

        ClientResponse response = webResource.type("application/json").get(ClientResponse.class);
        String output = response.getEntity(String.class);
        System.out.print(output);
    }

    public void deleteTest(String URL_DELETE, Integer id) {
        Client client = Client.create();

        url = new StringBuffer();
        url.append(URL_DELETE);
        url.append("/");
        url.append(id);

        WebResource webResource = client.resource(url.toString());

        ClientResponse response = webResource.type("application/json").get(ClientResponse.class);
        String output = response.getEntity(String.class);
        System.out.print(output);
    }

    public void updateTest(String URL_UPDATE, EntidadeDominio entidade) {
        Client client = Client.create();

        WebResource webResource = client.resource(URL_UPDATE);

        ClientResponse response = webResource.type("application/json").put(ClientResponse.class, entidade);
        String output = response.getEntity(String.class);
        System.out.print(output);
    }

    void login(String URL_LOGIN, EntidadeDominio entidade) {

        Client client = Client.create();
        WebResource webResource = client.resource(URL_LOGIN);

        ClientResponse response = webResource.type("application/json").post(ClientResponse.class, entidade);
        String output = response.getEntity(String.class);

        Gson gson = new Gson();
        Type jogadorType = new TypeToken<Jogador>() {
        }.getType();

        Jogador jogador = new Jogador();
        jogador = gson.fromJson(output, jogadorType);

        System.out.print(jogador);

    }

    public void findByNomeTest(String URL_FIND_BY_NOME, String nome) {
        Client client = Client.create();

        url = new StringBuffer();
        url.append(URL_FIND_BY_NOME);
        url.append("/");
        url.append(nome);

        WebResource webResource = client.resource(url.toString());

        ClientResponse response = webResource.type("application/json").get(ClientResponse.class);
        String output = response.getEntity(String.class);
        System.out.print(output);
    }

    public void findAllPartidasAnalitica(String URL, Integer id) {
        Client client = Client.create();

        url = new StringBuffer();
        url.append(URL);
        url.append("/");
        url.append(id);

        WebResource webResource = client.resource(url.toString());

        ClientResponse response = webResource.type("application/json").get(ClientResponse.class);
        String output = response.getEntity(String.class);
        System.out.print(output);
    }

    public void updateByJogadorTest(String URL_UPDATE, EntidadeDominio entidade) {
        Client client = Client.create();

        WebResource webResource = client.resource(URL_UPDATE);

        ClientResponse response = webResource.type("application/json").put(ClientResponse.class, entidade);
        String output = response.getEntity(String.class);
        System.out.print(output);
    }

    public void findAllPartidasTest(String URL_FIND_ALL) {
        Client client = Client.create();
        WebResource webResource = client.resource(URL_FIND_ALL);

        ClientResponse response = webResource.type("application/json").get(ClientResponse.class);
        String output = response.getEntity(String.class);
        System.out.print(output);
    }

    public void findByPartidaTest(String URL, Integer id) {
        Client client = Client.create();

        url = new StringBuffer();
        url.append(URL);
        url.append("/");
        url.append(id);

        WebResource webResource = client.resource(url.toString());

        ClientResponse response = webResource.type("application/json").get(ClientResponse.class);
        String output = response.getEntity(String.class);
        System.out.print(output);
    }

    public void deleteByPartidaTest(String URL_DELETE, Integer id) {
        Client client = Client.create();

        url = new StringBuffer();
        url.append(URL_DELETE);
        url.append("/");
        url.append(id);

        WebResource webResource = client.resource(url.toString());

        ClientResponse response = webResource.type("application/json").get(ClientResponse.class);
        String output = response.getEntity(String.class);
        System.out.print(output);
    }
    
    public void pesquisarPremiacao(String URL_FIND_ALL) {
        Client client = Client.create();
        WebResource webResource = client.resource(URL_FIND_ALL);

        ClientResponse response = webResource.type("application/json").get(ClientResponse.class);
        String output = response.getEntity(String.class);
        System.out.print(output);
    }
    
    public void pesquisarPorPontuacao(String URL, Integer id) {
        Client client = Client.create();

        url = new StringBuffer();
        url.append(URL);
        url.append("/");
        url.append(id);

        WebResource webResource = client.resource(url.toString());

        ClientResponse response = webResource.type("application/json").get(ClientResponse.class);
        String output = response.getEntity(String.class);
        System.out.print(output);
    }
    
    public void pesquisarPontosDeParticipacao(String URL, Integer id) {
        Client client = Client.create();

        url = new StringBuffer();
        url.append(URL);
        url.append("/");
        url.append(id);

        WebResource webResource = client.resource(url.toString());

        ClientResponse response = webResource.type("application/json").get(ClientResponse.class);
        String output = response.getEntity(String.class);
        System.out.print(output);
    }
    
    public void pesquisarPremiacaoVSQuantidade(String URL, Integer id) {
        Client client = Client.create();

        url = new StringBuffer();
        url.append(URL);
        url.append("/");
        url.append(id);
        
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();

        WebResource webResource = client.resource(url.toString());

        ClientResponse response = webResource.type("application/json").get(ClientResponse.class);
        String output = response.getEntity(String.class);
        System.out.print(output);
    }

}
