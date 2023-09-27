package com.deysondev.empleadosapp;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.deysondev.empleadosapp.databinding.ActivityCrearEmpleadoBinding;

public class CrearEmpleado extends AppCompatActivity {

    Button enviarDatos;
    EditText nombre,apellido,edad,profesion,empresa,sueldo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_empleado);


    }

}