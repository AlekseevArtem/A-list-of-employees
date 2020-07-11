package ru.job4j.a_list_of_employees.Store;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import ru.job4j.a_list_of_employees.Employee;
import ru.job4j.a_list_of_employees.Specialty;
import ru.job4j.a_list_of_employees.VirtualDatabase;

public class EmployeeBaseHelper extends SQLiteOpenHelper {
    public static final String DB = "employees.db";
    public static final int VERSION = 1;
    private static EmployeeBaseHelper INST;
    private List<Employee> employees = new ArrayList<>();

    private EmployeeBaseHelper(Context context) {
        super(context, DB, null, VERSION);
        Cursor cursor = this.getWritableDatabase().query(
                EmployeesDbSchema.employeeTable.NAME,
                null, null, null,
                null, null, null
        );
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            employees.add(new Employee(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex(EmployeesDbSchema.employeeTable.Cols.NAME)),
                    cursor.getString(cursor.getColumnIndex(EmployeesDbSchema.employeeTable.Cols.SURNAME)),
                    cursor.getString(cursor.getColumnIndex(EmployeesDbSchema.employeeTable.Cols.BIRTHDAY)),
                    cursor.getInt(cursor.getColumnIndex(EmployeesDbSchema.employeeTable.Cols.IMAGE)),
                    takeSpecialty(cursor.getInt(cursor.getColumnIndex(EmployeesDbSchema.employeeTable.Cols.SPECIALTY_ID)))

            ));
            cursor.moveToNext();
        }
        cursor.close();
    }

    private Specialty takeSpecialty(int specialtyID) {
        Cursor cursor = this.getWritableDatabase().query(
                EmployeesDbSchema.specialtyTable.NAME,
                null, "id = ?",
                new String[]{String.valueOf(specialtyID)},
                null, null, null
        );
        cursor.moveToFirst();
        Specialty specialty = new Specialty(
                cursor.getInt(cursor.getColumnIndex(EmployeesDbSchema.specialtyTable.Cols.ID)),
                cursor.getString(cursor.getColumnIndex(EmployeesDbSchema.specialtyTable.Cols.NAME))
        );
        cursor.close();
        return specialty;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public static EmployeeBaseHelper getInstance(Context context) {
        if (INST == null){
            INST = new EmployeeBaseHelper(context);
        }
        return INST;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + EmployeesDbSchema.specialtyTable.NAME + " ("+
                        EmployeesDbSchema.specialtyTable.Cols.ID + " integer, " +
                        EmployeesDbSchema.specialtyTable.Cols.NAME + " text)"
        );
        db.execSQL(
                "create table " + EmployeesDbSchema.employeeTable.NAME + " (" +
                        "id integer primary key autoincrement, " +
                        EmployeesDbSchema.employeeTable.Cols.NAME + " text, " +
                        EmployeesDbSchema.employeeTable.Cols.SURNAME + " text, " +
                        EmployeesDbSchema.employeeTable.Cols.BIRTHDAY + " text, " +
                        EmployeesDbSchema.employeeTable.Cols.IMAGE + " integer, " +
                        EmployeesDbSchema.employeeTable.Cols.SPECIALTY_ID + " integer)"
        );
        insertAllEmployees(new VirtualDatabase().getEmployees(), db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    }

    private String insertEmployee(String name, String surname, String birthday, int image, int specialty_id) {
        return "insert into employee (name, surname, birthday, image, specialty_id) values (" +
                "'" + name + "'," +
                "'" + surname + "'," +
                "'" + birthday + "'," +
                "'" + image + "'," +
                "'" + specialty_id + "')";
    }

    private String insertSpecialty(int id, String name) {
        return "insert into specialty (id, name) values (" +
                "'" + id + "'," +
                " '" + name + "')";
    }

    private void insertAllEmployees(List<Employee> employees, SQLiteDatabase db) {
        employees.stream()
                .map(Employee::getSpecialty)
                .distinct()
                .forEach(specialty -> db.execSQL(insertSpecialty(specialty.getId(), specialty.getName())));
        for (int index = 0; index < employees.size(); index++){
            Employee employee = employees.get(index);
            Specialty specialty = employee.getSpecialty();
            db.execSQL(insertEmployee(employee.getName(), employee.getSurname(), employee.getBirthday(),employee.getImage(),specialty.getId()));
        }
    }
}