package com.example.rest.client;

import com.example.rest.model.Employee;
import com.example.rest.model.EmployeeResponse;
import org.junit.Test;

import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

public class HttpUtilTest {

    @Test
    public void getEmployee() throws Exception {
      HttpUtil.HttpUtilBuilder builder = new HttpUtil.HttpUtilBuilder("http://localhost:3000/api/names");

      Response response = builder.build()
             .get();
      EmployeeResponse emp = response.readEntity(EmployeeResponse.class);
        System.out.println(emp);
    }

    @Test
    public void postJson() throws Exception {
        HttpUtil.HttpUtilBuilder builder = new HttpUtil.HttpUtilBuilder("http://localhost:3000/api/names");

        Employee employee = new Employee();
        employee.setName("Ajit");
        builder.build()
                .postJson(employee);
    }

    @Test
    public void postFormData() throws Exception {
        HttpUtil.HttpUtilBuilder builder = new HttpUtil.HttpUtilBuilder("http://localhost:3000/api/names");

        Form form = new Form();
        form.param("name", "Ajit");
        builder.build()
                .postFormData(form);
    }

}