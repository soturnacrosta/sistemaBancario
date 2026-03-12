package com.github.soturnacrosta;
import java.text.DecimalFormat;

public class MoedaUtilizada { // formatação dos valores double para decimal (bancária)

    private static final DecimalFormat df = new DecimalFormat("R$ #,##0.00");  //formata aqui

    public static String formatar(double valor) { // retorna aqui
        return df.format(valor);
    }
    
}
