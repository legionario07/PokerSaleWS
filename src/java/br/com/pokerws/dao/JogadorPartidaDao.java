package br.com.pokerws.dao;

import br.com.pokerws.entity.Colocacao;
import br.com.pokerws.entity.Despesa;
import br.com.pokerws.entity.EntidadeDominio;
import br.com.pokerws.entity.Jogador;
import br.com.pokerws.entity.Local;
import br.com.pokerws.entity.Partida;
import br.com.pokerws.entity.Perfil;
import br.com.pokerws.entity.Premiacao;
import br.com.pokerws.entity.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JogadorPartidaDao implements IDao {

    private static Connection con = null;

    @Override
    public Integer save(EntidadeDominio entidade) {

        if (!(entidade instanceof Partida)) {
            return 0;
        }

        Partida partida = new Partida();
        partida = (Partida) entidade;

        StringBuffer sql = new StringBuffer();
        sql.append("INSERT INTO jogador_partida ");
        sql.append("(ENTRADA, REBUY, ADD_ON, JOGADOR_ID, PARTIDA_ID, PREMIACAO_ID) VALUES (?, ?, ?, ?, ?, ?) ");

        try {
            if (con == null || con.isClosed()) {
                con = ConnectionFactory.getConnection();
            }
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        PreparedStatement pstm;
        try {
            pstm = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            pstm.setBigDecimal(1, partida.getJogadores().get(0).getDespesa().getEntrada());
            pstm.setBigDecimal(2, partida.getJogadores().get(0).getDespesa().getRebuy());
            pstm.setBigDecimal(3, partida.getJogadores().get(0).getDespesa().getAdd_on());
            pstm.setInt(4, partida.getJogadores().get(0).getId());
            pstm.setInt(5, partida.getId());
            pstm.setInt(6, partida.getJogadores().get(0).getPremiacao().getId());

            pstm.executeUpdate();

            ResultSet rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                partida.getJogadores().get(0).getDespesa().setId(rs.getInt(1));
            }
            rs.close();
            pstm.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return partida.getId();
    }

    @Override
    public void edite(EntidadeDominio entidade) {
        if (!(entidade instanceof Partida)) {
            return;
        }

        Partida partida = new Partida();
        partida = (Partida) entidade;

        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE jogador_partida SET ");
        sql.append("ENTRADA=?, REBUY=?, ADD_ON=?, JOGADOR_ID=?, PARTIDA_ID=?, PREMIACAO_ID=? ");
        sql.append("WHERE partida_id=? and jogador_id = ?");

        try {
            if (con == null || con.isClosed()) {
                con = ConnectionFactory.getConnection();
            }
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        PreparedStatement pstm;
        try {
            pstm = con.prepareStatement(sql.toString());
            pstm.setBigDecimal(1, partida.getJogadores().get(0).getDespesa().getEntrada());
            pstm.setBigDecimal(2, partida.getJogadores().get(0).getDespesa().getRebuy());
            pstm.setBigDecimal(3, partida.getJogadores().get(0).getDespesa().getAdd_on());
            pstm.setInt(4, partida.getJogadores().get(0).getId());
            pstm.setInt(5, partida.getId());
            pstm.setInt(6, partida.getJogadores().get(0).getPremiacao().getId());
            pstm.setInt(7, partida.getId());
            pstm.setInt(8, partida.getJogadores().get(0).getId());

            pstm.executeUpdate();

            pstm.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String delete(EntidadeDominio entidade) {
        if (!(entidade instanceof Partida)) {
            return null;
        }

        Partida partida = new Partida();
        partida = (Partida) entidade;

        StringBuffer sql = new StringBuffer();
        sql.append("DELETE FROM jogador_partida WHERE jogador_id = ? and partida_id = ?");

        try {
            if (con == null || con.isClosed()) {
                con = ConnectionFactory.getConnection();
            }
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        PreparedStatement pstm;
        try {
            pstm = con.prepareStatement(sql.toString());
            pstm.setInt(1, partida.getJogadores().get(0).getId());
            pstm.setInt(2, partida.getId());
            pstm.executeUpdate();

            pstm.close();

            return "Excluido com sucesso";

        } catch (SQLException e) {
            return "Erro ao excluir : " + e.getMessage();
        }

    }

    @Override
    public EntidadeDominio find(EntidadeDominio entidade) {
        if (!(entidade instanceof Partida)) {
            return null;
        }

        Partida partida = new Partida();
        partida = (Partida) entidade;

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM jogador_partida ");
        sql.append("inner join premiacao on premiacao.id = jogador_partida.premiacao_id ");
        sql.append("inner join colocacao on colocacao.id = premiacao.colocacao_id ");
        sql.append("inner join partida on partida.id = jogador_partida.partida_id ");
        sql.append("inner join local on partida.local_id = local.id ");
        sql.append("inner join jogador on jogador.id = jogador_partida.jogador_id ");
        sql.append("inner join usuario on jogador.usuario_id = usuario.id ");
        sql.append("inner join perfil on usuario.perfil_id = perfil.id ");
        sql.append("where partida_id = ?");

        con = ConnectionFactory.getConnection();

        PreparedStatement pstm;
        try {
            pstm = con.prepareStatement(sql.toString());
            pstm.setInt(1, partida.getId());

            ResultSet rSet = pstm.executeQuery();

            while (rSet.next()) {
                if (rSet.isFirst()) {

                    partida = new Partida();

                    // get Partida
                    partida.setId(rSet.getInt("partida_id"));
                    partida.setData(rSet.getDate("data"));
                    partida.setPremiacaoRanking(rSet.getBigDecimal("valor_ranking"));
                    partida.setParticipacao(rSet.getInt("participacao"));

                    // Get Local
                    Local local = new Local();
                    local.setId(rSet.getInt("local_id"));
                    local.setLocal(rSet.getString("local"));
                    partida.setLocal(local);

                }

                // Get Premiação
                Premiacao premiacao = new Premiacao();
                premiacao.setId(rSet.getInt("premiacao_id"));
                premiacao.setValor(rSet.getBigDecimal("valor"));

                // Get Colocação
                Colocacao colocacao = new Colocacao();
                colocacao.setId(rSet.getInt("colocacao_id"));
                colocacao.setPosicao(rSet.getString("posicao"));
                colocacao.setPontos(rSet.getInt("pontos"));
                premiacao.setColocacao(colocacao);

                // Get Despesa
                Despesa despesa = new Despesa();
                despesa.setEntrada(rSet.getBigDecimal("entrada"));
                despesa.setRebuy(rSet.getBigDecimal("rebuy"));
                despesa.setAdd_on(rSet.getBigDecimal("add_on"));

                // get Jogador
                Jogador jogador = new Jogador();
                jogador.setId(rSet.getInt("jogador_id"));
                jogador.setNome(rSet.getString("nome"));

                // get Usuario
                Usuario usuario = new Usuario();
                usuario.setId(rSet.getInt("usuario_id"));
                usuario.setLogin(rSet.getString("login"));
                usuario.setSenha(rSet.getString("senha"));

                // Get Perfil
                Perfil perfil = new Perfil();
                perfil.setId(rSet.getInt("perfil_id"));
                perfil.setPerfil(rSet.getString("perfil"));

                usuario.setPerfil(perfil);
                jogador.setUsuario(usuario);
                jogador.setPremiacao(premiacao);
                jogador.setDespesa(despesa);

                partida.getJogadores().add(jogador);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return partida;
    }

    public String deleteByPartida(EntidadeDominio entidade) {
        if (!(entidade instanceof Partida)) {
            return null;
        }

        Partida partida = new Partida();
        partida = (Partida) entidade;

        StringBuffer sql = new StringBuffer();
        sql.append("DELETE FROM jogador_partida WHERE PARTIDA_ID = ? ");

        con = ConnectionFactory.getConnection();

        PreparedStatement pstm;
        try {
            pstm = con.prepareStatement(sql.toString());
            pstm.setInt(1, partida.getId());
            pstm.executeUpdate();

            pstm.close();
            con.close();

            return "Excluido com sucesso";

        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao excluir: " + e.getMessage();
        }

    }

    public void editeByJogador(EntidadeDominio entidade) {
        if (!(entidade instanceof Partida)) {
            return;
        }

        Partida partida = new Partida();
        partida = (Partida) entidade;

        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE jogador_partida SET ");
        sql.append("ENTRADA=?, REBUY=?, ADD_ON=?, JOGADOR_ID=?, PARTIDA_ID=?, PREMIACAO_ID=? WHERE JOGADOR_ID=? AND PARTIDA_ID = ?");

        con = ConnectionFactory.getConnection();

        PreparedStatement pstm;
        try {
            pstm = con.prepareStatement(sql.toString());
            pstm.setBigDecimal(1, partida.getJogadores().get(0).getDespesa().getEntrada());
            pstm.setBigDecimal(2, partida.getJogadores().get(0).getDespesa().getRebuy());
            pstm.setBigDecimal(3, partida.getJogadores().get(0).getDespesa().getAdd_on());
            pstm.setInt(4, partida.getJogadores().get(0).getId());
            pstm.setInt(5, partida.getId());
            pstm.setInt(6, partida.getJogadores().get(0).getPremiacao().getId());
            pstm.setInt(7, partida.getJogadores().get(0).getId());
            pstm.setInt(8, partida.getId());
            pstm.executeUpdate();

            pstm.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<EntidadeDominio> findAll() {

        List<EntidadeDominio> partidas = new ArrayList<EntidadeDominio>();
        Partida partida = new Partida();

        StringBuffer sql = new StringBuffer();

        sql.append("SELECT * FROM jogador_partida ");
        sql.append("inner join premiacao on premiacao.id = jogador_partida.premiacao_id ");
        sql.append("inner join colocacao on colocacao.id = premiacao.colocacao_id ");
        sql.append("inner join partida on partida.id = jogador_partida.partida_id ");
        sql.append("inner join local on partida.local_id = local.id ");
        sql.append("inner join jogador on jogador.id = jogador_partida.jogador_id ");
        sql.append("inner join usuario on jogador.usuario_id = usuario.id ");
        sql.append("inner join perfil on usuario.perfil_id = perfil.id ");
        sql.append("order by partida_id");

        try {
            if (con == null || con.isClosed()) {
                con = ConnectionFactory.getConnection();
            }
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        PreparedStatement pstm;
        try {
            pstm = con.prepareStatement(sql.toString());

            ResultSet rSet = pstm.executeQuery();

            boolean isHasLast = false;

            while (rSet.next()) {

                if (rSet.isFirst()) {

                    // get Partida
                    partida.setId(rSet.getInt("partida_id"));

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                    partida.setData(rSet.getDate("data"));
                    partida.setPremiacaoRanking(rSet.getBigDecimal("valor_ranking"));
                    partida.setParticipacao(rSet.getInt("participacao"));

                    // Get Local
                    Local local = new Local();
                    local.setId(rSet.getInt("local_id"));
                    local.setLocal(rSet.getString("local"));
                    partida.setLocal(local);

                }

                if (partida.getId() == rSet.getInt("partida_id")) {

                    // Get Premiação
                    Premiacao premiacao = new Premiacao();
                    premiacao.setId(rSet.getInt("premiacao_id"));
                    premiacao.setValor(rSet.getBigDecimal("valor"));

                    // Get Colocação
                    Colocacao colocacao = new Colocacao();
                    colocacao.setId(rSet.getInt("colocacao_id"));
                    colocacao.setPosicao(rSet.getString("posicao"));
                    colocacao.setPontos(rSet.getInt("pontos"));
                    premiacao.setColocacao(colocacao);

                    // Get Despesa
                    Despesa despesa = new Despesa();
                    despesa.setEntrada(rSet.getBigDecimal("entrada"));
                    despesa.setRebuy(rSet.getBigDecimal("rebuy"));
                    despesa.setAdd_on(rSet.getBigDecimal("add_on"));

                    // get Jogador
                    Jogador jogador = new Jogador();
                    jogador.setId(rSet.getInt("jogador_id"));
                    jogador.setNome(rSet.getString("nome"));

                    // get Usuario
                    Usuario usuario = new Usuario();
                    usuario.setId(rSet.getInt("usuario_id"));
                    usuario.setLogin(rSet.getString("login"));
                    usuario.setSenha(rSet.getString("senha"));

                    // Get Perfil
                    Perfil perfil = new Perfil();
                    perfil.setId(rSet.getInt("perfil_id"));
                    perfil.setPerfil(rSet.getString("perfil"));

                    usuario.setPerfil(perfil);
                    jogador.setUsuario(usuario);
                    jogador.setPremiacao(premiacao);
                    jogador.setDespesa(despesa);

                    partida.getJogadores().add(jogador);

                }

                if (partida.getId() != rSet.getInt("partida_id")) {

                    partidas.add(partida);

                    partida = new Partida();

                    // get Partida
                    partida.setId(rSet.getInt("partida_id"));

                    partida.setData(rSet.getDate("data"));

                    partida.setPremiacaoRanking(rSet.getBigDecimal("valor_ranking"));
                    partida.setParticipacao(rSet.getInt("participacao"));

                    // Get Local
                    Local local = new Local();
                    local.setId(rSet.getInt("local_id"));
                    local.setLocal(rSet.getString("local"));
                    partida.setLocal(local);

                    // Get Premiação
                    Premiacao premiacao = new Premiacao();
                    premiacao.setId(rSet.getInt("premiacao_id"));
                    premiacao.setValor(rSet.getBigDecimal("valor"));

                    // Get Colocação
                    Colocacao colocacao = new Colocacao();
                    colocacao.setId(rSet.getInt("colocacao_id"));
                    colocacao.setPosicao(rSet.getString("posicao"));
                    colocacao.setPontos(rSet.getInt("pontos"));
                    premiacao.setColocacao(colocacao);

                    // Get Despesa
                    Despesa despesa = new Despesa();
                    despesa.setEntrada(rSet.getBigDecimal("entrada"));
                    despesa.setRebuy(rSet.getBigDecimal("rebuy"));
                    despesa.setAdd_on(rSet.getBigDecimal("add_on"));

                    // get Jogador
                    Jogador jogador = new Jogador();
                    jogador.setId(rSet.getInt("jogador_id"));
                    jogador.setNome(rSet.getString("nome"));

                    // get Usuario
                    Usuario usuario = new Usuario();
                    usuario.setId(rSet.getInt("usuario_id"));
                    usuario.setLogin(rSet.getString("login"));
                    usuario.setSenha(rSet.getString("senha"));

                    // Get Perfil
                    Perfil perfil = new Perfil();
                    perfil.setId(rSet.getInt("perfil_id"));
                    perfil.setPerfil(rSet.getString("perfil"));

                    usuario.setPerfil(perfil);
                    jogador.setUsuario(usuario);
                    jogador.setPremiacao(premiacao);
                    jogador.setDespesa(despesa);

                    partida.getJogadores().add(jogador);

                }

                if (rSet.isLast() && (!partidas.isEmpty())) {
                    isHasLast = true;
                }

            }

            if (isHasLast) {
                partidas.add(partida);
            }

            rSet.close();
            pstm.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return partidas;
    }

    public List<EntidadeDominio> findByJogador(Jogador j) {

        List<EntidadeDominio> lista = new ArrayList<EntidadeDominio>();

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM jogador_partida ");
        sql.append("inner join premiacao on premiacao.id = jogador_partida.premiacao_id ");
        sql.append("inner join colocacao on colocacao.id = premiacao.colocacao_id ");
        sql.append("inner join partida on partida.id = jogador_partida.partida_id ");
        sql.append("inner join local on partida.local_id = local.id ");
        sql.append("inner join jogador on jogador.id = jogador_partida.jogador_id ");
        sql.append("inner join usuario on jogador.usuario_id = usuario.id ");
        sql.append("inner join perfil on usuario.perfil_id = perfil.id ");
        sql.append("where jogador_id = ? ");
        sql.append("order by partida_id asc");

        con = ConnectionFactory.getConnection();

        PreparedStatement pstm;
        try {
            pstm = con.prepareStatement(sql.toString());
            pstm.setInt(1, j.getId());

            ResultSet rSet = pstm.executeQuery();

            while (rSet.next()) {

                Partida partida = new Partida();

                // get Partida
                partida.setId(rSet.getInt("partida_id"));
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                try {
                    partida.setData(sdf.parse(rSet.getString("data")));
                } catch (ParseException ex) {
                    Logger.getLogger(JogadorPartidaDao.class.getName()).log(Level.SEVERE, null, ex);
                }

                partida.setPremiacaoRanking(rSet.getBigDecimal("valor_ranking"));
                partida.setParticipacao(rSet.getInt("participacao"));

                // Get Local
                Local local = new Local();
                local.setId(rSet.getInt("local_id"));
                local.setLocal(rSet.getString("local"));
                partida.setLocal(local);

                // Get Premiação
                Premiacao premiacao = new Premiacao();
                premiacao.setId(rSet.getInt("premiacao_id"));
                premiacao.setValor(rSet.getBigDecimal("valor"));

                // Get Colocação
                Colocacao colocacao = new Colocacao();
                colocacao.setId(rSet.getInt("colocacao_id"));
                colocacao.setPosicao(rSet.getString("posicao"));
                colocacao.setPontos(rSet.getInt("pontos"));
                premiacao.setColocacao(colocacao);

                // Get Despesa
                Despesa despesa = new Despesa();
                despesa.setEntrada(rSet.getBigDecimal("entrada"));
                despesa.setRebuy(rSet.getBigDecimal("rebuy"));
                despesa.setAdd_on(rSet.getBigDecimal("add_on"));

                // get Jogador
                Jogador jogador = new Jogador();
                jogador.setId(rSet.getInt("jogador_id"));
                jogador.setNome(rSet.getString("nome"));

                // get Usuario
                Usuario usuario = new Usuario();
                usuario.setId(rSet.getInt("usuario_id"));
                usuario.setLogin(rSet.getString("login"));
                usuario.setSenha(rSet.getString("senha"));

                // Get Perfil
                Perfil perfil = new Perfil();
                perfil.setId(rSet.getInt("perfil_id"));
                perfil.setPerfil(rSet.getString("perfil"));

                usuario.setPerfil(perfil);
                jogador.setUsuario(usuario);
                jogador.setPremiacao(premiacao);
                jogador.setDespesa(despesa);

                partida.getJogadores().add(jogador);

                lista.add(partida);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

}
