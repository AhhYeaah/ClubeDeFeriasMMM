package com.example.clubeseis.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.clubeseis.R;
import com.example.clubeseis.app_pos_login.ui.FragmentsPosLogin.Alocacao.AlocacaoSegundo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {


    public SelectDateFragment() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        final long oneday = 24 * 60 * 60 * 1000;

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, yy, mm, dd);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + oneday * 31);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        return datePickerDialog;
    }


    public void populateSetDate(int year, int month, int day) {
        Log.i("My Calendar", month + "/" + day + "/" + year);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        populateSetDate(year, month + 1, dayOfMonth);
        Calendar newDate = Calendar.getInstance();
        newDate.set(year, month, dayOfMonth);
        if (newDate.get(Calendar.DAY_OF_WEEK) == 2 ||
                newDate.get(Calendar.DAY_OF_WEEK) == 3 ||
                newDate.get(Calendar.DAY_OF_WEEK) == 4 ||
                newDate.get(Calendar.DAY_OF_WEEK) == 5) {
            MyDialogFragment alert = new MyDialogFragment();
            alert.showDialogError(getActivity(), "O clube só esta aberto das sexta aos domingos");

        } else {

            Bundle bundle = new Bundle();

            DateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");
            bundle.putString("data", dtf.format(newDate.getTime()));
            bundle.putInt("diadasemana", newDate.get(Calendar.DAY_OF_WEEK));

            //TODO: MUDAR ISSO PRO SQL SERVER RECONHECER, USANDO UM SIMPLE DATE FORMATTER
            AlocacaoSegundo alocacaoSegundo = new AlocacaoSegundo();
            alocacaoSegundo.setArguments(bundle);
            final FragmentTransaction iniciarFrag = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, alocacaoSegundo);
            iniciarFrag.commit();

        }
    }
}