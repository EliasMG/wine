package br.com.fametro.wine.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fametro.wine.model.Cliente;
import br.com.fametro.wine.model.TipoPessoa;
import br.com.fametro.wine.repository.Clientes;
import br.com.fametro.wine.repository.filter.ClienteFilter;
import br.com.fametro.wine.service.CadastroClienteService;

@Controller
@RequestMapping("/clientes")
public class ClientesController {
	
	@Autowired
	private Clientes clientes;
	
	@Autowired
	private CadastroClienteService cadastroClienteService;
	
	@RequestMapping
	public ModelAndView pesquisa(ClienteFilter clienteFilter, BindingResult result) {
		ModelAndView mv = new ModelAndView("/cliente/PesquisaClientes");
		mv.addObject("clientes", clientes.findAll());
		mv.addObject("tiposPessoa", TipoPessoa.values());
		return mv;
	}
	
	@RequestMapping("/novo")
	public ModelAndView novo(Cliente cliente) {
		ModelAndView mv = new ModelAndView("/cliente/CadastroCliente");
		mv.addObject("tiposPessoa", TipoPessoa.values());
		return mv;
	}
	
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView salvar(@Valid Cliente cliente, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(cliente);
		}
		
		cadastroClienteService.salvar(cliente);
		attributes.addFlashAttribute("mensagem", "Cliente salvo com sucesso!");
		return new ModelAndView("redirect:/clientes/novo");
	}
	
	@RequestMapping("/novo/{codigo}")
	public ModelAndView edicao(@PathVariable("codigo") Cliente cliente) {
		ModelAndView mv = new ModelAndView("/cliente/CadastroCliente");
		
		mv.addObject(cliente);
		mv.addObject("tiposPessoa", TipoPessoa.values());
		
		return mv;
	}
	
	@RequestMapping(value="{codigo}", method = RequestMethod.DELETE)
	public String excluir(@PathVariable Long codigo, RedirectAttributes attributes) {
		clientes.delete(codigo);
		
		attributes.addFlashAttribute("mensagem", "Cliente excluído com sucesso");
		return "redirect:/clientes";
	}
	
	@RequestMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody List<Cliente> pesquisar(String nome) {
		validarTamanhoNome(nome);
		
		return clientes.findByNomeStartingWithIgnoreCase(nome);
	}
	
	private void validarTamanhoNome(String nome) {
		if (StringUtils.isEmpty(nome) || nome.length() < 3) {
			throw new IllegalArgumentException();
		}
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Void> tratarIllegalArgumentException(IllegalArgumentException e) {
		return ResponseEntity.badRequest().build();
	}
}
