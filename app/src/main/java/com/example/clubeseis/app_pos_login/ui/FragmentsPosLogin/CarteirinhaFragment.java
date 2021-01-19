package com.example.clubeseis.app_pos_login.ui.FragmentsPosLogin;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.clubeseis.R;
import com.example.clubeseis.sql.Data;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class CarteirinhaFragment extends Fragment {

    public CarteirinhaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_carteirinha, container, false);
        final ImageView imgQRCode = view.findViewById(R.id.img_QRCode);
        final TextView tv_ola_nome = view.findViewById(R.id.tv_nome_carteirinha);
        final Data data = new Data();
        String arr[] = data.getUserName().split(" ", 2);
        String nome = arr[0];
        tv_ola_nome.setText("Olá " + nome.substring(0, 1).toUpperCase() + nome.substring(1).toLowerCase() + "!");
        QRCodeGenerator(data.getUserCarteira(), imgQRCode);

        return view;
    }

    public void QRCodeGenerator(int cart, ImageView imgQRCode) {
        String texto = String.valueOf(cart);
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter
                    .encode(texto, BarcodeFormat.QR_CODE, 500, 500);
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(bitMatrix);
            imgQRCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}