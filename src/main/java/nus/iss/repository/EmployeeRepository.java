package nus.iss.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import io.micrometer.common.lang.Nullable;
import nus.iss.model.Dependant;
import nus.iss.model.Employee;

@Repository
public class EmployeeRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;
;

    String findAllSQL = "select e.id as emp_id, e.first_name, e.last_name, e.salary " + 
                            "d.id as dep_id, d.full_name, d.birth_date, d.relationship " +
                            "from employee as e " +
                            "inner join dependant d " +
                            "on e.id = d.employee_id";
    String findByIdSQL = "select e.id as emp_id, e.first_name, e.last_name, e.salary " + 
                            "d.id as dep_id, d.full_name, d.birth_date, d.relationship " +
                            "from employee as e " +
                            "inner join dependant d " +
                            "on e.id = d.employee_id " +
                            "where e.id = ?";
    String insertSQL = "insert into employee (first_name, last_name, salary) values (?,?,?)";
    String updateSQL = "update employee set first_name=?, last_name=?, salary=? where id=?";
    String deleteSQL = "delete from employee where id = ?";

    public Boolean save(Employee employee){
        Boolean bSaved = false;

        PreparedStatementCallback<Boolean> psc = new PreparedStatementCallback<Boolean>() {
            
            @Override
            @Nullable
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException{
                ps.setString(1, employee.getFirstName());
                ps.setString(2,employee.getLastName());
                ps.setInt(3, employee.getSalary());
                Boolean result = ps.execute();
                return result;
            }
        };

        bSaved = jdbcTemplate.execute(insertSQL, psc);

        return bSaved;
    }

    public int update(Employee employee) {
        int updated = 0;

        PreparedStatementSetter pss = new PreparedStatementSetter() {
            
            @Override
            public void setValues(PreparedStatement ps) throws SQLException, DataAccessException{
                ps.setString(1, employee.getFirstName());
                ps.setString(2,employee.getLastName());
                ps.setInt(3, employee.getSalary());
                ps.setInt(4, employee.getId());
            }
        };     

        updated = jdbcTemplate.update(updateSQL, pss);

        return updated;
    }

    
    public int deleteById(Integer id) {
        // TODO Auto-generated method stub
        int deleted = 0;

        PreparedStatementSetter pss = new PreparedStatementSetter(){
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1, id);
                }
        };

        deleted = jdbcTemplate.update(deleteSQL, pss);

        return deleted;
    }

    public List<Employee> findAll(){
        List<Employee> employees = new ArrayList<>();

        
        employees = jdbcTemplate.query(findAllSQL, new ResultSetExtractor<List<Employee>>() {
            
            @Override
            public List<Employee> extractData(ResultSet rs){
                List<Employee> emps = new ArrayList<>();

                while (rs.next()){
                    Employee employee = new Employee();
                    employee.setId(rs.getInt("emp_id"));
                    employee.setFirstName(rs.getString("first_name"));
                    employee.setLastName(rs.getString("last_name"));
                    employee.setSalary(rs.getInt("salary"));

                    Dependant dependant = new Dependant();
                    dependant.setId(rs.getInt("emp_id"));
                    dependant.setFullName(rs.getString("full_name"));
                    dependant.setRelationship(rs.getString("relationship"));
                    dependant.setBirthDate(rs.getDate("birth_date"));

                    if (emps.size()==0){
                        employee.getDependants().add(dependant);
                        emps.add(employee);
                    } else { //check if the employee id exists in the list<employee> emps
                        List<Employee> tmpList = emps.stream().filter(e->e.getId() == employee.getId()).collect(Collectors.toList());

                        if (tmpList.size() > 0){ //if employee record found
                            int idx = emps.indexOf(tmpList.get(0));
                        }

                        if (idx >= 0){
                            emps.get(idx).getDependants().add(dependant);
                        } else {
                            employee.getDependants().add(dependant);
                            emps.add(employee);
                        }
                    } 
                }

                


            }
        });
    }
}
