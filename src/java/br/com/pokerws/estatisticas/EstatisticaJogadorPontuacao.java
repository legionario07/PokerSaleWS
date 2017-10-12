package br.com.pokerws.estatisticas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.pokerws.entity.Partida;

public class EstatisticaJogadorPontuacao extends Estatistica{
	
	private List<Partida> partidas;
	private Integer participacao;
	private Integer jogos;
	private Integer pontosConquistados;
	private Integer total;
	private Map<Integer, Integer> posicaoVsQtde;
	//private Map<Integer, List<Map<Integer,Partida>>> posicaoVsQtde2;
	
	public EstatisticaJogadorPontuacao(){
		
		partidas = new ArrayList<Partida>();
		
		posicaoVsQtde = new HashMap<Integer, Integer>();
		posicaoVsQtde.put(1, 0);
		posicaoVsQtde.put(2, 0);
		posicaoVsQtde.put(3, 0);
		posicaoVsQtde.put(4, 0);
		
		jogos = 0;
		pontosConquistados =0;
		total = 0;
		jogos =0;
	}
	
	public Integer getParticipacao() {
		return participacao;
	}
	public void setParticipacao(Integer participacao) {
		this.participacao = participacao;
	}
	public Integer getJogos() {
		return jogos;
	}
	public void setJogos(Integer jogos) {
		this.jogos = jogos;
	}
	public Integer getPontosConquistados() {
		return pontosConquistados;
	}
	public void setPontosConquistados(Integer pontosConquistados) {
		this.pontosConquistados = pontosConquistados;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}

	public Map<Integer, Integer> getPosicaoVsQtde() {
		return posicaoVsQtde;
	}

	public void setPosicaoVsQtde(Map<Integer, Integer> posicaoVsQtde) {
		this.posicaoVsQtde = posicaoVsQtde;
	}

	public List<Partida> getPartidas() {
		return partidas;
	}

	public void setPartidas(List<Partida> partidas) {
		this.partidas = partidas;
	}

	

}
