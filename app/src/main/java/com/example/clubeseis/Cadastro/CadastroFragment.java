package com.example.clubeseis.Cadastro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.clubeseis.Fragments.MyDialogFragment;
import com.example.clubeseis.R;
import com.example.clubeseis.login_and_splashs.Splash;
import com.example.clubeseis.sql.Conexao;
import com.example.clubeseis.sql.Data;
import com.example.clubeseis.sql.Select;


public class CadastroFragment extends Fragment {


    public CadastroFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastro, container, false);
        String nome = getArguments().getString("nome");

        final EditText edt_email = view.findViewById(R.id.edt_email);
        final EditText edt_password = view.findViewById(R.id.edt_password);
        final EditText edt_password_again = view.findViewById(R.id.edt_password_again);
        final Button btn_cadastro = view.findViewById(R.id.btn_cadastra);
        final CadastroViewModel cadastroViewModel = new CadastroViewModel();
        final Select select = new Select();
        final Conexao conexao = new Conexao();
        final Context context = getContext();
        final Data data = new Data();

        btn_cadastro.setEnabled(false);

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
                if (cadastroViewModel.isEmailValid(edt_email.getText().toString()) &&
                        cadastroViewModel.isPasswordValid(edt_password.getText().toString()) &&
                        cadastroViewModel.isSecondPasswordValid(edt_password.getText().toString(), edt_password_again.getText().toString())) {
                    cadastroViewModel.isEverythingValid(btn_cadastro);
                } else if (!cadastroViewModel.isEmailReady()) {
                    edt_email.setError("Digite um email valido (deve conter @)");
                    btn_cadastro.setEnabled(false);
                } else if (!cadastroViewModel.isPasswordValid(edt_password.getText().toString())) {
                    edt_password.setError("A senha deve ter mais de 6 caracteres");
                    btn_cadastro.setEnabled(false);
                } else if (!cadastroViewModel.isSecondPasswordValid(edt_password_again.getText().toString(), edt_password.getText().toString())) {
                    edt_password_again.setError("Senhas nao coincidem");
                    btn_cadastro.setEnabled(false);
                }

            }

        };

        edt_email.addTextChangedListener(afterTextChangedListener);
        edt_password.addTextChangedListener(afterTextChangedListener);
        edt_password_again.addTextChangedListener(afterTextChangedListener);

        btn_cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (select.updateCartCpf(getArguments().getString("cpf"), edt_email.getText().toString(), edt_password.getText().toString())) {
                    select.logar(edt_email.getText().toString(), edt_password.getText().toString());
                    edt_email.setEnabled(false);
                    btn_cadastro.setEnabled(false);
                    MyDialogFragment alert = new MyDialogFragment();
                    alert.showDialogNice(getActivity(), "Sucesso!\n voce sera logado em sua nova conta em 5 segundos...");

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            data.setUserLog(true);
                            Intent intent = new Intent(getActivity(), Splash.class); //intent para mudar a tela para o login após o logout
                            startActivity(intent); //iniciando atividade login
                            getActivity().finish(); //tela inicial is dead, rip
                        }
                    }, 6000);
                } else {
                    MyDialogFragment alert = new MyDialogFragment();
                    alert.showDialogError(getActivity(), "Esse email ja esta em uso");
                    edt_email.setError("Este email ja esta em uso");
                    btn_cadastro.setEnabled(false);
                }
            }

        });

        MyDialogFragment alert = new MyDialogFragment();
        alert.showDialogHearth(getActivity(), "Olá " + nome);

        return view;
    }

}