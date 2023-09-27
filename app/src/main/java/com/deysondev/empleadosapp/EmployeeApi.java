package com.deysondev.empleadosapp;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import com.google.gson.Gson;
public class EmployeeApi {
    private static final String API_URL = "https://apiempleados20.bsite.net/api/Empleados";
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();
    public List<Employee> getAllEmployees() throws IOException {
        URL url = new URL(API_URL + "/ListadoEmpleados");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            return parseJsonResponse(response.toString());
        } else {
            throw new IOException("Error en la solicitud HTTP: " + connection.getResponseCode());
        }
    }

    public static Employee getEmployeeById(int employeeId) throws IOException {
        URL url = new URL(API_URL + "/ObtenerEmpleado/" + employeeId);
        // Realiza una solicitud GET y procesa la respuesta para obtener un solo empleado.
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Response response = client.newCall(request).execute();

        if (response.isSuccessful()){
            String responseBody = response.body().string();
            return  parseEmployeeFromJson(responseBody);
        }
        return null;
    }
    private static Employee parseEmployeeFromJson(String json){
        try{
            JSONObject jsonObject = new JSONObject(json);
            String nombre, apellido,empresa,profesion,sueldo;
            int edad, id;
            id = jsonObject.getInt("id");
            edad = jsonObject.getInt("edad");
            nombre = jsonObject.getString("nombre");
            apellido = jsonObject.getString("apellido");
            sueldo = jsonObject.getString("sueldo");
            profesion = jsonObject.getString("profesion");
            empresa = jsonObject.getString("empresa");

            return  new Employee(id,edad,nombre,apellido,profesion,sueldo,empresa);
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }
    public void createEmployee(Employee employee) throws IOException {
        // Configura una solicitud POST con los datos del nuevo empleado en el cuerpo de la solicitud y envíala.

        try {
            String json = gson.toJson(employee);
            String _url = API_URL + "/CrearEmpleado";
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),json);

            Request request = new Request.Builder()
                    .url(_url)
                    .post(requestBody)
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()){
                //mostral algun toast
            }else{
                //mostrar algun toast de error
                throw new IOException("Error al crear el empleado: " + response.code());

            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void updateEmployee(Employee updatedEmployee) throws IOException {
         // Configura una solicitud PUT con los datos actualizados del empleado en el cuerpo de la solicitud y envíala.

        try{
            String _json = gson.toJson(updatedEmployee);
            String _url = API_URL + "/ActualizarEmpleado/"+updatedEmployee.getId();

            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),_json);

            Request request = new Request.Builder()
                    .url(_url)
                    .put(requestBody)
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                // La solicitud se completó con éxito, puedes manejar la respuesta si es necesario
            } else {
                // Manejar errores de la solicitud HTTP, por ejemplo, lanzar una excepción
                throw new IOException("Error al actualizar el empleado: " + response.code());
            }
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
    public void deleteEmployee(int employeeId) throws IOException {
        try{

            String _url = API_URL + "/EliminarEmpleado/"+employeeId;
            Request request = new Request.Builder()
                    .url(_url)
                    .delete()
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                // La solicitud se completó con éxito, puedes manejar la respuesta si es necesario
            } else {
                // Manejar errores de la solicitud HTTP, por ejemplo, lanzar una excepción
                throw new IOException("Error al actualizar el empleado: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static List<Employee> parseJsonResponse(String jsonResponse) {
        List<Employee> employeeList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(jsonResponse);

            for (int i = 0; i<jsonArray.length();i++){
                JSONObject jsonObject = new JSONObject(jsonResponse);
                String nombre, apellido,empresa,profesion,sueldo;
                int edad, id;
                id = jsonObject.getInt("id");
                edad = jsonObject.getInt("edad");
                nombre = jsonObject.getString("nombre");
                apellido = jsonObject.getString("apellido");
                sueldo = jsonObject.getString("sueldo");
                profesion = jsonObject.getString("profesion");
                empresa = jsonObject.getString("empresa");

                Employee employee =  new Employee(id,edad,nombre,apellido,profesion,sueldo,empresa);
                employeeList.add(employee);
            }
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
        return employeeList;
    }
}

