package com.github.soturnacrosta;

import com.github.soturnacrosta.model.dao.ContaBancariaDAO;
import com.github.soturnacrosta.model.dao.UsuarioDAO;
import com.github.soturnacrosta.model.bean.Usuario; //atenção nos imports!
import com.github.soturnacrosta.model.bean.ContaBancaria;

public class Gerente { // responsável por administrar as contas e os dados do usuário

    UsuarioDAO usuarioDao = new UsuarioDAO();
    ContaBancariaDAO contaDao = new ContaBancariaDAO();
    //static ArrayList <Usuario> usuarios = new ArrayList <> (); 
    // importante perceber que as listas dentro da classe permanecem sempre ativas e quando dentro dos métodos, somem quando os métodos terminam
    // lista static para que todos os métodos de usuario compartilhem a mesma lista

    void abrirConta (Usuario usuarioNovo) { //abre conta

        Usuario usuarioEncontrado = usuarioDao.readByCpf(usuarioNovo.getCpf()); //pega o cpf do usuarioNovo que é um objeto

        if (usuarioEncontrado != null) { // compara o cpf que estou passando com o um cpf existente na lista!

            System.out.println();
            System.out.println("Conta já aberta!");
            System.out.println("Uma conta corresponde ao CPF do usuário já foi encontrada no nosso banco de dados.");
            System.out.println();

        }

        else {

            //cria o usuario primeiro, pois precisa existir no banco
            usuarioDao.create(usuarioNovo);
            // cria a nova conta
            ContaBancaria contaNova = new ContaBancaria();
            // salva na lista
            contaNova.setAgencia("0001-9"); // seta a criação da agencia na hora da abertura
            // delegue a criação da conta apenas para o gerente. não crie duplicidades
            contaNova.setNumero(0);
            contaNova.setSaldo(0); // o saldo da conta nova se inicia com zero 
            contaNova.setUsuario_cpf(usuarioNovo); //associa o cpf A conta

            contaDao.create(contaNova);

                System.out.println();
                System.out.println("Parabéns!");
                System.out.println("Conta aberta com sucesso!");
                System.out.println("Nome: " + usuarioNovo.getNome() + ".");
                System.out.println("CPF: " + FormatadorCpf.formatarCpf(usuarioNovo.getCpf()));
                System.out.println(("Agência: " + contaNova.getAgencia()));
                System.out.println("Conta " + contaNova.getNumero());
                System.out.println();

        }                    

    }

    void fecharConta (String cpfDeletar) { //fecha conta
                    
        Usuario usuarioEncontrado = usuarioDao.readByCpf(cpfDeletar); //buscar cpf

        if (usuarioEncontrado != null) {

            ContaBancaria contaDoUsuario = contaDao.readByCpf(usuarioEncontrado.getCpf());

            if (contaDoUsuario != null && contaDoUsuario.getSaldo() == 0) { // verifica se o saldo é 0
                
                contaDao.delete(contaDoUsuario); // apaga de fora pra dentro. primeiro a conta e depois usuario
                usuarioDao.delete(usuarioEncontrado);

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

    void alterarUsuario (String usuarioAltCpf, String novoNome, String novaSenha, String novoCpf ) { //altera usuario
      
        Usuario usuarioEncontrado = usuarioDao.readByCpf(usuarioAltCpf); //buscar

        if (usuarioEncontrado != null) {        

            usuarioEncontrado.setNome(novoNome); //altere os dados do objeto que foi pesquisado. não instancie um novo objeto vazio!!!
            usuarioEncontrado.setSenha(novaSenha);
            usuarioEncontrado.setCpf(novoCpf);
            usuarioDao.update(usuarioEncontrado);

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
