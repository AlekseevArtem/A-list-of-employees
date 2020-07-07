package ru.job4j.a_list_of_employees.Store;

public class EmployeesDbSchema {
    public static final class employeeTable {
        public static final String NAME = "employee";

        public static final class Cols {
            public static final String NAME = "name";
            public static final String SURNAME = "surname";
            public static final String BIRTHDAY = "birthday";
            public static final String IMAGE = "image";
            public static final String SPECIALTY_ID = "specialty_id";
        }
    }

    public static final class specialtyTable {
        public static final String NAME = "specialty";

        public static final class Cols {
            public static final String ID = "id";
            public static final String NAME = "name";
        }
    }
}