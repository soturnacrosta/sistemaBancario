package com.github.soturnacrosta;

import org.junit.jupiter.api.Test;

import com.github.soturnacrosta.model.bean.ContaBancaria;
import com.github.soturnacrosta.model.bean.Usuario;
import com.github.soturnacrosta.model.dao.ContaBancariaDAO;
import com.github.soturnacrosta.model.dao.UsuarioDAO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class GerenteTest {

    @Test
    @DisplayName("Testa se abre conta duplicada")
    public void consegueAbrirConta () {

        Gerente gerente = new Gerente();
        UsuarioDAO usuarioDao = new UsuarioDAO();
        ContaBancariaDAO contaDao = new ContaBancariaDAO();

        // cpf ficticio para buscar
        String cpfTeste = "00000000000";
        //cria uma conta padrão bean
        Usuario usuario1 = new Usuario();
        usuario1.setCpf(cpfTeste);
        usuario1.setNome("Mailton Teste");
        usuario1.setSenha("senha123");

        // Abre a conta no banco de dados
        gerente.abrirConta(usuario1);        
        //cria uma segunda conta com mesmo cpf 
        Usuario usuario2 = new Usuario();
        usuario2.setCpf(cpfTeste);
        usuario2.setNome("Hacker Invasor");
        usuario2.setSenha("senhaHacker");

        // O método do Gerente deve barrar isso e não salvar no banco
        gerente.abrirConta(usuario2);

        // VALIDAÇÃO: Vamos perguntar ao banco de dados o que aconteceu
        Usuario usuarioNoBanco = usuarioDao.readByCpf(cpfTeste);

        // Se o sistema funcionou, o nome gravado no banco ainda deve ser o do "usuario1"
        Assertions.assertEquals("Mailton Teste", usuarioNoBanco.getNome(), "Erro: O sistema permitiu cadastrar dois usuários com o mesmo CPF ou sobrescreveu os dados!");
        // LIMPEZA (MUITO IMPORTANTE PARA TESTES COM BANCO DE DADOS!)
        // Como estamos testando no banco real, precisamos apagar esses dados falsos no final,
        // senão amanhã quando você rodar o teste de novo, ele vai dar erro dizendo que o CPF já existe!
        
        ContaBancaria contaLimpar = contaDao.readByCpf(cpfTeste);
        
        if (contaLimpar != null) {
            // Como seu delete faz soft delete (muda pra ENCERRADA), para testes o ideal 
            // seria um hard delete (DELETE FROM), mas vamos usar as ferramentas que temos:
            contaDao.delete(contaLimpar); 

        }

    }

}
