package utilities;

import io.restassured.response.Response;
import pojos.Department;
import pojos.Employee;
import pojos.Location;

public class ApiTest2 {

    public static void main(String[] args) {
     /**   Response response1 = ApiUtils.getCall("/api/employees/100");
        System.out.println(response1.body().asString());
        response1.then().log().all();

        System.out.println(response1.andReturn().statusCode());

        Employee responseBody = new Employee(); // Employee Object
        //Deserialization -> Converting JSON to POJO
        responseBody = response1.body().as(Employee.class);
        System.out.println(responseBody.getLastName());
        System.out.println(responseBody.getJob().getSalary());
        System.out.println(responseBody.getDepartment().getLocation().getLocationCity());
        System.out.println(responseBody.getDepartment().getDepartmentName()); */




        /**
         * Assignment:
         * Create Department with name "Student Relations"
         * Get Department
         * Update department name with to "SR"
         * Delete created department
         */

        String endpoint = "/api/departments";
        Department department = new Department();
        department.setDepartmentName("Student");
        Location location = new Location();
        location.setLocationCity("Chicago");
        department.setLocation(location);
        Response response2 = ApiUtils.postCall(endpoint, department);
        System.out.println(response2.statusCode());

        response2 = ApiUtils.getCall(endpoint+"/student");
        response2.then().log().all();
        System.out.println(response2.statusCode());

     /*   String updatedName = "SR";
        response2 = ApiUtils.putCall(endpoint+"/jdgfjsdf", updatedName);
        System.out.println(response2.statusCode()); */

        response2 = ApiUtils.deleteCall(endpoint+"/student");
        System.out.println(response2.statusCode());

    }
}
