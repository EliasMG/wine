package br.com.fametro.wine.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fametro.wine.model.ItemVenda;
import br.com.fametro.wine.model.StatusVenda;
import br.com.fametro.wine.model.Venda;
import br.com.fametro.wine.repository.Vendas;

@Service
public class CadastroVendaService {
	
	@Autowired
	private Vendas vendas;
	
	@Transactional
	public void salvar(Venda venda) {
		/*if (venda.isNova()) {
			venda.setDataCriacao(LocalDateTime.now());
		}*/
		
		BigDecimal valorTotalItens = venda.getItens().stream()
				.map(ItemVenda::getValorTotal)
				.reduce(BigDecimal::add)
				.get();
		BigDecimal valorTotal = calcularValorTotal(valorTotalItens, venda.getValorFrete(), venda.getValorDesconto());
		venda.setValorTotal(valorTotal);
		
		/*if (venda.getDataEntrega() != null) {
			venda.setDataHoraEntrega(LocalDateTime.of(venda.getDataEntrega(), venda.getHoraEntrega()));
		}*/
		
		vendas.save(venda);
	}
	
	private BigDecimal calcularValorTotal(BigDecimal valorTotalItens, BigDecimal valorFrete, BigDecimal valorDesconto) {
		BigDecimal valorTotal = valorTotalItens
				.add(Optional.ofNullable(valorFrete).orElse(BigDecimal.ZERO))
				.subtract(Optional.ofNullable(valorDesconto).orElse(BigDecimal.ZERO));
		return valorTotal;
	}
	
	@Transactional
	public void emitir(Venda venda) {
		venda.setStatus(StatusVenda.EMITIDA);
		salvar(venda);
	}
	
	@Transactional
	public void cancelar(Venda venda) {
		Venda vendaExistente = vendas.findOne(venda.getCodigo());
		
		vendaExistente.setStatus(StatusVenda.CANCELADA);
		vendas.save(vendaExistente);
	}

}
