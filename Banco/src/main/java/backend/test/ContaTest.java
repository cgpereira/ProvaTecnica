package backend.test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import backend.exception.ContaException;
import backend.model.ContaModel;
import backend.service.ContaService;

public class ContaTest {

	@Autowired
	ContaService contaService;

	@Test
	@DisplayName("Os dados das contas precisam ser recuperáveis e se manter íntegros")  
	void test() {
		try {
			ContaModel conta = contaService.recuperarPorId((long)1234567890);
			boolean testeDados = (conta.getSaldo()==0)&&(conta.getBloqueio()==0)&&(conta.getLimite()==1000);
			assertTrue(testeDados);
		} catch (ContaException e) {
			fail(e.getMessage());
		}
	}

	@BeforeEach
	void testBeforeEach() {
		ContaModel conta = new ContaModel();
		conta.setId((long)1234567890);
		conta.setBloqueio(0);
		conta.setSaldo(0);
		conta.setLimite(1000);
		try {
			contaService.salvar(conta);
		} catch (ContaException e) {
			fail(e.getMessage());
		}
	}

	@AfterEach
	void testAfterEach() {
		try {
			contaService.deletarPorId((long)1234567890);
		} catch (ContaException e) {
			fail(e.getMessage());
		}
	}

	@BeforeAll
	public static void testBeforeAll() {
	}

	@AfterAll
	public static void testAfterAll() {
	}
	
}

/*
	//alguns TESTES a implementar nesta classe

	debitar(Long id, Double valorCompra, String formaPagamento, Integer quantidadeParcelas)
		if (valorCompra<0)
			-> ContaException("###_Valor improprio");
		if (!contaEncontrada.isPresent())
		 	-> ContaException("###_Conta nao encontrada");
		if (valorGastos1>valorDisponivel1)
			-> ContaException("###_Cartao sem limite disponivel");
		if (valorGastos2>valorDisponivel2)
			-> ContaException("###_Cartao sem limite disponivel");
		-> TransacaoException("###_Forma de pagamento invalida");
	creditar(Long id, Double valorCreditado, String formaPagamento, Integer quantidadeParcelas)
		if (valorCreditado<0)
			-> ContaException("###_Valor improprio");
		if (!contaEncontrada.isPresent())
			-> new ContaException("###_Conta nao encontrada");
		-> TransacaoException("###_Forma de pagamento invalida")
	salvar(ContaModel contaModel)
		-> ContaException("###_Falha ao salvar", e)
	deletarPorId(Long id)
		-> ContaException("###_Conta nao encontrada")
	recuperarPorId(Long id)
		-> ContaException("###_Conta nao encontrada");
*/