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

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearEmpleado extends AppCompatActivity {

    Button enviarDatos;
    EditText nombre, apellido, edad, profesion, empresa, sueldo;
    String _nombre, _apellido, _edad, _profesion, _empresa, _sueldo;

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

                //Validar los campos de entrada de texto
                boolean hayError = false;

                if (nombre.getText().toString().isEmpty()) {
                    hayError = true;
                    Toasty.error(CrearEmpleado.this, "El campo nombre está vacío", Toast.LENGTH_SHORT,true).show();
                }

                if (apellido.getText().toString().isEmpty()) {
                    hayError = true;
                    Toasty.error(CrearEmpleado.this, "El campo apellido está vacío", Toast.LENGTH_SHORT).show();
                }

                if (edad.getText().toString().isEmpty()) {
                    hayError = true;
                    Toasty.error(CrearEmpleado.this, "El campo edad está vacío", Toast.LENGTH_SHORT).show();
                }

                if (profesion.getText().toString().isEmpty()) {
                    hayError = true;
                    Toasty.error(CrearEmpleado.this, "El campo profesión está vacío", Toast.LENGTH_SHORT).show();
                }

                if (empresa.getText().toString().isEmpty()) {
                    hayError = true;
                    Toasty.error(CrearEmpleado.this, "El campo empresa está vacío", Toast.LENGTH_SHORT,true).show();
                }

                if (sueldo.getText().toString().isEmpty()) {
                    hayError = true;
                    Toasty.error(CrearEmpleado.this, "El campo sueldo está vacío", Toast.LENGTH_SHORT,true).show();
                }

                // Si hay un error, no se envía el formulario
                if (hayError) {
                    return;
                }
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
                        if (response.isSuccessful()) {
                            Toasty.success(CrearEmpleado.this, "Datos guardatos correctamente!", Toast.LENGTH_SHORT, true).show();
                            Toast.makeText(CrearEmpleado.this, "Se ha guardado el registro: \n" + _nombre + " " + _apellido, Toast.LENGTH_LONG).show();
                            Intent i = new Intent(CrearEmpleado.this, EmployeeListActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            Toasty.error(CrearEmpleado.this, "No se pudo realizar esta acción.", Toast.LENGTH_LONG, true).show();
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