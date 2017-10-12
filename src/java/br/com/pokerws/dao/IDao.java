package br.com.pokerws.dao;

import java.util.List;

import br.com.pokerws.entity.EntidadeDominio;


public interface IDao {

	public Integer save(EntidadeDominio entidade);
	public void edite(EntidadeDominio entidade);
	public String delete(EntidadeDominio entidade);
	public EntidadeDominio find(EntidadeDominio entidade);
	public List<EntidadeDominio> findAll();
	
}
