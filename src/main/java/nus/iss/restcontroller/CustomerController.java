package nus.iss.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nus.iss.model.Customer;
import nus.iss.model.Order;
import nus.iss.services.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    
    @Autowired
    CustomerService customerService;

    @GetMapping("/all")
    public List<Customer> getAllCustomers(){
        return customerService.returnCustomerList();
    }

    @GetMapping("/limit")
    public List<Customer> getAllCustomersLimitOffSet(@RequestParam("limit") int limit, @RequestParam("offset") int offset){
        return customerService.returnCustomerListLimitOffset(limit,offset);

    }

    @GetMapping("/{id}")
    public Customer getAllCustomersId(@PathVariable("id") int id){
        return customerService.returnCustomerId(id);

    } 

    @GetMapping("/northwind/{id}")
    public List<Order> getCustomer(@PathVariable("id") int id){
        return customerService.returnCustomerOrder(id);

    } 

    

}
