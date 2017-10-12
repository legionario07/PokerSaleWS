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

public class ColocacaoDao implements IDao{

	private static Connection con = null;

	public Integer save(EntidadeDominio entidade) {

		if (!(entidade instanceof Colocacao)) {
			return 0;
		}

		Colocacao colocacao = new Colocacao();
		colocacao = (Colocacao) entidade;

		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO colocacao ");
		sql.append("(POSICAO, PONTOS) VALUES (?, ?) ");

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			pstm.setString(1, colocacao.getPosicao());
			pstm.setInt(2, colocacao.getPontos());
			pstm.executeUpdate();

			ResultSet rs = pstm.getGeneratedKeys();
			if (rs.next()) {
				colocacao.setId(rs.getInt(1));
			}
			rs.close();
			pstm.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return colocacao.getId();
	}

	public void edite(EntidadeDominio entidade) {
		if (!(entidade instanceof Colocacao)) {
			return;
		}

		Colocacao colocacao = new Colocacao();
		colocacao = (Colocacao) entidade;

		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE colocacao SET ");
		sql.append("POSICAO = ?, PONTOS=? WHERE ID=? ");

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString());
			pstm.setString(1, colocacao.getPosicao());
			pstm.setInt(2, colocacao.getPontos());
			pstm.setInt(3, colocacao.getId());
			pstm.executeUpdate();

			pstm.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public String delete(EntidadeDominio entidade) {
		if (!(entidade instanceof Colocacao)) {
			return null;
		}

		Colocacao colocacao = new Colocacao();
		colocacao = (Colocacao) entidade;
		
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM colocacao WHERE ID=? ");

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString());
			pstm.setInt(1, colocacao.getId());
			pstm.executeUpdate();

			pstm.close();
			con.close();
                        
                        return "Excluido com sucesso";

		} catch (SQLException e) {
			return "Erro ao excluir : "+e.getMessage();
		}

	}

	public EntidadeDominio find(EntidadeDominio entidade) {
		if (!(entidade instanceof Colocacao)) {
			return null;
		}

		Colocacao colocacao = new Colocacao();
		colocacao = (Colocacao) entidade;
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM colocacao WHERE ID=?");

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString());
			pstm.setInt(1, colocacao.getId());
			
			ResultSet rSet = pstm.executeQuery();

			if(rSet.next()){
				colocacao = new Colocacao();
				colocacao.setId(rSet.getInt("id"));
				colocacao.setPosicao(rSet.getString("posicao"));
				colocacao.setPontos(rSet.getInt("pontos"));
			}
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return colocacao;
	}

	public List<EntidadeDominio> findAll() {
		
		List<EntidadeDominio> lista = new ArrayList<EntidadeDominio>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM colocacao ORDER BY ID ASC");

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
			
			ResultSet rSet = pstm.executeQuery();

			while(rSet.next()){
				Colocacao colocacao = new Colocacao();
				colocacao.setId(rSet.getInt("id"));
				colocacao.setPosicao(rSet.getString("posicao"));
				colocacao.setPontos(rSet.getInt("pontos"));
				
				lista.add(colocacao);
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
