package utilities;

import io.restassured.response.Response;
import pojos.Department;
import pojos.Employee;
import pojos.Job;

public class ApiTest3 {
    public static void main(String[] args) {

        /**Post call
         * Create and Employee
         * Endpoint + Headers + Json
         */
        String endpoint = "/api/employees";
        Employee requestBody = new Employee();

        requestBody.setFirstName("Lily");
        requestBody.setLastName("Zi");
        Department department = new Department();
        department.setDepartmentName("IT");
        requestBody.setDepartment(department);
        Job job = new Job();
        job.setTitle("SDET");
        job.setSalary(100000.0);
        requestBody.setJob(job);

        Response response = ApiUtils.postCall(endpoint, requestBody); //Endpoint and Json body
        System.out.println(response.statusCode());


    }


}
