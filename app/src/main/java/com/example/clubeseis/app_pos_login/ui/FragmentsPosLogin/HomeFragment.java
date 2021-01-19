package com.example.clubeseis.app_pos_login.ui.FragmentsPosLogin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.clubeseis.R;
import com.example.clubeseis.app_pos_login.ui.FragmentsPosLogin.Alocacao.AlocacaoFragment;
import com.google.android.material.navigation.NavigationView;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final NavigationView navigationView = getActivity().findViewById(R.id.nav_view);

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        final Button button_home_alocacao = view.findViewById(R.id.home_button_alocacao);
        final Button button_home_carteirinha = view.findViewById(R.id.home_button_carteirinha);

        button_home_alocacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new CarteirinhaFragment()).commit();
                navigationView.getMenu().getItem(1).setChecked(true);
                button_home_alocacao.setEnabled(false);
                button_home_carteirinha.setEnabled(false);

            }
        });

        button_home_carteirinha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new AlocacaoFragment()).commit();
                navigationView.getMenu().getItem(2).setChecked(true);
                button_home_alocacao.setEnabled(false);
                button_home_carteirinha.setEnabled(false);
            }
        });

        return view;
    }

}