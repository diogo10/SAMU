package br.com.samupe.model;

import java.io.Serializable;

public class Hospitais implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String descricao;
	private String telefone;
	private Double x;
	private Double y;
	
	
	
	public Hospitais(Integer id, String descricao, String telefone,Double x, Double y) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.telefone = telefone;
		this.x = x;
		this.y = y;
	}
	
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Double getX() {
		return x;
	}
	public void setX(Double x) {
		this.x = x;
	}
	public Double getY() {
		return y;
	}
	public void setY(Double y) {
		this.y = y;
	}




	public String getTelefone() {
		return telefone;
	}




	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	
	
	
	

}
