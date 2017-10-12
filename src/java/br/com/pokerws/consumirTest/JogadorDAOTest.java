/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pokerws.consumirTest;

import br.com.pokerws.dao.JogadorDao;
import br.com.pokerws.entity.Jogador;
import br.com.pokerws.entity.Partida;
import java.text.SimpleDateFormat;

/**
 *
 * @author PauLinHo
 */
public class JogadorDAOTest {
    
    public static Partida findLastPartida(){
     
         return (Partida) new JogadorDao().findLastPartida(new Jogador(1));
        
        
    }
    
    public static void main(String[] args){
        Partida p = new Partida();
        
        p = findLastPartida();
        
        if(p==null)
            System.out.println("Não foi encontrado ultimo jogo");
       
        System.out.println(p.getData());
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        System.out.println(sdf.format(p.getData()));
        System.out.println(p.getId());
        System.out.println(p.getLocal());
        System.out.println(p.getParticipacao());
        System.out.println(p.getJogadores().get(0).getNome());
        System.out.println(p.getJogadores().get(0).getId());
        System.out.println(p.getJogadores().get(0).getDespesa().getAdd_on());
        System.out.println(p.getJogadores().get(0).getDespesa().getEntrada());
        System.out.println(p.getJogadores().get(0).getDespesa().getRebuy());
        
        
        
    }
    
}
