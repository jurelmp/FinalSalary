package ph.petrologisticscorp.finalsalary.domain.company;

import ph.petrologisticscorp.finalsalary.database.dao.IGenericDAO;

import javax.inject.Inject;
import java.util.List;

public class CompanyService {

    private final IGenericDAO<CompanyModel, Integer> companyDAO;

    @Inject
    public CompanyService(IGenericDAO<CompanyModel, Integer> companyDAO) {
        this.companyDAO = companyDAO;
    }

    public List<CompanyModel> getAll() {
        return companyDAO.getAll();
    }

    public CompanyModel getById(Integer id) {
        return companyDAO.find(id);
    }

    public CompanyModel save(CompanyModel company) {
        return companyDAO.saveOrUpdate(company);
    }
}
