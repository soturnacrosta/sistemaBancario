package com.github.soturnacrosta;
import java.util.Scanner;

import com.github.soturnacrosta.model.bean.Usuario;
import com.github.soturnacrosta.model.dao.UsuarioDAO;

public class ControleGerente {

    Usuario usuarioAutenticado = null;
    private boolean condicao = false;
    Scanner input = new Scanner (System.in);
    Gerente gerente = new Gerente();  
    UsuarioDAO usuarioDao = new UsuarioDAO();

    public void painelGerente () {

        while (!condicao) {

            try {

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

            }

            catch (SaldoInsuficienteException e) { // saldo insuficiente

                System.out.println(e.getMessage());
                
            }

            catch (EntradaInvalidaException e) { // entrada errada de dados

                System.out.println(e.getMessage());

            }

        }

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
                        String abrirNome = input.nextLine().trim();

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

                    Usuario usuario = new Usuario ();   // instancia um novo usuario com os dados passados 
                    
                    usuario.setCpf(abrirCPF); //como o construtor mudou do original pro bean, devemos adicionar o dados em seguida
                    usuario.setNome(abrirNome);
                    usuario.setSenha(abrirSenha);

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
            
            Usuario usuarioEncontrado = usuarioDao.readByCpf(cpfDeletar); //pega o cpf do usuarioNovo que é um objeto

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
        Usuario usuarioEncontrado = null;
        String alterarUsuarioCpf;

        while (!cpfAceito) {

            System.out.println("Digite o CPF do cliente para alterar dados da conta. Digite '0' para cancelar:");
                alterarUsuarioCpf = input.nextLine();

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

            usuarioEncontrado = usuarioDao.readByCpf(alterarUsuarioCpf); //pega o cpf do usuarioNovo que é um objeto
            // faz a busca de cpf no banco de dados

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
