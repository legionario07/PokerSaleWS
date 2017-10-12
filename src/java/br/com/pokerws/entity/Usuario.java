package br.com.pokerws.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Usuario extends EntidadeDominio {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String login;
    private String senha;
    private Perfil perfil;

    public Usuario(String login, String senha, Perfil perfil) {
        this(login, senha);
        this.perfil = perfil;

    }

    public Usuario(String login, String senha) {
        this.login = login;
        this.senha = senha;

    }

    public Usuario(int id) {
        this.id = id;
    }

    public Usuario() {
        this.perfil = new Perfil();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    @Override
    public String toString() {

        StringBuilder retorno = new StringBuilder();
        retorno.append(super.toString());
        retorno.append("\nLogin: ");
        retorno.append(getLogin());
        retorno.append("\nSenha: ");
        retorno.append(getSenha());
        retorno.append("\nPerfil: ");
        retorno.append(getPerfil().toString());

        return retorno.toString();
    }
}
