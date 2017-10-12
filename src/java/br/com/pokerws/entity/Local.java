package br.com.pokerws.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Local extends EntidadeDominio {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String local;

    public Local(String local) {
        this.local = local;
    }

    public Local(Integer id) {
        this.id = id;
    }

    public Local() {

    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    @Override
    public String toString() {

        StringBuilder retorno = new StringBuilder();
        retorno.append(super.toString());
        retorno.append("\nLocal: ");
        retorno.append(getLocal());

        return retorno.toString();
    }

}
