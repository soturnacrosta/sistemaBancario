package com.github.soturnacrosta;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Controle { // responsável pelos menus de contato ao usuário

    Usuario usuarioAutenticado = null;
    String escolha; // switches tem que ser public
    private boolean condicao = false;
    ContaBancaria contaBancaria;
    Scanner input = new Scanner (System.in);
    Gerente gerente = new Gerente();    
    Usuario usuario;

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

                    System.out.println();

                    switch (escolha) {
        
                        case "1": //login usuário

                            boolean menuUsuario = false;

                            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX BEM VINDO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
                            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXX USUÁRIO XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                            System.out.println("Digite o CPF da sua conta:");
                                String loginCliente = input.nextLine();

                            System.out.println("Digite sua senha:");
                                String senhaCliente = input.nextLine();

                            System.out.println();

                            for (Usuario u : Gerente.usuarios) { // para comparar a senha passada com a senha de um usuário, precisa percorrer a lista. Isso serve para não criar uma instância do objeto
                                // Valida CPF e Senha
                                if (u.getCpf().equals(loginCliente) && u.getSenha().equals(senhaCliente)) {

                                    usuarioAutenticado = u;
                                    this.contaBancaria = u.getContaBancaria(); // associa a conta encontrada a uma conta do banco

                                    break; 

                                }
                            }

                            if (usuarioAutenticado != null) {


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

                                                contaBancaria.imprimirExtrato();
                                                break; 

                                            case "6": // detalhes da conta

                                                usuarioDados();
                                                break;

                                            case "0": // sair

                                                menuUsuario = true;
                                                break;
            
                                            default:

                                                System.out.println("Opção inválida. Tente novamente.");
                                                break;

                                        }
                                
                                }

                            } 
                            
                            else {

                                System.out.println("CPF ou Senha incorretos.");
                                System.out.println();

                            }

                        break;

                        case "2": //login gerente

                            boolean menuGerente = false;

                            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX BEM VINDO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
                            System.out.println("Digite o CPF da sua conta:");
                                String contaGerente = input.nextLine();

                            System.out.println("Digite sua senha:");
                                String senhaGerente = input.nextLine();

                            System.out.println();

                                if (contaGerente.equals("admin") && (senhaGerente.equals("admin"))) { // acesso do gerente
    
                                    while (!menuGerente) {

                                    System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX BEM VINDO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
                                    System.out.println("Escolha uma opção:");
                                    System.out.println("1. Abrir uma conta."); // pode ser tipo gerente ou normal
                                    System.out.println("2. Encerrar uma conta.");
                                    System.out.println("3. Alterar dados de uma conta.");
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

                                            case "3":

                                                gerenteAlterarConta();
                                                break;

                                            case "0": // sair 

                                                menuGerente = true;
                                                break;

                                            default:

                                                System.out.println("Opção inválida. Tente novamente.");
                                                break;

                                        }

                                    }
            
                                }

                                else {

                                    System.out.println();
                                    System.out.println("Erro! CPF Ou senha incorreta. Tente novamente");
                                    System.out.println();
                                    
                                }

                        break;
                    
                        case "0": // sair

                            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX BEM VINDO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
                            System.out.println("Encerrando...");
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
            System.out.println("Saldo: R$" + MoedaUtilizada.formatar(contaBancaria.getSaldo()) + ".");
            System.out.println("Digito o valor:");

            try {

                double saque = input.nextDouble();
                input.nextLine(); // limpa o buffer para evitar duplicação de submenus

                contaBancaria.sacar(saque); // chama o método de saque em ContaBancária

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

                contaBancaria.depositar(deposito); // chama o método de depósito em ContaBancária

            }

            catch (InputMismatchException e) {

                input.nextLine();
                throw new EntradaInvalidaException();
                
            }

        }

        void usuarioTed () {

            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX BEM VINDO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX DEPÓSITO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println("Saldo: R$" + MoedaUtilizada.formatar(contaBancaria.getSaldo()) + ".");

            try {

                System.out.println("Digite o número da conta destino: ");
                    String contaDestino = input.nextLine();

                System.out.println("Digito o valor:");
                    double ted = input.nextDouble();
                    input.nextLine();

                System.out.println("Adicione uma descrição:");  
                    String descricao = input.nextLine();

                contaBancaria.realizarTed(ted, contaDestino, descricao); // conta destino já é o numero da conta!

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
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX DEPÓSITO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println("Saldo: R$" + MoedaUtilizada.formatar(contaBancaria.getSaldo()) +".");
            System.out.println();

        }

        void usuarioDados () {

            System.out.println();
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX BEM VINDO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX DADOS XXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println("Nome: " + this.usuarioAutenticado.getNome()); // para acessar um atributo de outra classe sem instanciar novamente, transforme o atributo de login em global e o 
            System.out.println("CPF: " + this.usuarioAutenticado.getCpf()); // utilize
            System.out.println("Conta bancária: " + contaBancaria.getConta());
            System.out.println("Saldo: " + MoedaUtilizada.formatar(contaBancaria.getSaldo()));
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
            
            System.out.println();

            ContaBancaria novaConta = new ContaBancaria(); // instancia uma nova conta para o usuario
             // instancia um novo gerente para administrar

            Usuario usuario = new Usuario (novaConta, abrirSenha, abrirNome, abrirCPF);   // instancia um novo usuario com os dados passados            
            gerente.abrirConta(usuario); // chama o método em Gerente

        } 

        void gerenteFecharConta () {

            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX BEM VINDO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXX FECHAR CONTA XXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println("Digite o CPF do cliente para encerrar conta:");
                String encerrarConta = input.nextLine();

            gerente.fecharConta(encerrarConta); //fecha a conta com o cpf utilizado da entrada do usuario conforme a classe Gerente

        }

        void gerenteAlterarConta () {

            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX BEM VINDO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXX ALTERAR CONTA XXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println("Digite o CPF do cliente para alterar dados da conta:");
                String alterarUsuario = input.nextLine();

            gerente.alterarUsuario(alterarUsuario);

        }

}

