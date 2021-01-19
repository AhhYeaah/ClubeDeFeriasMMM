package com.example.clubeseis.app_pos_login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.clubeseis.R;
import com.example.clubeseis.login_and_splashs.Splash;
import com.example.clubeseis.sql.Data;
import com.google.android.material.navigation.NavigationView;

public class TelaAplicativo extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_aplicativo);

        final View mainview = this.findViewById(android.R.id.content);
        final Context context = getApplicationContext(); //Criar contexto pro toast
        Data data = new Data();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_carteirinha, R.id.nav_alocacao)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        View hView = navigationView.getHeaderView(0);

        TextView nome_usuario = (TextView) hView.findViewById(R.id.lbl_name_header);
        nome_usuario.setText(data.getUserName());

        TextView email_usuario = (TextView) hView.findViewById(R.id.lbl_email_header);
        email_usuario.setText(data.getUserEmail());

        //TODO: CHANGE APP STYLE
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tela_aplicativo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_logout:
                Data data = new Data(); //isso é mto feio pqp
                confirmacao(this, data);

            default: //isso é para abrir o drawer, o metodo on onOptionItemSelected tambem funciona pra ele, então se dermos um override ele vai parar de abrir
                return super.onOptionsItemSelected(item);
        }
    }

    //Para poder confirmar se o usuario quer sair

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();
        /*
         *   Creditos ao sancto homem HW.Master
         *   https://stackoverflow.com/questions/5448653/how-to-implement-onbackpressed-in-fragments
         */
        if (count == 0) {
            if (exit)
                TelaAplicativo.this.finish();
            else {
                Toast.makeText(this, R.string.confirmacao_fechar,
                        Toast.LENGTH_SHORT).show();
                exit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exit = false;
                    }
                }, 3 * 1000);
            }
        } else {
            getSupportFragmentManager().popBackStack();
        }

    }


    private void confirmacao(Context context, final Data data) {
        new AlertDialog.Builder(context)

                .setTitle(R.string.Alert_titulo)
                .setMessage(R.string.Alert_sair)
                .setPositiveButton(R.string.Sim, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                TelaAplicativo.this, Splash.class); //intent para mudar a tela para o login após o logout

                        data.loggout(); //saindo
                        startActivity(intent); //iniciando atividade login
                        finish(); //tela inicial is dead, rip
                    }

                })
                .setNegativeButton(R.string.Nao, null)
                .show();
    }

}
