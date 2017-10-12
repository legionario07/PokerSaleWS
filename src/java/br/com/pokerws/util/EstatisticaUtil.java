package br.com.pokerws.util;

import br.com.pokerws.dao.JogadorDao;
import br.com.pokerws.dao.JogadorPartidaDao;
import br.com.pokerws.entity.EntidadeDominio;
import br.com.pokerws.entity.Jogador;
import br.com.pokerws.entity.Partida;
import br.com.pokerws.estatisticas.EstatisticaJogadorPontuacao;
import br.com.pokerws.estatisticas.EstatisticaJogadorSaldo;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EstatisticaUtil {

    private static List<EntidadeDominio> partidas;
    private static List<EntidadeDominio> jogadores;

    public EstatisticaUtil() {

        partidas = new ArrayList<EntidadeDominio>();

        jogadores = new ArrayList<EntidadeDominio>();

        jogadores = new JogadorDao().findAll();

        partidas = new JogadorPartidaDao().findAll();

    }

    /**
     * Metodo que devolve a pontuação de determinado jogador
     *
     * @param jogador - Recebe um entidade Jogador
     * @return - Uma EstatisticaJogadorPontuacao com o jogador Preenchido Total
     * de jogos Preenchidos Total de Pontos Conquistados
     *
     */
    public EstatisticaJogadorPontuacao getPontuacao(Jogador jogador) {

        EstatisticaJogadorPontuacao ejp = new EstatisticaJogadorPontuacao();
        ejp.setJogador(jogador);
        ejp.setJogos(getJogos(ejp.getJogador()));

        for (EntidadeDominio e : partidas) {

            if (e instanceof Partida) {
                Partida p = new Partida();
                p = (Partida) e;

                for (Jogador j : p.getJogadores()) {
                    if (j.getId() == jogador.getId()) {
                        if (j.getPremiacao().getColocacao().getPosicao().equals("1")
                                || j.getPremiacao().getColocacao().getPosicao().equals("2")
                                || j.getPremiacao().getColocacao().getPosicao().equals("3")) {
                            ejp.setPontosConquistados(ejp.getPontosConquistados()
                                    + j.getPremiacao().getColocacao().getPontos() + p.getParticipacao());
                        } else {
                            ejp.setPontosConquistados(
                                    ejp.getPontosConquistados() + j.getPremiacao().getColocacao().getPontos());
                        }
                    }
                }
            }
        }

        ejp.setParticipacao(ejp.getJogos() * 10);
        ejp.setTotal(ejp.getPontosConquistados() + ejp.getParticipacao());

        return ejp;

    }

    /**
     * Metodo que devolve o total de jogos de determinado jogador
     *
     * @param jogador
     * @return - Um Integer com o total de jogos
     */
    public Integer getJogos(Jogador jogador) {

        Integer jogos = 0;

        for (EntidadeDominio e : partidas) {
            if (e instanceof Partida) {
                Partida p = new Partida();
                p = (Partida) e;

                for (Jogador j : p.getJogadores()) {

                    if (jogador.getId() == j.getId()) {
                        jogos += 1;
                    }
                }

            }

        }

        return jogos;
    }

    /**
     * Metodo que devolve as Estatisticas Financeiras do Jogador
     *
     * @param jogador - Recebe um jogador
     * @return - Uma Entidade EstatisticaJogadorSaldo preenchidos os valores: -
     * Jogador -Total de Entradas (Entradas+rebuy+AddOn) -Total de Premiações
     * (Ganhos) -Saldo (Premiações - Ganhos)
     *
     */
    public EstatisticaJogadorSaldo getEstatisticasFinanceiraJogador(Jogador jogador) {

        EstatisticaJogadorSaldo ejsTemp = new EstatisticaJogadorSaldo();
        ejsTemp.setJogador(jogador);

        for (EntidadeDominio p : partidas) {
            if (p instanceof Partida) {
                Partida pTemp = new Partida();
                pTemp = (Partida) p;

                for (int i = 0; i < pTemp.getJogadores().size(); i++) {

                    if (jogador.getId() == pTemp.getJogadores().get(i).getId()) {
                        // Entradas
                        ejsTemp.setEntrada(
                                ejsTemp.getEntrada().add(pTemp.getJogadores().get(i).getDespesa().getEntrada()));
                        ejsTemp.setEntrada(
                                ejsTemp.getEntrada().add(pTemp.getJogadores().get(i).getDespesa().getRebuy()));
                        ejsTemp.setEntrada(
                                ejsTemp.getEntrada().add(pTemp.getJogadores().get(i).getDespesa().getAdd_on()));

                        // Premiacao
                        ejsTemp.setPremiacao(
                                ejsTemp.getPremiacao().add(pTemp.getJogadores().get(i).getPremiacao().getValor()));

                        i = pTemp.getJogadores().size();

                    }

                }

            }
        }

        ejsTemp.setSaldo(ejsTemp.getPremiacao().subtract(ejsTemp.getEntrada()));

        return ejsTemp;

    }

    /**
     * Metodo que devolve as Estatisticas Financeiras de Todos os Jogadores
     *
     * @return - Uma List<EstatisticaJogadorSaldo> preenchidos os seguintes
     * valores: - Jogador -Total de Entradas (Entradas+rebuy+AddOn) -Total de
     * Premiações (Ganhos) -Saldo (Premiações - Ganhos) -
     */
    public List<EstatisticaJogadorSaldo> getEstatisticasFinanceiraGeral() {

        List<EstatisticaJogadorSaldo> lista = new ArrayList<EstatisticaJogadorSaldo>();

        for (EntidadeDominio j : jogadores) {

            if (j instanceof Jogador) {
                Jogador jTemp = new Jogador();
                jTemp = (Jogador) j;

                EstatisticaJogadorSaldo ejsTemp = new EstatisticaJogadorSaldo();
                ejsTemp.setJogador(jTemp);

                for (EntidadeDominio p : partidas) {
                    if (p instanceof Partida) {
                        Partida pTemp = new Partida();
                        pTemp = (Partida) p;

                        for (int i = 0; i < pTemp.getJogadores().size(); i++) {

                            if (jTemp.getId() == pTemp.getJogadores().get(i).getId()) {
                                // Entradas
                                ejsTemp.setEntrada(ejsTemp.getEntrada()
                                        .add(pTemp.getJogadores().get(i).getDespesa().getEntrada()));
                                ejsTemp.setEntrada(
                                        ejsTemp.getEntrada().add(pTemp.getJogadores().get(i).getDespesa().getRebuy()));
                                ejsTemp.setEntrada(
                                        ejsTemp.getEntrada().add(pTemp.getJogadores().get(i).getDespesa().getAdd_on()));

                                // Premiacao
                                ejsTemp.setPremiacao(ejsTemp.getPremiacao()
                                        .add(pTemp.getJogadores().get(i).getPremiacao().getValor()));

                                i = pTemp.getJogadores().size();

                            }

                        }

                    }
                }

                ejsTemp.setSaldo(ejsTemp.getPremiacao().subtract(ejsTemp.getEntrada()));

                lista.add(ejsTemp);

            }
        }

        return lista;

    }

    /**
     * Metodo que devolve o valor total do pote
     *
     * @return - Um BigDecimal com o valor total do pote
     */
    public BigDecimal getValorPote() {
        BigDecimal valor = new BigDecimal("0.0");

        for (EntidadeDominio e : partidas) {
            if (e instanceof Partida) {
                valor = valor.add(((Partida) e).getPremiacaoRanking());
            }
        }

        return valor;
    }

    /**
     * Metodo que devolve a posição das pontuações de forma ordenada
     *
     * @return - Um List<EstatisticaJogadorPontuacao> ordenada contendo todos o
     * jogadores
     */
    public List<EstatisticaJogadorPontuacao> getPosicaoOrdenada() {

        List<EstatisticaJogadorPontuacao> listaPontuacao = new ArrayList<EstatisticaJogadorPontuacao>();
        for (EntidadeDominio e : jogadores) {
            if (e instanceof Jogador) {
                EstatisticaJogadorPontuacao ej = new EstatisticaJogadorPontuacao();
                ej = getPontuacao((Jogador) e);
                listaPontuacao.add(ej);
            }

        }

        Collections.sort(listaPontuacao, getPontosAsc());

        return listaPontuacao;
    }

    /**
     * Metodo que devolve a posição dos saldos de forma ordenada
     *
     * @return - Um List<EstatisticaJogadorSaldo> ordenada contendo todos o
     * jogadores
     */
    public List<EstatisticaJogadorSaldo> getSaldoOrdenado() {

        List<EstatisticaJogadorSaldo> listaSaldo = new ArrayList<EstatisticaJogadorSaldo>();
        listaSaldo = getEstatisticasFinanceiraGeral();

        Collections.sort(listaSaldo, getSaldoAsc());

        return listaSaldo;
    }

    public static Comparator<EstatisticaJogadorPontuacao> getPontosAsc() {
        return new Comparator<EstatisticaJogadorPontuacao>() {
            @Override
            public int compare(EstatisticaJogadorPontuacao o1, EstatisticaJogadorPontuacao o2) {
                int valor = o1.getTotal().compareTo(o2.getTotal()) * -1;
                // se for igual, comparar por jogos
                if (valor == 0) {
                    return o1.getJogos().compareTo(o2.getJogos());
                }
                return valor;
            }
        };
    }

    public static Comparator<EstatisticaJogadorSaldo> getSaldoAsc() {
        return new Comparator<EstatisticaJogadorSaldo>() {
            @Override
            public int compare(EstatisticaJogadorSaldo o1, EstatisticaJogadorSaldo o2) {
                int valor = o1.getSaldo().compareTo(o2.getSaldo()) * -1;
                return valor;
            }
        };
    }

}
