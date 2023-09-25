package com.deysondev.empleadosapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EmployeeDetailActivity extends AppCompatActivity {

    private TextView textViewName;
    private TextView textViewPosition;
    private TextView textViewOtherDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_detail);

        textViewName = findViewById(R.id.text_view_employee_name);
        textViewPosition = findViewById(R.id.text_view_employee_position);
        textViewOtherDetails = findViewById(R.id.text_view_employee_other_details);

        // Obtén el objeto Employee enviado desde la actividad de lista.
        Employee employee = (Employee) getIntent().getSerializableExtra("employee");

        // Muestra los detalles del empleado en la vista.
        textViewName.setText(employee.getName());
        textViewPosition.setText(employee.getProfession());
        textViewOtherDetails.setText(employee.getCompany());
        // Añade más campos según los datos de tu modelo de Employee.
    }
}

