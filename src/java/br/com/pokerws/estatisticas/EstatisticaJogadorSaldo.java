package br.com.pokerws.estatisticas;

import java.math.BigDecimal;

import br.com.pokerws.entity.Jogador;

public class EstatisticaJogadorSaldo extends Estatistica {
	private BigDecimal entrada;
	private BigDecimal premiacao;
	private BigDecimal saldo;
	private Jogador jogador;

	public EstatisticaJogadorSaldo() {

		entrada = new BigDecimal("0.0");
		premiacao = new BigDecimal("0.0");
		saldo = new BigDecimal("0.0");
		
		jogador = new Jogador();
	}

	public BigDecimal getEntrada() {
		return entrada;
	}

	public void setEntrada(BigDecimal entrada) {
		this.entrada = entrada;
	}

	public BigDecimal getPremiacao() {
		return premiacao;
	}

	public void setPremiacao(BigDecimal premiacao) {
		this.premiacao = premiacao;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public Jogador getJogador() {
		return jogador;
	}

	public void setJogador(Jogador jogador) {
		this.jogador = jogador;
	}

}
