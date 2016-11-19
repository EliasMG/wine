package br.com.fametro.wine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fametro.wine.model.ItemVenda;
import br.com.fametro.wine.model.StatusVenda;
import br.com.fametro.wine.model.TipoPessoa;
import br.com.fametro.wine.model.Venda;
import br.com.fametro.wine.model.Vinho;
import br.com.fametro.wine.repository.Vendas;
import br.com.fametro.wine.repository.Vinhos;
import br.com.fametro.wine.repository.filter.VendaFilter;
import br.com.fametro.wine.security.UsuarioSistema;
import br.com.fametro.wine.service.CadastroVendaService;
import br.com.fametro.wine.session.TabelaItensVenda;

@Controller
@RequestMapping("/vendas")
public class VendasController {
	
	@Autowired
	private Vinhos vinhos;
	
	@Autowired
	private Vendas vendas;
	
	@Autowired
	private TabelaItensVenda tabelaItensVenda;
	
	@Autowired
	private CadastroVendaService cadastroVendaService;
	
	@GetMapping("/nova")
	public ModelAndView nova(Venda venda) {
		ModelAndView mv = new ModelAndView("venda/CadastroVenda");
		
		mv.addObject("itens", venda.getItens());
		mv.addObject("valorFrete", venda.getValorFrete());
		mv.addObject("valorDesconto", venda.getValorDesconto());
		mv.addObject("valorTotalItens", tabelaItensVenda.getValorTotal());
		
		return mv;
	}

	@PostMapping(value = "/nova", params = "salvar")
	public ModelAndView salvar(Venda venda, RedirectAttributes attributes, @AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		ModelAndView mv = new ModelAndView("redirect:/vendas/nova");
		
		venda.setUsuario(usuarioSistema.getUsuario());
		
		venda.adicionarItens(tabelaItensVenda.getItens());
		
		cadastroVendaService.salvar(venda);
		attributes.addFlashAttribute("mensagem", "Venda salva com sucesso");
		return mv;
	}
	
	@PostMapping(value = "/nova", params = "emitir")
	public ModelAndView emitir(Venda venda, RedirectAttributes attributes, @AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		ModelAndView mv = new ModelAndView("redirect:/vendas/nova");
		
		venda.setUsuario(usuarioSistema.getUsuario());
		
		venda.adicionarItens(tabelaItensVenda.getItens());
		
		cadastroVendaService.emitir(venda);
		attributes.addFlashAttribute("mensagem", "Venda emitida com sucesso");
		return mv;
	}
	
	@PostMapping(value = "/nova", params = "cancelar")
	public ModelAndView cancelar(Venda venda, RedirectAttributes attributes, @AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		ModelAndView mv = new ModelAndView("redirect:/vendas/" + venda.getCodigo());
		
		try {
		cadastroVendaService.cancelar(venda);
		} catch (AccessDeniedException e) {
			return new ModelAndView("/error/403");
		}
		attributes.addFlashAttribute("mensagem", "Venda cancelada com sucesso");
		return mv;
	}
	
	@PostMapping("/item")
	public ModelAndView adicionarItem(Long codigoVinho) {
		Vinho vinho = vinhos.findOne(codigoVinho);
		tabelaItensVenda.adicionarItem(vinho, 1);

		return mvTabelaItensVenda();
	}
	
	@PutMapping("/item/{codigoVinho}")
	public ModelAndView alterarQuantidadeItem(@PathVariable Long codigoVinho, Integer quantidade) {
		Vinho vinho = vinhos.findOne(codigoVinho);
		tabelaItensVenda.alterarQuantidadeItens(vinho, quantidade);
		return mvTabelaItensVenda();
	}
	
	@DeleteMapping("/item/{codigoVinho}")
	public ModelAndView excluirItem(@PathVariable("codigoVinho") Vinho vinho) {
		tabelaItensVenda.excluirItem(vinho);
		return mvTabelaItensVenda();
	}
	
	@GetMapping
	public ModelAndView pesquisar(VendaFilter vendaFilter) {
		ModelAndView mv = new ModelAndView("/venda/PesquisaVendas");
		mv.addObject("todosStatus", StatusVenda.values());
		mv.addObject("tiposPessoa", TipoPessoa.values());
		mv.addObject("vendas", vendas.findAll());
		
		
		return mv;
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo) {
		Venda venda = vendas.buscarComItens(codigo);
		
		for (ItemVenda item : venda.getItens()) {
			tabelaItensVenda.adicionarItem(item.getVinho(), item.getQuantidade());
		}
		
		ModelAndView mv = nova(venda);
		mv.addObject(venda);
		return mv;
	}

	private ModelAndView mvTabelaItensVenda() {
		ModelAndView mv = new ModelAndView("venda/TabelaItensVenda");
		mv.addObject("itens", tabelaItensVenda.getItens());
		mv.addObject("valorTotal", tabelaItensVenda.getValorTotal());
		return mv;
	}

}
