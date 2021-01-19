package com.example.clubeseis.login_and_splashs;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.clubeseis.Cadastro.Check;
import com.example.clubeseis.R;
import com.example.clubeseis.app_pos_login.TelaAplicativo;
import com.example.clubeseis.sql.Conexao;
import com.example.clubeseis.sql.Data;
import com.example.clubeseis.sql.Select;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET},
                PackageManager.PERMISSION_GRANTED); //pedir internet
        final LoginViewModel loginViewModel = new LoginViewModel();
        final Data data = new Data();
        final Select select = new Select();

        final Conexao conexao = new Conexao(); //criar classe de conexão com o banco
        final Context context = getApplicationContext(); //Criar contexto pro toast
        final View mainview = this.findViewById(android.R.id.content);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.btn_login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        final TextView lbl_cadastrar = findViewById(R.id.lbl_registrar);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy); //politica do banco (nem mexe nessa merda)

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (select.logar(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString())) {
                    data.setUserLog(true);
                    Intent myIntent = new Intent(context, TelaAplicativo.class);
                    startActivity(myIntent);
                    finish();
                } else {
                    loginButton.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake));
                    if (conexao.getConnection() == null) {
                        Toast.makeText(context, "Sem conexão", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, "Login invalido", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

        lbl_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context, Check.class);
                startActivity(myIntent);
                finish();
            }
        });


        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (loginViewModel.isUserNameValid(usernameEditText.getText().toString()) &&
                        loginViewModel.isPasswordValid(passwordEditText.getText().toString())) {
                    loginViewModel.isEverythingValid(loginButton);
                } else if (!loginViewModel.isUserNameValid(usernameEditText.getText().toString())) {
                    usernameEditText.setError("Insira um email valido!");
                    loginButton.setEnabled(false);
                } else if (!loginViewModel.isPasswordValid(passwordEditText.getText().toString())) {
                    passwordEditText.setError("A senha deve ser maior que 6 caracteres");
                    loginButton.setEnabled(false);
                }

            }

        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        select.setContext(getApplicationContext());
    }
}