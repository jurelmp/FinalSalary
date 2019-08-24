package ph.petrologisticscorp.finalsalary.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ph.petrologisticscorp.finalsalary.database.EmployeeService;
import ph.petrologisticscorp.finalsalary.model.Employee;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Date;

@Singleton
public class EmployeeListController {

    @FXML
    private TableView<Employee> tableViewEmployees;

    @FXML
    private TableColumn<Employee, String> colCode;

    @FXML
    private TableColumn<Employee, String> colLastName;

    @FXML
    private TableColumn<Employee, String> colFirstName;

    @FXML
    private TableColumn<Employee, String> colMidInitial;

    @FXML
    private TableColumn<Employee, String> colCompany;

    @FXML
    private TableColumn<Employee, String> colArea;

    @FXML
    private TableColumn<Employee, Date> colHireDate;

    @FXML
    private TableColumn<Employee, Boolean> colStatus;

    @Inject
    private EmployeeService employeeService;

    private ObservableList<Employee> mEmployeeObservableList;

    @FXML
    private void initialize() {
        mEmployeeObservableList = FXCollections.observableArrayList();
        colCode.setCellValueFactory(param -> param.getValue().codeProperty());
        colLastName.setCellValueFactory(param -> param.getValue().lastNameProperty());
        colFirstName.setCellValueFactory(param -> param.getValue().firstNameProperty());
        colMidInitial.setCellValueFactory(param -> param.getValue().middleNameProperty());
        colCompany.setCellValueFactory(param -> param.getValue().companyProperty().getValue().nameProperty());
        colArea.setCellValueFactory(param -> param.getValue().areaProperty().getValue().nameProperty());
        colHireDate.setCellValueFactory(param -> param.getValue().hireDateProperty());
        colStatus.setCellValueFactory(param -> param.getValue().activeProperty());
        mEmployeeObservableList.addAll(employeeService.getAll());
        tableViewEmployees.setItems(mEmployeeObservableList);
    }
}
