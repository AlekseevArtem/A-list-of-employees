package ru.job4j.a_list_of_employees;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.job4j.a_list_of_employees.Store.EmployeeBaseHelper;
import ru.job4j.a_list_of_employees.Store.EmployeesDbSchema;

public class SpecialtiesFragment extends Fragment {
    private RecyclerView recycler;
    private SQLiteDatabase store;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.specialties, container, false);
        store = new EmployeeBaseHelper(getContext()).getWritableDatabase();
        this.recycler = view.findViewById(R.id.employees);
        this.recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        updateUI();
        return view;
    }

    private void updateUI() {
        List<Specialty> specialty = new ArrayList<>();
        Cursor cursor = this.store.query(
                EmployeesDbSchema.specialtyTable.NAME,
                null, null, null,
                null, null, null
        );
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            specialty.add(new Specialty(
                    cursor.getInt(cursor.getColumnIndex(EmployeesDbSchema.specialtyTable.Cols.ID)),
                    cursor.getString(cursor.getColumnIndex(EmployeesDbSchema.specialtyTable.Cols.NAME))
            ));
            cursor.moveToNext();
        }
        cursor.close();
        this.recycler.setAdapter(new SpecialtiesAdapter(specialty));
    }

    public static class Holder extends RecyclerView.ViewHolder {
        private View view;
        private final CallbackEmployees callback;

        public Holder(@NonNull View view, CallbackEmployees callback) {
            super(view);
            this.view = itemView;
            this.callback = callback;
        }

        public void bind(View view, Specialty specialty) {
            callback.createFragmentEmployees(view, specialty);
        }
    }

    public class SpecialtiesAdapter extends RecyclerView.Adapter<Holder> {
        private final List<Specialty> specialty;
        public SpecialtiesAdapter(List<Specialty> specialty) {
            this.specialty = specialty;
        }
        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.info_specialties, parent, false);
            return new Holder(view, (CallbackEmployees) getActivity());
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int i) {
            final Specialty specialty = this.specialty.get(i);
            TextView specialtyName = holder.view.findViewById(R.id.SpecialtyName);
            specialtyName.setText(specialty.getName());
            TextView specialtyId = holder.view.findViewById(R.id.SpecialtyId);
            specialtyId.setText(String.valueOf(specialty.getId()));
            specialtyName.setOnClickListener(view -> {
                        openToast(view, specialty);
                        holder.bind(view, specialty);
            });
        }

        private void openToast(View view, Specialty specialty) {
            Toast.makeText(
                    getContext(), "You select " + specialty,
                    Toast.LENGTH_SHORT
            ).show();
        }

        @Override
        public int getItemCount() {
            return this.specialty.size();
        }
    }
}
