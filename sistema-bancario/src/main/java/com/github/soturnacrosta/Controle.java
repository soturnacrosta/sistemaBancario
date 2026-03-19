package com.github.soturnacrosta;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Controle { // responsável pelos menus de contato ao usuário

    Usuario usuarioAutenticado = null;
    private String escolha; // switches tem que ser public
    private boolean condicao = false;
    ContaBancaria contaBancaria;
    Scanner input = new Scanner (System.in);
    Gerente gerente = new Gerente();    

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

                            usuarioAutenticado = null; // força o usuário a logar toda vez, resetando a variável autenticado

                            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX BEM VINDO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
                            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXX USUÁRIO XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                            System.out.println("Digite o CPF da sua conta:");
                                String loginCliente = input.nextLine();

                            System.out.println("Digite sua senha:");
                                String senhaCliente = input.nextLine();

                            System.out.println();

                            loginCliente = FormatadorCpf.formatarCpf(loginCliente); // formata o cpf pra fazer o login

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
                                                System.out.println();

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
                                                System.out.println();

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
            System.out.println("Digite a senha:");
                String senhaSaque = input.nextLine();

            if (!usuarioAutenticado.getSenha().equals(senhaSaque)) {

                System.out.println("\nErro: Senha incorreta. Operação cancelada.\n");

                return; // O 'return' encerra esse método na hora e volta pro menu principal

            }
           
            System.out.println("Saldo: R$" + MoedaUtilizada.formatar(contaBancaria.getSaldo()) + ".");

            try {

                System.out.println("Digito o valor:");
                    double saque = input.nextDouble();
                    input.nextLine(); // limpa o buffer para evitar duplicação de submenus

                contaBancaria.sacar(saque, senhaSaque); // chama o método de saque em ContaBancária

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

                    // precisa converter a senha do tipo String para o tipo classe que contem a conta bancária
                    ContaBancaria contaDestino = ContaBancaria.buscarContaPorNumero(numeroDestino); // converte a string de entrada para objeto de classe
                    
                    if (contaDestino != null) { // Se a conta foi encontrada na lista global
                    
                        if (!contaDestino.getConta().equals(usuarioAutenticado.getContaBancaria().getConta())) { // compara os numeros das contas

                            System.out.println("Digito o valor:");
                                double ted = input.nextDouble();
                                input.nextLine();

                            System.out.println("Adicione uma descrição:");  
                                String descricao = input.nextLine();

                            contaBancaria.realizarTed(ted, numeroDestino, descricao); // conta destino já é o numero da conta!
                            // chama o método
                        }

                        else {

                            System.out.println();
                            System.out.println("Erro! A conta de destino não deve ser a conta remetente."); // se for a mesma conta
                            System.out.println();

                        }

                    }

                    else {

                        System.out.println();
                        System.out.println("Usuário não encontrado para esse CPF! Tente novamente.");
                        System.out.println();

                    }

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
            System.out.println("Conta bancária: " + contaBancaria.getConta());
            System.out.println("Saldo: " + MoedaUtilizada.formatar(contaBancaria.getSaldo()));
            System.out.println();

        }

        void gerenteAbrirConta () {

            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX BEM VINDO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXX ABRIR CONTA XXXXXXXXXXXXXXXXXXXXXXXXX");
            
            boolean cpfAceito = false;
                
                while (!cpfAceito) {
                
                    System.out.println("Digite o CPF do cliente para abrir conta:");
                        String abrirCPF = input.nextLine();

                    if (FormatadorCpf.isFormatoValido(abrirCPF)) { // barra a formatação do cpf

                        abrirCPF = FormatadorCpf.formatarCpf(abrirCPF); // se tiver 11 digitos, formata a visualização e prossegue

                        System.out.println("Digite o nome completo:");
                            String abrirNome = input.nextLine();

                        if (!ValidarEntrada.isNomeValido(abrirNome)) {

                            System.out.println("\nErro: O nome deve conter apenas letras e ter no máximo 8 caracteres.\n");

                            continue;

                        }

                        System.out.println("Digite uma senha:");
                            String abrirSenha = input.nextLine();

                        if (!ValidarEntrada.isSenhaValida(abrirSenha)) {

                            System.out.println("\nErro: A senha deve conter apenas letras e números e ter no máximo 8 caracteres.\n");

                            continue;

                        }
                        
                        System.out.println();

                        ContaBancaria novaConta = new ContaBancaria(); // instancia uma nova conta para o usuario
                        // instancia um novo gerente para administrar

                        Usuario usuario = new Usuario (novaConta, abrirSenha, abrirNome, abrirCPF);   // instancia um novo usuario com os dados passados            
                        gerente.abrirConta(usuario); // chama o método em Gerente

                        cpfAceito = true;

                    }

                    else {

                        System.out.println("\nErro! Formato inválido. O CPF deve conter 11 números.\n");

                        break;
                    
                    }

                }

        } 

        void gerenteFecharConta () {

            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX BEM VINDO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXX FECHAR CONTA XXXXXXXXXXXXXXXXXXXXXXXXX");
            
            boolean cpfAceito = false;

            while (!cpfAceito) {

                System.out.println("Digite o CPF do cliente para encerrar conta. Digite '0' caso queira cancelar:");
                String cpfDeletar = input.nextLine();


                if (cpfDeletar.equals("0")) { // encerra o laço caso o usuário decida

                    System.out.println();
                    System.out.println("Voltando...");
                    System.out.println();

                    break;

                }

                if (!FormatadorCpf.isFormatoValido(cpfDeletar)) { // verifica o formato do cpf

                    System.out.println("\nErro! Formato inválido. O CPF deve conter 11 números.\n");

                    continue;

                }

                cpfDeletar = FormatadorCpf.formatarCpf(cpfDeletar); // DEVE Formatar a visualização para encaixar com o cpf da conta aberta

                Usuario usuarioEncontrado = null;
                
                for (Usuario u : Gerente.usuarios) {

                    if (u.getCpf().equals(cpfDeletar)) {

                        usuarioEncontrado = u;

                        break;

                    }

                }

                if (usuarioEncontrado == null) { // verifica se existe uma conta com o cpf

                    System.out.println();
                    System.out.println("Usuário não encontrado para esse CPF! Tente novamente.");
                    System.out.println();

                    continue;

                }

                System.out.println("Digite a senha:"); // verifica a senha
                    String senhaFecharConta = input.nextLine();
                       
                if (!usuarioEncontrado.getSenha().equals(senhaFecharConta)) {

                    System.out.println();
                    System.out.println("Ops! Senha incorreta. Tente novamente.");
                    System.out.println();

                    break;

                }

                gerente.fecharConta(cpfDeletar);

                cpfAceito = true;                  
        
            }
           
        }

        void gerenteAlterarConta () {

            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX BEM VINDO XXXXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXX ALTERAR CONTA XXXXXXXXXXXXXXXXXXXXXXXXX");

            boolean cpfAceito = false;

            while (!cpfAceito) {

                System.out.println("Digite o CPF do cliente para alterar dados da conta. Digite '0' para cancelar:");
                    String alterarUsuarioCpf = input.nextLine();

                if (alterarUsuarioCpf.equals("0")) { // encerra o laço caso o usuário decida

                    System.out.println();
                    System.out.println("Voltando...");
                    System.out.println();

                    break;

                }

                // primeira clausula de guarda
                if (!FormatadorCpf.isFormatoValido(alterarUsuarioCpf)) { // verifica se o formato do cpf está valido

                    System.out.println();
                    System.out.println("Erro! Formato inválido. O CPF deve conter 11 números.");
                    System.out.println();

                    continue; // força o gerente a inserir um cpf válido na busca

                }

                alterarUsuarioCpf = FormatadorCpf.formatarCpf(alterarUsuarioCpf); // DEVE Formatar a visualização para encaixar com o cpf da conta aberta

                Usuario usuarioEncontrado = null;

                for (Usuario u : Gerente.usuarios) {

                    if (u.getCpf().equals(alterarUsuarioCpf)) {

                        usuarioEncontrado = u;

                        break;

                    }

                } // faz a busca de cpf no banco de dados

                // segunda clausula de guarda
                if (usuarioEncontrado == null) { // caso não encontre um cpf no banco de dados

                    System.out.println();
                    System.out.println("Usuário não encontrado para esse CPF! Tente novamente.");
                    System.out.println();

                    continue; // força o gerente a procurar um cpf existente

                }

                System.out.println("Usuário para CPF " + usuarioEncontrado.getCpf() + " encontrado. Digite a senha antiga do usuário:");
                    String senhaAlt = input.nextLine();

                // terceira clausula de guarda
                if (!usuarioEncontrado.getSenha().equals(senhaAlt)) { // verificação de senha

                    System.out.println();
                    System.out.println("Ops! Senha incorreta. Tente novamente.");
                    System.out.println();

                    break; // aborta inserção de senha inválida

                }

                System.out.println("Digite o novo nome:");
                    String novoNome = input.nextLine();

                if (!ValidarEntrada.isNomeValido(novoNome)) {

                    System.out.println("\nErro: O nome deve conter apenas letras e ter no máximo 8 caracteres.\n");

                    continue;

                }
                
                System.out.println("Digite a nova senha:");
                    String novaSenha = input.nextLine();

                if (!ValidarEntrada.isSenhaValida(novaSenha)) {

                    System.out.println("\nErro: A senha deve conter apenas letras e números e ter no máximo 8 caracteres.\n");

                    continue;

                }

                System.out.println("Digite o novo CPF:");
                    String novoCpf = input.nextLine();

                if (!FormatadorCpf.isFormatoValido(novoCpf)) { // verifica o formato do novo cpf na hora da inserção

                    System.out.println();
                    System.out.println("Erro! Formato inválido. O CPF deve conter 11 números.");
                    System.out.println("Voltando...");
                    System.out.println();
                    
                    continue; //força nova senha válida

                }

                novoCpf = FormatadorCpf.formatarCpf(novoCpf); // DEVE Formatar a visualização para encaixar com o cpf da conta aberta

                gerente.alterarUsuario(alterarUsuarioCpf, novoNome, novaSenha,novoCpf);

                cpfAceito = true;

            }

        }

}
