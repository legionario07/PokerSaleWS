package br.com.pokerws.entity;

import java.math.BigDecimal;

public class Despesa extends EntidadeDominio {

	private BigDecimal entrada;
	private BigDecimal rebuy;
	private BigDecimal add_on;
	
	public Despesa(BigDecimal entrada, BigDecimal rebuy, BigDecimal add_on){
		this.entrada = entrada;
		this.rebuy = rebuy;
		this.add_on = add_on;
	}
	
	public Despesa(Integer id){
		this.id = id;
	}
	
	public Despesa(){
		this.entrada = new BigDecimal("0.0");
		this.rebuy = new BigDecimal("0.0");
		this.add_on = new BigDecimal("0.0");
	}
	
	public BigDecimal getEntrada() {
		return entrada;
	}
	public void setEntrada(BigDecimal entrada) {
		this.entrada = entrada;
	}
	public BigDecimal getAdd_on() {
		return add_on;
	}
	public void setAdd_on(BigDecimal add_on) {
		this.add_on = add_on;
	}

	public BigDecimal getRebuy() {
		return rebuy;
	}

	public void setRebuy(BigDecimal rebuy) {
		this.rebuy = rebuy;
	}
	
}
