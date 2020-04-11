package com.cursoandroid.balbino.whatsapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cursoandroid.balbino.whatsapp.R;
import com.cursoandroid.balbino.whatsapp.adapter.TabAdapter;
import com.cursoandroid.balbino.whatsapp.config.ConfiguracaoFirebase;
import com.cursoandroid.balbino.whatsapp.helper.Base64Custom;
import com.cursoandroid.balbino.whatsapp.helper.Preferencias;
import com.cursoandroid.balbino.whatsapp.helper.SlidingTabLayout;
import com.cursoandroid.balbino.whatsapp.model.Contato;
import com.cursoandroid.balbino.whatsapp.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//import androidx.annotation.RecentlyNonNull;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference referenceFirebase;
    private Button sair;
    private FirebaseAuth autenticacao;
    private Toolbar toolbar;
    private FirebaseAuth usuarioAutenticacao;

    private SlidingTabLayout slidingTapLayout;
    private ViewPager viewPager;
    private String identificadorContato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuarioAutenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("WhatsApp");
        setSupportActionBar(toolbar);

        slidingTapLayout = (SlidingTabLayout) findViewById(R.id.stl_tabs);
        viewPager = (ViewPager) findViewById(R.id.vp_pagina);

        //Configurar sliding tabs
        slidingTapLayout.setDistributeEvenly(true);
        slidingTapLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.colorAccents));

        //Configurando Adapter
        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);

        slidingTapLayout.setViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_sair:
                deslogarUsuario();
                return true;
            case R.id.item_adicionar:
                abrirCadastroContato();
                return true;
            case R.id.item_configuracoes:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deslogarUsuario() {

        usuarioAutenticacao.signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void abrirCadastroContato() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

        //Configurando a AlertDialog
        alertDialog.setTitle("Novo Contato");
        alertDialog.setMessage("E-mail do usuário");
        alertDialog.setCancelable(false);

        //Inserindo caixa de texto do AlertDialog
        final EditText editText = new EditText(MainActivity.this);
        alertDialog.setView(editText);

        //Configurando Botões
        alertDialog.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String emailContato = editText.getText().toString();

                //Valida e-mail digitado
                if (emailContato.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Preencha o e-mail!", Toast.LENGTH_LONG).show();
                } else {

                    //Verificar se o usuário já está cadastrado no nosso APP
                    identificadorContato = Base64Custom.codificarBase64(emailContato);

                    //Recuperar instância Firebase
                    referenceFirebase = ConfiguracaoFirebase.getFirebase().child("usuarios").child(identificadorContato);

                    referenceFirebase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (dataSnapshot.getValue() != null) {

                                //Recuperar dados do contato a ser adicionado
                                Usuario usuarioContato = dataSnapshot.getValue(Usuario.class);


                                //Recuperar identificador usuario logado (base64)
                                Preferencias preferencias = new Preferencias(MainActivity.this);
                                String identificadorUsuarioLogado = preferencias.getIdentificador();

                                referenceFirebase = ConfiguracaoFirebase.getFirebase();
                                referenceFirebase = referenceFirebase.child("contatos")
                                        .child(identificadorUsuarioLogado)
                                        .child(identificadorContato);

                                Contato contato = new Contato();
                                contato.setIdentificadorUsuario(identificadorContato);
                                contato.setEmail(usuarioContato.getEmail());
                                contato.setNome(usuarioContato.getNome());


                                referenceFirebase.setValue(contato);

                            } else {
                                Toast.makeText(MainActivity.this, "Usuário não possui cadastro.", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }
        });

        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        alertDialog.create();
        alertDialog.show();

    }

}
