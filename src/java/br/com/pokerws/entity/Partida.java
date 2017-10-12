package br.com.pokerws.entity;

import br.com.pokerws.util.GsonUtil;
import com.google.gson.annotations.JsonAdapter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Partida extends EntidadeDominio {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Date data;
    private Integer participacao;
    private List<Jogador> jogadores;
    private BigDecimal premiacaoRanking;
    private Local local;

    public Partida(Integer id, Date data, Local local, Integer participacao) {
        this(data, local, participacao);
        this.id = id;
    }

    public Partida(Date data, Local local, Integer participacao) {
        this.data = data;
        this.local = local;
        this.participacao = participacao;
    }

    public Partida(Integer id) {
        this.id = id;
    }

    public Partida() {
        jogadores = new ArrayList<Jogador>();
        local = new Local();
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Integer getParticipacao() {
        return participacao;
    }

    public void setParticipacao(Integer participacao) {
        this.participacao = participacao;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public void setJogadores(List<Jogador> jogadores) {
        this.jogadores = jogadores;
    }

    public BigDecimal getPremiacaoRanking() {
        return premiacaoRanking;
    }

    public void setPremiacaoRanking(BigDecimal premiacaoRanking) {
        this.premiacaoRanking = premiacaoRanking;
    }

    @Override
    public String toString() {

        StringBuilder retorno = new StringBuilder();
        retorno.append(super.toString());
        retorno.append("\nData: ");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        retorno.append(sdf.format(getData()));
        retorno.append("\tParticipação: ");
        retorno.append(getParticipacao());
        retorno.append("\tLocal: ");
        retorno.append(getLocal());
        retorno.append("\tPremiação Ranking: ");
        retorno.append(getPremiacaoRanking());
        for (Jogador j : getJogadores()) {
            retorno.append(j);
        }

        return retorno.toString();
    }

}
