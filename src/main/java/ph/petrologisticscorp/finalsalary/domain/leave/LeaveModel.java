package ph.petrologisticscorp.finalsalary.domain.leave;

import javafx.beans.property.*;
import ph.petrologisticscorp.finalsalary.domain.employee.EmployeeModel;
import ph.petrologisticscorp.finalsalary.gui.GUIRepresentable;

import javax.persistence.*;

@Entity
@Table(name = "leaves")
public class LeaveModel implements GUIRepresentable {
    private final IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private final ObjectProperty<EmployeeModel> employee = new SimpleObjectProperty<>(this, "employee");
    private final IntegerProperty year = new SimpleIntegerProperty(this, "year");
    private final DoubleProperty rate = new SimpleDoubleProperty(this, "rate");
    private final DoubleProperty days = new SimpleDoubleProperty(this, "days");

    public LeaveModel() {
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
    public EmployeeModel getEmployee() {
        return employee.get();
    }

    public ObjectProperty<EmployeeModel> employeeProperty() {
        return employee;
    }

    public void setEmployee(EmployeeModel employee) {
        this.employee.set(employee);
    }

    @Column(name = "year")
    public int getYear() {
        return year.get();
    }

    public IntegerProperty yearProperty() {
        return year;
    }

    public void setYear(int year) {
        if (year < 0) {
            throw new IllegalArgumentException("Must be a positive value");
        }
        this.year.set(year);
    }

    @Column(name = "rate", columnDefinition = "DOUBLE DEFAULT 0.00", nullable = false)
    public double getRate() {
        return rate.get();
    }

    public DoubleProperty rateProperty() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate.set(rate);
    }

    @Column(name = "days", columnDefinition = "DOUBLE DEFAULT 0.00", nullable = false)
    public double getDays() {
        return days.get();
    }

    public DoubleProperty daysProperty() {
        return days;
    }

    public void setDays(double days) {
        this.days.set(days);
    }

    @Override
    @Transient
    public String getTitle() {
        return employee.get().getCode();
    }

    @Override
    public String toString() {
        return "LeaveModel{" +
                "id=" + id +
                ", employee=" + employee +
                ", year=" + year +
                ", rate=" + rate +
                ", days=" + days +
                '}';
    }
}
