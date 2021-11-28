package br.com.senior.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.senior.spring.entity.Pessoa;


@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> { }
