package com.deysondev.empleadosapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class EmployeeService {
    private static final String API_URL = "https://apiempleados20.bsite.net/api/Empleados/";
    private EmployeeApiService apiService;

    public EmployeeService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(EmployeeApiService.class);
    }
    public Call<List<Employee>> getAllEmployees() {
        return apiService.getAllEmployees();
    }

    public Call<Employee> getEmployeeById(int employeeId) {
        return apiService.getEmployeeById(employeeId);
    }

    public Call<Void> createEmployee(Employee employee) {
        return apiService.createEmployee(employee);
    }

    public Call<Void> updateEmployee(int employeeId, Employee updatedEmployee) {
        return apiService.updateEmployee(employeeId, updatedEmployee);
    }

    public Call<Void> deleteEmployee(int employeeId) {
        return apiService.deleteEmployee(employeeId);
    }
}
