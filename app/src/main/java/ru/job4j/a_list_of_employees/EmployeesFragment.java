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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class EmployeesFragment extends Fragment {
    private RecyclerView recycler;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.employees, container, false);
        this.recycler = view.findViewById(R.id.employees);
        this.recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        updateUI();
        return view;
    }

    private void updateUI() {
        List<Employee> employees = VirtualDatabase.getInstance().getEmployees();
        employees = employees.stream().
                filter(employee -> employee.getSpecialty().getId() == Objects.requireNonNull(getArguments()).getInt("specialtyId", 0))
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

    public class ExamHolder extends RecyclerView.ViewHolder {
        private View view;
        public ExamHolder(@NonNull View view) {
            super(view);
            this.view = itemView;
        }
    }

    public class ExamAdapter extends RecyclerView.Adapter<EmployeesFragment.ExamHolder> {
        private final List<Employee> employees;
        public ExamAdapter(List<Employee> specialty) {
            this.employees = specialty;
        }
        @NonNull
        @Override
        public EmployeesFragment.ExamHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.info_employees, parent, false);
            return new EmployeesFragment.ExamHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull EmployeesFragment.ExamHolder holder, int i) {
            final Employee employee = this.employees.get(i);
            TextView employeeName = holder.view.findViewById(R.id.EmployeeNameSurname);
            employeeName.setText(String.format("%s %s", employee.getName(), employee.getSurname()));
            employeeName.setOnClickListener(view -> openToast(view, employee));
        }

        private void openToast(View view, Employee employee) {
            Toast.makeText(
                    getContext(), "You select " + employee,
                    Toast.LENGTH_SHORT
            ).show();
            EmployeeFragment fragment = (EmployeeFragment) Objects.requireNonNull(getActivity()).getSupportFragmentManager().findFragmentByTag("EmployeeFragment");
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            if(fragment == null) {
                fragmentTransaction.add(R.id.content, EmployeeFragment.of(employee.hashCode()), "EmployeeFragment").commit();
            } else {
                fragmentTransaction.remove(fragment).add(R.id.content, EmployeeFragment.of(employee.hashCode()), "EmployeeFragment").commit();
            }
        }

        @Override
        public int getItemCount() {
            return this.employees.size();
        }
    }
}
