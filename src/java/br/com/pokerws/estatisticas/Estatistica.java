package br.com.pokerws.estatisticas;

import br.com.pokerws.entity.EntidadeDominio;
import br.com.pokerws.entity.Jogador;

public class Estatistica extends EntidadeDominio {

    private Jogador jogador;

    public Estatistica() {
        jogador = new Jogador();
    }

    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

}
