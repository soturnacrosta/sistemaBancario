package com.github.soturnacrosta;
import java.util.ArrayList;

public class Gerente { // responsável por administrar as contas e os dados do usuário

    static ArrayList <Usuario> usuarios = new ArrayList <> (); 
    // importante perceber que as listas dentro da classe permanecem sempre ativas e quando dentro dos métodos, somem quando os métodos terminam
    // lista static para que todos os métodos de usuario compartilhem a mesma lista

        void abrirConta (Usuario usuarioNovo) {

            boolean existe = false;

            for (Usuario u : usuarios ) { // verifica primeiro se já existe um usuario através de um laço for, comparando o usuario do método com o 
                //usuário da lista existente

                if (u.getCpf().equals(usuarioNovo.getCpf())){

                    existe = true;
                    
                    break;

                }

            }

            if (existe) { // compara o cpf que estou passando com o um cpf existente na lista!

                System.out.println();
                System.out.println("Conta já aberta!");
                System.out.println("Uma conta corresponde ao CPF do usuário já foi encontrada no nosso banco de dados.");
                System.out.println();

            }

            else {

                // cria a nova conta
                ContaBancaria contaNova = new ContaBancaria();

                // associa a um espaço no banco de dados
                usuarioNovo.setContaBancaria(contaNova);

                // salva na lista
                usuarios.add(usuarioNovo);
                ContaBancaria.contasAbertas.add(contaNova);
                contaNova.setAgencia("0001-9"); // seta a criação da agencia na hora da abertura
                // delegue a criação da conta apenas para o gerente. não crie duplicidades

                contaNova.setSaldo(0); // o saldo da conta nova se inicia com zero 

                    System.out.println();
                    System.out.println("Parabéns!");
                    System.out.println("Conta aberta com sucesso!");
                    System.out.println("Nome: " + usuarioNovo.getNome() + ".");
                    System.out.println("CPF: " + FormatadorCpf.formatarCpf(usuarioNovo.getCpf()));
                    System.out.println(("Agência: " + contaNova.getAgencia()));
                    System.out.println("Conta " + usuarioNovo.getContaBancaria());
                    System.out.println();

            }                    

        }

        void fecharConta (String cpfDeletar) {

            Usuario usuarioEncontrado = null;

            for (Usuario u : usuarios) {

                if (u.getCpf().equals(cpfDeletar)){

                    usuarioEncontrado = u;

                    break;

                }

            }

            if (usuarioEncontrado != null) {


                if (usuarioEncontrado.getContaBancaria().getSaldo() == 0) { // verifica se o saldo é 0
                    
                    ContaBancaria.contasAbertas.remove(usuarioEncontrado.getContaBancaria());
                    usuarios.remove(usuarioEncontrado);

                    System.out.println();
                    System.out.println("Conta e dados de " + usuarioEncontrado.getNome() + " excluídos com sucesso.");
                    System.out.println();

                        
                }

                else {

                    System.out.println();
                    System.out.println("Erro: A conta possui saldo. Zere o saldo antes de encerrar.");
                    System.out.println();

                }

            }

            else {

                System.out.println();
                System.out.println("Usuário não encontrado para esse CPF! Tente novamente.");
                System.out.println();

            }
 
        }

        void alterarUsuario (String usuarioAltCpf, String novoNome, String novaSenha, String novoCpf ) {

             
            Usuario usuarioEncontrado = null;

            for (Usuario u : usuarios) {

                if (u.getCpf().equals(usuarioAltCpf)) {

                    usuarioEncontrado = u;

                    break;

                }

            }

            if (usuarioEncontrado != null) {        

                usuarioEncontrado.setNome(novoNome); //altere os dados do objeto que foi pesquisado. não instancie um novo objeto vazio!!!
                usuarioEncontrado.setSenha(novaSenha);
                usuarioEncontrado.setCpf(novoCpf);

                System.out.println();
                System.out.println("Conta atualizada com sucesso!");
                System.out.println("Novo nome: " + usuarioEncontrado.getNome());
                System.out.println();

            }

              else {

                System.out.println();
                System.out.println("Usuário não encontrado para esse CPF! Tente novamente.");
                System.out.println();

            }

        }

}
