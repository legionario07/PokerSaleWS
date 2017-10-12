package br.com.pokerws.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Perfil extends EntidadeDominio {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String perfil;

    public Perfil(String perfil) {
        this.perfil = perfil;
    }

    public Perfil(int id, String perfil) {
        this(id);
        this.perfil = perfil;
    }

    public Perfil(int id) {
        this.id = id;
    }

    public Perfil() {

    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    @Override
    public String toString() {

        StringBuilder retorno = new StringBuilder();
        retorno.append(super.toString());
        retorno.append("\nPerfil: ");
        retorno.append(getPerfil());

        return retorno.toString();
    }
}
