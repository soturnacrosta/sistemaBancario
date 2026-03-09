package com.github.soturnacrosta;
import java.util.ArrayList;

public class Gerente {

    // importante perceber que as listas dentro da classe permanecem sempre ativas e quando dentro dos métodos, somem quando os métodos terminam
    static ArrayList <Usuario> usuarios = new ArrayList <> (); 
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

                System.out.println("Conta já aberta!");
                System.out.println("Uma conta corresponde ao CPF do usuário já foi encontrada no nosso banco de dados.");
           
            }

            else {

                // cria a nova conta
                ContaBancaria contaNova = new ContaBancaria();

                // associa a um espaço no banco de dados
                usuarioNovo.setContaBancaria(contaNova);

                // salva na lista
                usuarios.add(usuarioNovo);
                ContaBancaria.contasAbertas.add(contaNova);

                    System.out.println("Parabéns!");
                    System.out.println("Conta aberta com sucesso!");
                    System.out.println("Nome: " + usuarioNovo.getNome() + ".");
                    System.out.println("CPF: " + usuarioNovo.getCpf());
                    System.out.println("Conta " + contaNova.getConta());

            }                    

        }

        void fecharConta (String cpfDeletar) {

            Usuario usuarioEncontrado = null;

            for (Usuario u : usuarios) {

                if (u.getCpf().equals(cpfDeletar)) {

                    usuarioEncontrado = u;

                    break;

                }

            }

            if (usuarioEncontrado != null) {

                if (usuarioEncontrado.getContaBancaria().getSaldo() == 0) { // verifica se o saldo é 0

                    ContaBancaria.contasAbertas.remove(usuarioEncontrado.getContaBancaria());
                    usuarios.remove(usuarioEncontrado);

                    System.out.println("Conta e dados de " + usuarioEncontrado.getNome() + " excluídos com sucesso.");

                }

               System.out.println("Erro: A conta possui saldo. Zere o saldo antes de encerrar.");
               System.out.println();

            }

            else { // se não houver cadastro, não há conta para fechar

                System.out.println("Erro! Não existe cadastro para este CPF!");

            }
 
        }

}
