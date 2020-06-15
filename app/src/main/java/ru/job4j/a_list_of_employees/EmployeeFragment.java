package ru.job4j.a_list_of_employees;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;
import java.util.Objects;

public class EmployeeFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.employee, container, false);
        List<Employee> employees = VirtualDatabase.getInstance().getEmployees();
        Employee employee = employees.stream()
                .filter(empl -> empl.hashCode() == Objects.requireNonNull(getArguments()).getInt("employeeHashCode", 0))
                .findFirst().orElse(null);
        ImageView image = view.findViewById(R.id.imageView);
        image.setImageResource(Objects.requireNonNull(employee).getImage());
        TextView name = view.findViewById(R.id.emploName);
        name.setText(employee.getName());
        TextView surname = view.findViewById(R.id.emploSurname);
        surname.setText(employee.getSurname());
        TextView birthday = view.findViewById(R.id.emploBirthday);
        birthday.setText(employee.getBirthday());
        TextView specialty = view.findViewById(R.id.emploSpecialty);
        specialty.setText(employee.getSpecialty().getName());
        return view;
    }

    static EmployeeFragment of(int id) {
        EmployeeFragment employee = new EmployeeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("employeeHashCode", id);
        employee.setArguments(bundle);
        return employee;
    }
}
