package ph.petrologisticscorp.finalsalary.model;

import javafx.beans.property.*;
import ph.petrologisticscorp.finalsalary.gui.GUIRepresentable;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Salary implements GUIRepresentable {
    private final IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private final ObjectProperty<Employee> employee = new SimpleObjectProperty<>(this, "employee");
    private final DoubleProperty salary = new SimpleDoubleProperty(this, "salary");
    private final DoubleProperty sinking = new SimpleDoubleProperty(this, "sinking");
    private final DoubleProperty canteen = new SimpleDoubleProperty(this, "canteen");
    private final ObjectProperty<Date> durationFrom = new SimpleObjectProperty<>(this, "durationFrom");
    private final ObjectProperty<Date> durationTo = new SimpleObjectProperty<>(this, "durationTo");

    public Salary() {
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_code", referencedColumnName = "code", columnDefinition = "VARCHAR(10)")
    public Employee getEmployee() {
        return employee.get();
    }

    public ObjectProperty<Employee> employeeProperty() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee.set(employee);
    }

    @Column(name = "salary", columnDefinition = "DOUBLE DEFAULT 0.00", nullable = false)
    public double getSalary() {
        return salary.get();
    }

    public DoubleProperty salaryProperty() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary.set(salary);
    }

    @Column(name = "sinking", columnDefinition = "DOUBLE DEFAULT 0.00", nullable = false)
    public double getSinking() {
        return sinking.get();
    }

    public DoubleProperty sinkingProperty() {
        return sinking;
    }

    public void setSinking(double sinking) {
        this.sinking.set(sinking);
    }

    @Column(name = "canteen", columnDefinition = "DOUBLE DEFAULT 0.00", nullable = false)
    public double getCanteen() {
        return canteen.get();
    }

    public DoubleProperty canteenProperty() {
        return canteen;
    }

    public void setCanteen(double canteen) {
        this.canteen.set(canteen);
    }

    @Column(name = "duration_from")
    @Temporal(TemporalType.DATE)
    public Date getDurationFrom() {
        return durationFrom.get();
    }

    public ObjectProperty<Date> durationFromProperty() {
        return durationFrom;
    }

    public void setDurationFrom(Date durationFrom) {
        this.durationFrom.set(durationFrom);
    }

    @Column(name = "duration_to")
    @Temporal(TemporalType.DATE)
    public Date getDurationTo() {
        return durationTo.get();
    }

    public ObjectProperty<Date> durationToProperty() {
        return durationTo;
    }

    public void setDurationTo(Date durationTo) {
        this.durationTo.set(durationTo);
    }

    @Override
    @Transient
    public String getTitle() {
        return employee.get().getCode();
    }

    @Override
    public String toString() {
        return "SalaryModel{" +
                "id=" + id.getValue().toString() +
                ", employee=" + employee.getValue().getCode() +
                ", salary=" + salary.getValue().toString() +
                ", sinking=" + sinking.getValue().toString() +
                ", canteen=" + canteen.getValue().toString() +
                ", durationFrom=" + durationFrom.getValue().toString() +
                ", durationTo=" + durationTo.getValue().toString() +
                '}';
    }
}
