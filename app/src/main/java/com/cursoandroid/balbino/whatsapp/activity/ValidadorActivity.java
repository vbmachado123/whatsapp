package com.cursoandroid.balbino.whatsapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cursoandroid.balbino.whatsapp.R;
import com.cursoandroid.balbino.whatsapp.helper.Preferencias;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;

public class ValidadorActivity extends AppCompatActivity {

    private EditText codigoValidacao;
    private Button validarCodigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validador);

        codigoValidacao = (EditText) findViewById(R.id.edit_cod_validacao);
        validarCodigo = (Button) findViewById(R.id.botao_validar);

        SimpleMaskFormatter simpleMaskCodigoValidacao = new SimpleMaskFormatter("NNNN");
        MaskTextWatcher mascaraCodigoValidacao = new MaskTextWatcher(codigoValidacao, simpleMaskCodigoValidacao);

        codigoValidacao.addTextChangedListener(mascaraCodigoValidacao);

        validarCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

    /*  *//*          //Recuperar dados das preferências do usuário
                Preferencias preferencias = new Preferencias(ValidadorActivity.this);
                HashMap<String, String> usuario = preferencias.getDadosUsuario();*//*

               *//* String tokenGerado = usuario.get("token");
                String tokenDigitado = codigoValidacao.getText().toString();
*//*
                if(tokenDigitado.equals(tokenGerado)){
                    Toast.makeText(ValidadorActivity.this, "Token VALIDADO", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(ValidadorActivity.this, "Token INCORRETO", Toast.LENGTH_LONG).show();
                }*/
            }
        });

    }
}
