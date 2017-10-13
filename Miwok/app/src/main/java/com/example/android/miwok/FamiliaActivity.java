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

public class FamiliaActivity extends AppCompatActivity {

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
    private MediaPlayer.OnCompletionListener mOnCompletionListener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
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
        palavras.add(new Palavra("pai", "әpә", R.drawable.family_father, R.raw.family_father));
        palavras.add(new Palavra("mãe", "әṭa", R.drawable.family_mother, R.raw.family_mother));
        palavras.add(new Palavra("filho", "angsi", R.drawable.family_son, R.raw.family_son));
        palavras.add(new Palavra("filha", "tune", R.drawable.family_daughter, R.raw.family_daughter));
        palavras.add(new Palavra("irmão mais velho", "taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
        palavras.add(new Palavra("irmão mais novo", "chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        palavras.add(new Palavra("irmã mais velha", "teṭe", R.drawable.family_older_sister, R.raw.family_older_sister));
        palavras.add(new Palavra("irmã mais nova", "kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        palavras.add(new Palavra("avó", "ama", R.drawable.family_grandmother, R.raw.family_grandmother));
        palavras.add(new Palavra("avô", "paapa", R.drawable.family_grandfather, R.raw.family_grandfather));

        PalavraAdapter itensAdapter = new PalavraAdapter(this, palavras, R.color.categoria_familia);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(itensAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Palavra PalavraClicada = palavras.get(position);

                releaseMediaPlayer();

                int resultado = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(resultado==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mMediaPlayer = MediaPlayer.create(FamiliaActivity.this, PalavraClicada.getReferenciaAudio());
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
