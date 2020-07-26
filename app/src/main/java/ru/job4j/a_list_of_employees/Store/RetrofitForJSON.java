package ru.job4j.a_list_of_employees.Store;

import android.content.Context;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.job4j.a_list_of_employees.BuildConfig;
import ru.job4j.a_list_of_employees.Employee;

public class RetrofitForJSON {
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private Context callback;

    public interface getAllEmployees {
        void successGetAllEmployees(boolean response, int code);
        void failedGetAllEmployees(String code);
    }

    public RetrofitForJSON(Context callback) {
        this.callback = callback;
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if(BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        }
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://gist.githubusercontent.com/AlekseevArtem/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
    }

    public void getAllEmployees() {
        Call<List<Employee>> call = jsonPlaceHolderApi.getEmployees();
        call.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(@NotNull Call<List<Employee>> call, @NotNull Response<List<Employee>> response) {
                EmployeeBaseHelper.getInstance(callback).insertAllEmployees(response.body());
                ((getAllEmployees)callback)
                        .successGetAllEmployees(response.isSuccessful(), response.code());
            }

            @Override
            public void onFailure(@NotNull Call<List<Employee>> call, @NotNull Throwable t) {
                ((getAllEmployees)callback)
                        .failedGetAllEmployees(t.getMessage());
            }
        });
    }


}
