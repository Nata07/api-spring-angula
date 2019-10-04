package br.com.nsystem.comercialapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.nsystem.comercialapi.model.Oportunidade;
import br.com.nsystem.comercialapi.repository.Oportunidades;

@CrossOrigin
@RestController
@RequestMapping("/oportunidades")
public class OportunidadeController {
	
	@Autowired
	private Oportunidades oportunidades;
	
	@GetMapping
	public List<Oportunidade> listar() {
		
		return oportunidades.findAll();
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Oportunidade> buscar(@PathVariable Long codigo) {
		
		Optional<Oportunidade> oportunidade =  oportunidades.findById(codigo);
		
		if (!oportunidade.isPresent()){
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(oportunidade.get());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Oportunidade cadastrar(@RequestBody Oportunidade oportunidade) {
		Optional<Oportunidade> oportunidadeExiste = oportunidades.findByDescricaoAndEmpresa(oportunidade.getDescricao(), oportunidade.getEmpresa());
		if(oportunidadeExiste.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já possui uma oportunidade com essa descrição para esta empresa.");
		}
		return oportunidades.save(oportunidade);
	}
}
