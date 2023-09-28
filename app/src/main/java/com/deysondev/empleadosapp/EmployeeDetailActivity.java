package com.deysondev.empleadosapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeDetailActivity extends AppCompatActivity {

    private TextView txtNombre,txtApellido,txtEdad,txtSueldo,txtProfesion,txtEmpresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_detail);

        Button eliminarEmpleado = findViewById(R.id.Eliminar);
        Button editarEmpleado = findViewById(R.id.Editar);

        EmployeeService employeeService = new EmployeeService();

        // Obtén el objeto Employee enviado desde la actividad de lista.
        Employee employee = (Employee) getIntent().getSerializableExtra("employee");
        eliminarEmpleado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = employee.getId();
                dialogoEliminar(id);
            }
        });
        txtApellido = findViewById(R.id.apellidoEmpleado);
        txtNombre = findViewById(R.id.nombreEmpleado);
        txtEdad = findViewById(R.id.edadEmpleado);
        txtSueldo = findViewById(R.id.sueldoEmpleado);
        txtEmpresa = findViewById(R.id.empresaEmpleado);
        txtProfesion = findViewById(R.id.profesionEmpleado);


        // Muestra los detalles del empleado en la vista.
        txtProfesion.setText("Profesión: "+employee.getProfesion());
        txtApellido.setText("Apellido: "+employee.getApellido());
        txtEmpresa.setText("Empresa: "+employee.getEmpresa());
        txtNombre.setText("Nombre: "+employee.getNombre());
        txtSueldo.setText("Sueldo: "+employee.getSueldo());
        txtEdad.setText(String.valueOf("Edad: "+employee.getEdad()));


        editarEmpleado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeeDetailActivity.this,EditarEmpleado.class);
                intent.putExtra("employee",employee);
                startActivity(intent);
            }
        });
    }
    private void eliminar(int id){
        EmployeeService eliminacion = new EmployeeService();
        Call<Void> call = eliminacion.deleteEmployee(id);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toasty.warning(EmployeeDetailActivity.this, "Se ha eliminado un registro.", Toast.LENGTH_SHORT, true).show();
                    Toast.makeText(EmployeeDetailActivity.this,"El empleado ha sido eliminado",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(EmployeeDetailActivity.this,EmployeeListActivity.class);
                    startActivity(intent);
                }else {
                    int statusCode = response.code();
                    Toasty.error(EmployeeDetailActivity.this, "No se pudo realizar la acción.", Toast.LENGTH_SHORT, true).show();
                    Toast.makeText(EmployeeDetailActivity.this,"No fue posible eliminar el registro",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(EmployeeDetailActivity.this,"El empleado ha sido eliminado",Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }
    private void dialogoEliminar(int id){
        AlertDialog dialog = new AlertDialog.Builder(EmployeeDetailActivity.this)
                .setPositiveButton("Sí, eliminar registro", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eliminar(id);
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setTitle("Confirmar eliminación")
                .setMessage("¿En realidad quieres eliminar el registro?")
                .create();
        dialog.show();
    }
}

