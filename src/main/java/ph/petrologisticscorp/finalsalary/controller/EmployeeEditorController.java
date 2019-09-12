package ph.petrologisticscorp.finalsalary.controller;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.controlsfx.control.table.TableFilter;
import ph.petrologisticscorp.finalsalary.Helper;
import ph.petrologisticscorp.finalsalary.database.*;
import ph.petrologisticscorp.finalsalary.gui.modeladapter.ListViewModelAdapter;
import ph.petrologisticscorp.finalsalary.model.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Date;
import java.util.Optional;

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
    @FXML
    private Button btnLeaveAction;

    @Inject
    private CompanyService mCompanyService;
    @Inject
    private AreaService mAreaService;
    @Inject
    private EmployeeService mEmployeeService;
    @Inject
    private SalaryService mSalaryService;
    @Inject
    private LeaveService mLeaveService;

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
            btnLeaveAction.setDisable(true);
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

    public void showSalaryDialog() {
        Dialog<Salary> dialog = new Dialog<>();
        dialog.setTitle("Add Salary");

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);
        ((Stage) dialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/images/graph.png"));

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 15, 10, 15));

        TextField textFieldSalary = new TextField();
        textFieldSalary.setTextFormatter(Helper.generateDoubleTextFormatter());
        TextField textFieldSinking = new TextField();
        textFieldSinking.setTextFormatter(Helper.generateDoubleTextFormatter());
        TextField textFieldCanteen = new TextField();
        textFieldCanteen.setTextFormatter(Helper.generateDoubleTextFormatter());
        DatePicker datePickerFrom = new DatePicker(new java.sql.Date(new Date().getTime()).toLocalDate());
        DatePicker datePickerTo = new DatePicker(new java.sql.Date(new Date().getTime()).toLocalDate());

        gridPane.add(new Label("Salary"), 0, 0);
        gridPane.add(textFieldSalary, 1, 0);
        gridPane.add(new Label("Sinking Fund"), 0, 1);
        gridPane.add(textFieldSinking, 1, 1);
        gridPane.add(new Label("Canteen"), 0, 2);
        gridPane.add(textFieldCanteen, 1, 2);
        gridPane.add(new Label("From"), 0, 3);
        gridPane.add(datePickerFrom, 1, 3);
        gridPane.add(new Label("To"), 0, 4);
        gridPane.add(datePickerTo, 1, 4);

        dialog.getDialogPane().setContent(gridPane);

        Platform.runLater(textFieldSalary::requestFocus);

        dialog.setResultConverter(dialogButtonType -> {
            if (dialogButtonType == saveButtonType) {
                Salary salary = new Salary();
                salary.setSalary(Double.parseDouble(textFieldSalary.getText()));
                salary.setSinking(Double.parseDouble(textFieldSinking.getText()));
                salary.setCanteen(Double.parseDouble(textFieldCanteen.getText()));
                salary.setDurationFrom(Helper.toUtilDate(datePickerFrom.getValue()));
                salary.setDurationTo(Helper.toUtilDate(datePickerTo.getValue()));
                salary.setEmployee(employeeSelected);
                return salary;
            }
            return null;
        });

        Optional<Salary> result = dialog.showAndWait();

        result.ifPresent(salary -> {
            Salary s = mSalaryService.saveOrUpdate(salary);
            mSalaryObservableList.add(s);
            System.out.println(s);
        });
    }

    public void showLeaveDialog() {
        Dialog<Leave> dialog = new Dialog<>();
        dialog.setTitle("Leaves");
        Leave leave = employeeSelected.getLeave();

        if (employeeSelected.getLeave() == null) {
            leave = new Leave();
        }

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);
        ((Stage) dialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/images/graph.png"));

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 15, 10, 15));

        TextField textFieldDays = new TextField();
        textFieldDays.setTextFormatter(Helper.generateDoubleTextFormatter());

        TextField textFieldRate = new TextField();
        textFieldRate.setTextFormatter(Helper.generateDoubleTextFormatter());

        ComboBox<Integer> comboBoxYear = new ComboBox<>(FXCollections.observableArrayList(Helper.generateYear()));

        gridPane.add(new Label("Days"), 0, 0);
        gridPane.add(textFieldDays, 1, 0);
        gridPane.add(new Label("Rate"), 0, 1);
        gridPane.add(textFieldRate, 1, 1);
        gridPane.add(new Label("Year"), 0, 2);
        gridPane.add(comboBoxYear, 1, 2);

        dialog.getDialogPane().setContent(gridPane);

        textFieldDays.setText(String.valueOf(leave.getDays()));
        textFieldRate.setText(String.valueOf(leave.getRate()));
        if (leave.getYear() == 0) comboBoxYear.getSelectionModel().selectFirst();
        else comboBoxYear.getSelectionModel().select(new Integer(leave.getYear()));
        Platform.runLater(textFieldDays::requestFocus);

        Leave finalLeave = leave;
        dialog.setResultConverter(dialogButtonType -> {
            if (dialogButtonType == saveButtonType) {
                finalLeave.setDays(Double.parseDouble(textFieldDays.getText()));
                finalLeave.setRate(Double.parseDouble(textFieldRate.getText()));
                finalLeave.setYear(comboBoxYear.getValue());
                finalLeave.setEmployee(employeeSelected);
                return finalLeave;
            }
            return null;
        });

        Optional<Leave> result = dialog.showAndWait();

        result.ifPresent(leave1 -> {
            Leave temp = mLeaveService.saveOrUpdate(leave1);
            employeeSelected.setLeave(temp);
            System.out.println(temp);
        });
    }
}
