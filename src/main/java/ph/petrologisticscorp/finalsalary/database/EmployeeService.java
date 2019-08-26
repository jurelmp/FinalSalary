package ph.petrologisticscorp.finalsalary.database;

import ph.petrologisticscorp.finalsalary.database.dao.IGenericDAO;
import ph.petrologisticscorp.finalsalary.model.Employee;

import javax.inject.Inject;
import java.util.List;

public class EmployeeService {
    private final IGenericDAO<Employee, Integer> employeeDAO;

    @Inject
    public EmployeeService(IGenericDAO<Employee, Integer> employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    public List<Employee> getAll() {
        return employeeDAO.getAll();
    }

    public Employee create(Employee employee) {
        employeeDAO.add(employee);
        return employee;
    }

    public Employee update(Employee employee) {
        return employeeDAO.update(employee);
    }

    public Employee saveOrUpdate(Employee employee) {
        return employeeDAO.saveOrUpdate(employee);
    }

    public Employee find(int id) {
        return employeeDAO.find(id);
    }
}
