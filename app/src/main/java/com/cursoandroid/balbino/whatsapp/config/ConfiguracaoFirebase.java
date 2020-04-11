package com.cursoandroid.balbino.whatsapp.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Victor Balbino on 13/01/2020.
 */

public final class ConfiguracaoFirebase {

   private static DatabaseReference refenciaFirebase;
    private static FirebaseAuth autenticacao;

    public static DatabaseReference getFirebase(){

        if(refenciaFirebase == null){
            refenciaFirebase = FirebaseDatabase.getInstance().getReference();
        }

        return refenciaFirebase;
    }

    public static FirebaseAuth getFirebaseAutenticacao(){
        if(autenticacao == null){
            autenticacao = FirebaseAuth.getInstance();
        }
        return autenticacao;
    }

}
