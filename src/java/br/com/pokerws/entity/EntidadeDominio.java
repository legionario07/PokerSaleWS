package br.com.pokerws.entity;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EntidadeDominio implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    protected Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        StringBuilder retorno = new StringBuilder();
        retorno.append("ID - ");
        retorno.append(getId());

        return retorno.toString();
    }
}
