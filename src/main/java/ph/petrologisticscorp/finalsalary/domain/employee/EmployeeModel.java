package ph.petrologisticscorp.finalsalary.domain.employee;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ph.petrologisticscorp.finalsalary.domain.company.CompanyModel;
import ph.petrologisticscorp.finalsalary.gui.GUIRepresentable;

import javax.persistence.*;

@Entity
@Table(name = "employees")
public class EmployeeModel implements GUIRepresentable {

    private final IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private final StringProperty firstName = new SimpleStringProperty(this, "firstName");
    private final StringProperty lastName = new SimpleStringProperty(this, "lastName");
    private CompanyModel company;

    public EmployeeModel(String firstName, String lastName, CompanyModel company) {
        this.firstName.set(firstName);
        this.lastName.set(lastName);
        this.company = company;
    }

    public EmployeeModel() {
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
    @Column(name = "first_name")
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
    @Column(name = "last_name")
    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    public CompanyModel getCompany() {
        return company;
    }

    public void setCompany(CompanyModel company) {
        this.company = company;
    }

    @Override
    @Transient
    public String getTitle() {
        return firstName.getValue() + " " + lastName.getValue();
    }

    @Override
    public String toString() {
        return "EmployeeModel{" +
                "id=" + id +
                ", firstName=" + firstName +
                ", lastName=" + lastName +
                ", company=" + company +
                '}';
    }
}
