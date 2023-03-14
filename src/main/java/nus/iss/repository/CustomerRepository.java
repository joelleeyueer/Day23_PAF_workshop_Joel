package nus.iss.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import nus.iss.model.Customer;
import nus.iss.model.Order;

//slide number 24
@Repository
public class CustomerRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String findAllSQL = "select * from customer";
    private final String findAllSQLLimitOffset = "select * from customer limit ? offset ?";
    private final String findById = "select * from customer where id=?";
    private final String findOrderByCustomer = "SELECT id, employee_id, customer_id, order_date, shipped_date, ship_name FROM orders WHERE customer_id=?"; 



    public List<Customer> getAllCustomers(){
        final List<Customer> custList = new ArrayList<>();

        final SqlRowSet rs = jdbcTemplate.queryForRowSet(findAllSQL);

        while (rs.next()){
            Customer cust = new Customer();
            cust.setId(rs.getInt("id"));
            cust.setFirstName(rs.getString("first_name"));
            cust.setLastName(rs.getString("last_name"));
            cust.setDob(rs.getDate("dob"));
            custList.add(cust);
            
        }

        return Collections.unmodifiableList(custList);
        
    }

    public List<Customer> getAllCustomerWithLimitOffSet(int limit, int offset){
        final List<Customer> custList = new ArrayList<>();

        final SqlRowSet rs = jdbcTemplate.queryForRowSet(findAllSQLLimitOffset, limit, offset);

        while (rs.next()){
            Customer cust = new Customer();
            cust.setId(rs.getInt("id"));
            cust.setFirstName(rs.getString("first_name"));
            cust.setLastName(rs.getString("last_name"));
            cust.setDob(rs.getDate("dob"));
            custList.add(cust);
        }

        return Collections.unmodifiableList(custList);
        
    }

    public Customer getCustomerById(int id){

        return jdbcTemplate.queryForObject(findById, BeanPropertyRowMapper.newInstance(Customer.class), id);
        
    }

    public List<Order> getCustomerOrder(int customer_id){
        final List<Order> orderList = new ArrayList<>();

        final SqlRowSet rs = jdbcTemplate.queryForRowSet(findOrderByCustomer, customer_id);


        while (rs.next()){
            Order order = new Order();
            order.setId(rs.getInt("id"));
            order.setEmployeeId(rs.getInt("employee_id"));
            order.setCustomerId(rs.getInt("customer_id"));
            LocalDateTime incomingLDT = (LocalDateTime) rs.getObject("order_date");
            order.setOrderDate(incomingLDT);
            incomingLDT = (LocalDateTime) rs.getObject("shipped_date");
            order.setShippedDate(incomingLDT);
            order.setShipName(rs.getString("ship_name"));


            orderList.add(order);
        }
        System.out.println(orderList);

        return Collections.unmodifiableList(orderList);
        
    }

    

    
}
