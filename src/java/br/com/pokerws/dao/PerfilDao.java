package br.com.pokerws.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.pokerws.entity.EntidadeDominio;
import br.com.pokerws.entity.Perfil;

public class PerfilDao implements IDao {

    private static Connection con = null;

    @Override
    public Integer save(EntidadeDominio entidade) {

        if (!(entidade instanceof Perfil)) {
            return 0;
        }

        Perfil perfil = new Perfil();
        perfil = (Perfil) entidade;

        StringBuffer sql = new StringBuffer();
        sql.append("INSERT INTO perfil ");
        sql.append("(PERFIL) VALUES (?) ");

        con = ConnectionFactory.getConnection();

        PreparedStatement pstm;
        try {
            pstm = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, perfil.getPerfil().toUpperCase());
            pstm.executeUpdate();

            ResultSet rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                perfil.setId(rs.getInt(1));
            }
            rs.close();
            pstm.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return perfil.getId();
    }

    @Override
    public void edite(EntidadeDominio entidade) {
        if (!(entidade instanceof Perfil)) {
            return;
        }

        Perfil perfil = new Perfil();
        perfil = (Perfil) entidade;

        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE perfil SET ");
        sql.append("perfil = ? WHERE ID=? ");

        con = ConnectionFactory.getConnection();

        PreparedStatement pstm;
        try {
            pstm = con.prepareStatement(sql.toString());
            pstm.setString(1, perfil.getPerfil().toUpperCase());
            pstm.setInt(2, perfil.getId());
            pstm.executeUpdate();

            pstm.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }

    @Override
    public String delete(EntidadeDominio entidade) {
        if (!(entidade instanceof Perfil)) {
            return null;
        }

        Perfil perfil = new Perfil();
        perfil = (Perfil) entidade;

        StringBuffer sql = new StringBuffer();
        sql.append("DELETE FROM perfil WHERE ID=? ");

        con = ConnectionFactory.getConnection();

        PreparedStatement pstm;
        try {
            pstm = con.prepareStatement(sql.toString());
            pstm.setInt(1, perfil.getId());
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
        if (!(entidade instanceof Perfil)) {
            return null;
        }

        Perfil perfil = new Perfil();
        perfil = (Perfil) entidade;

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM perfil WHERE ID=?");

        con = ConnectionFactory.getConnection();

        PreparedStatement pstm;
        try {
            pstm = con.prepareStatement(sql.toString());
            pstm.setInt(1, perfil.getId());

            ResultSet rSet = pstm.executeQuery();

            if (rSet.next()) {
                perfil = new Perfil();
                perfil.setId(rSet.getInt("id"));
                perfil.setPerfil(rSet.getString("perfil"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return perfil;
    }

    @Override
    public List<EntidadeDominio> findAll() {

        List<EntidadeDominio> lista = new ArrayList<EntidadeDominio>();

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM perfil ORDER BY ID ASC");

        con = ConnectionFactory.getConnection();

        PreparedStatement pstm;
        try {
            pstm = con.prepareStatement(sql.toString());

            ResultSet rSet = pstm.executeQuery();

            while (rSet.next()) {
                Perfil perfil = new Perfil();
                perfil.setId(rSet.getInt("id"));
                perfil.setPerfil(rSet.getString("perfil"));

                lista.add(perfil);
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
