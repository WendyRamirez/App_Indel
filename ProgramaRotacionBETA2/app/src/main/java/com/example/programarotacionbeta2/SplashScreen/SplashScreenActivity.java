package com.example.programarotacionbeta2.SplashScreen;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.programarotacionbeta2.Login;

import androidx.appcompat.app.AppCompatActivity;

import com.example.programarotacionbeta2.MainActivity;
import com.example.programarotacionbeta2.R;

public class SplashScreenActivity extends AppCompatActivity {

    //Creamos una instacion de Animation
    private Animation animation;

    private ImageView ivLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ivLogo = findViewById(R.id.ivLogo);
        //Instanciamos un objeto
        animation = AnimationUtils.loadAnimation(this, R.anim.animation);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        //Establecemos la animacion al image view
        ivLogo.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //Este metodo es llamado cuando la animacion inicia
                //Aqui podriamos realizar algunas comprobaciones iniciales
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //Este metodo es llamado cuando la animacion inicia
                //Al culminar la animacion vamos a pasar al siguiente activity
                Intent i = new Intent(SplashScreenActivity.this, Login.class);
                startActivity(i);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                //Este metodo es llamado en cada repeticion
            }
        });
    }
}

