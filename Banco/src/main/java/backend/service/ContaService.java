package backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.dto.ContaDTO;
import backend.enu.EnumFormaPagamento;
import backend.exception.ContaException;
import backend.exception.TransacaoException;
import backend.model.ContaModel;
import backend.repository.ContaRepository;

@Service
public class ContaService {
	
	@Autowired
	ContaRepository contaRepository;
	
//ACOES------------------------------------------------------------------------------------------------------	

	public void debitar(Long id, Double valorCompra, String formaPagamento, Integer quantidadeParcelas) throws ContaException, TransacaoException {
		if(valorCompra<0) throw new ContaException("###_Valor improprio");
		Optional<ContaModel> contaEncontrada = contaRepository.findById(id);
		if (!contaEncontrada.isPresent()) throw new ContaException("###_Conta nao encontrada");
		ContaModel conta = contaEncontrada.get();
		switch(EnumFormaPagamento.getFormaPagamento(formaPagamento)) {
			case A_VISTA:
				double valorGastos1 = (conta.getSaldo()+valorCompra);
				double valorDisponivel1 = (conta.getLimite()-conta.getBloqueio());
				if(valorGastos1>valorDisponivel1) throw new ContaException("###_Cartao sem limite disponivel");
				conta.setSaldo(conta.getSaldo()+valorCompra);
				break;
			case PARCELADO_EMISSOR:
			case PARCELADO_LOJA:
				//Logica pagamento de parcelado criada, pois nao havia na proposta da prova-técnica
				double prestacao = valorCompra/quantidadeParcelas;
				double valorGastos2 = (conta.getSaldo()+prestacao);
				double valorDisponivel2 = (conta.getLimite()-(conta.getBloqueio()+(valorCompra-prestacao)));
				if(valorGastos2>valorDisponivel2) throw new ContaException("###_Cartao sem limite disponivel");
				conta.setSaldo(conta.getSaldo()+prestacao);
				conta.setBloqueio(conta.getBloqueio()+valorCompra);
				break;
			default:
				throw new TransacaoException("###_Forma de pagamento invalida");
		}
				contaRepository.save(conta);
	}
	
	public void creditar(Long id, Double valorCreditado, String formaPagamento, Integer quantidadeParcelas) throws ContaException, TransacaoException {
		if(valorCreditado<0) throw new ContaException("###_Valor improprio");
		Optional<ContaModel> contaEncontrada = contaRepository.findById(id);
		if (!contaEncontrada.isPresent()) throw new ContaException("###_Conta nao encontrada");
		ContaModel conta = contaEncontrada.get();
		switch(EnumFormaPagamento.getFormaPagamento(formaPagamento)) {
			case A_VISTA:
				conta.setSaldo(conta.getSaldo()-valorCreditado);
				conta.setBloqueio((valorCreditado>=conta.getBloqueio()?0:(conta.getBloqueio()-valorCreditado)));
				break;
			case PARCELADO_EMISSOR:
			case PARCELADO_LOJA:
				//Logica de estorno de parcelado criada, pois nao havia na proposta da prova-técnica
				double prestacao = valorCreditado/quantidadeParcelas;
				conta.setSaldo(conta.getSaldo()-prestacao);
				conta.setBloqueio((prestacao>=conta.getBloqueio()?0:(conta.getBloqueio()-prestacao)));
				break;
			default:
				throw new TransacaoException("###_Forma de pagamento invalida");
		}
		contaRepository.save(conta);
	}
	
	public void salvar(ContaDTO contaDTO) throws ContaException {		
		salvar(mapToModel(contaDTO));
	}	
	
	public void salvar(ContaModel contaModel) throws ContaException {		
		try {
			contaModel = contaRepository.save(contaModel);
		} catch(Exception e) {
			throw new ContaException("###_Falha ao salvar", e);
		}
	}	
	
	public void deletarPorId(Long id) throws ContaException {
		Optional<ContaModel> contaEncontrada = contaRepository.findById(id);
		if (contaEncontrada.isPresent()) contaRepository.delete(contaEncontrada.get());
		throw new ContaException("###_Conta nao encontrada");
	}
	
//MAP------------------------------------------------------------------------------------------------------	

	public ContaModel mapToModel(ContaDTO contaDTO) throws ContaException {
		ContaModel contaModel = new ContaModel();
		contaModel.setId(contaDTO.getId());
		contaModel.setSaldo(contaDTO.getSaldo());
		contaModel.setBloqueio(contaDTO.getBloqueio());
		contaModel.setLimite(contaDTO.getLimite());
		return contaModel;
	}
	
	public ContaDTO mapToDTO(ContaModel contaModel) {
		ContaDTO contaDTO = new ContaDTO();
		contaDTO.setId(contaModel.getId());
		contaDTO.setSaldo(contaModel.getSaldo());
		contaDTO.setBloqueio(contaModel.getBloqueio());
		contaDTO.setLimite(contaModel.getLimite());
		return contaDTO;
	}
	
	public List<ContaDTO> listModelToDTO(List<ContaModel> listaContaModel){
		List<ContaDTO> listaContaDTO = new ArrayList<ContaDTO>();
		for(ContaModel contaModelItem:listaContaModel) listaContaDTO.add(mapToDTO(contaModelItem));
		return listaContaDTO;
	}

//GET----------------------------------------------------------------------------------
	
	public List<ContaDTO> listar(){
		return listModelToDTO(contaRepository.findAll());
	}
	
	public ContaDTO consultarPorId(Long id) throws ContaException {
		Optional<ContaModel> contaEncontrada = contaRepository.findById(id);
		if (contaEncontrada.isPresent()) return mapToDTO(contaEncontrada.get());
		throw new ContaException("###_Conta nao encontrada");
	}
	
	public ContaModel recuperarPorId(Long id) throws ContaException {
		Optional<ContaModel> contaEncontrada = contaRepository.findById(id);
		if (contaEncontrada.isPresent()) return contaEncontrada.get();
		throw new ContaException("###_Conta nao encontrada");
	}
	
}
