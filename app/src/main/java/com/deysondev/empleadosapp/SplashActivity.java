package com.deysondev.empleadosapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private ImageView imageViewLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imageViewLogo = findViewById(R.id.image_view_logo);

        // Cargar la animación de rotación
        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        imageViewLogo.startAnimation(rotation);

        // Agregar un retraso antes de iniciar la siguiente actividad
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Aquí inicia la siguiente actividad (por ejemplo, EmployeeListActivity)
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4000); // 4000 milisegundos (3 segundos) de retraso
    }
}


