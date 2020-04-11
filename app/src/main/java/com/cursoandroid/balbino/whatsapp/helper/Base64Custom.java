package com.cursoandroid.balbino.whatsapp.helper;

import android.util.Base64;

/**
 * Created by Victor Balbino on 14/01/2020.
 */

public class Base64Custom {

    public static String codificarBase64(String texto){
        return Base64.encodeToString(texto.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");
    }

    public static String decodificar(String textoCodificado){
       return new String(Base64.decode(textoCodificado, Base64.DEFAULT));
    }

}
