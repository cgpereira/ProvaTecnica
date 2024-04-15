# ProvaTecnica
Repositório de Prova Tecnica de Backend

Neste Read-me há duas seções: 'Proposta' (da prova técnica) e 'Implementação, uso e Teste' (breve descrição do trabalho realizado) 

PROPOSTA:
---------------------------------------------------------------------------------------------------------

Tools Java Challenge [C]
Sabemos que o dia a dia de um desenvolvedor consiste em resolver desafios, desenvolver sistemas, entender o negócio, sempre buscando trazer o máximo de valor para empresa. Com isso o objetivo deste desafio é propor um cenário de problema para você resolver da melhor maneira que conseguir.
A qualidade das entregas é um diferencial dos nossos times, portanto o Teste de Software é fundamental.
Antes de iniciar o Projeto, imagine você daqui 2 anos, certamente terá passado por N problemas repetidamente e em diferentes Projetos. Portanto, esperamos que você possa resolvê-lo de tal forma que consiga aplicar em outros projetos.

Informações importantes:
1. Não esqueça do Readme.md. Utilize-o para expor informações necessárias para sobre o
  objetivo e entendimento do Projeto.
Submissão do código-fonte:
   Usar Git para versionamento
   Crie em modo público o Repositório: ToolsChallenge
   Compartilhe na tecnologia java e commit nogithub

Projeto:
Você trabalha em um Banco, na área de cartões de crédito e faz parte do Time Elite.
O time recebe uma demanda: Implementar uma API de Pagamentos.
As operações serão as seguintes:
  - Pagamento:
  - Solicitação e resposta conforme JSON abaixo
  - Estorno:
  - consulta: por ID
  - retorno: conforme JSON de retorno de estorno
  - Consulta:
  - consulta: todos e por ID
  - retorno: conforme JSON de retorno do pagamento

A API deve utilizar conceitos REST, JSON e protocolo padrão HTTP de retorno.
O servidor da aplicação pode ser Tomcat, JBoss ou pode ser utilizado SpringBoot.
Requisitos das transações:
  - Transacao > id: Deve ser único
  - Transacao > Status: “AUTORIZADO”, “NEGADO”
  - formaPagamento > Tipo: “AVISTA”, “PARCELADO LOJA”, “PARCELADO EMISSOR

PAGAMENTO - Request:{
	"transacao":{
		"cartao":"4444000000001234"
		,"id": "100023568900001"
		,"descricao":{
			"valor":"500.50"
			,"dataHora":"01/05/2021 18:30:00"
			,"estabelecimento":"PetShop Mundo cão"
		}
		,"formaPagamento":{
			"tipo":"AVISTA"
			,"parcelas":"1"
		}
	}
}

PAGAMENTO - Response:{
	"transacao":{
		"cartao":"4444000000001234"
		,"id": "100023568900001"
		,"descricao":{
			"valor":"500.50"
			,"dataHora":"01/05/2021 18:30:00"
			,"estabelecimento":"PetShop Mundo cão"
			,"nsu":"1234567890"
			,"codigoAutorizacao":"147258369"
			,"status":"AUTORIZADO"
		}
		,"formaPagamento":{
			"tipo":"AVISTA"
			,"parcelas":"1"
		}
	}
}

ESTORNO - Response:{
	"transacao":{
		"cartao":"4444000000001234"
		,"id": "100023568900001"
		,"descricao":{
			"valor":"50.00"
			,"dataHora":"01/05/2021 18:30:00"
			,"estabelecimento":"PetShop Mundo cão"
			,"nsu":"1234567890"
			,"codigoAutorizacao":"147258369"
			,"status":"CANCELADO"
		}
		,"formaPagamento":{
			"tipo":"AVISTA"
			,"parcelas":"1"
		}
	}
}

IMPLEMENTAÇÃO, USO E TESTE:
---------------------------------------------------------------------------------------------------------

Esta aplicação backend foi implementada com springboot security, swagger e banco de dados h2.

Utilizamos para resolução um modelo um-pra-muitos conta-transação.
O conceito de conta foi igualado com o de cartão e o sistema permite transações até o saldo alcançar o limite.
A partir do momento em que se alcança o limite, todas as operações são negadas até acontecer um crédito (pagamento de fatura).
Compras parceladas produzem um bloqueio de limite e sua fração mensal é somada ao saldo da fatura.

As credenciais da aplicação estão hardcoded e são as seguintes: (admin/12345) e (user/123) e seus usos não =foram diferenciados um do outro (!).

Descrição dos serviços implementados:

Admin (Serviço de segurança da aplicação)
- ​/seguranca​/autenticar : Neste serviço é gerado, após autenticação, um Bearer token para uso dos serviços abaixo

Conta (serviços de gestão de contas disponíveis para transações nos cartões)
​- /conta​/consultar​/{id} : consulta à um cartão específico
​- /conta​/creditar​/{id} : serviço para receber pagamentos de fatura do cartão
​- /conta​/debitar​/{id} : serviço para receber gastos no cartão
​- /conta​/listar : listagem das contas dos cartões
- ​/conta​/salvar : criação de conta de cartão, com imposição de limite de conta

Transacao (serviço público para registro de transações com os cartões)
​- /transacao​/consultar​/{id} : consulta à transação unitária
​- /transacao​/estornar​/{id} :  registro de estorno de transação (recupera saldo de conta de cartão)
​- /transacao​/listar : consulta à listagem de transações
​- /transacao​/pagar : registro de pagamento (checa saldo de conta de cartão)

Para testar a aplicação e observar os dados em tempo real no banco de dados local, basta rodar a aplicação, entrar nos links abaixo, se autenticar e disparar pelo swagger as requisições de teste.

Interface Web do Swagger
  http://localhost:9090/swagger-ui/
  Serviçoes disponívei
  
Interface banco H2:
  http://localhost:9090/h2-console/
  jdbc url: jdbc:h2:file:./BackendBD
  usuario: admin
  senha: 12345
  Queries básicas:
    SELECT * FROM BANCO_CONTA ;
    SELECT * FROM BANCO_TRANSACAO ;

OBS: não foram implementados nesta solução testes unitários nem fim-a-fim com Junit e Mockito.
