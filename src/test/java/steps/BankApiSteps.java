package steps;

import groovy.transform.stc.ClosureSignatureConflictResolver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Ignore;
import pojos.bankpojos.Account;
import pojos.bankpojos.Customer;
import pojos.bankpojos.Transaction;
import utilities.ApiUtils;
import utilities.ApiUtilsBank;
import utilities.Configuration;

import java.lang.reflect.Array;
import java.util.*;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.oauth;

public class BankApiSteps {
    Response response;
    String customerID;
    String accountId;
    Double accountBalance;
    Double transactionAmount;

    @Given("User sends get customers api call with access token")
    public void user_sends_get_customers_api_call_with_access_token() {
        response = ApiUtilsBank.getCall("/api/customers");
    }

    @Then("User validates {int} status code")
    public void user_validates_status_code(int expectedStatusCode) {
        int actualStatusCode = response.statusCode();
        Assert.assertEquals(expectedStatusCode, actualStatusCode);
    }

    @Given("User sends get customers api call without access token")
    public void user_sends_get_customers_api_call_without_access_token() {
        response = given().baseUri(Configuration.getProperty("ApiBankBaseURI"))
                .accept(ContentType.JSON).and().contentType(ContentType.JSON)
                .when().get("/api/customers");
    }

    @Given("User creates customers with post api call using data")
    public void user_creates_customers_with_post_api_call_using_data(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, Object>> data = dataTable.asMaps(String.class, Object.class);
        //Headers + Body + Endpoint
        String endpoint = "/api/customer";
        for (int i = 0; i < data.size(); i++) {
            Customer customer = new Customer();
            customer.setFullName(data.get(i).get("Name").toString());
            customer.setAddress(data.get(i).get("Address").toString());
            customer.setActive(Boolean.valueOf(data.get(i).get("isActive").toString()));// Object->String->Boolean
            response = ApiUtilsBank.postCall(endpoint, customer);
            Assert.assertEquals(201, response.statusCode());
        }
    }

    @When("User sends get customer api call with {string} order")
    public void user_sends_get_customer_api_call_with_order(String order) {
        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put("order", order);
        response = ApiUtilsBank.getCall("/api/customers", queryParam);
        Assert.assertEquals(200, response.statusCode());
    }

    @Then("User validates that customer are in {string} order")
    public void user_validates_that_customer_are_in_order(String order) {
        Customer[] customers = response.body().as(Customer[].class);
        List<Customer> actualCustomers = Arrays.asList(customers);
        List<Customer> expectedCustomers = actualCustomers;
        if (order.equalsIgnoreCase("asc")) {
            //Validating customers array names are in ascending order.
            //We can create a new list, and we will save original values in that list
            //then we will sort customers, and then compare if they are equal
            Collections.sort(actualCustomers, new Customer());
            Assert.assertEquals(expectedCustomers, actualCustomers);
        } else if (order.equalsIgnoreCase("desc")) {
            Collections.sort(actualCustomers, Collections.reverseOrder(new Customer()));
            Assert.assertEquals(expectedCustomers, actualCustomers);
        }

    }

    @When("User sends get customers api call with {string} limit")
    public void user_sends_get_customers_api_call_with_limit(String limit) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("limit", limit);
        response = ApiUtilsBank.getCall("/api/customers", queryParams);
        Assert.assertEquals(200, response.statusCode());
    }

    @Then("User validates that get customers response has {string} customers")
    public void user_validates_that_get_customers_response_has_customers(String limit) {
        Customer[] customers = response.body().as(Customer[].class);
        int size = customers.length;
        System.out.println(size);
        Assert.assertEquals(limit, customers.length + "");
    }


    @Then("User validates that customer is created with data")
    public void user_validates_that_customer_is_created_with_data(io.cucumber.datatable.DataTable dataTable) {

        Customer customer = response.body().as(Customer.class); //comverting Json->POJO =Deserialization
        customerID = customer.getId().toString();
        String endpoint = "/api/customers/" + customerID;

        response = ApiUtilsBank.getCall(endpoint);
        Assert.assertEquals(200, response.statusCode());

        List<Map<String, Object>> expectedData = dataTable.asMaps(String.class, Object.class);
        Customer customer1 = response.body().as(Customer.class);//Json->Pojo = Deserialization
        Assert.assertEquals(expectedData.get(0).get("Name").toString(), customer1.getFullName());
        Assert.assertEquals(expectedData.get(0).get("Address").toString(), customer1.getAddress());
        Assert.assertEquals(expectedData.get(0).get("isActive").toString(), customer1.getActive().toString());
    }

    @When("User deletes created customer")
    public void user_deletes_created_customer() {
        /*
        in order to delete we need endpoint /api/customers/:id
         */
        String endpoint = "/api/customers/" + customerID;
        response = ApiUtilsBank.deleteCall(endpoint);
        Assert.assertEquals(204, response.statusCode());
    }

    @Then("User validates that customer is deleted")
    public void user_validates_that_customer_is_deleted() {
        // Send GET call and validate that customer NOT FOUND with status code 404
        // Endpoint: /api/customers/:id

        String endpoint = "/api/customers/" + customerID;
        response = ApiUtilsBank.getCall(endpoint);
        Assert.assertEquals(404, response.statusCode());

        String errorMessage = response.jsonPath().getString("message");//from response body
        String expectedErrorMessage = "Customer not found with ID " + customerID;//expected response code
        System.out.println(errorMessage);
        Assert.assertEquals(expectedErrorMessage, errorMessage);
    }

    @When("User creates account for a customer with data")
    public void user_creates_account_for_a_customer_with_data(DataTable dataTable) {
        String endpoint = "/api/account";
        Account account = new Account();
        Map<String, Object> data = dataTable.asMap(String.class, Object.class);
        account.setAccountType(data.get("accountType").toString());
        account.setBalance((Double.valueOf(data.get("Balance").toString())));
        Customer customer = new Customer();
        customerID = response.jsonPath().getString("id");
        customer.setId(Integer.valueOf(customerID));
        account.setCustomer(customer);


        response = ApiUtilsBank.postCall(endpoint, account);
        Assert.assertEquals(201, response.statusCode());

        accountId = response.jsonPath().getString("id");
        accountBalance = Double.valueOf(data.get("Balance").toString());


    }

    @Then("User validates that customer is linked to created account")
    public void user_validates_that_customer_is_linked_to_created_account(DataTable dataTable) {
        //Send get customer api call, and validate json body includes that account info.
        //Endpoint (/api/customer/id) + headers
        Map<String, Object> expectedData = dataTable.asMap(String.class, Object.class);
        String endpoint = "/api/customers/" + customerID;
        response = ApiUtilsBank.getCall(endpoint);
        Customer customer = response.body().as(Customer.class); //JSON ->POJO Deserialization

        //AccountType, Balance, customerID
        Assert.assertEquals(expectedData.get("accountType").toString(), customer.getAccounts().get(0).getAccountType());
        Assert.assertEquals(Double.valueOf(expectedData.get("Balance").toString()), customer.getAccounts().get(0).getBalance());
        Assert.assertEquals(Integer.valueOf(customerID), customer.getId());
    }

    @When("User deletes created account")
    public void user_deletes_created_account() {
        String endpoint = "/api/accounts/" + accountId;
        response = ApiUtilsBank.deleteCall(endpoint);
        Assert.assertEquals(204, response.statusCode());
    }


    @Then("Use validates that account is deleted")
    public void use_validates_that_account_is_deleted() {
        String endpoint = "/api/accounts/" + accountId;
        response = ApiUtilsBank.getCall(endpoint);
        Assert.assertEquals(404, response.statusCode());
        String expectedMessage = "Account not found with ID " + accountId;
        Assert.assertEquals(expectedMessage, response.jsonPath().getString("message"));

    }
    @When("User updates account balance with put API calls")
    public void user_updates_account_balance_with_put_API_calls(io.cucumber.datatable.DataTable dataTable) {
        String endpoint = "/api/account";
        customerID = response.jsonPath().getString("id");
        Map<String, Object> data = dataTable.asMap(String.class, Object.class);
        Account account = new Account();
        account.setAccountType(data.get("accountType").toString());
        account.setBalance(Double.valueOf(data.get("balance").toString()));
        Customer customer = new Customer();
        customer.setId(Integer.valueOf(customerID));
        account.setCustomer(customer);
        response = ApiUtils.putCall(endpoint, account);
        accountId = response.jsonPath().getString("id");
        Assert.assertEquals(201, response.getStatusCode());
    }

    @Then("User validates that balance is updates")
    public void user_validates_that_balance_is_updates(DataTable dataTable) {
        String endpoint = "/api/customers/" + customerID;
        response = ApiUtils.getCall(endpoint);
        Customer customer = response.body().as(Customer.class);
        Map<String, Object> expectedData = dataTable.asMap(String.class, Object.class);
        Assert.assertEquals(expectedData.get("accountType"), customer.getAccounts().get(0).getAccountType());
        Assert.assertEquals(Double.valueOf(expectedData.get("balance").toString()), customer.getAccounts().get(0).getBalance());
    }
    @When("User creates transaction for an account with data")
    public void user_creates_transaction_for_a_account_with_data(io.cucumber.datatable.DataTable dataTable) {
       /*
       Create transaction. POST call: Endpoint + Headers +Body
        */
        String endpoint = "/api/transaction";
        Transaction transaction = new Transaction(); //POJO body
        Account account = new Account();
        List<Map<String, Object>> data = dataTable.asMaps(String.class, Object.class);
        transaction.setAmount(Double.valueOf(data.get(0).get("transactionAmount").toString()));
        transaction.setTransactionName(data.get(0).get("transactionName").toString());
        account.setId(Integer.parseInt(accountId));
        transaction.setAccount(account);
        transactionAmount = Double.valueOf(data.get(0).get("transactionAmount").toString());
        response = ApiUtilsBank.postCall(endpoint, transaction);
        Assert.assertEquals(201, response.statusCode());
    }

    @Then("User validates that balance is decreased in account")
    public void user_validates_that_balance_is_decreased_in_account() {
        String endpoint = "/api/accounts/"+accountId;
        response = ApiUtilsBank.getCall(endpoint);
        Assert.assertEquals(200, response.statusCode());
        //Validate that balance is decreased
        Double actualBalance = response.body().as(Account.class).getBalance();
        Double expectedBalance = accountBalance-transactionAmount;
        Assert.assertEquals(expectedBalance,actualBalance);


    }
}
