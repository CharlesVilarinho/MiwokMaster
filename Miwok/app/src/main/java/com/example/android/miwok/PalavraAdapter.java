package com.example.android.miwok;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import static com.example.android.miwok.R.id.miwok;
/**import static com.example.android.miwok.R.id.miwok;*/

/**Classe que organiza a lista de itens de acordo com a necessidade de cada tela*/
public class PalavraAdapter extends ArrayAdapter<Palavra>{

    private int mCorFundo;

    /**Metodo construtor que gerencia os itens de uma listView
     * @param context - tela atual para adicionar a lista
     * @param palavras - lista de traduções a serem exibidas
     * @param corFundo - cor de fundo passa a ser a cor do textView
     */
    public PalavraAdapter(Activity context, ArrayList<Palavra> palavras, int corFundo){
        super(context, 0, palavras);
        mCorFundo = corFundo;
    }

    /**Recebe um item da lista e retorna de acordo com o layout definido em item_lista
     * @param position - posição do item no array de traduções
     * @param convertView - objeto que deve ser personalizado
     * @param parent - parentes do item atual
     * @return - itemListaView após ser personalizado
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View itemListaView = convertView;
        if(itemListaView == null){
            itemListaView = LayoutInflater.from(getContext()).inflate(R.layout.item_lista, parent, false);
        }

        Palavra palavraAtual = getItem(position);

        LinearLayout layout = (LinearLayout) itemListaView.findViewById(R.id.container_global);
        layout.setBackgroundResource(mCorFundo);

        TextView miwok = (TextView) itemListaView.findViewById(R.id.miwok);
        miwok.setText(palavraAtual.getTraducaoMiwok());

        TextView padrao = (TextView) itemListaView.findViewById(R.id.padrao);
        padrao.setText(palavraAtual.getTraducaoPadrao());

        ImageView imagem = (ImageView) itemListaView.findViewById(R.id.container_imagem);
        if(palavraAtual.hasImagem()) {
            imagem.setImageResource(palavraAtual.getReferenciaImagem());
            imagem.setVisibility(View.VISIBLE);
        } else {
            imagem.setVisibility(View.GONE);
        }
        return itemListaView;
    }
}