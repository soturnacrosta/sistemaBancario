package com.github.soturnacrosta;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.github.soturnacrosta.model.bean.ContaBancaria;
import com.github.soturnacrosta.model.bean.Usuario;
import com.github.soturnacrosta.model.dao.ContaBancariaDAO;
import com.github.soturnacrosta.model.dao.UsuarioDAO;

public class Controle { // responsável pelos menus de contato ao usuário

    Usuario usuarioAutenticado = null;
    private String escolha; // switches tem que ser public
    private boolean condicao = false;
    ContaBancaria contaBancaria = new ContaBancaria();
    Scanner input = new Scanner (System.in);
    Gerente gerente = new Gerente();    
    UsuarioDAO usuarioDao = new UsuarioDAO();
    Servicos servico = new Servicos();

        void painelControle () {

            while (!condicao) {

                        try { // trata exceções traduzidas de camadas mais baixas, mas agora no mais alto nível

                            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX BEM VINDO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
                            System.out.println("Escolha uma opção:");
                            System.out.println("1. Login usuário.");
                            System.out.println("2. Login gerente.");
                            System.out.println("0. Sair.");
                            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                                escolha = input.nextLine(); 

                            switch (escolha) {

                                case "1": 

                                System.out.println();
                
                                    boolean menuUsuario = false;

                                    usuarioAutenticado = null; // força o usuário a logar toda vez, resetando a variável autenticado

                                    System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX BEM VINDO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
                                    System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXX USUÁRIO XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                                    System.out.println("Digite o CPF da sua conta:");
                                        String loginCliente = input.nextLine();

                                    System.out.println("Digite sua senha:");
                                        String senhaCliente = input.nextLine();

                                    System.out.println();

                                    loginCliente = FormatadorCpf.formatarCpf(loginCliente); // formata o cpf pra fazer o login
                                    Usuario usuarioEncontrado = usuarioDao.readByCpf(loginCliente); //pega o cpf do usuarioNovo que é um objeto
                                    //verifica cpf e senha de uma vez e caso seja válido, tira o null e inicia o login.
                                    if (usuarioEncontrado != null && usuarioEncontrado.getSenha().equals(senhaCliente)) {

                                        usuarioAutenticado = usuarioEncontrado;

                                        //// Carregue a conta bancária do usuário logado!
                                        ContaBancariaDAO cDao = new ContaBancariaDAO();
                                        this.contaBancaria = cDao.readByCpf(usuarioAutenticado.getCpf());

                                        if (this.contaBancaria != null && this.contaBancaria.getStatus().equals("ENCERRADA")) {

                                            System.out.println("\nErro: Esta conta encontra-se ENCERRADA.");
                                            System.out.println("Por favor, procure a gerência para mais informações.\n");
                                            
                                            usuarioAutenticado = null; // Derruba o login
                                            this.contaBancaria = null; // Limpa a variável
                                            
                                            break; // Sai do case e volta pro menu principal (não entra no while do menuUsuario)
}
                                        while (!menuUsuario) {

                                            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX BEM VINDO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
                                            System.out.println("Escolha uma opção:");
                                            System.out.println("1. Sacar.");
                                            System.out.println("2. Depositar.");
                                            System.out.println("3. TED.");
                                            System.out.println("4. Ver saldo na conta.");
                                            System.out.println("5. Tirar extrato");
                                            System.out.println("6. Ver detalhes da conta.");
                                            System.out.println("0. Sair.");
                                                String escolhaUsuario = input.nextLine();

                                            System.out.println();

                                                switch (escolhaUsuario) {

                                                    case "1": // sacar
                                                        
                                                        usuarioSacar(); // chama a função de saque lá embaixo
                                                        break;

                                                    case "2": // depositar

                                                        usuarioDepositar(); // chama a função de depósito lá embaixo
                                                        break;
                                                    
                                                    case "3": // TED

                                                        usuarioTed();
                                                        break;

                                                    case "4": // Ver saldo

                                                        usuarioSaldo();
                                                        break;
                                                    
                                                    case "5": // Tirar extrato

                                                        usuarioImprimirExtrato();
                                                        break; 

                                                    case "6": // detalhes da conta

                                                        usuarioDados();
                                                        break;

                                                    case "0": // sair

                                                        menuUsuario = true;
                                                        input.close();

                                                        break;
                    
                                                    default:

                                                        System.out.println("Opção inválida. Tente novamente.");
                                                        System.out.println();

                                                        break;

                                                }
                                        
                                        }

                                    } 
                                
                                    else {
                                        //caso o login não seja válido e não saia do usuarioAutenticado = null
                                        System.out.println("CPF ou Senha incorretos.");
                                        System.out.println();

                                    }

                                    break;

                                case "2":

                                    ControleGerente painelGerente = new ControleGerente();

                                    painelGerente.painelGerente();

                                    break;

                                case "0":

                                    System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX BEM VINDO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
                                    System.out.println("Encerrando...");
                                    System.out.println();

                                    condicao = true;

                                    break;

                                default:
                                        
                                    System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX BEM VINDO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
                                    System.out.println("Opção inválida. Tente novamente.");
                                    System.out.println();

                            }

                        }       
                        
                        catch (SaldoInsuficienteException e) { // saldo insuficiente

                            System.out.println(e.getMessage());
                    
                        }

                        catch (EntradaInvalidaException e) { // entrada errada de dados

                            System.out.println(e.getMessage());

                        }

            }

        }

        void usuarioSacar () {

            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX BEM VINDO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX SAQUE XXXXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println("Digite a senha:");
                String senhaSaque = input.nextLine();

            if (!usuarioAutenticado.getSenha().equals(senhaSaque)) {

                System.out.println("\nErro: Senha incorreta. Operação cancelada.\n");

                return; // O 'return' encerra esse método na hora e volta pro menu principal

            }
           
            System.out.println("Saldo: R$" + MoedaUtilizada.formatar(this.contaBancaria.getSaldo()) + ".");

            try {

                System.out.println("Digito o valor:");
                    double saque = input.nextDouble();
                    input.nextLine(); // limpa o buffer para evitar duplicação de submenus

                servico.sacar(this.contaBancaria, saque, senhaSaque); // chama o método de saque em ContaBancária

            }

            catch (SaldoInsuficienteException e) {

                System.out.println(e.getMessage());

            }

            catch (InputMismatchException e) { // entrada errada de dados

                input.nextLine();
                throw new EntradaInvalidaException();

            }

        }

        void usuarioDepositar () {

            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX BEM VINDO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX DEPÓSITO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println("Saldo: R$" + MoedaUtilizada.formatar(contaBancaria.getSaldo()) + ".");
            System.out.println("Digito o valor:");

            try {

                double deposito = input.nextDouble();
                input.nextLine(); // limpa o buffer para evitar duplicação de submenus

                servico.depositar(this.contaBancaria, deposito); // chama o método de depósito em ContaBancária

            }

            catch (InputMismatchException e) {

                input.nextLine();
                throw new EntradaInvalidaException();
                
            }

        }

        void usuarioTed () {

            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX BEM VINDO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX DEPÓSITO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println("Digite a senha:");
                String senhaTed = input.nextLine();

            if (!usuarioAutenticado.getSenha().equals(senhaTed)) { // verifica a senha

                System.out.println("\nErro: Senha incorreta. Operação cancelada.\n");

                return; 
            }

            System.out.println("Saldo: R$" + MoedaUtilizada.formatar(contaBancaria.getSaldo()) + "."); // mostra o saldo

            try {

                System.out.println("Digite o número da conta destino: "); // pede a senha
                    String numeroDestino = input.nextLine();
                    
                System.out.println("Digito o valor:");
                    double valor = input.nextDouble();
                    input.nextLine();

                System.out.println("Adicione uma descrição:");  
                    String descricao = input.nextLine();

                servico.realizarTed(this.contaBancaria, valor, numeroDestino, descricao); // conta destino já é o numero da conta!
                // chama o método

            }

            catch (SaldoInsuficienteException e) {

                System.out.println(e.getMessage());

            }

            catch (InputMismatchException e) { // entrada errada de dados 

                input.nextLine();
                throw new EntradaInvalidaException();
                
            }

        }

        void usuarioSaldo () {

            System.out.println();
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX BEM VINDO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX SALDO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println("Saldo: R$" + MoedaUtilizada.formatar(contaBancaria.getSaldo()) +".");
            System.out.println();

        }

        void usuarioDados () {

            System.out.println();
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX BEM VINDO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX DADOS XXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println("Nome: " + this.usuarioAutenticado.getNome()); // para acessar um atributo de outra classe sem instanciar novamente, transforme o atributo de login em global e o 
            System.out.println("CPF: " + FormatadorCpf.formatarCpf(this.usuarioAutenticado.getCpf())); // utilize
            System.out.println("Agência: " + this.contaBancaria.getAgencia());
            System.out.println("Conta bancária: " + this.contaBancaria.getNumero());
            System.out.println("Saldo: " + MoedaUtilizada.formatar(contaBancaria.getSaldo()));
            System.out.println();

        }

        void usuarioImprimirExtrato () {

            ContaBancariaDAO contaDao = new ContaBancariaDAO();

            ContaBancaria contaBean = contaDao.readByCpf(usuarioAutenticado.getCpf());

        if (contaBean != null) {

                servico.imprimirExtrato(contaBean.getNumero());       

            } else {

                System.out.println("Erro: Conta não encontrada para este usuário.");
                
            }

        }

}
