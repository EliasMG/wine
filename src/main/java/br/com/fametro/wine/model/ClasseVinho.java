package br.com.fametro.wine.model;

public enum ClasseVinho {

	MESA("Mesa"), LEVE("Leve"), CHAMPANHE("Chapanhe"), LOCOROSO("Locoroso"), COMPOSTO("Composto");

	private String descricao;

	ClasseVinho(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
