package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import javax.xml.transform.Result;
import java.security.Key;
import java.sql.*;

public class JDBC_practice {
    public static void main(String[] args) throws SQLException {
        String URL = "jdbc:oracle:thin:@hrapp.cr5llg7g5fq9.us-east-2.rds.amazonaws.com:1521/ORCL";
        String username = "adminL";
        String password = "Thisismypassword0801!";

        DriverManager.getConnection(URL, username, password);
        Connection connection = DriverManager.getConnection(URL, username, password);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM employees where first_name='Steven' AND last_name = 'King'");
//        resultSet.next();
//        System.out.println(resultSet.getString("first_name"));
//        resultSet.next();
//        System.out.println(resultSet.getInt("employee_id"));

//        while(resultSet.next()) {
//                System.out.println(resultSet.getString("first_name")+"     "+resultSet.getString("last_name"));
//                System.out.println(resultSet.getString("last_name"));
//              }

//        WebDriver driver = Driver.getDriver();
//        driver.get("http://google.com");
//        driver.findElement(By.xpath("//input[@name='q']")).sendKeys(resultSet.getString("first_name") + " " + resultSet.getString("last_name") + Keys.ENTER);
//        }

        /**
         Print employee_id, first_name, last_name, manager_id and hire_date columns from employees
         */
        ResultSet resultSet1 = statement.executeQuery("SELECT * FROM employees");
        while (resultSet1.next()) {
            System.out.println(resultSet1.getInt("employee_id") + "\t\t" + resultSet1.getString("first_name") +
                    "\t" + resultSet1.getString("last_name") + "\t\t" + resultSet1.getInt("manager_id") +
                    "\t\t" + resultSet1.getString("hire_date"));
        }
        System.out.println("--------------------------------");

        /**
         Print country id, country name, and region_id columns from countries
         */
        ResultSet resultSet2 = statement.executeQuery("SELECT * FROM countries");
        while (resultSet2.next()) {
            System.out.println(resultSet2.getString("country_id") + "\t\t" + resultSet2.getString("country_name") +
                    "\t\t" + resultSet2.getInt("region_id"));
        }
        System.out.println("--------------------------------");

        /**
         Print department_id, department_name, and location_id columns from departments
         */
        ResultSet resultSet3 = statement.executeQuery("SELECT * FROM departments");
        while (resultSet3.next()) {
            System.out.println(resultSet3.getInt("department_id") + "\t\t" + resultSet3.getString("department_name")
                    + "\t\t" + resultSet3.getInt("location_id"));
        }
        System.out.println("--------------------------------");

        /**
         Print start_date, end_date, and job_id columns from job_history.
         */
        ResultSet resultSet4 = statement.executeQuery("SELECT * FROM job_history");
        while (resultSet4.next()) {
            System.out.println(resultSet4.getString("start_date") + "\t\t" + resultSet4.getString("end_date") +
                    "\t\t" + resultSet4.getString("job_id"));
        }
        System.out.println("--------------------------------");

        /**
         Ask google how to live on the lowest min_salary from jobs table? Min_salary should be dynamic from database
         */
//        ResultSet resultSet5 = statement.executeQuery("SELECT MIN(min_salary) FROM jobs");
//        while (resultSet5.next()) {
//            WebDriver driver = Driver.getDriver();
//            driver.get("http://google.com");
//            driver.findElement(By.xpath("//input[@name='q']")).sendKeys("How to live when your salary is  " + resultSet5.getInt("min(min_salary)") + Keys.ENTER);
//
//        }
        ResultSet resultSet6 = statement.executeQuery("SELECT * FROM employees");
        ResultSetMetaData resultSetMetaData = resultSet6.getMetaData();
        System.out.println("Employees table has "+resultSetMetaData.getColumnCount());
        System.out.println(resultSetMetaData.getColumnName(1));
        System.out.println(resultSetMetaData.getColumnName(2));
    }
}
