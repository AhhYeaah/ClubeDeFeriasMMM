package com.example.clubeseis.login_and_splashs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.clubeseis.Fragments.OopsFragment;
import com.example.clubeseis.R;
import com.example.clubeseis.app_pos_login.TelaAplicativo;
import com.example.clubeseis.sql.Conexao;
import com.example.clubeseis.sql.Data;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final Conexao conexao = new Conexao();
        final Data data = new Data();
        final Context context = getApplicationContext();


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy); //politica do banco (nem mexe nessa merda)

        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction iniciarFrag = fragmentManager.beginTransaction().replace(R.id.content_fragment, new OopsFragment());

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                conexao.conectarBanco(context);
                if (conexao.getConnection() == null) {
                    iniciarFrag.commit();
                } else {
                    if (data.isUserLog()) {
                        mostrarTelaActivity();
                    } else {
                        mostrarLoginActivity();
                    }
                }
            }
        }, 2000);
    }

    private void mostrarLoginActivity() {
        Intent intent = new Intent(
                Splash.this, LoginActivity.class
        );

        startActivity(intent);
        finish();
    }

    private void mostrarTelaActivity() {
        Intent intent = new Intent(
                Splash.this, TelaAplicativo.class
        );

        startActivity(intent);
        finish();
    }

}