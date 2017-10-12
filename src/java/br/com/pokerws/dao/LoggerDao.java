package br.com.pokerws.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.pokerws.entity.EntidadeDominio;
import br.com.pokerws.entity.Local;
import br.com.pokerws.util.Logger;
import java.text.SimpleDateFormat;

public class LoggerDao implements IDao {

    private static Connection con = null;

    @Override
    public Integer save(EntidadeDominio entidade) {

        if (!(entidade instanceof Logger)) {
            return 0;
        }

        Logger logger = new Logger();
        logger = (Logger) entidade;

        StringBuffer sql = new StringBuffer();
        sql.append("INSERT INTO logger ");
        sql.append("(jogador_id, jogador_nome, data_acesso) VALUES (?,?,?) ");

        con = ConnectionFactory.getConnection();

        PreparedStatement pstm;
        try {
            pstm = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            pstm.setInt(1, logger.getJogador().getId());
            pstm.setString(2, logger.getJogador().getNome());

            SimpleDateFormat stf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String data = stf.format(logger.getDataAcesso());

            pstm.setString(3, data);
            pstm.executeUpdate();

            ResultSet rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                logger.setId(rs.getInt(1));
            }
            rs.close();
            pstm.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logger.getId();
    }

    @Override
    public void edite(EntidadeDominio entidade) {
      
    }

    @Override
    public String delete(EntidadeDominio entidade) {
      return null;

    }

    @Override
    public List<EntidadeDominio> findAll() {
        
        List<EntidadeDominio> lista = new ArrayList<EntidadeDominio>();
        

        Logger logger = null;

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM logger WHERE id=?");

        con = ConnectionFactory.getConnection();

        PreparedStatement pstm;
        try {
            pstm = con.prepareStatement(sql.toString());
            pstm.setInt(1, logger.getJogador().getId());

            ResultSet rSet = pstm.executeQuery();

            if (rSet.next()) {
                logger = new Logger();
                
                logger.setId(rSet.getInt("id"));
                logger.getJogador().setId(rSet.getInt("jogador_id"));
                logger.getJogador().setNome(rSet.getString("jogador_nome"));
                logger.setDataAcesso(rSet.getDate("data_acesso"));
                
                lista.add(logger);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public EntidadeDominio find(EntidadeDominio entidade) {

      return null;
    }


}
