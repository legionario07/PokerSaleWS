package br.com.pokerws.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.pokerws.entity.Colocacao;
import br.com.pokerws.entity.Despesa;
import br.com.pokerws.entity.EntidadeDominio;
import br.com.pokerws.entity.Jogador;
import br.com.pokerws.entity.Local;
import br.com.pokerws.entity.Partida;
import br.com.pokerws.entity.Perfil;
import br.com.pokerws.entity.Premiacao;
import br.com.pokerws.entity.Usuario;

public class JogadorDao implements IDao {

	private static Connection con = null;

	@Override
	public Integer save(EntidadeDominio entidade) {

		if (!(entidade instanceof Jogador)) {
			return 0;
		}

		Jogador jogador = new Jogador();
		jogador = (Jogador) entidade;

		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO jogador ");
		sql.append("(NOME, USUARIO_ID) VALUES (?, ?) ");

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			pstm.setString(1, jogador.getNome().toUpperCase());
			pstm.setInt(2, jogador.getUsuario().getId());
			pstm.executeUpdate();

			ResultSet rs = pstm.getGeneratedKeys();
			if (rs.next()) {
				jogador.setId(rs.getInt(1));
			}
			rs.close();
			pstm.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return jogador.getId();
	}

	@Override
	public void edite(EntidadeDominio entidade) {
		if (!(entidade instanceof Jogador)) {
			return;
		}

		Jogador jogador = new Jogador();
		jogador = (Jogador) entidade;

		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE jogador SET ");
		sql.append("NOME = ?,USUARIO_ID = ? WHERE ID=? ");

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString());
			pstm.setString(1, jogador.getNome().toUpperCase());
			pstm.setInt(2, jogador.getUsuario().getId());
			pstm.setInt(3, jogador.getId());
			pstm.executeUpdate();

			pstm.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public String delete(EntidadeDominio entidade) {
		if (!(entidade instanceof Jogador)) {
			return null;
		}

		Jogador jogador = new Jogador();
		jogador = (Jogador) entidade;

		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM jogador WHERE ID=? ");

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString());
			pstm.setInt(1, jogador.getId());
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
		if (!(entidade instanceof Jogador)) {
			return null;
		}

		Jogador jogador = new Jogador();
		jogador = (Jogador) entidade;

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM jogador ");
		sql.append("inner join usuario on usuario.id = jogador.usuario_id ");
		sql.append("inner join perfil on perfil.id = usuario.perfil_id ");
		sql.append(" WHERE jogador.ID=?");

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString());
			pstm.setInt(1, jogador.getId());

                        jogador = null;
                        
			ResultSet rSet = pstm.executeQuery();

			if (rSet.next()) {
				jogador = new Jogador();
				jogador.setId(rSet.getInt("id"));
				jogador.setNome(rSet.getString("nome"));

				Usuario usuario = new Usuario();
				usuario.setId(rSet.getInt("usuario_id"));
				usuario.setLogin(rSet.getString("login"));
				usuario.setSenha(rSet.getString("senha"));

				Perfil perfil = new Perfil(rSet.getInt("perfil_id"));
				perfil.setPerfil(rSet.getString("perfil"));

				usuario.setPerfil(perfil);

				jogador.setUsuario(usuario);

			}

		} catch (SQLException e) {
			e.printStackTrace();
                        return null;
		}

		return jogador;
	}

	@Override
	public List<EntidadeDominio> findAll() {

		List<EntidadeDominio> lista = new ArrayList<EntidadeDominio>();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM jogador ");
		sql.append("inner join usuario on usuario.id = jogador.usuario_id ");
		sql.append("inner join perfil on perfil.id = usuario.perfil_id ");
		sql.append("order by jogador.id asc");

		try {
			if (con == null || con.isClosed())
				con = ConnectionFactory.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString());

			ResultSet rSet = pstm.executeQuery();

			while (rSet.next()) {
				Jogador jogador = new Jogador();
				jogador.setId(rSet.getInt("id"));
				jogador.setNome(rSet.getString("nome"));

				Usuario usuario = new Usuario();
				usuario.setId(rSet.getInt("usuario_id"));
				usuario.setLogin(rSet.getString("login"));
				usuario.setSenha(rSet.getString("senha"));

				Perfil perfil = new Perfil(rSet.getInt("perfil_id"));
				perfil.setPerfil(rSet.getString("perfil"));

				usuario.setPerfil(perfil);

				jogador.setUsuario(usuario);

				lista.add(jogador);
			}

			rSet.close();
			pstm.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public EntidadeDominio findByNome(EntidadeDominio entidade) {
		if (!(entidade instanceof Jogador)) {
			return null;
		}

		Jogador jogador = new Jogador();
		jogador = (Jogador) entidade;

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM jogador WHERE NOME=?");

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString());
			pstm.setString(1, jogador.getNome());

			ResultSet rSet = pstm.executeQuery();

			if (rSet.next()) {
				jogador = new Jogador();
				jogador.setId(rSet.getInt("id"));
				jogador.setNome(rSet.getString("nome"));

				Usuario usuario = new Usuario(rSet.getInt("usuario_id"));
				usuario = (Usuario) new UsuarioDao().find(usuario);

				jogador.setUsuario(usuario);

			}

			rSet.close();
			pstm.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return jogador;
	}

	public EntidadeDominio login(EntidadeDominio entidade) {
		if (!(entidade instanceof Jogador)) {
			return null;
		}

		Jogador jogador = new Jogador();
		jogador = (Jogador) entidade;

		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT jogador.ID, jogador.NOME, usuario.ID, usuario.LOGIN, usuario.SENHA, usuario.perfil_id FROM jogador ");
		sql.append("INNER JOIN usuario ON usuario.ID = jogador.usuario_id ");
		sql.append("WHERE LOGIN = ? AND SENHA = md5(?)");

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString());
			pstm.setString(1, jogador.getUsuario().getLogin().toUpperCase());
			pstm.setString(2, jogador.getUsuario().getSenha());

			ResultSet rSet = pstm.executeQuery();

			if (rSet.next()) {
				jogador = new Jogador();
				jogador.setId(rSet.getInt("jogador.id"));
				jogador.setNome(rSet.getString("jogador.nome"));

				Usuario usuario = new Usuario(rSet.getInt("usuario.id"));
				usuario = (Usuario) new UsuarioDao().find(usuario);

				jogador.setUsuario(usuario);

			}else{
                            return null;
                        }

			rSet.close();
			pstm.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
                        return null;
		}

		return jogador;
	}

	public List<EntidadeDominio> findAllPartidasAnalitica(EntidadeDominio entidade) {

		if (!(entidade instanceof Jogador)) {
			return null;
		}

		Jogador jogador = new Jogador();
		jogador = (Jogador) entidade;

		List<EntidadeDominio> lista = new ArrayList<EntidadeDominio>();

		StringBuffer sql = new StringBuffer();
		sql.append("select * from jogador_partida ");
		sql.append("inner join premiacao on premiacao.id = jogador_partida.premiacao_id ");
		sql.append("inner join colocacao on colocacao.id = premiacao.colocacao_id ");
		sql.append("inner join partida on partida.id = jogador_partida.partida_id ");
		sql.append("inner join local on local.id = partida.local_id ");
		sql.append("inner join jogador on jogador.id = jogador_partida.jogador_id ");
		sql.append("inner join usuario on usuario.id = jogador.usuario_id ");
		sql.append("inner join perfil on perfil.id = usuario.perfil_id ");
		sql.append("where jogador_id = ? ");
		sql.append("order by partida.data, colocacao.posicao ");

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString());
			pstm.setInt(1, jogador.getId());

			ResultSet rSet = pstm.executeQuery();


			while (rSet.next()) {

				Partida partida = new Partida();

				jogador = new Jogador();
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

				jogador.setPremiacao(premiacao);
				jogador.setDespesa(despesa);

				partida.getJogadores().add(jogador);

				lista.add(partida);
			}

			
			rSet.close();
			pstm.close();
			con.close();

		} catch (

		SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	/**
	 * Devolve a ultima partida do jogador
	 *
	 * @param entidade
	 *            um jogador
	 * @return uma partida
	 */
	public EntidadeDominio findLastPartida(EntidadeDominio entidade) {

		Partida partida = null;
		if (!(entidade instanceof Jogador)) {
			return null;
		}

		Jogador jogador = new Jogador();
		jogador = (Jogador) entidade;

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT *, partida.id FROM jogador_partida ");
		sql.append("inner join partida on partida.id = jogador_partida.partida_id ");
		sql.append("where jogador_id = ? ");
		sql.append("order by partida.data desc limit 1");

		con = ConnectionFactory.getConnection();

		PreparedStatement pstm;
		try {
			pstm = con.prepareStatement(sql.toString());
			pstm.setInt(1, jogador.getId());

			ResultSet rSet = pstm.executeQuery();

			while (rSet.next()) {

				partida = new Partida();

				partida.setId(rSet.getInt("partida_id"));

				Local local = new Local(rSet.getInt("local"));
				local = (Local) new LocalDao().find(local);

				jogador = new Jogador();
				jogador.setId(rSet.getInt("jogador_id"));
				jogador = (Jogador) new JogadorDao().find(jogador);

				Despesa despesa = new Despesa();
				despesa.setEntrada(rSet.getBigDecimal("entrada"));
				despesa.setRebuy(rSet.getBigDecimal("rebuy"));
				despesa.setAdd_on(rSet.getBigDecimal("add_on"));
				despesa.setId(rSet.getInt("id"));

				Premiacao premiacao = new Premiacao();
				premiacao.setId(rSet.getInt("premiacao_id"));
				premiacao = (Premiacao) new PremiacaoDao().find(premiacao);

				jogador.setPremiacao(premiacao);
				jogador.setDespesa(despesa);

				List<Jogador> jogadores = new ArrayList<Jogador>();
				jogadores.add(jogador);

				partida.setJogadores(jogadores);
				partida.setPremiacaoRanking(rSet.getBigDecimal("valor_ranking"));
				partida.setParticipacao(rSet.getInt("participacao"));
				partida.setLocal(local);

				partida.setData(rSet.getDate("data"));

			}

			rSet.close();
			pstm.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return partida;

	}

}
