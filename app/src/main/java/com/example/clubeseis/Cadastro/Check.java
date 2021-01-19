package com.example.clubeseis.Cadastro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.clubeseis.Fragments.MyDialogFragment;
import com.example.clubeseis.R;
import com.example.clubeseis.login_and_splashs.LoginActivity;
import com.example.clubeseis.sql.Select;

public class Check extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        this.overridePendingTransition(R.anim.swipe_left,
                R.anim.swipe_right);

        final Activity activity = this;
        final View view = findViewById(R.id.container_cadastro);
        final EditText edt_CPF = findViewById(R.id.edt_cpf);
        final EditText edt_cart = findViewById(R.id.edt_num_cart);
        final Button btn_prox = findViewById(R.id.btn_prosseguir);
        final Button btn_volta = findViewById(R.id.btn_voltar);
        final CheckViewModel checkViewModel = new CheckViewModel();
        final Context context = getApplicationContext();
        final Select select = new Select();
        btn_prox.setEnabled(false);

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
                if (checkViewModel.isCartValid(edt_cart.getText().toString()) && checkViewModel.isCPFValido(Mask.unmask(edt_CPF.getText().toString()))) {
                    checkViewModel.isEverythingValid(btn_prox);
                } else if (!checkViewModel.isCPFValido(Mask.unmask(edt_CPF.getText().toString()))) {
                    edt_CPF.setError("Digite um CPF valido");
                    btn_prox.setEnabled(false);
                } else if (!checkViewModel.isCartValid(edt_cart.getText().toString())) {
                    edt_cart.setError("Por favor, digite um numero valido (Deve ter 9 caracteres)");
                    btn_prox.setEnabled(false);
                }

            }

        };

        edt_CPF.addTextChangedListener(Mask.insert(Mask.CPF_MASK, edt_CPF));
        edt_cart.addTextChangedListener(afterTextChangedListener);
        edt_CPF.addTextChangedListener(afterTextChangedListener);

        btn_volta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context, LoginActivity.class);
                startActivity(myIntent);
                finish();
            }
        });

        btn_prox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkViewModel.isCPFValido(Mask.unmask(edt_CPF.getText().toString())) && checkViewModel.isCartValid(edt_cart.getText().toString())) {
                    if (select.checarCartCpf(Integer.parseInt(edt_cart.getText().toString()), Mask.unmask(edt_CPF.getText().toString())) == 1) {
                        IniciarCadastroFrag(Mask.unmask(edt_CPF.getText().toString()), select.getName_check());
                        btn_prox.setVisibility(View.GONE);

                    } else if (select.checarCartCpf(Integer.parseInt(edt_cart.getText().toString()), Mask.unmask(edt_CPF.getText().toString())) == 2) {
                        MyDialogFragment alert = new MyDialogFragment();
                        alert.showDialogError(activity, "Essa conta ja possui um email cadastrado");

                    } else if (select.checarCartCpf(Integer.parseInt(edt_cart.getText().toString()), Mask.unmask(edt_CPF.getText().toString())) == 0) {
                        MyDialogFragment alert = new MyDialogFragment();
                        alert.showDialogError(activity, "Não existe nenhuma conta cadastrada nessa carteirinha");
                    }
                }
            }
        });


    }

    public void IniciarCadastroFrag(String cpf, String name) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        CadastroFragment cadastroFragment = new CadastroFragment();
        Bundle bundle = new Bundle();
        bundle.putString("cpf", cpf);
        bundle.putString("nome", name);
        cadastroFragment.setArguments(bundle);
        final FragmentTransaction iniciarFrag = fragmentManager.beginTransaction().replace(R.id.container_cadastro, cadastroFragment);
        iniciarFrag.commit();
    }
}