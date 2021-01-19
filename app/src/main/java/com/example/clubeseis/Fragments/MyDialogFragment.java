package com.example.clubeseis.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.clubeseis.R;

public class MyDialogFragment {

    public void showDialogError(Activity activity, String msg) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_check);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setWindowAnimations(R.style.PauseDialogAnimation);

        TextView text = (TextView) dialog.findViewById(R.id.tv_erro_mesage);
        text.setText(msg);

        LottieAnimationView lottieAnimationView = dialog.findViewById(R.id.AnimLottie);
        lottieAnimationView.setAnimation("error.json");

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setBackgroundResource(R.drawable.no_dialog_button_style);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public void showDialogNice(Activity activity, String msg) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_check);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        LottieAnimationView lottieAnimationView = dialog.findViewById(R.id.AnimLottie);
        lottieAnimationView.setAnimation("done.json");


        TextView text = (TextView) dialog.findViewById(R.id.tv_erro_mesage);
        text.setText(msg);

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setBackgroundResource(R.drawable.yes_dialog_button_style);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public void showDialogHearth(Activity activity, String msg) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_check);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        LottieAnimationView lottieAnimationView = dialog.findViewById(R.id.AnimLottie);
        lottieAnimationView.setAnimation("coracao.json");


        TextView text = (TextView) dialog.findViewById(R.id.tv_erro_mesage);
        text.setText(msg);
        text.setTextSize(16f);

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setText("Olá!");
        dialogButton.setBackgroundResource(R.drawable.coracao_dialog_button_style);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public void showDialogDone(final Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_completed);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 3000);

    }

}