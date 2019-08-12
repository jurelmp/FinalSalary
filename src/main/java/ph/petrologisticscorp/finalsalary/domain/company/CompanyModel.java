package ph.petrologisticscorp.finalsalary.domain.company;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ph.petrologisticscorp.finalsalary.domain.employee.EmployeeModel;
import ph.petrologisticscorp.finalsalary.gui.GUIRepresentable;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "company")
public class CompanyModel implements GUIRepresentable {
    private final IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private final StringProperty name = new SimpleStringProperty(this, "name");
    private Set<EmployeeModel> employees = new HashSet<>(0);

    public CompanyModel(String name) {
        this.name.set(name);
    }

    public CompanyModel(String name, Set<EmployeeModel> employees) {
        this.name.set(name);
        this.employees = employees;
    }

    public CompanyModel() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
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
        return this.employees;
    }

    public void setEmployees(Set<EmployeeModel> employees) {
        this.employees = employees;
    }

    @Override
    @Transient
    public String getTitle() {
        return name.getValue();
    }

    @Override
    public String toString() {
        return "CompanyModel{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }
}
