package br.com.fametro.wine.model;

public enum CorVinho {

	TINTO("Tinto"), ROSADO("Rosado"), BRANCO("Branco");

	private String descricao;

	CorVinho(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
