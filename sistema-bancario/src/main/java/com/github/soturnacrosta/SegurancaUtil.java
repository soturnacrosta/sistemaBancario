package com.github.soturnacrosta;

import org.mindrot.jbcrypt.BCrypt;

public class SegurancaUtil {

    // Transforma a senha em hash com Salt automático
    public static String gerarHash (String senhaPura) {

        return BCrypt.hashpw(senhaPura, BCrypt.gensalt());

    }

    // Verifica se a senha digitada bate com o hash do banco
    public static boolean verificarSenha (String senhaPura, String senhaHash) {

        try {

            return BCrypt.checkpw(senhaPura, senhaHash);

        }

        catch (Exception e) {

            return false;

        }

    }

}
