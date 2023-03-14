package nus.iss.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nus.iss.model.Customer;
import nus.iss.model.Order;
import nus.iss.repository.CustomerRepository;

@Service
public class CustomerService {
    
    @Autowired
    CustomerRepository customerRepository;

    public List<Customer> returnCustomerList(){
        return customerRepository.getAllCustomers();
    }

    public List<Customer> returnCustomerListLimitOffset(int limit, int offset){
        return customerRepository.getAllCustomerWithLimitOffSet(limit, offset);
    }

    public Customer returnCustomerId(int id){
        return customerRepository.getCustomerById(id);
    }

    public List<Order> returnCustomerOrder(int id){
        return customerRepository.getCustomerOrder(id);
    }

    
    
}
