# Desafio portfólio: Sistema Bancário

Sistema bancário com integração banco de dados e autenticação hashing (BCrypt). O sistema segue o padrão DAO (Data Access Object).

## Objetivo:

Este documento tem como objetivo servir de orientação técnica para futuros interessados em conhecer e testar o aplicativo.
É importante frisar que uma boa documentação é sempre uma boa prática, visando futuras melhorias e uma boa manutenção do software.

## Tecnologias utilizadas

### Back-end:

- **JAVA 21**: Linguagem de programação base para lógica da aplicação.
- **MySQL**: Banco de dados relacional para salvar os dados do sistema.
- **BCrypt**: Hashing code para criptografia de dados de usuários.

## Funcionalidades:

- **Menu interativo**: Roda um menu simples e interativo em 'loop' para o usuário transitar entre as diferentes operações bancárias sem precisar interromper o fluxo.
- **Login usuário e login gerente**: Realiza o login de usuário ou gerente (superusuário) no sistema.
- **Operações bancárias**: Permite saque, depósito, transferências TED, extrato, ver saldo e detalhes da conta.
- **Gerenciamento de contas**: Possibilita a abertura, alteração e encerramento de contas.

## Como rodar o projeto:

1. **Pré-requisitos**

Java 21 ou superior;
MySQL Server.
Maven 3.9+: (para gerenciamento de dependências).

2. **Clonando o repositório**

Use o seguinte comando para clonar:

```
git clone https://github.com/soturnacrosta/sistemaBancario
cd sistemaBancario
```

3. **Limpe e compile os arquivos Maven**

Já na pasta raiz, insira no terminal:

```
mvn clean install
```

4. **Abra o seu gerenciador de banco de dados (Ex: MySQL Workbench).**

Localize o arquivo SCRIPT BANCO.sql na raiz do projeto.
Copie o conteúdo do script e execute-o no seu terminal SQL ou Workbench.
O script criará o schema 'sistema_bancario' e as tabelas 'Usuario', 'ContaBancaria' e 'Transacao' automaticamente.

5. **Execute a aplicação**

Execute o arquivo que contém a classe principal:

```
mvn exec:java -Dexec.mainClass="com.github.soturnacrosta.Main"
```

## Testando a aplicação

1. **No menu interativo, escolha o tipo de login desejado.**
2. **Insira as credenciais válidas correspondentes.**
3. **Navegue entre as diferentes operações bancárias utilizando as teclas do teclado.**
4. **Teste operações bancárias tentando inserir valores e senhas inválidas.**
5. **Teste operações bancárias tentando inserir valores e senhas válidas.**

## Observações

Para realizar login via gerente (superusuário):
Login: admin
Senha: admin

**Devido ao uso de BCrypt, senhas inseridas manualmente direto no SQL não funcionarão no login.**
**Sempre utilize o menu do Gerente para criar novos usuários e garantir o hashing correto das credenciais.**

Todas as informações necessárias para construir o sistema estão no arquivo Sistema Bancario.pdf;
