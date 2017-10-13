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

public class FrasesActivity extends AppCompatActivity {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_palavras);

        mAudioManager=(AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Palavra> palavras = new ArrayList<>();
        palavras.add(new Palavra("onde você está indo?", "minto wuksus", R.raw.phrase_where_are_you_going));
        palavras.add(new Palavra("qual o seu nome?", "tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        palavras.add(new Palavra("meu nome é...", "oyaaset...", R.raw.phrase_my_name_is));
        palavras.add(new Palavra("como está se sentindo?", "michәksәs?", R.raw.phrase_how_are_you_feeling));
        palavras.add(new Palavra("estou me sentindo bem", "kuchi achit", R.raw.phrase_im_feeling_good));
        palavras.add(new Palavra("você está vindo?", "әәnәs'aa?", R.raw.phrase_are_you_coming));
        palavras.add(new Palavra("sim, estou indo", "hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        palavras.add(new Palavra("estou indo", "әәnәm", R.raw.phrase_im_coming));
        palavras.add(new Palavra("vamos", "yoowutis", R.raw.phrase_lets_go));
        palavras.add(new Palavra("venha aqui", "әnni'nem", R.raw.phrase_come_here));

        PalavraAdapter itensAdapter = new PalavraAdapter(this, palavras, R.color.categoria_frases);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(itensAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Palavra PalavraClicada = palavras.get(position);

                releaseMediaPlayer();

                int resultado = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(resultado==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mMediaPlayer = MediaPlayer.create(FrasesActivity.this, PalavraClicada.getReferenciaAudio());
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
            mMediaPlayer=null;

            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}