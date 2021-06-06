package hotel;

import java.util.ArrayList;

public class Customer {
    // TODO: make array singleton
    private static final ArrayList<Customer> CUSTOMERS = new ArrayList<Customer>();

    private Integer customerId;
    private String firstname;
    private String lastname;

    public Customer(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public static Customer getCustomer(Integer customerId) {
        for (Customer customer : CUSTOMERS) {
            if (customer.customerId == customerId) {
                return customer;
            }
        }

        return null;
    }
}