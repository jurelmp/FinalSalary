package ph.petrologisticscorp.finalsalary.domain.company;

import javafx.beans.property.*;
import ph.petrologisticscorp.finalsalary.domain.employee.EmployeeModel;
import ph.petrologisticscorp.finalsalary.gui.GUIRepresentable;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "companies")
public class CompanyModel implements GUIRepresentable {
    private final IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private final StringProperty name = new SimpleStringProperty(this, "name");
    private final ObjectProperty<Set<EmployeeModel>> employees = new SimpleObjectProperty<>(this, "employees");

    public CompanyModel() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
    public Set<EmployeeModel> getEmployees() {
        return employees.get();
    }

    public ObjectProperty<Set<EmployeeModel>> employeesProperty() {
        return employees;
    }

    public void setEmployees(Set<EmployeeModel> employees) {
        this.employees.set(employees);
    }

    @Override
    @Transient
    public String getTitle() {
        return name.getValue();
    }

    @Override
    public String toString() {
        return "CompanyModel{" +
                "id=" + id.getValue().toString() +
                ", name=" + name.getValue() +
                ", employees=" + employees.getValue().size() +
                '}';
    }
}
