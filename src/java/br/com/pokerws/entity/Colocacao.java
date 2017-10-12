package br.com.pokerws.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Colocacao extends EntidadeDominio {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String posicao;
	private Integer pontos;
	

	public Colocacao(Integer id, String posicao, Integer pontos){
		this(id);
		this.posicao = posicao;
		this.pontos = pontos;
	}
	
	public Colocacao(String posicao, Integer pontos){
		this.posicao = posicao;
		this.pontos = pontos;
			
	}
	
	public Colocacao(Integer id){
		this.id = id;
	}
	
	public Colocacao(){
		
	}
	
	public String getPosicao() {
		return posicao;
	}
	public void setPosicao(String posicao) {
		this.posicao = posicao;
	}
	public Integer getPontos() {
		return pontos;
	}
	public void setPontos(Integer pontos) {
		this.pontos = pontos;
	}
	
	@Override
	public String toString() {

		StringBuilder retorno = new StringBuilder();
		retorno.append(super.toString());
		retorno.append("\nPosição: ");
		retorno.append(getPosicao());
		retorno.append("\nPontos: ");
		retorno.append(getPontos());

		return retorno.toString();
	}	
	
	
}
