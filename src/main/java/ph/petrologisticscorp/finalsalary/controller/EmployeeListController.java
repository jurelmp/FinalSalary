package ph.petrologisticscorp.finalsalary.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ph.petrologisticscorp.finalsalary.database.EmployeeService;
import ph.petrologisticscorp.finalsalary.gui.WindowManager;
import ph.petrologisticscorp.finalsalary.model.Employee;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Date;

@Singleton
public class EmployeeListController {

    @FXML
    private BorderPane employeeListPane;

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

    @Inject
    private WindowManager mWindowManager;

    @FXML
    private void initialize() {
        mEmployeeObservableList = FXCollections.observableArrayList();
        setupBindings();
        setupListeners();
    }

    private void setupBindings() {
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

    private void setupListeners() {
        tableViewEmployees.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                System.out.println(tableViewEmployees.getSelectionModel().getSelectedItem());
                mWindowManager.switchScene(WindowManager.SCENES.EMPLOYEE_EDITOR_SCENE);
            }
        });

        employeeListPane.sceneProperty().addListener((observableScene, oldScene, newScene) -> {
            if (oldScene == null && newScene != null) {
                // scene is set for the first time. Now its the time to listen stage changes.
                newScene.windowProperty().addListener((observableWindow, oldWindow, newWindow) -> {
                    if (oldWindow == null && newWindow != null) {
                        // stage is set. now is the right time to do whatever we need to the stage in the controller.
                        ((Stage) newWindow).maximizedProperty().addListener((a, b, c) -> {
                            if (c) {
                                System.out.println("I am maximized!");
                            }
                        });
                        newWindow.setOnCloseRequest(event -> {
                            Platform.exit();
                            System.exit(0);
                        });
                    }
                });
            }
        });
    }

    public Employee getEmployeeSelected() {
        return tableViewEmployees.getSelectionModel().getSelectedItem();
    }

    public void newAction(ActionEvent event) {
        tableViewEmployees.getSelectionModel().clearSelection();
        System.out.println(tableViewEmployees.getSelectionModel().getSelectedItem());
        mWindowManager.switchScene(WindowManager.SCENES.EMPLOYEE_EDITOR_SCENE);
    }

    public void addEmployeeToTable(Employee employee) {
        mEmployeeObservableList.add(employee);
        tableViewEmployees.getSelectionModel().select(employee);
    }

    public void closeAction(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }
}
