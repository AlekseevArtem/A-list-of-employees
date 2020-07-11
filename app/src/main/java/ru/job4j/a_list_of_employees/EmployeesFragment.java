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
import java.util.Objects;
import java.util.stream.Collectors;

import ru.job4j.a_list_of_employees.Store.EmployeeBaseHelper;


public class EmployeesFragment extends Fragment {
    private RecyclerView recycler;
    private EmployeeBaseHelper store = EmployeeBaseHelper.getInstance(getContext());

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.employees, container, false);
        this.recycler = view.findViewById(R.id.employees);
        this.recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        updateUI();
        return view;
    }

    private void updateUI() {
        int specialtyID = Objects.requireNonNull(getArguments()).getInt("specialtyId", 0);
        List<Employee> employees = store.getEmployees().stream()
                .filter(employee -> employee.getSpecialty().getId() == specialtyID)
                .collect(Collectors.toList());
        this.recycler.setAdapter(new EmployeesFragment.ExamAdapter(employees));
    }

    static EmployeesFragment of(int id) {
        EmployeesFragment employees = new EmployeesFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("specialtyId", id);
        employees.setArguments(bundle);
        return employees;
    }

    public static class Holder extends RecyclerView.ViewHolder {
        private View view;
        private final CallbackEmployee callback;

        public Holder(@NonNull View view, CallbackEmployee callback) {
            super(view);
            this.view = itemView;
            this.callback = callback;
        }

        public void bind(View view, Employee employee) {
            callback.createFragmentEmployee(view, employee);
        }
    }

    public class ExamAdapter extends RecyclerView.Adapter<Holder> {
        private final List<Employee> employees;
        public ExamAdapter(List<Employee> specialty) {
            this.employees = specialty;
        }
        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.info_employees, parent, false);
            return new Holder(view, (CallbackEmployee) getActivity());
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int i) {
            final Employee employee = this.employees.get(i);
            TextView employeeName = holder.view.findViewById(R.id.EmployeeNameSurname);
            employeeName.setText(String.format("%s %s", employee.getName(), employee.getSurname()));
            employeeName.setOnClickListener(view -> {
                openToast(view, employee);
                holder.bind(view, employee);
            });
        }

        private void openToast(View view, Employee employee) {
            Toast.makeText(
                    getContext(), "You select " + employee,
                    Toast.LENGTH_SHORT
            ).show();
        }

        @Override
        public int getItemCount() {
            return this.employees.size();
        }
    }
}
