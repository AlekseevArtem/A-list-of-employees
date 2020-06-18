package ru.job4j.a_list_of_employees;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements CallbackEmployees,CallbackEmployee {

    @Override
    protected void onCreate(@Nullable Bundle saved) {
        super.onCreate(saved);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void createFragmentEmployees(View view, Specialty specialty) {
        EmployeesFragment FragEmployees = (EmployeesFragment) getSupportFragmentManager()
                .findFragmentByTag("EmployeesFragment");
        EmployeeFragment FragEmployee = (EmployeeFragment) getSupportFragmentManager()
                .findFragmentByTag("EmployeeFragment");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(FragEmployees == null) {
            transaction
                    .add(R.id.content, EmployeesFragment.of(specialty.getId()), "EmployeesFragment")
                    .commit();
        } else {
            if (FragEmployee != null) { transaction = transaction.remove(FragEmployee); }
            transaction
                    .remove(FragEmployees)
                    .add(R.id.content, EmployeesFragment.of(specialty.getId()), "EmployeesFragment")
                    .commit();
        }
    }

    @Override
    public void createFragmentEmployee(View view, Employee employee) {
        EmployeeFragment FragEmployee = (EmployeeFragment) getSupportFragmentManager()
                .findFragmentByTag("EmployeeFragment");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (FragEmployee == null) {
            transaction
                    .add(R.id.content, EmployeeFragment.of(employee.hashCode()), "EmployeeFragment")
                    .commit();
        } else {
            transaction
                    .remove(FragEmployee)
                    .add(R.id.content, EmployeeFragment.of(employee.hashCode()), "EmployeeFragment")
                    .commit();
        }
    }
}