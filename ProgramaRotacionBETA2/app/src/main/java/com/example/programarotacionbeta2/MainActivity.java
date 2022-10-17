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

        //Aqui se obtienen la hora para el relog
        date = new SimpleDateFormat("HH:mm").format(new Date());

        // obtengo los minutos actuales para que cuando pasen del minuto 30 cambiar la lista
        cambiorecycler = new SimpleDateFormat("mm").format(new Date());

        //aqui obtengo la fecha
        day = new SimpleDateFormat("d 'de' MMMM").format(new Date());

        dia.setText(day);
        hora.setText(date);
        ejecutar();

        //Aqui solo es para hacer transparente el status bar
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

                //vuelvo a obtener los datos anterirmente mensionados porque cambian costantemente
                // y necesito que esten actualizados en todo momento para que funcione en tiempo real
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
            //evento donde se cambiara la lista

            //decharo este archivo para poder almacenar de forma local el valor de la hora y no perderlo
            // y poder saber que lista debo mostrar en cada momento
            preferences = getSharedPreferences("horario", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            //obtengo la hora en formato de 24 horas para poder saber que lista toca en esa hora
            hora_actual = new SimpleDateFormat("H").format(new Date());

            //transformo los datos String de las horas y minutos a INT
            hora_actual_num = Integer.parseInt(hora_actual);
            Minutos = Integer.parseInt(cambiorecycler);


            //verifico que si el valor de la hora actual es igual a 0
            if (hora_actual_num == 0) {
                //si lo es eso significa que el dia se acabo por lo tanto la hora que guardo en el
                //archivo cambiara a 0 de igual manera
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

            //verifico que si la hora guardada es menor que la hora actual, esto por si el usuario
            // no abre la app en varias horas y asi poder obtener la lista correspondiente a esa hora
            if (hora_guardada_num < hora_actual_num) {
                hora_guardada_num = hora_actual_num;
            }


            //pregunto si la hora actual es la misma que la hora guardada
            if (hora_guardada_num == hora_actual_num) {

                //pregunto si los minutos actuales son igual o mayor que 30
                if (Minutos >= 30) {
                    variable_1 = 1;

                    //le sumo uno a la hora guardada y la registro, para que de esta manera no vuelva
                    //a entrar a esta seccion asta que la hora actual cambie
                    hora_guardar = hora_guardada_num++;
                    editor.putInt("hora", hora_guardar);
                    editor.commit();

                    //paso la lista del evento listausuarios a un adaptador para que rellene los datos
                    //del diseño con los datos de la lista y los muestre en el recyclerview
                    AdapterUsers adapter = new AdapterUsers(listausuarios(hora_actual_num), MainActivity.this);
                    RVusuarios.setHasFixedSize(true);
                    RVusuarios.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    RVusuarios.setAdapter(adapter);

                } else if (variable_1 == 1) {

                    //si los minutos son menores a 30 eso significa que debo de mandarle la lista
                    // de la hora anterior asi que le resto uno a la hora actual y se la paso al
                    // evento de listausuario
                    AdapterUsers adapter = new AdapterUsers(listausuarios(hora_actual_num - 1), MainActivity.this);
                    RVusuarios.setHasFixedSize(true);
                    RVusuarios.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    RVusuarios.setAdapter(adapter);

                    variable_1 = 0;
                }
            } else if (variable_1 == 1) {

                //si la hora actual y la hora guardada no son iguales eso significa los minutos actuales
                //superan los 30 por ende se muestra la lista de la hora actual
                AdapterUsers adapter = new AdapterUsers(listausuarios(hora_actual_num), MainActivity.this);
                RVusuarios.setHasFixedSize(true);
                RVusuarios.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                RVusuarios.setAdapter(adapter);

                //iguala  0 la variable de verificacion para que solo entre una veces, y no este constantemente
                //refrescando la lista cuando la hora no a cambiado
                variable_1 = 0;
            }


        } catch (Exception e) {

        }
    }

    public ArrayList<DatosUsuarios> listausuarios(int hora) {

        //en base a la hora enviada se generar la lista
        switch (hora) {

            //lo indicado en el case 13 aplica para todos los demas
            case 13: {

                //vacia la lista
                UserList.clear();

                //aqui se añaden los daros a la lista
                UserList.add(new DatosUsuarios("Edgar", 1));
                UserList.add(new DatosUsuarios("Carlos", 2));
                UserList.add(new DatosUsuarios("Wendy", 3));
                UserList.add(new DatosUsuarios("Mayte", 4));
                UserList.add(new DatosUsuarios("Valeria", 5));
                UserList.add(new DatosUsuarios("Daniel", 6));
                UserList.add(new DatosUsuarios("Mauricio", 7));
                break;
            }

            case 14: {
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

            case 15: {
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

            case 16: {
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

            case 17: {
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

            case 18: {
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

            case 19: {
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

            case 20: {
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

            case 21: {
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

            case 22: {
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

            case 23: {
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

            case 0: {
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
        //regresa la lista a donde fue llamado el evento
        return UserList;
    }

}