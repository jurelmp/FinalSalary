package ph.petrologisticscorp.finalsalary.database;

import org.kohsuke.randname.RandomNameGenerator;
import ph.petrologisticscorp.finalsalary.database.dao.IGenericDAO;
import ph.petrologisticscorp.finalsalary.model.Person;

import javax.inject.Inject;
import java.util.List;
import java.util.Random;

public class PersonService {

    private final IGenericDAO<Person, Integer> personDAO;

    private final RandomNameGenerator randomNameGenerator = new RandomNameGenerator(new Random().nextInt());

    @Inject
    public PersonService(IGenericDAO<Person, Integer> personDAO) {
        this.personDAO = personDAO;
    }

    public List<Person> getAll() {
        return personDAO.getAll();
    }

    public Person createRandom() {
        final Person person = new Person(randomNameGenerator.next());
        personDAO.add(person);
        return person;
    }
}
