package com.github.soturnacrosta;
import java.util.ArrayList;

public class Gerente {

    ArrayList <ContaBancaria> contas = new ArrayList<>(); // importante perceber que as listas dentro da classe permanecem sempre ativas e quando
    ArrayList <Usuario> usuarios = new ArrayList <> (); // dentro dos métodos, somem quando os métodos terminam
    private Usuario usuario;

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
                contas.add(contaNova);

                    System.out.println("Parabéns!");
                    System.out.println("Conta aberta com sucesso!");
                    System.out.println("Nome: " + usuarioNovo.getNome() + ".");

            }                    

        }

        void fecharConta (String cpfDeletar) {

            boolean existe = false;

            for (Usuario u : usuarios) {

                if (u.getCpf().equals(cpfDeletar)) {

                    existe = true;

                    break;

                }

            }

            if (existe) {

                // se existir um usuario com o CPF
                usuarios.remove(cpfDeletar); // remove da lista
                System.out.println("Sucesso! Conta encerrada!");

            }

            else { // se não houver cadastro, não há conta para fechar

                System.out.println("Erro! Não existe cadastro para este CPF!");

            }
 
        }

}
