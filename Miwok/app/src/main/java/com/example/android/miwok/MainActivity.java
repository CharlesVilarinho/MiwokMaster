package com.example.android.miwok;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**Classe principal do aplicativo*/
public class MainActivity extends AppCompatActivity {

    /**Cria a pagina inicial do sistema com as TextViews */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView numerosTextView = (TextView) findViewById(R.id.numeros);
        numerosTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NumerosActivity.class);

                startActivity(intent);
            }
        });

        TextView familiaTextView = (TextView) findViewById(R.id.familia);
        familiaTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FamiliaActivity.class);

                startActivity(intent);
            }
        });

        TextView coresTextView = (TextView) findViewById(R.id.cores);
        coresTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CoresActivity.class);

                startActivity(intent);
            }
        });

        TextView frasesTextView = (TextView) findViewById(R.id.frases);
        frasesTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FrasesActivity.class);

                startActivity(intent);
            }
        });
    }
}