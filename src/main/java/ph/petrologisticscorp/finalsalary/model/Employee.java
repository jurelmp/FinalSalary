package ph.petrologisticscorp.finalsalary.model;

import javafx.beans.property.*;
import ph.petrologisticscorp.finalsalary.gui.GUIRepresentable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Employee implements GUIRepresentable, Serializable {

    private final IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private final StringProperty code = new SimpleStringProperty(this, "code");
    private final StringProperty lastName = new SimpleStringProperty(this, "lastName");
    private final StringProperty firstName = new SimpleStringProperty(this, "firstName");
    private final StringProperty middleName = new SimpleStringProperty(this, "middleName");
    private final ObjectProperty<Company> company = new SimpleObjectProperty<>(this, "company");
    private final ObjectProperty<Area> area = new SimpleObjectProperty<>(this, "area");
    private final ObjectProperty<Date> hireDate = new SimpleObjectProperty<>(this, "hireDate");
    private final BooleanProperty active = new SimpleBooleanProperty(this, "isActive");
    private final ObjectProperty<Set<Leave>> leaves = new SimpleObjectProperty<>(this, "leaves");
    private final ObjectProperty<Set<Salary>> salaries = new SimpleObjectProperty<>(this, "salaries");

    public Employee() {
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

    @Column(name = "code", length = 10, unique = true)
    public String getCode() {
        return code.get();
    }

    public StringProperty codeProperty() {
        return code;
    }

    public void setCode(String code) {
        this.code.set(code);
    }

    @Basic
    @Column(name = "first_name", nullable = false)
    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    @Basic
    @Column(name = "last_name", nullable = false)
    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    @Basic
    @Column(name = "middle_name")
    public String getMiddleName() {
        return middleName.get();
    }

    public StringProperty middleNameProperty() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName.set(middleName);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    public Company getCompany() {
        return company.get();
    }

    public ObjectProperty<Company> companyProperty() {
        return company;
    }

    public void setCompany(Company company) {
        this.company.set(company);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id", nullable = false)
    public Area getArea() {
        return area.get();
    }

    public ObjectProperty<Area> areaProperty() {
        return area;
    }

    public void setArea(Area area) {
        this.area.set(area);
    }

    @Column(name = "hire_date")
    @Temporal(TemporalType.DATE)
    public Date getHireDate() {
        return hireDate.get();
    }

    public ObjectProperty<Date> hireDateProperty() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate.set(hireDate);
    }

    @Column(name = "is_active", nullable = false, columnDefinition = "INT(1) DEFAULT 1")
    public boolean isActive() {
        return active.get();
    }

    public BooleanProperty activeProperty() {
        return active;
    }

    public void setActive(boolean active) {
        this.active.set(active);
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    public Set<Leave> getLeaves() {
        return leaves.get();
    }

    public ObjectProperty<Set<Leave>> leavesProperty() {
        return leaves;
    }

    public void setLeaves(Set<Leave> leaves) {
        this.leaves.set(leaves);
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    public Set<Salary> getSalaries() {
        if (salaries.get() == null) {
            return new HashSet<>();
        }
        return salaries.get();
    }

    public ObjectProperty<Set<Salary>> salariesProperty() {
        return salaries;
    }

    public void setSalaries(Set<Salary> salaries) {
        this.salaries.set(salaries);
    }

    @Override
    @Transient
    public String getTitle() {
        return firstName.getValue() + " " + lastName.getValue();
    }

    @Override
    public String toString() {
        return "EmployeeModel{" +
                "id=" + id.getValue() +
                ", code=" + code.getValue() +
                ", lastName=" + lastName.getValue() +
                ", firstName=" + firstName.getValue() +
                ", middleName=" + middleName.getValue() +
                ", area=" + area.getValue().getName() +
                ", hireDate=" + hireDate.getValue() +
                ", active=" + active.getValue() +
                '}';
    }
}
