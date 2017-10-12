package br.com.pokerws.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Jogador extends EntidadeDominio {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String nome;
    private Premiacao premiacao;
    private Despesa despesa;
    private Usuario usuario;

    public Jogador(Integer id, String nome) {
        this(id);
        this.nome = nome;
    }

    public Jogador(String nome) {
        this.nome = nome;
    }

    public Jogador(Integer id) {
        this.id = id;
    }

    public Jogador() {
        premiacao = new Premiacao();
        despesa = new Despesa();
        usuario = new Usuario();

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Premiacao getPremiacao() {
        return premiacao;
    }

    public void setPremiacao(Premiacao premiacao) {
        this.premiacao = premiacao;
    }

    public Despesa getDespesa() {
        return despesa;
    }

    public void setDespesa(Despesa despesa) {
        this.despesa = despesa;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {

        StringBuilder retorno = new StringBuilder();
        retorno.append(super.toString());
        retorno.append("\nNome: ");
        retorno.append(getNome());
        retorno.append("\nPremiacao: ");
        retorno.append(getPremiacao());
        retorno.append("\nDespesa: ");
        retorno.append(getDespesa());
        retorno.append("\nUsuário: ");
        retorno.append(getUsuario());

        return retorno.toString();
    }

}
