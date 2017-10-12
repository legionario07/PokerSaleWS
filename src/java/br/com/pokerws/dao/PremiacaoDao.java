package br.com.pokerws.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.pokerws.entity.Colocacao;
import br.com.pokerws.entity.EntidadeDominio;
import br.com.pokerws.entity.Premiacao;

public class PremiacaoDao implements IDao {

    private static Connection con = null;

    @Override
    public Integer save(EntidadeDominio entidade) {

        if (!(entidade instanceof Premiacao)) {
            return 0;
        }

        Premiacao premiacao = new Premiacao();
        premiacao = (Premiacao) entidade;

        StringBuffer sql = new StringBuffer();
        sql.append("INSERT INTO premiacao ");
        sql.append("(VALOR, COLOCACAO_ID) VALUES (?, ?) ");

        try {
   			if(con==null || con.isClosed())
   				con = ConnectionFactory.getConnection();
   		} catch (SQLException e1) {
   			// TODO Auto-generated catch block
   			e1.printStackTrace();
   		}


        PreparedStatement pstm;
        try {
            pstm = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            pstm.setBigDecimal(1, premiacao.getValor());
            pstm.setInt(2, premiacao.getColocacao().getId());
            pstm.executeUpdate();

            ResultSet rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                premiacao.setId(rs.getInt(1));
            }
            rs.close();
            pstm.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return premiacao.getId();
    }

    @Override
    public void edite(EntidadeDominio entidade) {
        if (!(entidade instanceof Premiacao)) {
            return;
        }

        Premiacao premiacao = new Premiacao();
        premiacao = (Premiacao) entidade;

        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE premiacao SET ");
        sql.append("VALOR = ?, COLOCACAO_ID = ? WHERE ID=? ");

        try {
   			if(con==null || con.isClosed())
   				con = ConnectionFactory.getConnection();
   		} catch (SQLException e1) {
   			// TODO Auto-generated catch block
   			e1.printStackTrace();
   		}


        PreparedStatement pstm;
        try {
            pstm = con.prepareStatement(sql.toString());
            pstm.setBigDecimal(1, premiacao.getValor());
            pstm.setInt(2, premiacao.getColocacao().getId());
            pstm.setInt(3, premiacao.getId());
            pstm.executeUpdate();

            pstm.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String delete(EntidadeDominio entidade) {
        if (!(entidade instanceof Premiacao)) {
            return null;
        }

        Premiacao premiacao = new Premiacao();
        premiacao = (Premiacao) entidade;

        StringBuffer sql = new StringBuffer();
        sql.append("DELETE FROM premiacao WHERE ID=? ");

        con = ConnectionFactory.getConnection();

        PreparedStatement pstm;
        try {
            pstm = con.prepareStatement(sql.toString());
            pstm.setInt(1, premiacao.getId());
            pstm.executeUpdate();

            pstm.close();

            return "Excluido com sucesso";

        } catch (SQLException e) {
            return "Erro ao excluir : " + e.getMessage();
        }

    }

    @Override
    public EntidadeDominio find(EntidadeDominio entidade) {
        if (!(entidade instanceof Premiacao)) {
            return null;
        }

        Premiacao premiacao = new Premiacao();
        premiacao = (Premiacao) entidade;

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM premiacao ");
        sql.append("inner join colocacao on colocacao.id = premiacao.colocacao_id ");
        sql.append("WHERE premiacao.ID=?");

        con = ConnectionFactory.getConnection();

        PreparedStatement pstm;
        try {
            pstm = con.prepareStatement(sql.toString());
            pstm.setInt(1, premiacao.getId());

            ResultSet rSet = pstm.executeQuery();

            if (rSet.next()) {
                premiacao = new Premiacao();
                premiacao.setId(rSet.getInt("id"));
                premiacao.setValor(rSet.getBigDecimal("valor"));

                Colocacao c = new Colocacao(rSet.getInt("colocacao_id"));
                c.setPontos(rSet.getInt("pontos"));
                c.setPosicao(rSet.getString("posicao"));

                premiacao.setColocacao(c);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return premiacao;
    }

    @Override
    public List<EntidadeDominio> findAll() {

        List<EntidadeDominio> lista = new ArrayList<EntidadeDominio>();

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM premiacao ");
        sql.append("inner join colocacao on colocacao.id = premiacao.colocacao_id ");
        sql.append(" order by premiacao.id asc");

        con = ConnectionFactory.getConnection();

        PreparedStatement pstm;
        try {
            pstm = con.prepareStatement(sql.toString());

            ResultSet rSet = pstm.executeQuery();

            while (rSet.next()) {
                Premiacao premiacao = new Premiacao();
                premiacao.setId(rSet.getInt("id"));
                premiacao.setValor(rSet.getBigDecimal("valor"));

                Colocacao c = new Colocacao(rSet.getInt("colocacao_id"));
                c.setPontos(rSet.getInt("pontos"));
                c.setPosicao(rSet.getString("posicao"));
                
                premiacao.setColocacao(c);

                lista.add(premiacao);
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
