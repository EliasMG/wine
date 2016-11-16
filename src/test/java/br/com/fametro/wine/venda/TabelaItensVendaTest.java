package br.com.fametro.wine.venda;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import br.com.fametro.wine.model.Vinho;
import br.com.fametro.wine.session.TabelaItensVenda;

public class TabelaItensVendaTest {
	
private TabelaItensVenda tabelaItensVenda;
	
	@Before
	public void setUp() {
		this.tabelaItensVenda = new TabelaItensVenda();
	}
	
	@Test
	public void deveCalcularValorTotalSemItens() throws Exception {
		assertEquals(BigDecimal.ZERO, tabelaItensVenda.getValorTotal());
	}
	
	@Test
	public void deveCalcularValorTotalComUmItem() throws Exception {
		Vinho vinho = new Vinho();
		BigDecimal valor = new BigDecimal("8.90");
		vinho.setValor(valor);
		
		tabelaItensVenda.adicionarItem(vinho, 1);
		
		assertEquals(valor, tabelaItensVenda.getValorTotal());
	}
	
	@Test
	public void deveCalcularValorTotalComVariosItens() throws Exception {
		Vinho c1 = new Vinho();
		c1.setCodigo(1L);
		BigDecimal v1 = new BigDecimal("8.90");
		c1.setValor(v1);
		
		Vinho c2 = new Vinho();
		c2.setCodigo(2L);
		BigDecimal v2 = new BigDecimal("4.99");
		c2.setValor(v2);
		
		tabelaItensVenda.adicionarItem(c1, 1);
		tabelaItensVenda.adicionarItem(c2, 2);
		
		assertEquals(new BigDecimal("18.88"), tabelaItensVenda.getValorTotal());
	}
	
	@Test
	public void deveManterTamanhoDaListaParaMesmosVinhos() throws Exception {
		Vinho v1 = new Vinho();
		v1.setCodigo(1L);
		v1.setValor(new BigDecimal("4.50")); 
		
		tabelaItensVenda.adicionarItem(v1, 1);
		tabelaItensVenda.adicionarItem(v1, 1);
		
		assertEquals(1, tabelaItensVenda.total());
	}
	
	@Test
	public void deveAlterarQuantidadeDoItem() throws Exception {
		Vinho v1 = new Vinho();
		v1.setCodigo(1L);
		v1.setValor(new BigDecimal("4.50")); 
		
		tabelaItensVenda.adicionarItem(v1, 1);
		tabelaItensVenda.alterarQuantidadeItens(v1, 3);
		
		assertEquals(1, tabelaItensVenda.total());
		assertEquals(new BigDecimal("13.50"), tabelaItensVenda.getValorTotal());
	}
	
	@Test
	public void deveExcluirItem() throws Exception {
		Vinho v1 = new Vinho();
		v1.setCodigo(1L);
		v1.setValor(new BigDecimal("8.90"));
		
		Vinho v2 = new Vinho();
		v2.setCodigo(2L);
		v2.setValor(new BigDecimal("4.99"));
		
		Vinho v3 = new Vinho();
		v3.setCodigo(3L);
		v3.setValor(new BigDecimal("2.00"));
		
		tabelaItensVenda.adicionarItem(v1, 1);
		tabelaItensVenda.adicionarItem(v2, 2);
		tabelaItensVenda.adicionarItem(v3, 1);
		
		tabelaItensVenda.excluirItem(v2);
		
		assertEquals(2, tabelaItensVenda.total());
		assertEquals(new BigDecimal("10.90"), tabelaItensVenda.getValorTotal());
	}

}
