package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import java.util.ArrayList;

public class NumerosActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;


    /**Faz o gerenciamento de áudio:
     * se o aúdio seja interrompido ele é pausado,
     * se o foco do telefone se volte ao aplicativo, então o áudio será retomado
     * se o foco sair do aplicativo entao o áudio é liberado da mémoria*/
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener(){
        @Override
        public void onAudioFocusChange(int focusChange){
            if(focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                //QUEREMOS PAUSAR O AUDIO
                mMediaPlayer.pause();;
                mMediaPlayer.seekTo(0);
            } else if(focusChange==AudioManager.AUDIOFOCUS_GAIN){
                //QUEREMOS REPRODUZIR O AUDIO
                mMediaPlayer.start();
            } else if(focusChange==AudioManager.AUDIOFOCUS_LOSS){
                //QUEREMOS PARAR O AUDIO
                releaseMediaPlayer();
            }
        }
    };

    /**Indica que um áudio do Media Player foi finalizado*/
    private MediaPlayer.OnCompletionListener mOnCompletionListener=new MediaPlayer.OnCompletionListener(){
        @Override
        public void onCompletion(MediaPlayer mediaPlayer){
            releaseMediaPlayer();
        }
    };

    /**Metodo capaz de criar a lista de traduções da página e o gerenciador de áudio,
     * cria o adaptador de traduções e adiciona-o na lista de itens
     * Possui um evento que só é disparado quando um item da lista é clicado, que faz o áudio ser pausado
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_palavras);

        mAudioManager=(AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Palavra> palavras = new ArrayList<>();
        palavras.add(new Palavra("um", "lutti", R.drawable.number_one, R.raw.number_one));
        palavras.add(new Palavra("dois", "otiiko", R.drawable.number_two, R.raw.number_two));
        palavras.add(new Palavra("três", "tolookosu", R.drawable.number_three, R.raw.number_three));
        palavras.add(new Palavra("quatro", "oyyisa", R.drawable.number_four, R.raw.number_four));
        palavras.add(new Palavra("cindo", "massokka", R.drawable.number_five, R.raw.number_five));
        palavras.add(new Palavra("seis", "temmokka", R.drawable.number_six, R.raw.number_six));
        palavras.add(new Palavra("sete", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        palavras.add(new Palavra("oito", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        palavras.add(new Palavra("nove", "wo’e", R.drawable.number_nine, R.raw.number_nine));
        palavras.add(new Palavra("dez", "na’aacha", R.drawable.number_ten, R.raw.number_ten));

        PalavraAdapter itensAdapter = new PalavraAdapter(this, palavras, R.color.categoria_numeros);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(itensAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Palavra PalavraClicada = palavras.get(position);

                releaseMediaPlayer();

                int resultado = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(resultado==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mMediaPlayer = MediaPlayer.create(NumerosActivity.this, PalavraClicada.getReferenciaAudio());
                    mMediaPlayer.start();

                    mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
                }
            }
        });
    }

    /**Os recursos de áudio serão liberados*/
    @Override
    protected void onStop(){
        super.onStop();
        releaseMediaPlayer();
    }

    /**Libera o player de música da mémoria e retira o foco do aúdio*/
    private void releaseMediaPlayer(){
        if(mMediaPlayer!=null){
            mMediaPlayer.release();

            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
            mMediaPlayer=null;
        }
    }
}