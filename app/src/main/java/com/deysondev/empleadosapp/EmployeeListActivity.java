package com.deysondev.empleadosapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.List;

public class EmployeeListActivity extends AppCompatActivity {

    private EmployeeApi employeeApi;
    private RecyclerView recyclerView;
    private EmployeeListAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_list_item);

        recyclerView = findViewById(R.id.recycler_view_employee_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Obtén la lista de empleados desde la API (puede variar según tu implementación).
        List<Employee> employeeList = null;
        try {
            employeeList = getEmployeeListFromApi();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        adapter = new EmployeeListAdapter(employeeList, new EmployeeListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Employee employee) {
                // Manejar el clic en un empleado (navegar a la vista de detalle).
                Intent intent = new Intent(EmployeeListActivity.this, EmployeeDetailActivity.class);
                intent.putExtra("employee", String.valueOf(employee)); // Envía el objeto Employee a la vista de detalle.
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);
    }

    // Implementa este método para obtener la lista de empleados desde la API.
    private List<Employee> getEmployeeListFromApi() throws IOException {
        // Lógica para obtener la lista de empleados desde la API.
        return employeeApi.getAllEmployees();
    }
}

