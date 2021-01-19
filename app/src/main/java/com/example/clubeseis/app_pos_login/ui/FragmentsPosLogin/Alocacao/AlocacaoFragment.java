package com.example.clubeseis.app_pos_login.ui.FragmentsPosLogin.Alocacao;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.example.clubeseis.Fragments.MyDialogFragment;
import com.example.clubeseis.Fragments.SelectDateFragment;
import com.example.clubeseis.R;
import com.example.clubeseis.login_and_splashs.Splash;
import com.example.clubeseis.sql.Data;
import com.example.clubeseis.sql.Select;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AlocacaoFragment extends Fragment {

    public AlocacaoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_alocacao, container, false);
        final Data data = new Data();
        final Select select = new Select();
        String tipo = null;
        int tipoint = 0;
        Date currentTime = Calendar.getInstance().getTime();
        final Context context = getActivity().getApplicationContext();
        final Calendar newCalendar = Calendar.getInstance();
        boolean bol = select.canAssocAloc(data.getUserCarteira());


        if (bol) {

            AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
            final AlphaAnimation longerFadeIn = new AlphaAnimation(0.0f, 1.0f);
            TextView tv_comecar = view.findViewById(R.id.tv_comecar);
            tv_comecar.startAnimation(fadeIn);
            fadeIn.setDuration(2000);
            final LottieAnimationView lottieAnimationView = view.findViewById(R.id.anim_lottie_aloc);
            lottieAnimationView.setVisibility(View.INVISIBLE);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    lottieAnimationView.setVisibility(View.VISIBLE);
                    lottieAnimationView.setAnimation(longerFadeIn);
                }
            }, 2000);
            longerFadeIn.setDuration(2500);


            Button btn = view.findViewById(R.id.btn_comecar_alocc);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment newFragment = new SelectDateFragment();
                    newFragment.show(getFragmentManager(), "DatePicker");
                }
            });

        } else {
            try {
                if (select.whatsAssocAloc(data.getUserEmail(), data.getUserPassword(), context)) {
                    if (data.getAlocDate().getTime().after(currentTime)) {
                        select.deletarAloc(data.getUserEmail(), data.getUserPassword());
                    }
                    if (select.getValorAloc() == 200 || select.getValorAloc() == 300) {
                        tipo = "Super Festa";
                        tipoint = 50;
                    } else {
                        tipo = "Mega Festa";
                        tipoint = 100;
                    }
                    //Se houver uma alocação marcada
                    view = inflater.inflate(R.layout.fragment_aloc_true, container, false);
                    TextView tv_desc = view.findViewById(R.id.tv_desc_aloc);
                    DateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");
                    tv_desc.setText("A sua " + tipo + " esta chegando! \n\nMarcado para o dia: " + dtf.format(data.getAlocDate().getTime()) + "\n\nNão se esqueça de convidar todos os seus " + tipoint + " amigos!");
                    final Button button = view.findViewById(R.id.button_cancela_aloc);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            select.deletarAloc(data.getUserEmail(), data.getUserPassword());
                            MyDialogFragment alert = new MyDialogFragment();
                            alert.showDialogNice(getActivity(), "Sucesso!\nSua alocação foi deletada\nAtualizaremos a aplicação em 5 segundos...!");
                            button.setEnabled(false);
                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(
                                            getActivity(), Splash.class
                                    );

                                    startActivity(intent);
                                    getActivity().finish();
                                }
                            }, 5000);

                        }
                    });

                } else {

                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return view;
    }

}
