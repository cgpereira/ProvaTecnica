# ProvaTecnica -- Repositório de Prova Tecnica

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

