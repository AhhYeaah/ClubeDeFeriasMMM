package com.example.clubeseis.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.clubeseis.R;


public class OopsFragment extends Fragment {

    public OopsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_oops, container, false);
        Button con_again = view.findViewById(R.id.con_again);

        con_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().recreate();
                getParentFragmentManager().beginTransaction().remove(OopsFragment.this).commitAllowingStateLoss(); //eu mudei isso aq
            }
        });

        return view;
    }
}