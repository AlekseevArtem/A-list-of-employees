package ru.job4j.a_list_of_employees;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import ru.job4j.a_list_of_employees.Store.EmployeeBaseHelper;
import ru.job4j.a_list_of_employees.Store.EmployeesDbSchema;

public class EmployeeFragment extends Fragment {
    private SQLiteDatabase store;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.employee, container, false);
        store = new EmployeeBaseHelper(getContext()).getWritableDatabase();
        int employeeID = Objects.requireNonNull(getArguments()).getInt("employeeID", 0);
        Employee employee;
        int specialtyID;
        Cursor cursor = this.store.query(
                EmployeesDbSchema.employeeTable.NAME,
                null, "id = ?",
                new String[]{String.valueOf(employeeID)},
                null, null, null
        );
        cursor.moveToFirst();
        employee = new Employee(
                cursor.getInt(cursor.getColumnIndex("id")),
                cursor.getString(cursor.getColumnIndex(EmployeesDbSchema.employeeTable.Cols.NAME)),
                cursor.getString(cursor.getColumnIndex(EmployeesDbSchema.employeeTable.Cols.SURNAME)),
                cursor.getString(cursor.getColumnIndex(EmployeesDbSchema.employeeTable.Cols.BIRTHDAY)),
                cursor.getInt(cursor.getColumnIndex(EmployeesDbSchema.employeeTable.Cols.IMAGE))

        );
        specialtyID = cursor.getInt(cursor.getColumnIndex(EmployeesDbSchema.employeeTable.Cols.SPECIALTY_ID));
        cursor.close();
        ImageView image = view.findViewById(R.id.imageView);
        image.setImageResource(Objects.requireNonNull(employee).getImage());
        TextView name = view.findViewById(R.id.emploName);
        name.setText(employee.getName());
        TextView surname = view.findViewById(R.id.emploSurname);
        surname.setText(employee.getSurname());
        TextView birthday = view.findViewById(R.id.emploBirthday);
        birthday.setText(employee.getBirthday());
        TextView specialty = view.findViewById(R.id.emploSpecialty);
        cursor = this.store.query(
                EmployeesDbSchema.specialtyTable.NAME,
                null, "id = ?",
                new String[]{String.valueOf(specialtyID)},
                null, null, null
        );
        cursor.moveToFirst();
        specialty.setText(cursor.getString(cursor.getColumnIndex(EmployeesDbSchema.employeeTable.Cols.NAME)));
        cursor.close();
        return view;
    }

    static EmployeeFragment of(int id) {
        EmployeeFragment employee = new EmployeeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("employeeID", id);
        employee.setArguments(bundle);
        return employee;
    }
}
