# Desafio portfólio: sistema bancário com integração banco de dados e autenticação hashing

## Objetivo:

Este documento tem como objetivo servir de orientação técnica para futuros interessados em conhecer o aplicativo.
É importante frisar que uma boa documentação é sempre uma boa prática, visando futuras melhorias e uma boa manutenção do software.

## Tecnologias utilizadas

### Back-end:

- **JAVA 21**: Linguagem de programação base para lógica da aplicação.

## Funcionalidades:

- **Menu interativo**: Roda um menu simples e interativo em 'loop' para o usuário transitar entre as diferentes operações bancárias sem precisar interromper o fluxo.
- **Login usuário e login gerente**: Realiza o login de usuário ou gerente (superusuário) no sistema.
- **Operações bancárias**: Permite saque, depósito, transferências TED, extrato, ver saldo e detalhes da conta.
- **Gerenciamento de contas**: Possibilita a abertura, alteração e encerramento de contas.

## Como rodar o projeto:

1. **Pré-requisitos**

Java 21 ou superior.

2. **Clonando o repositório**

Use o seguinte comando para clonar:

```
https://github.com/soturnacrosta/sistemaBancario
cd sistema-bancario
```

3. **Limpe e compile os arquivos Maven**

Já na pasta raiz, insira no terminal:

```
mvn clean install
```

4. **Execute a aplicação**

Execute o arquivo que contém a classe principal:

```
java -cp target/classes com.github.soturnacrosta.Main  
```

## Testando a aplicação

1. **No menu interativo, escolha a operação desejada.**
2. **Digite os algarismos para calcular e pressione '=' para obter o resultado.**
3. **Navegue entre as diferentes operações matemáticas utilizando o mesmo cálculo.**
4. **Quando necessário, utilize a função 'C/Clear' para reiniciar os valores.**

## Observações

Ao reiniciar os valores, eles voltam para 0 (zero), mas esse zero não é considerado para multiplicações, divisões e soma. O zero do 'Clear' é considerado apenas na subtração
afim de evitar problemas de lógica na aplicação.
