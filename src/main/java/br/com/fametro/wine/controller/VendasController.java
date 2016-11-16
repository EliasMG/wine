package br.com.fametro.wine.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.fametro.wine.model.Venda;
import br.com.fametro.wine.model.Vinho;
import br.com.fametro.wine.repository.Vinhos;
import br.com.fametro.wine.service.CadastroVendaService;
import br.com.fametro.wine.session.TabelaItensVenda;

@Controller
@RequestMapping("/vendas")
public class VendasController {
	
	@Autowired
	private Vinhos vinhos;
	
	@Autowired
	private TabelaItensVenda tabelaItensVenda;
	
	@Autowired
	private CadastroVendaService cadastroVendaService;
	
	@GetMapping("/nova")
	public ModelAndView nova(Venda venda) {
		ModelAndView mv = new ModelAndView("venda/CadastroVenda");
		
		return mv;
	}

	@PostMapping("/nova")
	public ModelAndView salvar(Venda venda, RedirectAttributes attributes, @AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		ModelAndView mv = new ModelAndView("redirect:/vendas/nova");
		System.out.println("kkkkkkkkkkkkk");
		//venda.setUsuario(usuarioSistema.getUsuario());
		venda.adicionarItens(tabelaItensVenda.getItens());
		
		cadastroVendaService.salvar(venda);
		attributes.addFlashAttribute("mensagem", "Venda salva com sucesso");
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

	private ModelAndView mvTabelaItensVenda() {
		ModelAndView mv = new ModelAndView("venda/TabelaItensVenda");
		mv.addObject("itens", tabelaItensVenda.getItens());
		mv.addObject("valorTotal", tabelaItensVenda.getValorTotal());
		return mv;
	}

}
