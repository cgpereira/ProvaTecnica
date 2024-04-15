package backend.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.dto.TransacaoDTOpos;
import backend.dto.TransacaoRequestDTO;
import backend.dto.TransacaoResponseDTO;
import backend.exception.TransacaoException;
import backend.service.TransacaoService;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {
	
	@Autowired
	TransacaoService transacaoService;
	
	@GetMapping("/listar")
	public ResponseEntity<List<TransacaoDTOpos>> listaTodos(){
		return ResponseEntity.ok(transacaoService.listar());
	}
	
	@PostMapping("/pagar")
	public ResponseEntity<TransacaoResponseDTO> pagar(@RequestBody @Valid TransacaoRequestDTO transacaoRequestDTO) throws TransacaoException{
		return ResponseEntity.ok(new TransacaoResponseDTO(transacaoService.pagar(transacaoRequestDTO.getTransacao())));
	}
	
	@GetMapping("/consultar/{id}")
	public ResponseEntity<TransacaoResponseDTO> consultarPorId(@PathVariable Long id) throws TransacaoException{
		return ResponseEntity.ok(new TransacaoResponseDTO(transacaoService.consultarPorId(id)));
	}
	
	@PostMapping("/estornar/{id}")
	public ResponseEntity<TransacaoResponseDTO> estornar(@PathVariable Long id) throws TransacaoException{
		return ResponseEntity.ok(new TransacaoResponseDTO(transacaoService.estornar(id)));
	}

}
