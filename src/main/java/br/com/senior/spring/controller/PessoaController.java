package br.com.senior.spring.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.senior.spring.entity.Pessoa;
import br.com.senior.spring.repository.PessoaRepository;

@RestController
public class PessoaController {

	@Autowired
	private PessoaRepository _pessoaRepository;

	@RequestMapping(value = "/pessoa", method = RequestMethod.GET)
	public List<Pessoa> Get() {
		return _pessoaRepository.findAll();
	}

	@RequestMapping(value = "/pessoa/{id}", method = RequestMethod.GET)
	public ResponseEntity<Pessoa> GetById(@PathVariable(value = "id") long id) {
		Optional<Pessoa> pessoa = _pessoaRepository.findPessoaById(id);
		if (pessoa.isPresent()) {
			return new ResponseEntity<Pessoa>(pessoa.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/pessoa", method = RequestMethod.POST)
	public Pessoa Post(@Valid @RequestBody Pessoa pessoa) {
		return _pessoaRepository.save(pessoa);
	}

	@RequestMapping(value = "/pessoa/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Pessoa> Put(@PathVariable(value = "id") long id, @Valid @RequestBody Pessoa newPessoa) {
		Optional<Pessoa> oldPessoa = _pessoaRepository.findById(id);
		if (oldPessoa.isPresent()) {
			Pessoa pessoa = oldPessoa.get();
			pessoa.setNome(newPessoa.getNome());
			_pessoaRepository.save(pessoa);
			return new ResponseEntity<Pessoa>(pessoa, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/pessoa", method = RequestMethod.PATCH)
	public ResponseEntity<Pessoa> Patch(@Valid @RequestBody Pessoa pessoa) {
		Optional<Pessoa> oldPessoa = _pessoaRepository.findById(pessoa.getId());
		if (oldPessoa.isPresent()) {
			return this.Put(pessoa.getId(), pessoa);
		} else {
			this.Post(pessoa);
			return new ResponseEntity<Pessoa>(pessoa, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/pessoa/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> Delete(@PathVariable(value = "id") long id) {
		Optional<Pessoa> pessoa = _pessoaRepository.findById(id);
		if (pessoa.isPresent()) {
			_pessoaRepository.delete(pessoa.get());
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
