package ru.job4j.a_list_of_employees;

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

import java.util.List;
import java.util.stream.Collectors;

public class SpecialtiesFragment extends Fragment {
    private RecyclerView recycler;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.specialties, container, false);
        this.recycler = view.findViewById(R.id.employees);
        this.recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        updateUI();
        return view;
    }

    private void updateUI() {
        List<Employee> employees = VirtualDatabase.getInstance().getEmployees();
        List<Specialty> specialty = employees.stream()
                .map(Employee::getSpecialty)
                .distinct()
                .collect(Collectors.toList());
        this.recycler.setAdapter(new ExamAdapter(specialty));
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

    public class ExamAdapter extends RecyclerView.Adapter<Holder> {
        private final List<Specialty> specialty;
        public ExamAdapter(List<Specialty> specialty) {
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
