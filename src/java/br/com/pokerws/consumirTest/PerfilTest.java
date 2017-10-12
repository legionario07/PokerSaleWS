/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pokerws.consumirTest;

import br.com.pokerws.dao.JogadorDao;
import br.com.pokerws.dao.LocalDao;
import br.com.pokerws.dao.UsuarioDao;
import br.com.pokerws.entity.Colocacao;
import br.com.pokerws.entity.EntidadeDominio;
import br.com.pokerws.entity.Jogador;
import br.com.pokerws.entity.Local;
import br.com.pokerws.entity.Partida;
import br.com.pokerws.entity.Perfil;
import br.com.pokerws.entity.Usuario;
import br.com.pokerws.util.GsonUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 *
 * @author PauLinHo
 */
public class PerfilTest implements ITest {

    private static final String URL_SAVE = "http://poker07.jelasticlw.com.br/webresources/perfil/save";
    private static final String URL_DELETE = "http://localhost:8080/PokerWS/webresources/perfil/delete";
    private static final String URL_FIND = "http://192.168.0.102:8080/PokerWS/webresources/perfil/findAll";
    private static final String URL_FIND_ALL = "http://poker07.jelasticlw.com.br/webresources/perfil/findAll";
    private static final String URL_UPDATE = "http://poker07.jelasticlw.com.br/webresources/perfil/update";
    private static final String FIND_LAST_PARTIDA = "http://poker07.jelasticlw.com.br/webresources/jogador/findLastPartida";

    private CrudWSTest crudWSTest;

    public PerfilTest() {

        crudWSTest = new CrudWSTest();

    }

    public static void main(String[] args) {

        PerfilTest perfilTest = new PerfilTest();
        //perfilTest.updateTest();
          perfilTest.saveHttp();
        //perfilTest.httpPostTest();
        //perfilTest.salvarPartida();
        //perfilTest.testeHttpPUTClientApache();
        //perfilTest.findAllTest();
        //perfilTest.findLastPartida();
        //System.out.println((new PerfilDao().findAll()).size());

    }

    public void salvarPartida() {
        Partida partida = new Partida();

        BigDecimal b = new BigDecimal("0.0");

        Jogador j1 = new Jogador(1);
        j1 = (Jogador) new JogadorDao().find(j1);
        j1.getDespesa().setAdd_on(b);
        j1.getDespesa().setEntrada(b);
        j1.getDespesa().setRebuy(b);

        j1.getPremiacao().setValor(b);
        j1.getPremiacao().setColocacao(new Colocacao(1));

        partida.getJogadores().add(j1);

        Local local = new Local(1);
        local = (Local) new LocalDao().find(local);
        partida.setLocal(local);
        partida.setParticipacao(partida.getJogadores().size());
        partida.setData(new Date());
        partida.setPremiacaoRanking(new BigDecimal("0.0"));

        String enderecoURL = "http://localhost:8080/PokerWS/webresources/partida/save";
        
        partida.setId(Integer.valueOf(testandoSave(enderecoURL, partida)));
        
         for (Jogador e : partida.getJogadores()) {

                Partida p = new Partida();
                p = partida;
                p.setJogadores(new ArrayList<Jogador>());
                p.getJogadores().add(0, e);
                
                String enderecoURL3 = "http://localhost:8080/PokerWS/webresources/premiacao/save";
                
                p.getJogadores().get(0).getPremiacao().setId(Integer.valueOf(testandoSave(enderecoURL3, p.getJogadores().get(0).getPremiacao())));
                 
               


                // verifica se a Premiacao foi armazenada no BD
                if (p.getJogadores().get(0).getPremiacao().getId()==null) {
                    return;
                } else {
                    // Se nao houve erro salva os jogadores da partida
                    // no BD
                    String enderecoURL2 = "http://localhost:8080/PokerWS/webresources/jogadorpartida/save";
                    testandoSave(enderecoURL2, p);

                }
            }
        
    }

    @Override
    public void saveTest() {

        Perfil perfil = new Perfil();
        perfil.setPerfil("Testadfadfe");

        crudWSTest.saveTest(URL_SAVE, perfil);

    }

    @Override
    public void updateTest() {

        Perfil perfil = new Perfil();
        perfil.setId(8);
        perfil.setPerfil("Teste 21234");

        crudWSTest.updateTest(URL_UPDATE, perfil);
    }

    @Override
    public void deleteTest() {

        Perfil perfil = new Perfil(3);

        crudWSTest.deleteTest(URL_DELETE, perfil.getId());

    }

    @Override
    public void findTest() {
        Perfil perfil = new Perfil(1);

        crudWSTest.findTest(URL_FIND, perfil.getId());

    }

    @Override
    public void findAllTest() {
        crudWSTest.findAllTest(URL_FIND);
    }

    public String httpPostTest() {

        String output = null;

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://poker07.jelasticlw.com.br/webresources/jogador/login");
        httpPost.setHeader("Content-Type", "application/json");
        try {

            Jogador j = new Jogador();
            j.getUsuario().setLogin("Bruno");
            j.getUsuario().setSenha("123");
            Gson gson = new Gson();

            String dados = gson.toJson(j);

            HttpEntity entity = new StringEntity(dados);

            httpPost.setEntity(entity);

            HttpResponse response = httpClient.execute(httpPost);

            StatusLine statusLine = response.getStatusLine();

            int statusCode = statusLine.getStatusCode();

            if (!(statusCode == 200)) {

                return "Erro";
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuffer retorno = new StringBuffer();

            Partida p = new Partida();
            p = (Partida) response.getEntity();

            System.out.println(p.getData());

            while ((output = br.readLine()) != null) {
                retorno.append(output);
            }

            output = retorno.toString();

            System.out.println(output);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return output;
    }

    public String httpPutTest() {

        String output = null;

        try {

            URL url = new URL("http://192.168.0.101:8080/PokerWS/webresources/estatisticas/pesquisarPosicao/2");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");

            Gson gson = new Gson();

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            StringBuffer retorno = new StringBuffer();

            while ((output = br.readLine()) != null) {
                retorno.append(output);
            }

            output = retorno.toString();

            System.out.println(output);
            conn.disconnect();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return output;
    }

    public int testeHttpClientApache() {
        int responseCode = -1;
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpPost request = new HttpPost();
            request.setHeader("Content-Type", "application/json");

            request.setURI(new URI("http://omniatechnology.com.br/PokerWS/webresources/jogador/save/"));

            Jogador j = new Jogador();
            j.setNome("teste");
            Gson gson = new Gson();

            String jogador = gson.toJson(j);

            HttpEntity entity = new StringEntity(jogador);
            request.setEntity(entity);

            HttpResponse response = httpClient.execute(request);

            InputStream content = response.getEntity().getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(content));

            StringBuffer retorno = new StringBuffer();
            String output;

            while ((output = br.readLine()) != null) {
                retorno.append(output);
            }
            System.out.println(retorno.toString());

            content.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return responseCode;
    }

    public int testeHttpPUTClientApache() {
        int responseCode = -1;
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpPost request = new HttpPost();
            request.setHeader("Content-Type", "application/json");

            StringBuilder builder = new StringBuilder();
            builder.append("http://poker07.jelasticlw.com.br/webresources/jogador/login");

            request.setURI(new URI("http://omniatechnology.com.br/PokerWS/webresources/perfil/save/"));

            Perfil j = new Perfil(2);
            j.setPerfil("testeasdfa");
            Gson gson = new Gson();

            String jogador = gson.toJson(j);

            HttpEntity entity = new StringEntity(jogador);
            request.setEntity(entity);

            HttpResponse response = httpClient.execute(request);

            InputStream content = response.getEntity().getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(content));

            StringBuffer retorno = new StringBuffer();
            String output;

            while ((output = br.readLine()) != null) {
                retorno.append(output);
            }
            System.out.println(retorno.toString());

            content.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return responseCode;
    }

    public String findLastPartida() {

        String output = null;
        String enderecoURL = "http://omniatechnology.com.br/PokerWS/webresources/jogador/save";
        Usuario teste = new Usuario(2);
        teste = (Usuario) new UsuarioDao().find(teste);

        Jogador jogador = new Jogador();
        jogador.setNome("Teste 2");
        jogador.setUsuario(teste);

        Gson gson = new Gson();

        String dados = gson.toJson(jogador);

        HttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpPost request = new HttpPost(enderecoURL);
            request.setHeader("Content-Type", "application/json");

            StringEntity postingString = new StringEntity(dados);
            request.setEntity(postingString);
            request.setHeader("Content-type", "application/json");

            HttpResponse response = httpClient.execute(request);

            System.out.println(response.getEntity());

            StringBuffer retorno = new StringBuffer();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return output;
    }

    public String saveHttp() {
        String output = null;

        //  String enderecoURL = "http://omniatechnology.com.br/PokerWS/webresources/jogador/save";
        String enderecoURL = "http://localhost:8080/PokerWS/webresources/jogador/update";

        Jogador entidade = new Jogador(28);
        entidade = (Jogador) new JogadorDao().find(entidade);
        
        entidade.setNome("Paulo2323");
        

        try {

            Gson gson = new Gson();

            URL url = new URL(enderecoURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");

            gson = new Gson();

            String dados = gson.toJson(entidade);

            DataOutputStream wr = new DataOutputStream(
                    conn.getOutputStream());
            wr.writeBytes(dados);
            wr.flush();
            wr.close();

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            StringBuffer retorno = new StringBuffer();

            while ((output = br.readLine()) != null) {
                retorno.append(output);
            }

            output = retorno.toString();

            conn.disconnect();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return output;
    }

    public static String testandoSave(String enderecoURL, EntidadeDominio entidade) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        StringBuffer retorno = null;

        org.apache.http.client.HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpPost request = new HttpPost();
            request.setHeader("Content-Type", "application/json");

            StringBuilder builder = new StringBuilder();

            request.setURI(new URI(enderecoURL));

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Date.class, new GsonUtil(sdf));

            Gson gson = gsonBuilder.create();

            String dados = gson.toJson(entidade);

            HttpEntity entity = new StringEntity(dados);
            request.setEntity(entity);

            HttpResponse response = httpClient.execute(request);

            InputStream content = response.getEntity().getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(content));

            retorno = new StringBuffer();
            String output;

            while ((output = br.readLine()) != null) {
                retorno.append(output);
            }

            content.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return retorno.toString();

    }

    public String tentandoSalvar2() {
        Gson gson = new Gson();

        String output = null;

        try {

            Usuario teste = new Usuario();
            teste.setLogin("Teste");
            teste.setSenha("tese");
            teste.setPerfil(new Perfil(2));

            String dadosTemp = gson.toJson(teste);

            URL url = new URL("http://local.com.br/PokerWS/webresources/usuario/save/");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            gson = new Gson();

            String dados = gson.toJson(teste);

            DataOutputStream wr = new DataOutputStream(
                    conn.getOutputStream());
            wr.writeBytes(dados);
            wr.flush();
            wr.close();

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            StringBuffer retorno = new StringBuffer();

            while ((output = br.readLine()) != null) {
                retorno.append(output);
            }

            output = retorno.toString();

            conn.disconnect();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return output;
    }
}
