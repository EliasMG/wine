package br.com.fametro.wine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vendas")
public class VendasController {
	
	@RequestMapping("/nova")
	public String nova() {
		return "venda/CadastroVenda";
	}

}
