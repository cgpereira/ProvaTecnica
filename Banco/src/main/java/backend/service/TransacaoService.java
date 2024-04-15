package backend.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.dto.DescricaoDTOComStatus;
import backend.dto.DescricaoDTOSemStatus;
import backend.dto.FormaPagamentoDTO;
import backend.dto.TransacaoDTOpos;
import backend.dto.TransacaoDTOpre;
import backend.enu.EnumFormaPagamento;
import backend.exception.ContaException;
import backend.exception.TransacaoException;
import backend.model.TransacaoModel;
import backend.repository.TransacaoRepository;

@Service
public class TransacaoService {
	
	@Autowired
	TransacaoRepository transacaoRepository;
	
	@Autowired
	ContaService contaService;
	
//ACOES--------------------------------------------------------------------------------------------
	
	public TransacaoDTOpos pagar(TransacaoDTOpre transacaoDTOpre) throws TransacaoException{
		Optional<TransacaoModel> transacaoEncontrada = transacaoRepository.findById(Long.valueOf(transacaoDTOpre.getId()));
		if(transacaoEncontrada.isPresent()) throw new TransacaoException("###_Transacao duplicada");
		TransacaoModel transacaoModel = mapToModel(transacaoDTOpre);
		if (!EnumFormaPagamento.isFormaPagamento(transacaoModel.getTipo())) throw new TransacaoException("###_Forma de pagamento invalida");
		try {
			contaService.debitar(transacaoModel.getConta().getId(), transacaoModel.getValor(), transacaoModel.getTipo(), transacaoModel.getParcelas());
			transacaoModel.setStatus("AUTORIZADO");
			transacaoModel.setNsu(this.gerarCodigo());
			transacaoModel.setCodigoAutorizacao(this.gerarCodigo());
			transacaoModel = transacaoRepository.save(transacaoModel);
		} catch(ContaException e) {	
			transacaoModel.setStatus("NEGADO");
			transacaoModel = transacaoRepository.save(transacaoModel);
			throw new TransacaoException("###_Falha na transacao", e);
		} catch(Exception e) {
			throw new TransacaoException("###_Falha ao salvar", e);
		}
		return mapToDTOpos(transacaoModel);
	}
	
	public TransacaoDTOpos estornar(Long id) throws TransacaoException{
		Optional<TransacaoModel> transacaoEncontrada = transacaoRepository.findById(id);
		if(!transacaoEncontrada.isPresent()) throw new TransacaoException("###_Transacao inexistente");
		TransacaoModel transacaoModel = recuperarPorId(id);
		try {
			contaService.creditar(transacaoModel.getConta().getId(), transacaoModel.getValor(), transacaoModel.getTipo(), transacaoModel.getParcelas());
			transacaoModel.setStatus("CANCELADO");
			transacaoModel = transacaoRepository.save(transacaoModel);
		} catch(Exception e) {
			throw new TransacaoException("###_Falha ao estornar o pagamento", e);
		}
		return mapToDTOpos(transacaoModel);
	}
	
	public void deletarPorId(Long id) throws TransacaoException {
		Optional<TransacaoModel> transacaoEncontrada = transacaoRepository.findById(id);
		if (transacaoEncontrada.isPresent()) transacaoRepository.delete(transacaoEncontrada.get());
		throw new TransacaoException("###_Transacao nao encontrada");
	}
	
//MAP------------------------------------------------------------------------------------------------------	

	public TransacaoModel mapToModel(TransacaoDTOpre transacaoDTO) throws TransacaoException{
		TransacaoModel transacaoModel = new TransacaoModel();
		transacaoModel.setId(Long.valueOf(transacaoDTO.getId()));
		transacaoModel.setValor(Double.valueOf(transacaoDTO.getDescricao().getValor()));
		transacaoModel.setDataHora(transacaoDTO.getDescricao().getDataHora());
		transacaoModel.setEstabelecimento(transacaoDTO.getDescricao().getEstabelecimento());
		transacaoModel.setTipo(transacaoDTO.getFormaPagamento().getTipo());
		transacaoModel.setParcelas(Integer.valueOf( transacaoDTO.getFormaPagamento().getParcelas()));
		try {
			transacaoModel.setConta(contaService.recuperarPorId(Long.valueOf(transacaoDTO.getCartao())));
		} catch (ContaException e) {
			throw new TransacaoException(e.getMessage());
		}
		return transacaoModel;
	}

	public TransacaoDTOpre mapToDTOpre(TransacaoModel transacaoModel) {
		TransacaoDTOpre transacaoDTO = new TransacaoDTOpre();
		transacaoDTO.setId(String.valueOf(transacaoModel.getId()));
		DescricaoDTOSemStatus descricao = new DescricaoDTOSemStatus();
		descricao.setValor(String.valueOf(transacaoModel.getValor()));
		descricao.setDataHora(transacaoModel.getDataHora());
		descricao.setEstabelecimento(transacaoModel.getEstabelecimento());
		transacaoDTO.setDescricao(descricao);
		FormaPagamentoDTO formaPagamentoDTO = new FormaPagamentoDTO();
		formaPagamentoDTO.setTipo(transacaoModel.getTipo());
		formaPagamentoDTO.setParcelas(String.valueOf(transacaoModel.getParcelas()));
		transacaoDTO.setFormaPagamento(formaPagamentoDTO);
		return transacaoDTO;
	}


	public TransacaoDTOpos mapToDTOpos(TransacaoModel transacaoModel) {
		TransacaoDTOpos transacaoDTOpos = new TransacaoDTOpos();
		transacaoDTOpos.setCartao(String.valueOf(transacaoModel.getConta().getId()));
		transacaoDTOpos.setId(String.valueOf(transacaoModel.getId()));
		DescricaoDTOComStatus descricao = new DescricaoDTOComStatus();
		descricao.setValor(new DecimalFormat(".00").format(transacaoModel.getValor()));
		descricao.setDataHora(transacaoModel.getDataHora());
		descricao.setEstabelecimento(transacaoModel.getEstabelecimento());
		descricao.setNsu(String.valueOf(transacaoModel.getNsu()));
		descricao.setCodigoAutorizacao(String.valueOf(transacaoModel.getCodigoAutorizacao()));
		descricao.setStatus(transacaoModel.getStatus());
		transacaoDTOpos.setDescricao(descricao);
		FormaPagamentoDTO formaPagamentoDTO = new FormaPagamentoDTO();
		formaPagamentoDTO.setTipo(transacaoModel.getTipo());
		formaPagamentoDTO.setParcelas(String.valueOf(transacaoModel.getParcelas()));
		transacaoDTOpos.setFormaPagamento(formaPagamentoDTO);
		return transacaoDTOpos;
	}
	
	public List<TransacaoDTOpos> listModelToDTOpos(List<TransacaoModel> listaTransacaoModel){
		List<TransacaoDTOpos> listaTransacaoDTOpos = new ArrayList<TransacaoDTOpos>();
		for(TransacaoModel transacaoModelItem:listaTransacaoModel) listaTransacaoDTOpos.add(mapToDTOpos(transacaoModelItem));
		return listaTransacaoDTOpos;
	}
	
//GET--------------------------------------------------------------------------------------------

	public List<TransacaoDTOpos> listar(){
		return listModelToDTOpos(transacaoRepository.findAllByOrderByDataHoraDesc());
	}

	public TransacaoDTOpos consultarPorId(Long id) throws TransacaoException {
		Optional<TransacaoModel> transacaoEncontrada = transacaoRepository.findById(id);
		if(transacaoEncontrada!=null) return mapToDTOpos(transacaoEncontrada.get());
		throw new TransacaoException("###_Transacao nao encontrada");
	}
	
	public TransacaoModel recuperarPorId(Long id) throws TransacaoException {
		Optional<TransacaoModel> transacaoRecuperada = transacaoRepository.findById(id);
		if (transacaoRecuperada.isPresent()) return transacaoRecuperada.get();
		throw new TransacaoException("###_Transacao nao encontrada");
	}
	
//UTILS-----------------------------------------------------
	
	private Long gerarCodigo() {
		return Long.valueOf(Math.abs(new Random().nextInt()));
	}
	
}
