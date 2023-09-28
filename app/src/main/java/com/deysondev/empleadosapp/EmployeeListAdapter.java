package com.deysondev.empleadosapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.EmployeeViewHolder> {
    private List<Employee> employeeList;
    private OnItemClickListener listener;

    public EmployeeListAdapter(List<Employee> employeeList, OnItemClickListener listener) {
        this.employeeList = employeeList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.employee_list_item, parent, false);
        return new EmployeeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee employee = employeeList.get(position);
        holder.bind(employee, listener);
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    static class EmployeeViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNombre,txtApellido,txtEdad,txtSueldo,txtProfesion,txtEmpresa;


        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            txtApellido = itemView.findViewById(R.id.apellidoEmpleado);
            txtNombre = itemView.findViewById(R.id.nombreEmpleado);
            txtEdad = itemView.findViewById(R.id.edadEmpleado);
            txtSueldo = itemView.findViewById(R.id.sueldoEmpleado);
            txtEmpresa = itemView.findViewById(R.id.empresaEmpleado);
            txtProfesion = itemView.findViewById(R.id.profesionEmpleado);
        }

        public void bind(final Employee employee, final OnItemClickListener listener) {
            // Muestra los detalles del empleado en la vista.
            txtProfesion.setText("Profesión: "+employee.getProfesion());
            txtApellido.setText("Apellido: "+employee.getApellido());
            txtEmpresa.setText("Empresa: "+employee.getEmpresa());
            txtNombre.setText("Nombre: "+employee.getNombre());
            txtSueldo.setText("Sueldo: "+employee.getSueldo());
            txtEdad.setText(String.valueOf("Edad: "+employee.getEdad()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(employee);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Employee employee);
    }
}
