package br.com.nsystem.comercialapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nsystem.comercialapi.model.Oportunidade;

public interface Oportunidades extends JpaRepository<Oportunidade, Long>{
	
	Optional<Oportunidade> findByDescricaoAndEmpresa(String descricao, String empresa); 
}
