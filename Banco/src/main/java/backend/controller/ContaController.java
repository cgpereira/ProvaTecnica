package backend.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.dto.ContaDTO;
import backend.exception.ContaException;
import backend.exception.TransacaoException;
import backend.service.ContaService;

@RestController
@RequestMapping("/conta")
public class ContaController {
	
	@Autowired
	ContaService contaService;
	
	@GetMapping("/listar")
	public ResponseEntity<List<ContaDTO>> listar(){
		return ResponseEntity.ok(contaService.listar());
	}
	
	@PostMapping("/salvar")
	public ResponseEntity<Void> salvar(@RequestBody @Valid ContaDTO contaDTO) throws ContaException{
		contaService.salvar(contaDTO);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping("/consultar/{id}")
	public ResponseEntity<ContaDTO> consultarPorId(@PathVariable Long id) throws ContaException{
		return ResponseEntity.ok(contaService.consultarPorId(id));
	}
	
	@PostMapping("/debitar/{id}")
	public ResponseEntity<Void> debitar(@PathVariable Long id, Double valor, String formaPagamento, Integer parcelas) throws ContaException, TransacaoException{
		contaService.debitar(id, valor, formaPagamento, parcelas);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/creditar/{id}")
	public ResponseEntity<Void> creditar(@PathVariable Long id, Double valor, String formaPagamento, Integer parcelas) throws ContaException, TransacaoException{
		contaService.creditar(id, valor, formaPagamento, parcelas);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
}
