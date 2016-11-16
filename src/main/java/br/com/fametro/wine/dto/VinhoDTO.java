package br.com.fametro.wine.dto;

import java.math.BigDecimal;

import org.springframework.util.StringUtils;

public class VinhoDTO {
	
	private Long codigo;
	private String nome;
	private Integer safra;
	private BigDecimal valor;
	private String foto;

	public VinhoDTO(Long codigo, String nome, Integer safra, BigDecimal valor, String foto) {
		this.codigo = codigo;
		this.nome = nome;
		this.safra = safra;
		this.valor = valor;
		this.foto = StringUtils.isEmpty(foto) ? "mockup-garrafa.png" : foto;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getSafra() {
		return safra;
	}

	public void setSafra(Integer safra) {
		this.safra = safra;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}
	
}
