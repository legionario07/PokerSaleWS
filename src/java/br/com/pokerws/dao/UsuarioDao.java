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
import br.com.pokerws.entity.Usuario;

public class UsuarioDao implements IDao {

    private static Connection con = null;

    @Override
    public Integer save(EntidadeDominio entidade) {

        if (!(entidade instanceof Usuario)) {
            return 0;
        }

        Usuario usuario = new Usuario();
        usuario = (Usuario) entidade;

        StringBuffer sql = new StringBuffer();
        sql.append("INSERT INTO usuario ");
        sql.append("(LOGIN, SENHA, PERFIL_ID ) VALUES (?, MD5(?), ?) ");

        con = ConnectionFactory.getConnection();

        PreparedStatement pstm;
        try {
            pstm = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, usuario.getLogin().toUpperCase());
            pstm.setString(2, usuario.getSenha());
            pstm.setInt(3, usuario.getPerfil().getId());
            pstm.executeUpdate();

            ResultSet rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                usuario.setId(rs.getInt(1));
            }
            rs.close();
            pstm.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario.getId();
    }

    @Override
    public void edite(EntidadeDominio entidade) {
        if (!(entidade instanceof Usuario)) {
            return;
        }

        Usuario usuario = new Usuario();
        usuario = (Usuario) entidade;

        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE usuario SET ");
        sql.append("LOGIN = ?, SENHA = MD5(?), PERFIL_ID = ? WHERE ID=? ");

        con = ConnectionFactory.getConnection();

        PreparedStatement pstm;
        try {
            pstm = con.prepareStatement(sql.toString());
            pstm.setString(1, usuario.getLogin().toUpperCase());
            pstm.setString(2, usuario.getSenha());
            pstm.setInt(3, usuario.getPerfil().getId());
            pstm.setInt(4, usuario.getId());

            pstm.executeUpdate();

            pstm.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String delete(EntidadeDominio entidade) {
        if (!(entidade instanceof Usuario)) {
            return null;
        }

        Usuario usuario = new Usuario();
        usuario = (Usuario) entidade;

        StringBuffer sql = new StringBuffer();
        sql.append("DELETE FROM usuario WHERE ID=? ");

        con = ConnectionFactory.getConnection();

        PreparedStatement pstm;
        try {
            pstm = con.prepareStatement(sql.toString());
            pstm.setInt(1, usuario.getId());
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
        if (!(entidade instanceof Usuario)) {
            return null;
        }

        Usuario usuario = new Usuario();
        usuario = (Usuario) entidade;

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM usuario ");
        sql.append("inner join perfil on perfil.id = usuario.perfil_id ");
        sql.append("where usuario.id = ?");

        con = ConnectionFactory.getConnection();

        PreparedStatement pstm;
        try {
            pstm = con.prepareStatement(sql.toString());
            pstm.setInt(1, usuario.getId());

            ResultSet rSet = pstm.executeQuery();

            if (rSet.next()) {
                usuario = new Usuario();
                usuario.setId(rSet.getInt("id"));
                usuario.setLogin(rSet.getString("login"));
                usuario.setSenha(rSet.getString("senha"));

                Perfil perfil = new Perfil(rSet.getInt("perfil_id"));
                perfil.setPerfil(rSet.getString("perfil"));

                usuario.setPerfil(perfil);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }

    @Override
    public List<EntidadeDominio> findAll() {

        List<EntidadeDominio> lista = new ArrayList<EntidadeDominio>();

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM usuario ");
        sql.append("inner join perfil on perfil.id = usuario.perfil_id ");
        sql.append("order by usuario.id asc");

        con = ConnectionFactory.getConnection();

        PreparedStatement pstm;
        try {
            pstm = con.prepareStatement(sql.toString());

            ResultSet rSet = pstm.executeQuery();

            while (rSet.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rSet.getInt("id"));
                usuario.setLogin(rSet.getString("login"));
                usuario.setSenha(rSet.getString("senha"));

                Perfil perfil = new Perfil(rSet.getInt("perfil_id"));
                perfil.setPerfil(rSet.getString("perfil"));

                usuario.setPerfil(perfil);

                lista.add(usuario);
            }

            rSet.close();
            pstm.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public EntidadeDominio findByLogin(EntidadeDominio entidade) {
        if (!(entidade instanceof Usuario)) {
            return null;
        }

        Usuario usuario = new Usuario();
        usuario = (Usuario) entidade;

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM usuario WHERE LOGIN=?");

        con = ConnectionFactory.getConnection();

        PreparedStatement pstm;
        try {
            pstm = con.prepareStatement(sql.toString());
            pstm.setString(1, usuario.getLogin().toUpperCase());

            ResultSet rSet = pstm.executeQuery();

            if (rSet.next()) {
                usuario = new Usuario();
                usuario.setId(rSet.getInt("id"));
                usuario.setLogin(rSet.getString("login"));
                usuario.setSenha(rSet.getString("senha"));

                Perfil perfil = new Perfil(rSet.getInt("perfil_id"));
                perfil = (Perfil) new PerfilDao().find(perfil);

                usuario.setPerfil(perfil);
            }

            rSet.close();
            pstm.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }
    
    
     public void editeNoKey(EntidadeDominio entidade) {
        if (!(entidade instanceof Usuario)) {
            return;
        }

        Usuario usuario = new Usuario();
        usuario = (Usuario) entidade;

        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE usuario SET ");
        sql.append("LOGIN = ?, PERFIL_ID = ? WHERE ID=? ");

        con = ConnectionFactory.getConnection();

        PreparedStatement pstm;
        try {
            pstm = con.prepareStatement(sql.toString());
            pstm.setString(1, usuario.getLogin().toUpperCase());
            pstm.setInt(2, usuario.getPerfil().getId());
            pstm.setInt(3, usuario.getId());

            pstm.executeUpdate();

            pstm.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
