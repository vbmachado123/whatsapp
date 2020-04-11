package com.cursoandroid.balbino.whatsapp.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cursoandroid.balbino.whatsapp.R;
import com.cursoandroid.balbino.whatsapp.helper.Permissao;
import com.cursoandroid.balbino.whatsapp.helper.Preferencias;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.Random;

public class LoginActivityAntigo extends AppCompatActivity {

    private EditText codigoBR;
    private EditText dddTelefone;
    private EditText numeroTelefone;
    private EditText nome;
    private Button cadastrar;
    private String[] permissoesNecessarias = new String[]{
            Manifest.permission.SEND_SMS,
            Manifest.permission.INTERNET,
            //Manifest.permission.BATTERY_STATS
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Permissao.validaPermissoes(1, this, permissoesNecessarias);

        nome = (EditText) findViewById(R.id.edit_nome);
        cadastrar = (Button) findViewById(R.id.botao_cadastrar);
        codigoBR = (EditText) findViewById(R.id.edit_cod_pais);
        dddTelefone = (EditText) findViewById(R.id.edit_ddd);
        numeroTelefone = (EditText) findViewById(R.id.edit_telefone);

        //Definindo as mascaras
        SimpleMaskFormatter simpleMaskCodPais = new SimpleMaskFormatter("+NN");
        SimpleMaskFormatter simpleMaskCodArea = new SimpleMaskFormatter("NN");
        SimpleMaskFormatter simpleMaskTelefone = new SimpleMaskFormatter("NNNNN-NNNN");

        //Crianco objeto da mascara
        MaskTextWatcher maskCodigoBR = new MaskTextWatcher(codigoBR, simpleMaskCodPais);
        MaskTextWatcher maskDDD = new MaskTextWatcher(dddTelefone, simpleMaskCodArea);
        MaskTextWatcher maskTelefone = new MaskTextWatcher(numeroTelefone, simpleMaskTelefone);

        //Conectando a mascara ao campo
        codigoBR.addTextChangedListener(maskCodigoBR);
        dddTelefone.addTextChangedListener(maskDDD);
        numeroTelefone.addTextChangedListener(maskTelefone);


        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nomeUsuario = nome.getText().toString();
                String telefoneCompleto =
                        codigoBR.getText().toString() +
                                dddTelefone.getText().toString() +
                                numeroTelefone.getText().toString();

                String telefoneSemFormatacao = telefoneCompleto.replace("+", "");
                telefoneSemFormatacao = telefoneSemFormatacao.replace("-", "");

    /*            //Gerando TOKEN
                Random randomico = new Random();     //Numero randomico De 1000 - 9999
                int numeroRandomico = randomico.nextInt(9999 - 1000) + 1000;
                String token = String.valueOf(numeroRandomico);
                String mensagemEnvio = "WhatsApp Código de Confirmação: " + token;

                //Salvando dados e recuperando-os
                Preferencias preferencias = new Preferencias(LoginActivityAntigo.this);
                preferencias.salvarUsuarioPreferencias(nomeUsuario, telefoneSemFormatacao, token);

                //Enviando o SMS
                boolean enviadoSMS = enviaSMS("+" + telefoneSemFormatacao, mensagemEnvio);

                if(enviadoSMS ) {
                    Intent intent = new Intent(LoginActivityAntigo.this, ValidadorActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivityAntigo.this, "Problema ao enviar o SMS, tente novamente!!", Toast.LENGTH_LONG);
                }
*/                /*HashMap<String, String> usuario = preferencias.getDadosUsuario();
                Log.i("TOKEN", "T: " + usuario.get("token"));*/
            }
        });

    }

    /*Envio do SMS*/
    private boolean enviaSMS(String telefone, String mensagem) {
        try {

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefone, null, mensagem, null, null);

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int resultado : grantResults) {

            if (resultado == PackageManager.PERMISSION_DENIED) {
                alertaValidacaoPermissao();
            }
        }
    }

    private void alertaValidacaoPermissao() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões negadas!");
        builder.setMessage("Para utilizar esse app, é necessário aceitar as permissões!");

        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
