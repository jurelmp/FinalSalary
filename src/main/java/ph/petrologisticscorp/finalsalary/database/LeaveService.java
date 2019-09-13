package ph.petrologisticscorp.finalsalary.database;

import ph.petrologisticscorp.finalsalary.database.dao.IGenericDAO;
import ph.petrologisticscorp.finalsalary.model.Leave;

import javax.inject.Inject;

public class LeaveService {

    private final IGenericDAO<Leave, Integer> leaveDAO;

    @Inject
    public LeaveService(IGenericDAO<Leave, Integer> leaveDAO) {
        this.leaveDAO = leaveDAO;
    }

    public Leave saveOrUpdate(Leave leave) {
        return leaveDAO.saveOrUpdate(leave);
    }
}
