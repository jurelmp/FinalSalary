package ph.petrologisticscorp.finalsalary.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.controlsfx.control.table.TableFilter;
import ph.petrologisticscorp.finalsalary.Helper;
import ph.petrologisticscorp.finalsalary.database.AreaService;
import ph.petrologisticscorp.finalsalary.database.CompanyService;
import ph.petrologisticscorp.finalsalary.database.EmployeeService;
import ph.petrologisticscorp.finalsalary.gui.modeladapter.ListViewModelAdapter;
import ph.petrologisticscorp.finalsalary.model.Area;
import ph.petrologisticscorp.finalsalary.model.Company;
import ph.petrologisticscorp.finalsalary.model.Employee;
import ph.petrologisticscorp.finalsalary.model.Salary;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Date;

@Singleton
public class EmployeeEditorController {

    @FXML
    private SplitPane employeeEditorPane;

    @Inject
    private EmployeeListController mEmployeeListController;

    @FXML
    private TextField txtCode;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtMidInitial;
    @FXML
    private ComboBox<Company> cmbCompany;
    @FXML
    private ComboBox<Area> cmbArea;
    @FXML
    private DatePicker txtHireDate;
    @FXML
    private TextField txtStatus;

    @FXML
    private TableView<Salary> tableViewEmployeeSalaries;
    @FXML
    private TableColumn<Salary, Double> colSalary;
    @FXML
    private TableColumn<Salary, Double> colSinking;
    @FXML
    private TableColumn<Salary, Double> colCanteen;
    @FXML
    private TableColumn<Salary, Date> colFrom;
    @FXML
    private TableColumn<Salary, Date> colTo;

    @FXML
    private Button btnSalaryNew;
    @FXML
    private Button btnSave;

    @Inject
    private CompanyService mCompanyService;
    @Inject
    private AreaService mAreaService;
    @Inject
    private EmployeeService mEmployeeService;

    private ObservableList<Company> mCompanyObservableList;
    private ObservableList<Area> mAreaObservableList;
    private ObservableList<Salary> mSalaryObservableList;

    private Employee employeeSelected;
    private boolean isEditMode;

    @FXML
    private void initialize() {
        isEditMode = true;
        mCompanyObservableList = FXCollections.observableArrayList(Company.extractor());
        mSalaryObservableList = FXCollections.observableArrayList();
        mAreaObservableList = FXCollections.observableArrayList(Area.extractor());

        cmbCompany.setCellFactory((ListView<Company> param) -> new ListViewModelAdapter<>());
        cmbArea.setCellFactory((ListView<Area> param) -> new ListViewModelAdapter<>());

        employeeSelected = mEmployeeListController.getEmployeeSelected();
        if (employeeSelected == null) {
            employeeSelected = new Employee();
            isEditMode = false;
            txtCode.setDisable(false);
            btnSalaryNew.setDisable(true);
        }
        setupBindings();
        setupListeners();
    }

    private void setupListeners() {
        Helper.disableResize(employeeEditorPane.sceneProperty());
    }

    private void setupBindings() {
        BooleanBinding txtCodeValid = Bindings.createBooleanBinding(
                () -> txtCode.getText() != null && !txtCode.getText().isEmpty(), txtCode.textProperty());
        BooleanBinding txtLastNameValid = Bindings.createBooleanBinding(
                () -> txtLastName.getText() != null && !txtLastName.getText().isEmpty(), txtLastName.textProperty());
        BooleanBinding txtFirstNameValid = Bindings.createBooleanBinding(
                () -> txtFirstName.getText() != null && !txtFirstName.getText().isEmpty(), txtFirstName.textProperty());
        BooleanBinding txtMiddleNameValid = Bindings.createBooleanBinding(
                () -> txtMidInitial.getText() != null && !txtMidInitial.getText().isEmpty(), txtMidInitial.textProperty());
        BooleanBinding cmbCompanyValid = Bindings.createBooleanBinding(
                () -> cmbCompany.getValue() != null, cmbCompany.valueProperty());
        BooleanBinding cmbAreaValid = Bindings.createBooleanBinding(
                () -> cmbArea.getValue() != null, cmbArea.valueProperty());
        BooleanBinding txtHireDateValid = Bindings.createBooleanBinding(
                () -> txtHireDate.getValue() != null, txtHireDate.valueProperty());
        btnSave.disableProperty().bind(txtCodeValid.not()
                .or(txtLastNameValid.not())
                .or(txtFirstNameValid.not())
                .or(txtMiddleNameValid.not())
                .or(cmbCompanyValid.not())
                .or(cmbAreaValid.not())
                .or(txtHireDateValid.not()));

        colSalary.setCellValueFactory(param -> param.getValue().salaryProperty().asObject());
        colSinking.setCellValueFactory(param -> param.getValue().sinkingProperty().asObject());
        colCanteen.setCellValueFactory(param -> param.getValue().canteenProperty().asObject());
        colFrom.setCellValueFactory(param -> param.getValue().durationFromProperty());
        colTo.setCellValueFactory(param -> param.getValue().durationToProperty());

        if (isEditMode) {
            mSalaryObservableList.addAll(employeeSelected.getSalaries());
            tableViewEmployeeSalaries.setItems(mSalaryObservableList);
        }

        mCompanyObservableList.addAll(mCompanyService.getAll());
        mAreaObservableList.addAll(mAreaService.getAll());
        cmbCompany.setItems(mCompanyObservableList);
        cmbArea.setItems(mAreaObservableList);

        txtCode.setText(employeeSelected.getCode());
        txtLastName.setText(employeeSelected.getLastName());
        txtFirstName.setText(employeeSelected.getFirstName());
        txtMidInitial.setText(employeeSelected.getMiddleName());
        cmbCompany.setValue(employeeSelected.getCompany());
        cmbArea.setValue(employeeSelected.getArea());
        txtHireDate.setValue(employeeSelected.getHireDate() != null ?
                new java.sql.Date(employeeSelected.getHireDate().getTime()).toLocalDate() : null);
        txtStatus.setText(employeeSelected.isActive() ? "Active" : "Inactive");

        TableFilter.forTableView(tableViewEmployeeSalaries).lazy(true).apply();
    }

    public void save(ActionEvent event) {
        employeeSelected.setCode(txtCode.getText());
        employeeSelected.setFirstName(txtFirstName.getText().toUpperCase());
        employeeSelected.setLastName(txtLastName.getText().toUpperCase());
        employeeSelected.setMiddleName(txtMidInitial.getText().toUpperCase());
        employeeSelected.setCompany(cmbCompany.getValue());
        employeeSelected.setArea(cmbArea.getValue());
        employeeSelected.setHireDate(java.sql.Date.valueOf(txtHireDate.getValue()));
        employeeSelected.setActive(true);
        if (isEditMode) {
            mEmployeeService.update(employeeSelected);
        } else {
            mEmployeeListController.addEmployeeToTable(mEmployeeService.saveOrUpdate(employeeSelected));
        }
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }
}
