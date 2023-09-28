package com.deysondev.empleadosapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarEmpleado extends AppCompatActivity {
    Button enviarDatos;
    EditText nombre, apellido, edad, profesion, empresa, sueldo;
    String _nombre, _apellido, _edad, _profesion, _empresa, _sueldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_empleado);

        Context context = getApplicationContext();
        Employee employee = (Employee) getIntent().getSerializableExtra("employee");

        enviarDatos = findViewById(R.id.btnGuardarEdicion);

        EmployeeService editarEmpleado = new EmployeeService();

        int idEmpleado = employee.getId();

        //Setearle los datos a cada campo del formulario de acuerdo con el recibido
        nombre = findViewById(R.id.editar_nombreEmpleado);
        nombre.setText(employee.getNombre().toString());
        apellido = findViewById(R.id.editar_apellidoEmpleado);
        apellido.setText(employee.getApellido().toString());
        edad = findViewById(R.id.editar_edadEmpleado);
        edad.setText(String.valueOf(employee.getEdad()));
        profesion = findViewById(R.id.editar_ProfesionEmpleado);
        profesion.setText(employee.getProfesion().toString());
        empresa = findViewById(R.id.editar_empresaEmpleado);
        empresa.setText(employee.getEmpresa().toString());
        sueldo = findViewById(R.id.editar_sueldoEmpleado);
        sueldo.setText(employee.getSueldo().toString());

        enviarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Validar los campos de entrada de texto
                boolean hayError = false;

                if (nombre.getText().toString().isEmpty()) {
                    hayError = true;
                    Toasty.error(context, "El campo nombre está vacío", Toast.LENGTH_SHORT,true).show();
                }

                if (apellido.getText().toString().isEmpty()) {
                    hayError = true;
                    Toasty.error(context, "El campo apellido está vacío", Toast.LENGTH_SHORT).show();
                }

                if (edad.getText().toString().isEmpty()) {
                    hayError = true;
                    Toasty.error(context, "El campo edad está vacío", Toast.LENGTH_SHORT).show();
                }

                if (profesion.getText().toString().isEmpty()) {
                    hayError = true;
                    Toasty.error(context, "El campo profesión está vacío", Toast.LENGTH_SHORT).show();
                }

                if (empresa.getText().toString().isEmpty()) {
                    hayError = true;
                    Toasty.error(context, "El campo empresa está vacío", Toast.LENGTH_SHORT,true).show();
                }

                if (sueldo.getText().toString().isEmpty()) {
                    hayError = true;
                    Toasty.error(context, "El campo sueldo está vacío", Toast.LENGTH_SHORT,true).show();
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

                EmployeeDTO guardarEmpleado = new EmployeeDTO();

                guardarEmpleado.setApellido(_apellido);
                guardarEmpleado.setEdad(Integer.parseInt(_edad));
                guardarEmpleado.setNombre(_nombre);
                guardarEmpleado.setSueldo(_sueldo);
                guardarEmpleado.setProfesion(_profesion);
                guardarEmpleado.setEmpresa(_empresa);

                Call<Void> call = editarEmpleado.updateEmployee(idEmpleado,guardarEmpleado);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toasty.success(context, "Datos guardatos correctamente!", Toasty.LENGTH_LONG, true).show();
                            //Toast.makeText(context, "Se ha editado el registro: \n" + _nombre + " " + _apellido, Toast.LENGTH_LONG).show();
                            Intent i = new Intent(context, EmployeeListActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            Toasty.error(context, "No se pudo realizar esta acción."+response.message(), Toasty.LENGTH_LONG, true).show();
                            //Toast.makeText(context, "No se pudo editar el registro", Toast.LENGTH_LONG).show();
                            System.out.print("Mensaje de salida:"+response);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toasty.error(context, "No se pudo editar el registro", Toasty.LENGTH_LONG,true).show();
                        t.printStackTrace();
                    }
                });
            }
        });


    }

}