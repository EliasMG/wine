package br.com.fametro.wine.model;

public enum TeorAcucar {
	
	SECO("Seco"),
	MEIODOCE("Meio Doce"),
	SUAVE("Suave");
	
	private String descricao;
	
	private TeorAcucar(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
