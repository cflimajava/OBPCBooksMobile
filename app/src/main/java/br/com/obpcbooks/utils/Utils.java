package br.com.obpcbooks.utils;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {


    public static String formatarData(String dataInformada){
        SimpleDateFormat formatoRecebido = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        SimpleDateFormat formatoDesejado = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.sss");

        try {
            return formatoDesejado.format((Date)formatoRecebido.parse(dataInformada));
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("Formado de data informado fora do padrao, padrao esperado: \"yyyy-MM-dd'T'HH:mm:ss.SSSXXX\"");
            return dataInformada;
        }


    }

}
