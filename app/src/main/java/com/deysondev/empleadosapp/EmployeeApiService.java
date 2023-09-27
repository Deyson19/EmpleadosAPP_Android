package com.deysondev.empleadosapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface EmployeeApiService {
    @GET("ListadoEmpleados")
    Call<List<Employee>> getAllEmployees();

    @GET("ObtenerEmpleado/{id}")
    Call<Employee> getEmployeeById(@Path("id") int employeeId);

    @POST("CrearEmpleado")
    Call<Void> createEmployee(@Body Employee employee);

    @PUT("ActualizarEmpleado/{id}")
    Call<Void> updateEmployee(@Path("id") int employeeId, @Body Employee updatedEmployee);

    @DELETE("EliminarEmpleado/{id}")
    Call<Void> deleteEmployee(@Path("id") int employeeId);
}

