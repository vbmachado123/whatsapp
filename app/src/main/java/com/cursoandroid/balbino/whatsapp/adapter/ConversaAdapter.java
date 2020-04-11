package com.cursoandroid.balbino.whatsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cursoandroid.balbino.whatsapp.R;
import com.cursoandroid.balbino.whatsapp.model.Contato;
import com.cursoandroid.balbino.whatsapp.model.Conversa;

import java.util.ArrayList;

/**
 * Created by Victor Balbino on 14/01/2020.
 */

public class ConversaAdapter extends ArrayAdapter<Conversa> {

    private ArrayList<Conversa> conversas;
    private Context context;

    public ConversaAdapter(Context c, ArrayList<Conversa> objects) {
        super(c, 0, objects);

        this.conversas = objects;
        this.context = c;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;

        //Verifica se a lista está vazia
        if(conversas != null) {

            //Inicializar objeto para montagem da view
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( context.LAYOUT_INFLATER_SERVICE );

            //Montando view a partir do xml
            view = inflater.inflate(R.layout.lista_conversa, parent, false);

            //Recupera elemento para exibição
            TextView nomeContato = (TextView) view.findViewById(R.id.tv_titulo);
            TextView ultimaMensagem = (TextView) view.findViewById(R.id.tv_subtitulo);

            Conversa conversa = conversas.get(position);
            nomeContato.setText(conversa.getNome());
            ultimaMensagem.setText(conversa.getMensagem());

        }

        return view;

    }

}