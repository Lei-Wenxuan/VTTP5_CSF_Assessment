package vttp.batch5.csf.assessment.server.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp.batch5.csf.assessment.server.models.Customer;
import vttp.batch5.csf.assessment.server.models.Success;

// Use the following class for MySQL database
@Repository
public class RestaurantRepository {

    @Autowired
    private JdbcTemplate template;

    public static final String SQL_VALIDATE_CUSTOMER = """
            select count(*) as count from customers where username = ? and password = ?
            """;

    public static final String SQL_INSERT_SUCCESS = """
            insert into place_order (order_id, payment_id, order_date, total, username)
            values (?, ?, ?, ?, ?);
            """;

    public boolean validateCustomer(Customer c) {
        SqlRowSet rs = template.queryForRowSet(SQL_VALIDATE_CUSTOMER, c.getUsername(), c.getPassword());
        rs.next();
        return (rs.getInt("count") > 0);
    }

    public boolean insertSuccess(Success s) {
        try {
            template.update(SQL_INSERT_SUCCESS, s.getOrderId(), s.getPaymentId(), s.getOrderDate(), s.getTotal(), s.getUsername());
            return true;
        } catch (Exception e) {
            System.err.println("Error in adding to place_order");
            return false;
        }
        
    }
}
