package ru.job4j.a_list_of_employees;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

class VirtualDatabase {
    private int size = 200;
    private List<Employee> employees = new ArrayList<>(size);
    private static final VirtualDatabase INST = new VirtualDatabase();
    private List<String> maleName = Arrays.asList("Артем", "Иван", "Антон", "Павел", "Сергей", "Алексей", "Михаил");
    private List<String> femaleName = Arrays.asList("Алла", "Инна", "Елизавета", "Виктория", "Светлана", "Елена");
    private List<String> surname = Arrays.asList("Алексеев", "Иванов", "Петров", "Семенов", "Гагарин", "Павлов", "Королев", "Арсентьев");
    private List<String> birthday = Arrays.asList("08.11.1991г.", "30.04.1978г.", "13.04.1984г.", "12.04.1989г.", "01.04.1979г.", "04.04.1976г.", "22.11.1990г.", "16.04.1978г.");
    private List<Integer> maleImage = Arrays.asList(R.drawable.man1, R.drawable.man2, R.drawable.man3, R.drawable.man4, R.drawable.man5);
    private List<Integer> femaleImage = Arrays.asList(R.drawable.woman1, R.drawable.woman2, R.drawable.woman3, R.drawable.woman4);
    private List<Specialty> specialty = Arrays.asList(
            new Specialty(2031, "Инженер-проектировщик"),
            new Specialty(1123, "Бухгалтер"),
            new Specialty(4123, "Инженер-технолог"),
            new Specialty(2341, "Менеджер по продажам"),
            new Specialty(5453, "ГИП"),
            new Specialty(5213, "Android Developer")
    );

    private VirtualDatabase() {
        for (int i = 0; i < size; i++) {
            int femaleOrMale = new Random().nextInt(this.size);
            if (femaleOrMale < 60) {
                employees.add(new Employee(
                        maleName.get(new Random().nextInt(maleName.size())),
                        surname.get(new Random().nextInt(surname.size())),
                        birthday.get(new Random().nextInt(surname.size())),
                        maleImage.get(new Random().nextInt(maleImage.size())),
                        specialty.get(new Random().nextInt(specialty.size()))
                ));
            } else {
                employees.add(new Employee(
                        femaleName.get(new Random().nextInt(femaleName.size())),
                        surname.get(new Random().nextInt(surname.size()))+"a",
                        birthday.get(new Random().nextInt(surname.size())),
                        femaleImage.get(new Random().nextInt(femaleImage.size())),
                        specialty.get(new Random().nextInt(specialty.size()))
                ));
            }
        }
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public static VirtualDatabase getInstance() {
        return INST;
    }
}
