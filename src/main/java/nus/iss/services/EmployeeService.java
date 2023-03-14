package nus.iss.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import nus.iss.model.Employee;
import nus.iss.model.Room;
import nus.iss.repository.EmployeeRepository;

public class EmployeeService {



    @Autowired
    EmployeeRepository empRepo;

    

    public Boolean save(Employee employee){
        return empRepo.save(employee);
    }

    public List<Employee> findAll(){
        return empRepo.findAll();
    }

    // public Employee findById(Integer id){
    //     return empRepo..findById(id);
    // }

   public int update(Employee room){
        return roomRepo.update(room);
    }

    public int deleteById(Integer id){
        return roomRepo.deleteById(id);
    }


    
}

    
}
