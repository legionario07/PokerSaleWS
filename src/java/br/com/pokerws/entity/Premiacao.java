package br.com.pokerws.entity;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Premiacao extends EntidadeDominio {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private BigDecimal valor;
    private Colocacao colocacao;

    public Premiacao(Integer id, BigDecimal valor) {
        this(id);
        this.valor = valor;

    }

    public Premiacao(BigDecimal valor) {
        this.valor = valor;
    }

    public Premiacao(Integer id) {
        this.id = id;
    }

    public Premiacao() {
        colocacao = new Colocacao();
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Colocacao getColocacao() {
        return colocacao;
    }

    public void setColocacao(Colocacao colocacao) {
        this.colocacao = colocacao;
    }

    @Override
    public String toString() {

        StringBuilder retorno = new StringBuilder();
        retorno.append(super.toString());
        retorno.append("\nValor: ");
        retorno.append(getValor());
        retorno.append("\nColocação: ");
        retorno.append(getColocacao());

        return retorno.toString();
    }

}
