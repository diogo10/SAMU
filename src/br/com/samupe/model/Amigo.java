package br.com.samupe.model;

import java.io.Serializable;

public class Amigo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nome;
	private String sexo;
	private Integer idade;
	private String sangue;
	private String alergico;
	private String alergia;
	private String url;
	private String telefone;
	
	
	
	public Amigo(Integer id, String nome, String sexo, Integer idade,
			String sangue, String alergico, String alergia,String url,String telefone) {
		super();
		this.id = id;
		this.nome = nome;
		this.sexo = sexo;
		this.idade = idade;
		this.sangue = sangue;
		this.alergico = alergico;
		this.alergia = alergia;
		this.url = url;
		this.telefone = telefone;
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public String getSexo() {
		return sexo;
	}



	public void setSexo(String sexo) {
		this.sexo = sexo;
	}



	public Integer getIdade() {
		return idade;
	}



	public void setIdade(Integer idade) {
		this.idade = idade;
	}



	public String getSangue() {
		return sangue;
	}



	public void setSangue(String sangue) {
		this.sangue = sangue;
	}



	public String getAlergico() {
		return alergico;
	}



	public void setAlergico(String alergico) {
		this.alergico = alergico;
	}



	public String getAlergia() {
		return alergia;
	}



	public void setAlergia(String alergia) {
		this.alergia = alergia;
	}



	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url;
	}



	public String getTelefone() {
		return telefone;
	}



	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	
	
	
	
	
	
	
	
	
	

}
