package com.example.clubeseis.app_pos_login.ui.FragmentsPosLogin.Alocacao;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.example.clubeseis.Fragments.MyDialogFragment;
import com.example.clubeseis.R;
import com.example.clubeseis.login_and_splashs.Splash;
import com.example.clubeseis.sql.Data;
import com.example.clubeseis.sql.Select;

import java.text.ParseException;
import java.text.SimpleDateFormat;


public class AlocacaoFinal extends Fragment {


    public AlocacaoFinal() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alocacao_final, container, false);
        final Select select = new Select();
        final Data data = new Data();

        TextView tv_descricao = view.findViewById(R.id.tv_descricao_aloc);
        TextView tv_obs = view.findViewById(R.id.tv_obs);

        TextView tv_dia_escolhido = view.findViewById(R.id.tv_dia_escolhido);
        TextView tv_dia_escolhido_preco = view.findViewById(R.id.tv_preco_alocacao);

        TextView tv_tipo = view.findViewById(R.id.tv_tipo);
        TextView tv_desconto = (TextView) view.findViewById(R.id.tv_desconto);
        tv_desconto.setPaintFlags(tv_desconto.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        TextView tv_preco_tipo = view.findViewById(R.id.tv_preco_tipo);

        TextView tv_preco_total = view.findViewById(R.id.tv_preco_total);

        final RadioButton rb_mastercard = view.findViewById(R.id.rb_mastercard);
        final LottieAnimationView mastercard = view.findViewById(R.id.mastercard);
        final LottieAnimationView paypal = view.findViewById(R.id.paypal);
        final RadioButton rb_paypal = view.findViewById(R.id.rb_paypal);
        int id_valor = 0;
        int preco = 200;

        final Button btn_concluir = view.findViewById(R.id.btn_concluir_aloc);
        btn_concluir.setEnabled(false);

        rb_mastercard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rb_paypal.isChecked()) {
                    rb_paypal.setChecked(false);
                }

                btn_concluir.setEnabled(true);
                mastercard.playAnimation();
                paypal.pauseAnimation();
            }
        });

        rb_paypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rb_mastercard.isChecked()) {
                    rb_mastercard.setChecked(false);
                }

                btn_concluir.setEnabled(true);
                mastercard.pauseAnimation();
                paypal.playAnimation();
            }
        });

        int dia = getArguments().getInt("diadasemana");
        if (dia == 6) {
            tv_dia_escolhido.setText("Alocação realizada na sexta:");
            tv_dia_escolhido_preco.setText("R$" + preco + ",00");

            Log.w("ada", String.valueOf(dia));
        }
        if (dia == 7) {
            preco = 300;
            tv_dia_escolhido.setText("Alocação realizada no sábado:");
            tv_dia_escolhido_preco.setText("R$" + preco + ",00");

        }

        if (dia == 1) {
            preco = 300;
            tv_dia_escolhido.setText("Alocação realizada no domingo:");
            tv_dia_escolhido_preco.setText("R$" + preco + ",00");

        }

        if (getArguments().getBoolean("superf")) {
            preco += 150;
            tv_tipo.setText("Mega festa!");
            tv_obs.setText("Festa com até 100 pessoas");
            tv_preco_tipo.setText("R$150,00");
        } else {
            tv_tipo.setText("Super festa!");
            tv_obs.setText("Festa com até 50 pessoas");
            tv_preco_tipo.setText("Grátis!");
            tv_preco_tipo.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.verde));
            tv_desconto.setVisibility(View.VISIBLE);
        }
        tv_preco_total.setText("R$" + preco + ",00");
        tv_descricao.setText("O dia escolhido foi " + getArguments().getString("data"));

        if (preco == 200) {
            id_valor = 1;
        } else if (preco == 350) {
            id_valor = 2;
        } else if (preco == 300) {
            id_valor = 3;
        } else if (preco == 450) {
            id_valor = 4;
        }

        final int finalId_valor = id_valor;
        btn_concluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialogFragment alert = new MyDialogFragment();
                alert.showDialogDone(getActivity());
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(
                                getActivity(), Splash.class
                        );

                        startActivity(intent);
                        getActivity().finish();
                    }
                }, 3000);
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                String dataa = getArguments().getString("data");
                java.util.Date utilDate = new java.util.Date();
                try {
                    utilDate = formatter.parse(dataa);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                select.doAssocAloc(finalId_valor, data.getUserCarteira(), sqlDate);

            }
        });


        return view;
    }
}