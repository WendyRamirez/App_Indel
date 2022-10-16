package com.example.programarotacionbeta2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.programarotacionbeta2.Adapters.AdapterUsers;
import com.example.programarotacionbeta2.Adapters.DatosUsuarios;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    String date, day, cambiorecycler, hora_actual;
    TextView hora, dia;
    ArrayList<DatosUsuarios> UserList = new ArrayList<>();
    int contador = 1, variable_1 = 1, Variable_2 = 0, hora_actual_num = 0, hora_guardada_num = 0,
            hora_guardar = 0, enviar_hora = 0;
    int Minutos = 0;
    RecyclerView RVusuarios;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hora = findViewById(R.id.tClock);
        dia = findViewById(R.id.DayClock);
        RVusuarios = findViewById(R.id.RVusuarios);

        //Aqui se obtienen los datos de las fechas
        date = new SimpleDateFormat("HH:mm").format(new Date());
        cambiorecycler = new SimpleDateFormat("mm").format(new Date());
        day = new SimpleDateFormat("d 'de' MMMM").format(new Date());

        dia.setText(day);
        hora.setText(date);
        ejecutar();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }


    }

    private void ejecutar() {
        //Aqui se hace un bucle para verficiar las hora y cambiar la misma
        //y cambiar el contenido de la tabla
        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                cambiorecycler = new SimpleDateFormat("mm").format(new Date());
                date = new SimpleDateFormat("HH:mm").format(new Date());
                day = new SimpleDateFormat("d 'de' MMMM").format(new Date());
                cambiarrecycler();
                dia.setText(day);
                hora.setText(date);
                cambiarrecycler();

                handler.postDelayed(this, 100);
            }
        }, 100);

    }

    public void cambiarrecycler() {
        try {
            preferences = getSharedPreferences("horario", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            hora_actual = new SimpleDateFormat("H").format(new Date());
            hora_actual_num = Integer.parseInt(hora_actual);
            Minutos = Integer.parseInt(cambiorecycler);

            if(hora_actual_num == 0)
            {
                editor.putInt("hora", hora_actual_num);
                editor.commit();
                hora_guardada_num = preferences.getInt("hora", 0);
            }

            if (Objects.equals(preferences.getInt("hora", 0), 0)
                    || Objects.equals(preferences.getInt("hora", 0), null)) {
                editor.putInt("hora", hora_actual_num);
                editor.commit();
                hora_guardada_num = preferences.getInt("hora", 0);
            }

            if (hora_guardada_num < hora_actual_num) {
                hora_guardada_num = hora_actual_num;
            }


            if (hora_guardada_num == hora_actual_num) {
                if (Minutos >= 30) {
                    variable_1 = 1;

                    hora_guardar = hora_guardada_num++;
                    editor.putInt("hora", hora_guardar);
                    editor.commit();

                    AdapterUsers adapter = new AdapterUsers(listausuarios(hora_actual_num), MainActivity.this);
                    RVusuarios.setHasFixedSize(true);
                    RVusuarios.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    RVusuarios.setAdapter(adapter);

                } else if (variable_1 == 1) {

                    AdapterUsers adapter = new AdapterUsers(listausuarios(hora_actual_num - 1), MainActivity.this);
                    RVusuarios.setHasFixedSize(true);
                    RVusuarios.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    RVusuarios.setAdapter(adapter);

                    variable_1 = 0;
                }
            } else if (variable_1 == 1) {

                AdapterUsers adapter = new AdapterUsers(listausuarios(hora_actual_num), MainActivity.this);
                RVusuarios.setHasFixedSize(true);
                RVusuarios.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                RVusuarios.setAdapter(adapter);

                variable_1 = 0;
            }


            /*if (Minutos >= 30 && Minutos <= 59) {
                    if (variable_1 == 0) {
                        UserList.clear();
                        UserList.add(new DatosUsuarios("Indelfonso", 10));
                        UserList.add(new DatosUsuarios("Gabriel", 20));
                        UserList.add(new DatosUsuarios("Sara", 30));
                        UserList.add(new DatosUsuarios("Samuel", 40));
                        UserList.add(new DatosUsuarios("Luis", 50));
                        UserList.add(new DatosUsuarios("Gerardo", 60));
                        UserList.add(new DatosUsuarios("Rene", 70));
                        AdapterUsers adapter = new AdapterUsers(UserList, MainActivity.this);
                        RVusuarios.setHasFixedSize(true);
                        RVusuarios.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        RVusuarios.setAdapter(adapter);
                        variable_1 = 1;
                        Variable_2 = 0;

                }
            }else if(Minutos >= 0 && Minutos <= 29){
                    if (Variable_2 == 0) {

                    UserList.clear();
                    UserList.add(new DatosUsuarios("Edgar", 1));
                    UserList.add(new DatosUsuarios("Carlos", 2));
                    UserList.add(new DatosUsuarios("Wendy", 3));
                    UserList.add(new DatosUsuarios("Mayte", 4));
                    UserList.add(new DatosUsuarios("Valeria", 5));
                    UserList.add(new DatosUsuarios("Daniel", 6));
                    UserList.add(new DatosUsuarios("Mauricio", 7));
                    AdapterUsers adapter = new AdapterUsers(UserList, MainActivity.this);
                    RVusuarios.setHasFixedSize(true);
                    RVusuarios.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    RVusuarios.setAdapter(adapter);

                    variable_1 =0;
                    Variable_2 = 1;
                }else{
                    contador = 1;
                }
            }*/

        } catch (Exception e) {

        }
    }

    public ArrayList<DatosUsuarios> listausuarios(int hora){

        switch (hora){
            case 13:{

                UserList.clear();
                UserList.add(new DatosUsuarios("Edgar", 1));
                UserList.add(new DatosUsuarios("Carlos", 2));
                UserList.add(new DatosUsuarios("Wendy", 3));
                UserList.add(new DatosUsuarios("Mayte", 4));
                UserList.add(new DatosUsuarios("Valeria", 5));
                UserList.add(new DatosUsuarios("Daniel", 6));
                UserList.add(new DatosUsuarios("Mauricio", 7));
                break;
            }

            case 14:{
                UserList.clear();
                UserList.add(new DatosUsuarios("Indelfonso", 10));
                UserList.add(new DatosUsuarios("Gabriel", 20));
                UserList.add(new DatosUsuarios("Sara", 30));
                UserList.add(new DatosUsuarios("Samuel", 40));
                UserList.add(new DatosUsuarios("Luis", 50));
                UserList.add(new DatosUsuarios("Gerardo", 60));
                UserList.add(new DatosUsuarios("Rene", 70));

                break;
            }

            case 15:{
                UserList.clear();
                UserList.add(new DatosUsuarios("Lopez", 11));
                UserList.add(new DatosUsuarios("Raya", 21));
                UserList.add(new DatosUsuarios("Ramirez", 31));
                UserList.add(new DatosUsuarios("Ortiz", 41));
                UserList.add(new DatosUsuarios("Gonzales", 51));
                UserList.add(new DatosUsuarios("Perez", 61));
                UserList.add(new DatosUsuarios("Casas", 71));
                break;
            }

            case 16:{
                UserList.clear();
                UserList.add(new DatosUsuarios("Castro", 12));
                UserList.add(new DatosUsuarios("Marquez", 22));
                UserList.add(new DatosUsuarios("Torres", 32));
                UserList.add(new DatosUsuarios("Herrera", 42));
                UserList.add(new DatosUsuarios("Miranda", 52));
                UserList.add(new DatosUsuarios("Pulido", 62));
                UserList.add(new DatosUsuarios("Tezoco", 72));
                break;
            }

            case 17:{
                UserList.clear();
                UserList.add(new DatosUsuarios("Juan", 13));
                UserList.add(new DatosUsuarios("Manuel", 23));
                UserList.add(new DatosUsuarios("Angelica", 33));
                UserList.add(new DatosUsuarios("Brenda", 43));
                UserList.add(new DatosUsuarios("Jarely", 53));
                UserList.add(new DatosUsuarios("Alexis", 63));
                UserList.add(new DatosUsuarios("Maria", 73));
                break;
            }

            case 18:{
                UserList.clear();
                UserList.add(new DatosUsuarios("Brayan", 14));
                UserList.add(new DatosUsuarios("Celia", 24));
                UserList.add(new DatosUsuarios("Cecilia", 34));
                UserList.add(new DatosUsuarios("Paulina", 44));
                UserList.add(new DatosUsuarios("Alfonso", 54));
                UserList.add(new DatosUsuarios("Jesus", 64));
                UserList.add(new DatosUsuarios("Katalina", 74));
                break;
            }

            case 19:{
                UserList.clear();
                UserList.add(new DatosUsuarios("Hugo", 15));
                UserList.add(new DatosUsuarios("Mateo", 25));
                UserList.add(new DatosUsuarios("Martin", 35));
                UserList.add(new DatosUsuarios("Leo", 45));
                UserList.add(new DatosUsuarios("Lucas", 55));
                UserList.add(new DatosUsuarios("Daniel", 65));
                UserList.add(new DatosUsuarios("Alejandro", 75));
                break;
            }

            case 20:{
                UserList.clear();
                UserList.add(new DatosUsuarios("Berjamin", 16));
                UserList.add(new DatosUsuarios("Aldo", 26));
                UserList.add(new DatosUsuarios("Dilan", 36));
                UserList.add(new DatosUsuarios("Elvin", 46));
                UserList.add(new DatosUsuarios("Enrique", 56));
                UserList.add(new DatosUsuarios("Blanca", 66));
                UserList.add(new DatosUsuarios("Mario", 76));
                break;
            }

            case 21:{
                UserList.clear();
                UserList.add(new DatosUsuarios("Edgar", 17));
                UserList.add(new DatosUsuarios("Carlos", 27));
                UserList.add(new DatosUsuarios("Wendy", 37));
                UserList.add(new DatosUsuarios("Mayte", 47));
                UserList.add(new DatosUsuarios("Valeria", 57));
                UserList.add(new DatosUsuarios("Daniel", 67));
                UserList.add(new DatosUsuarios("Mauricio", 77));
                break;
            }

            case 22:{
                UserList.clear();
                UserList.add(new DatosUsuarios("Indelfonso", 10));
                UserList.add(new DatosUsuarios("Gabriel", 20));
                UserList.add(new DatosUsuarios("Sara", 30));
                UserList.add(new DatosUsuarios("Samuel", 40));
                UserList.add(new DatosUsuarios("Luis", 50));
                UserList.add(new DatosUsuarios("Gerardo", 60));
                UserList.add(new DatosUsuarios("Rene", 70));
                break;
            }

            case 23:{
                UserList.clear();
                UserList.add(new DatosUsuarios("Lopez", 11));
                UserList.add(new DatosUsuarios("Raya", 21));
                UserList.add(new DatosUsuarios("Ramirez", 31));
                UserList.add(new DatosUsuarios("Ortiz", 41));
                UserList.add(new DatosUsuarios("Gonzales", 51));
                UserList.add(new DatosUsuarios("Perez", 61));
                UserList.add(new DatosUsuarios("Casas", 71));
                break;
            }

            case 0:{
                UserList.clear();
                UserList.add(new DatosUsuarios("Castro", 12));
                UserList.add(new DatosUsuarios("Marquez", 22));
                UserList.add(new DatosUsuarios("Torres", 32));
                UserList.add(new DatosUsuarios("Herrera", 42));
                UserList.add(new DatosUsuarios("Miranda", 52));
                UserList.add(new DatosUsuarios("Pulido", 62));
                UserList.add(new DatosUsuarios("Tezoco", 72));
                break;
            }
        }

        return UserList;
    }

}