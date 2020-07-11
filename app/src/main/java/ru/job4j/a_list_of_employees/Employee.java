package ru.job4j.a_list_of_employees;

import java.util.Objects;

public class Employee {
    private int id;
    private String name;
    private String surname;
    private String birthday;
    private int image;
    private Specialty specialty;

    public Employee(int id, String name, String surname, String birthday, int image, Specialty specialty) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.image = image;
        this.specialty = specialty;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getBirthday() {
        return birthday;
    }

    public int getImage() {
        return image;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return birthday.equals(employee.birthday) &&
                Objects.equals(name, employee.name) &&
                Objects.equals(surname, employee.surname) &&
                Objects.equals(image, employee.image) &&
                Objects.equals(specialty, employee.specialty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, birthday, image, specialty);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthday='" + birthday + '\'' +
                '}';
    }
}
