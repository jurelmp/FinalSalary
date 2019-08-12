package ph.petrologisticscorp.finalsalary.domain.employee;

import ph.petrologisticscorp.finalsalary.database.dao.IGenericDAO;

import javax.inject.Inject;
import java.util.List;

public class EmployeeService {
    private final IGenericDAO<EmployeeModel, Integer> employeeDAO;

    @Inject
    public EmployeeService(IGenericDAO<EmployeeModel, Integer> employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    public List<EmployeeModel> getAll() {
        return employeeDAO.getAll();
    }

    public EmployeeModel createEmployee(EmployeeModel employee) {
        employeeDAO.add(employee);
        return employee;
    }
}
