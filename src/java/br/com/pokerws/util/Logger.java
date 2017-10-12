/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pokerws.util;

import br.com.pokerws.entity.EntidadeDominio;
import br.com.pokerws.entity.Jogador;
import java.util.Date;

/**
 *
 * @author PauLinHo
 */
public class Logger extends EntidadeDominio{
    
    private Jogador jogador;
    private Date dataAcesso;
    
    public Logger(){
        jogador = new Jogador();
    }

    /**
     * @return the jogador
     */
    public Jogador getJogador() {
        return jogador;
    }

    /**
     * @param jogador the jogador to set
     */
    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

    /**
     * @return the dataAcesso
     */
    public Date getDataAcesso() {
        return dataAcesso;
    }

    /**
     * @param dataAcesso the dataAcesso to set
     */
    public void setDataAcesso(Date dataAcesso) {
        this.dataAcesso = dataAcesso;
    }
    
}
