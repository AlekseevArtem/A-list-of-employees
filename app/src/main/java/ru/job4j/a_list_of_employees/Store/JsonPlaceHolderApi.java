package ru.job4j.a_list_of_employees.Store;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import ru.job4j.a_list_of_employees.Employee;

public interface JsonPlaceHolderApi {
    @GET("46b24e9c55d70ed9b42b83a3521b96b4/raw/f853ac489a430221fcac21b6f0b6b93dc8edcfc4/employees.json")
    Call<List<Employee>> getEmployees();

}