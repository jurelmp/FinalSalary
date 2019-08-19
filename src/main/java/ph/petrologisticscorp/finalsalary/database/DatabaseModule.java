package ph.petrologisticscorp.finalsalary.database;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.google.inject.persist.jpa.JpaPersistModule;
import ph.petrologisticscorp.finalsalary.database.dao.GenericDAOImpl;
import ph.petrologisticscorp.finalsalary.database.dao.IGenericDAO;
import ph.petrologisticscorp.finalsalary.domain.area.AreaModel;
import ph.petrologisticscorp.finalsalary.domain.company.CompanyModel;
import ph.petrologisticscorp.finalsalary.domain.employee.EmployeeModel;
import ph.petrologisticscorp.finalsalary.model.Person;

import java.util.Properties;

public class DatabaseModule extends AbstractModule {
    @Override
    protected void configure() {
        install(createJpaPersistModule());
        bind(new TypeLiteral<IGenericDAO<Person, Integer>>(){})
                .to(new TypeLiteral<GenericDAOImpl<Person, Integer>>(){})
                .in(Scopes.SINGLETON);
        bind(new TypeLiteral<IGenericDAO<AreaModel, Integer>>(){})
                .to(new TypeLiteral<GenericDAOImpl<AreaModel, Integer>>(){})
                .in(Scopes.SINGLETON);
        bind(new TypeLiteral<IGenericDAO<CompanyModel, Integer>>(){})
                .to(new TypeLiteral<GenericDAOImpl<CompanyModel, Integer>>(){})
                .in(Scopes.SINGLETON);
        bind(new TypeLiteral<IGenericDAO<EmployeeModel, Integer>>(){})
                .to(new TypeLiteral<GenericDAOImpl<EmployeeModel, Integer>>(){})
                .in(Scopes.SINGLETON);
        bind(JPAInitializer.class).asEagerSingleton();
    }

    private JpaPersistModule createJpaPersistModule() {
        Properties props = new Properties();

        props.put("javax.persistence.jdbc.url","jdbc:log4jdbc:h2:file:./data/plc;DB_CLOSE_DELAY=-1");
        JpaPersistModule jpaModule = new JpaPersistModule("javafx-db");
        jpaModule.properties(props);
        return jpaModule;
    }
}
