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

public class MainActivity extends AppCompatActivity {

    String date, day;
    TextView hora, dia;
    ArrayList<DatosUsuarios> UserList = new ArrayList<>();
    RecyclerView RVusuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hora = findViewById(R.id.tClock);
        dia = findViewById(R.id.DayClock);
        RVusuarios = findViewById(R.id.RVusuarios);

        date = new SimpleDateFormat("HH:mm").format(new Date());

        day = new SimpleDateFormat("d 'de' MMMM").format(new Date());

        dia.setText(day);
        hora.setText(date);
        ejecutar();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }


        UserList.add(new DatosUsuarios("Edgar", 1));
        UserList.add(new DatosUsuarios("Carlos", 2));
        UserList.add(new DatosUsuarios("Wendy", 3));
        UserList.add(new DatosUsuarios("Mayte", 4));
        UserList.add(new DatosUsuarios("Valeria", 5));
        UserList.add(new DatosUsuarios("Daniel", 6));
        UserList.add(new DatosUsuarios("Mauricio", 7));


        AdapterUsers adapter = new AdapterUsers(UserList, this);
        RVusuarios.setHasFixedSize(true);
        RVusuarios.setLayoutManager(new LinearLayoutManager(this));
        RVusuarios.setAdapter(adapter);


    }

    private void ejecutar() {
        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                date = new SimpleDateFormat("HH:mm").format(new Date());
                day = new SimpleDateFormat("d 'de' MMMM").format(new Date());

                dia.setText(day);
                hora.setText(date);

                handler.postDelayed(this, 100);
            }
        }, 100);

    }

}