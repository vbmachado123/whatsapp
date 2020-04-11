package com.cursoandroid.balbino.whatsapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cursoandroid.balbino.whatsapp.R;
import com.cursoandroid.balbino.whatsapp.activity.ConversaActivity;
import com.cursoandroid.balbino.whatsapp.adapter.ConversaAdapter;
import com.cursoandroid.balbino.whatsapp.adapter.ContatoAdapter;
import com.cursoandroid.balbino.whatsapp.config.ConfiguracaoFirebase;
import com.cursoandroid.balbino.whatsapp.helper.Base64Custom;
import com.cursoandroid.balbino.whatsapp.helper.Preferencias;
import com.cursoandroid.balbino.whatsapp.model.Contato;
import com.cursoandroid.balbino.whatsapp.model.Conversa;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConversasFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter<Conversa> adapter;
    private ArrayList<Conversa> conversas;
    private DatabaseReference referenciaFirebase;
    private ValueEventListener valueEventListenerConversas;

    public ConversasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_conversas, container, false);

        //Instânciar objetos
        conversas = new ArrayList<>();

        //Monta listview e adapter
        listView = (ListView) view.findViewById(R.id.lv_conversas);

        adapter = new ConversaAdapter(getActivity(), conversas);
        listView.setAdapter(adapter);

        //Recuperar dados do usuário
        Preferencias preferencias = new Preferencias (getActivity());
        String idUsuarioLogado = preferencias.getIdentificador();

        //Recuperar conversas do Firebase
        referenciaFirebase = ConfiguracaoFirebase.getFirebase()
                                        .child("conversas")
                                        .child(idUsuarioLogado);

        valueEventListenerConversas = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                conversas.clear();
                for(DataSnapshot dados : dataSnapshot.getChildren()){
                    Conversa conversa = dados.getValue(Conversa.class);
                    conversas.add(conversa);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Recuperar dados a serem passados
                Conversa conversa = conversas.get(position);
                Intent intent = new Intent(getActivity(), ConversaActivity.class);

                //Enviando dados para activity conversa
                intent.putExtra("nome", conversa.getNome());
                String email = Base64Custom.decodificar(conversa.getIdUsuario());
                intent.putExtra("email", email);

                startActivity(intent);

            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        referenciaFirebase.addValueEventListener(valueEventListenerConversas);
    }

    @Override
    public void onStop() {
        super.onStop();
        referenciaFirebase.removeEventListener(valueEventListenerConversas);
    }
}
