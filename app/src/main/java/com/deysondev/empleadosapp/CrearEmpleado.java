package com.deysondev.empleadosapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.deysondev.empleadosapp.databinding.ActivityCrearEmpleadoBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearEmpleado extends AppCompatActivity {

    Button enviarDatos;
    EditText nombre,apellido,edad,profesion,empresa,sueldo;
    String _nombre,_apellido,_edad,_profesion,_empresa,_sueldo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_empleado);


        EmployeeService guardado = new EmployeeService();

        nombre = findViewById(R.id.edtText_nombreEmpleado);
        apellido = findViewById(R.id.edtText_apellidoEmpleado);
        edad = findViewById(R.id.edtText_edadEmpleado);
        profesion = findViewById(R.id.edtText_ProfesionEmpleado);
        empresa = findViewById(R.id.edtText_empresaEmpleado);
        sueldo = findViewById(R.id.edtText_sueldoEmpleado);

        //boton de envio
        enviarDatos = findViewById(R.id.btnGuardar);

        enviarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _sueldo = sueldo.getText().toString();
                _nombre = nombre.getText().toString();
                _apellido = apellido.getText().toString();
                _edad = edad.getText().toString();
                _profesion = profesion.getText().toString();
                _empresa = empresa.getText().toString();


                Employee guardarEmpleado = new Employee();

                guardarEmpleado.setApellido(_apellido);
                guardarEmpleado.setEdad(Integer.parseInt(_edad));
                guardarEmpleado.setNombre(_nombre);
                guardarEmpleado.setSueldo(_sueldo);
                guardarEmpleado.setProfesion(_profesion);
                guardarEmpleado.setEmpresa(_empresa);

                Call<Void> call = guardado.createEmployee(guardarEmpleado);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(CrearEmpleado.this,"Se ha guardado el registro: \n"+_nombre +" "+ _apellido,Toast.LENGTH_LONG).show();
                            Intent i = new Intent(CrearEmpleado.this,EmployeeListActivity.class);
                            startActivity(i);
                            finish();
                        }else{
                            Toast.makeText(CrearEmpleado.this, "No se pudo crear el registro", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
    }

}