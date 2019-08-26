package ph.petrologisticscorp.finalsalary.model;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.util.Callback;
import ph.petrologisticscorp.finalsalary.gui.GUIRepresentable;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Area implements GUIRepresentable {
    private final IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private final StringProperty name = new SimpleStringProperty(this, "name");
    private final ObjectProperty<Set<Employee>> employees = new SimpleObjectProperty<>(this, "employees");

    public Area() {
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "area")
    public Set<Employee> getEmployees() {
        return employees.get();
    }

    public ObjectProperty<Set<Employee>> employeesProperty() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees.set(employees);
    }

    @Override
    @Transient
    public String getTitle() {
        return name.getValue();
    }

    public static Callback<Area, Observable[]> extractor() {
        return (Area area) -> new Observable[]{area.nameProperty()};
    }

    @Override
    public String toString() {
        return name.getValue();
    }
}
