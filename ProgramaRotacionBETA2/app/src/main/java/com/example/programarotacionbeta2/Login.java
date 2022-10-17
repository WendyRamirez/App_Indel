package com.example.programarotacionbeta2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.programarotacionbeta2.SplashScreen.SplashScreenActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.protobuf.Empty;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Login extends AppCompatActivity {

    private static final String TAG = "Login";
    private String mCustomToken;
    private FirebaseAuth mAuth;
    Button Login;
    EditText correo, contraseña;
    SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        Login = findViewById(R.id.btnLogin);
        correo = findViewById(R.id.edtUsuario);
        contraseña = findViewById(R.id.edtPassword);

        //dialogo de carga tipo Sweet Alert
        pDialog = new SweetAlertDialog(Login.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.setTitleText("Cargando ...");
        pDialog.setCancelable(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog.show();

                //verifico que los campos no esten vacions
                if (!contraseña.getText().toString().isEmpty() && !contraseña.getText().toString().isEmpty()) {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(correo.getText().toString(),
                            contraseña.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                //si la autenticacion es correcta se muestra la alerta y se envia al usuario al
                                //otro activity
                                pDialog.dismiss();
                                SweetAlertDialog correcto = new SweetAlertDialog(Login.this, SweetAlertDialog.SUCCESS_TYPE);
                                correcto.setTitleText("CORRECTO!");
                                correcto.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {
                                        Intent i = new Intent(Login.this, MainActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                });
                                correcto.setContentText("SU SESION SE A INICIADO CORRECTAMENTE!!");
                                correcto.setConfirmText("CONTINUAR");
                                correcto.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        Intent i = new Intent(Login.this, MainActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                });
                                correcto.show();


                            } else {

                                //en caso de que algo falle se muestra una alerta de error
                                pDialog.dismiss();
                                new SweetAlertDialog(Login.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("ERROR!")
                                        .setContentText("No hemos podido autenticar al usuario....")
                                        .show();
                            }
                        }
                    });
                } else {

                    //se le indica con una alerta al usuario que no a rellenado los campos
                    pDialog.dismiss();
                    new SweetAlertDialog(Login.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("ERROR!")
                            .setContentText("Rellene todos los campos")
                            .show();
                }
            }
        });


    }
}