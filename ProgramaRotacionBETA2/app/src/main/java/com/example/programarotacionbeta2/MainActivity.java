package com.example.programarotacionbeta2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
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

    String date, day, cambiorecycler;
    TextView hora, dia;
    ArrayList<DatosUsuarios> UserList = new ArrayList<>();
    int contador = 1, variable_1= 0,Variable_2=0;
    int numEntero = 0;
    RecyclerView RVusuarios;

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


        cambiarrecycler();
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


                handler.postDelayed(this, 100);
            }
        }, 100);

    }

    public void cambiarrecycler() {
        try{
            numEntero = Integer.parseInt(cambiorecycler);

            if (numEntero >= 30 && numEntero <= 59) {
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
            }else if(numEntero >= 0 && numEntero <= 29){
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
            }

        }catch (Exception e){

        }
    }

}