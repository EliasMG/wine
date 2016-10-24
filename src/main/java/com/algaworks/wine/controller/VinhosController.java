package com.algaworks.wine.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.wine.model.ClasseVinho;
import com.algaworks.wine.model.CorVinho;
import com.algaworks.wine.model.TeorAcucar;
import com.algaworks.wine.model.Vinho;
import com.algaworks.wine.repository.Vinhos;
import com.algaworks.wine.service.CadastroVinhoService;
import com.algaworks.wine.storage.FotoStorage;

@Controller
@RequestMapping("/vinhos")
public class VinhosController {

	@Autowired
	private Vinhos vinhos;
	
	@Autowired
	private CadastroVinhoService cadastroVinhoService;
	
	@Autowired
	private FotoStorage fotoStorage;
	
	@RequestMapping
	public ModelAndView pesquisa() {
		ModelAndView mv = new ModelAndView("/vinho/ListagemVinhos");
		mv.addObject("vinhos", vinhos.findAll());
		return mv;
	}
	
	@RequestMapping("/novo/{codigo}")
	public ModelAndView edicao(@PathVariable("codigo") Vinho vinho) {
		ModelAndView mv = new ModelAndView("/vinho/CadastroVinho");
		mv.addObject("classes", ClasseVinho.values());
		mv.addObject("corVinho", CorVinho.values());
		mv.addObject("teorAcucar", TeorAcucar.values());
		mv.addObject(vinho);
		
		return mv;
	}
	
	@RequestMapping("/novo")
	public ModelAndView novo(Vinho vinho) {
		ModelAndView mv = new ModelAndView("/vinho/CadastroVinho");
		mv.addObject("classes", ClasseVinho.values());
		mv.addObject("corVinho", CorVinho.values());
		mv.addObject("teorAcucar", TeorAcucar.values());
		return mv;
	}
	
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView salvar(@Valid Vinho vinho, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(vinho);
		}
		
		cadastroVinhoService.salvar(vinho);
		attributes.addFlashAttribute("mensagem", "Vinho salvo com sucesso!");
		return new ModelAndView("redirect:/vinhos/novo");
	}
	
	@RequestMapping(value="{codigo}", method = RequestMethod.DELETE)
	public String excluir(@PathVariable Long codigo, RedirectAttributes attributes) {
		vinhos.delete(codigo);
		
		attributes.addFlashAttribute("mensagem", "Título excluído com sucesso!");
		return "redirect:/vinhos";
	}
	
	@RequestMapping("/{codigo}")
	public ModelAndView visualizar(@PathVariable("codigo") Vinho vinho) {
		ModelAndView mv = new ModelAndView("/vinho/VisualizacaoVinho");
		
		if (vinho.temFoto()) {
			vinho.setUrl(fotoStorage.getUrl(vinho.getFoto()));
		}
		
		mv.addObject("vinho", vinho);
		return mv;
	}
	
}
