package com.deysondev.empleadosapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeListActivity extends AppCompatActivity {

    private EmployeeListAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);

        Button crearNuevos = findViewById(R.id.Crear);
        crearNuevos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EmployeeListActivity.this,CrearEmpleado.class);
                startActivity(i);
                //finish();
            }
        });


        RecyclerView recyclerView = findViewById(R.id.recycler_view_employee_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        EmployeeService employeeService = new EmployeeService();

        Call<List<Employee>> call = employeeService.getAllEmployees();

        call.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(@NonNull Call<List<Employee>> call, Response<List<Employee>> response) {
                if(response.isSuccessful()){
                    List<Employee> employeeList = response.body();
                    adapter = new EmployeeListAdapter(employeeList, new EmployeeListAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Employee employee) {
                            Intent intent = new Intent(EmployeeListActivity.this,EmployeeDetailActivity.class);
                            intent.putExtra("employee",employee);
                            startActivity(intent);
                        }
                    });
                    recyclerView.setAdapter(adapter);
                }else {
                    Toast.makeText(EmployeeListActivity.this,"Error en la respuesta de la API",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Employee>> call, Throwable t) {
                Toast.makeText(EmployeeListActivity.this, "Error en la solicitud HTTP", Toast.LENGTH_SHORT).show();

            }
        });

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



    }


}

