package ph.petrologisticscorp.finalsalary.database;

import ph.petrologisticscorp.finalsalary.database.dao.IGenericDAO;
import ph.petrologisticscorp.finalsalary.model.Area;

import javax.inject.Inject;
import java.util.List;

public class AreaService {
    private final IGenericDAO<Area, Integer> areaDAO;

    @Inject
    public AreaService(IGenericDAO<Area, Integer> areaDAO) {
        this.areaDAO = areaDAO;
    }

    public List<Area> getAll() {
        return areaDAO.getAll();
    }

    public Area update(Area area) {
        return areaDAO.update(area);
    }

    public void create(Area area) {
        areaDAO.add(area);
    }
}
