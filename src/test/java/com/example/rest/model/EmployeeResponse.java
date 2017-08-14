package com.example.rest.model;

import java.util.List;

public class EmployeeResponse {

    private List<Employee> names;

    public List<Employee> getNames() {
        return names;
    }

    public void setNames(List<Employee> names) {
        this.names = names;
    }
}
