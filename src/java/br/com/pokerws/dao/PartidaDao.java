package br.com.pokerws.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.pokerws.entity.EntidadeDominio;
import br.com.pokerws.entity.Local;
import br.com.pokerws.entity.Partida;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PartidaDao implements IDao {

    private static Connection con = null;

    @Override
    public Integer save(EntidadeDominio entidade) {

        if (!(entidade instanceof Partida)) {
            return 0;
        }

        Partida partida = new Partida();
        partida = (Partida) entidade;

        StringBuffer sql = new StringBuffer();
        sql.append("INSERT INTO partida ");
        sql.append("(DATA, LOCAL_ID, VALOR_RANKING, PARTICIPACAO) VALUES (?, ?, ?, ?) ");

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

            SimpleDateFormat stf = new SimpleDateFormat("yyyy/MM/dd");
            String data = stf.format(partida.getData());

            pstm.setString(1, data);
            pstm.setInt(2, partida.getLocal().getId());
            pstm.setBigDecimal(3, partida.getPremiacaoRanking());
            pstm.setInt(4, partida.getJogadores().size());
            pstm.executeUpdate();

            ResultSet rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                partida.setId(rs.getInt(1));
            }

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
        sql.append("UPDATE partida SET ");
        sql.append("DATA=?, LOCAL_ID=?, VALOR_RANKING=?, PARTICIPACAO=? WHERE ID=? ");

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
            SimpleDateFormat stf = new SimpleDateFormat("yyyy/MM/dd");
            String data = stf.format(partida.getData());

            pstm.setString(1, data);
            pstm.setInt(2, partida.getLocal().getId());
            pstm.setBigDecimal(3, partida.getPremiacaoRanking());
            pstm.setInt(4, partida.getJogadores().size());
            pstm.setInt(5, partida.getId());
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
        sql.append("DELETE FROM partida WHERE ID=? ");

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
        sql.append("SELECT * FROM partida ");
        sql.append("inner join local on local.id = partida.local_id ");
        sql.append("WHERE partida.id=?");

        con = ConnectionFactory.getConnection();

        PreparedStatement pstm;
        try {
            pstm = con.prepareStatement(sql.toString());
            pstm.setInt(1, partida.getId());

            ResultSet rSet = pstm.executeQuery();

            if (rSet.next()) {
                partida = new Partida();
                partida.setId(rSet.getInt("id"));
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                try {
                    partida.setData(sdf.parse(rSet.getString("data")));
                } catch (ParseException ex) {
                    Logger.getLogger(JogadorPartidaDao.class.getName()).log(Level.SEVERE, null, ex);
                }

                Local local = new Local(rSet.getInt("local_id"));
                local.setLocal(rSet.getString("local"));
                partida.setLocal(local);

                partida.setPremiacaoRanking(rSet.getBigDecimal("valor_ranking"));
                partida.setParticipacao(rSet.getInt("participacao"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return partida;
    }

    @Override
    public List<EntidadeDominio> findAll() {

        List<EntidadeDominio> lista = new ArrayList<EntidadeDominio>();

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM partida ");
        sql.append("inner join local on local.id=partida.local_id ");
        sql.append("ORDER BY partida.id ASC");

        con = ConnectionFactory.getConnection();

        PreparedStatement pstm;
        try {
            pstm = con.prepareStatement(sql.toString());

            ResultSet rSet = pstm.executeQuery();

            while (rSet.next()) {
                Partida partida = new Partida();
                partida.setId(rSet.getInt("id"));
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                try {
                    partida.setData(sdf.parse(rSet.getString("data")));
                } catch (ParseException ex) {
                    Logger.getLogger(JogadorPartidaDao.class.getName()).log(Level.SEVERE, null, ex);
                }

                Local local = new Local(rSet.getInt("local_id"));
                local.setLocal(rSet.getString("local"));
                partida.setLocal(local);

                partida.setPremiacaoRanking(rSet.getBigDecimal("valor_ranking"));
                partida.setParticipacao(rSet.getInt("participacao"));

                lista.add(partida);
            }

            rSet.close();
            pstm.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

}
