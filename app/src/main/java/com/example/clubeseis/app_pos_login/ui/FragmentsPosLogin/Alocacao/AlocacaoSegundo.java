package com.example.clubeseis.app_pos_login.ui.FragmentsPosLogin.Alocacao;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.clubeseis.R;


public class AlocacaoSegundo extends Fragment {

    public AlocacaoSegundo() {
        // Required empty public constructor

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alocacao_segundo, container, false);

        final RadioButton rad_superf = view.findViewById(R.id.rad_superf);
        final RadioButton rad_megaf = view.findViewById(R.id.rad_megaf);
        final Button btn_alocacaosegundo = view.findViewById(R.id.btn_proximo_aloca);

        btn_alocacaosegundo.setEnabled(false);

        rad_megaf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rad_superf.isChecked()) {
                    rad_superf.setChecked(false);
                }

                rad_megaf.setChecked(true);
                btn_alocacaosegundo.setEnabled(true);
            }
        });

        rad_superf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rad_megaf.isChecked()) {
                    rad_megaf.setChecked(false);
                }

                rad_superf.setChecked(true);
                btn_alocacaosegundo.setEnabled(true);
            }
        });

        btn_alocacaosegundo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean bol = rad_superf.isChecked();
                AlocacaoFinal alocacaoFinal = new AlocacaoFinal();
                Bundle bundle = new Bundle();
                bundle.putInt("diadasemana", getArguments().getInt("diadasemana"));
                bundle.putString("data", getArguments().getString("data"));
                bundle.putBoolean("superf", !bol);
                alocacaoFinal.setArguments(bundle);
                final FragmentTransaction iniciarFrag = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, alocacaoFinal).addToBackStack(null);
                iniciarFrag.commit();
            }
        });
        return view;
    }

}