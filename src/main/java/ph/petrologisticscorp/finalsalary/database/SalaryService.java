package ph.petrologisticscorp.finalsalary.database;

import ph.petrologisticscorp.finalsalary.database.dao.IGenericDAO;
import ph.petrologisticscorp.finalsalary.model.Salary;

import javax.inject.Inject;

public class SalaryService {
    private final IGenericDAO<Salary, Integer> salaryDAO;

    @Inject
    public SalaryService(IGenericDAO<Salary, Integer> salaryDAO) {
        this.salaryDAO = salaryDAO;
    }

    public Salary saveOrUpdate(Salary salary) {
        return salaryDAO.saveOrUpdate(salary);
    }
}
