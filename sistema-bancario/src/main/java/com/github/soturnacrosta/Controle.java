package com.github.soturnacrosta;
import java.util.Scanner;

public class Controle { // responsável pelos menus de contato ao usuário

    String escolha; // switches tem que ser public
    private boolean condicao = false;
    ContaBancaria contaBancaria;
    Scanner input = new Scanner (System.in);

        void painelControle () {

            while (!condicao) {


                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX BEM VINDO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
                System.out.println("Escolha uma opção:");
                System.out.println("1. Login usuário.");
                System.out.println("2. Login gerente.");
                System.out.println("0. Sair.");
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                escolha = input.nextLine();

                switch (escolha) {
    
                    case "1": //login usuário

                        contaBancaria = new ContaBancaria();
                        boolean menuUsuario = false;

                        while (!menuUsuario) {

                            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX BEM VINDO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
                            System.out.println("Escolha uma opção:");
                            System.out.println("1. Sacar.");
                            System.out.println("2. Depositar.");
                            System.out.println("3. TED.");
                            System.out.println("4. Ver saldo na conta.");
                            System.out.println("5. Tirar extrato");
                            System.out.println("0. Sair.");
                            System.out.println();
                                String escolhaUsuario = input.nextLine();

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

                                        contaBancaria.imprimirExtrato();
                                        break; 

                                    case "0": // sair

                                        menuUsuario = true;
                                        break;
    
                                    default:

                                        break;

                                 }

                        }

                        break;

                    case "2": //login gerente

                        boolean menuGerente = false;

                        while (!menuGerente) {

                            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX BEM VINDO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
                            System.out.println("Escolha uma opção:");
                            System.out.println("1. Abrir uma conta."); // pode ser tipo gerente ou normal
                            System.out.println("2. Encerrar uma conta.");
                            System.out.println("0. Sair.");
                            System.out.println();
                                String escolhaGerente = input.nextLine();

                                switch (escolhaGerente) {

                                    case "1": // abrir uma conta
                                        
                                    gerenteAbrirConta();
                                        break;
                                
                                    case "2": // encerrar uma conta

                                        gerenteFecharConta();
                                        break;

                                    case "0": // sair 

                                        menuGerente = true;
                                        break;

                                    default:

                                        break;
                                }

                        }

                        break;

                
                    case "0": // sair

                        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX BEM VINDO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
                        System.out.println("Saindo...");
                        System.out.println();

                        condicao = true;

                        break;
                   
                    default: // digito inválido

                        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX BEM VINDO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
                        System.out.println("Opção inválida. Tente novamente.");
                        System.out.println();

                        break;

                }          

            }

        }

        void usuarioSacar () {

            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX BEM VINDO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX SAQUE XXXXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println("Saldo: " + contaBancaria.getSaldo() +"R$.");
            System.out.println("Digito o valor:");
                double saque = input.nextDouble();

            contaBancaria.sacar(saque); // chama o método de saque em ContaBancária

        }

        void usuarioDepositar () {

            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX BEM VINDO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX DEPÓSITO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println("Saldo: " + contaBancaria.getSaldo() +"R$.");
            System.out.println("Digito o valor:");
                double deposito = input.nextDouble();

            contaBancaria.depositar(deposito); // chama o método de depósito em ContaBancária

        }

        void usuarioTed () {

            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX BEM VINDO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX DEPÓSITO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println("Saldo: " + contaBancaria.getSaldo() +"R$.");
            System.out.println("Digite o número da conta destino: ");
                String contaDestino = input.nextLine();

            System.out.println("Digito o valor:");
                double ted = input.nextDouble();
                input.nextLine();

            System.out.println("Adicione uma descrição:");  
                String descricao = input.nextLine();

            contaBancaria.realizarTed(ted, contaDestino, descricao); // conta destino já é o numero da conta!

        }

        void usuarioSaldo () {

            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX BEM VINDO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX DEPÓSITO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println("Saldo: " + contaBancaria.getSaldo() +"R$.");
            System.out.println();

        }

        void gerenteAbrirConta () {

            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX BEM VINDO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXX ABRIR CONTA XXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println("Digite o CPF do cliente para abrir conta:");
                String abrirCPF = input.nextLine();

            System.out.println("Digite o nome completo:");
                String abrirNome = input.nextLine();

            System.out.println("Digite uma senha:");
                String abrirSenha = input.nextLine();
            
            ContaBancaria novaConta = new ContaBancaria();
            Usuario usuario = new Usuario (contaBancaria, abrirSenha, abrirNome, abrirCPF);    
            ContaBancaria.contasAbertas.add(novaConta);
            
            Gerente gerente = new Gerente();
            gerente.abrirConta(usuario);

        } 

        void gerenteFecharConta () {

            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX BEM VINDO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXX FECHAR CONTA XXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println("Digite o CPF do cliente para encerrar conta:");
                String encerrarConta = input.nextLine();

            Gerente gerente = new Gerente(); //instancia o gerente
            gerente.fecharConta(encerrarConta); //fecha a conta com o cpf utilizado da entrada do usuario conforme a classe Gerente

        }
}

