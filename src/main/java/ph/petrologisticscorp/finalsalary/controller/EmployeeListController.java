package ph.petrologisticscorp.finalsalary.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.controlsfx.control.table.TableFilter;
import ph.petrologisticscorp.finalsalary.database.EmployeeService;
import ph.petrologisticscorp.finalsalary.gui.WindowManager;
import ph.petrologisticscorp.finalsalary.model.Employee;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
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
    @Inject
    private EntityManager entityManager;

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
        TableFilter.forTableView(tableViewEmployees).lazy(true).apply();
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
                        newWindow.setOnCloseRequest(event -> {
                            Platform.exit();
                            System.exit(0);
                        });
                        ((Stage) newWindow).setMaximized(true);
                    }
                });
            }
        });

        tableViewEmployees.setOnKeyReleased((keyEvent) -> {
            KeyCode keyCode = keyEvent.getCode();
            if (keyCode == KeyCode.ENTER) {
                mWindowManager.switchScene(WindowManager.SCENES.EMPLOYEE_EDITOR_SCENE);
            }
        });
    }

    public Employee getEmployeeSelected() {
        return tableViewEmployees.getSelectionModel().getSelectedItem();
    }

    public void newAction(ActionEvent event) {
        tableViewEmployees.getSelectionModel().clearSelection();
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

    public void aboutAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Petrologistics Corporation");
        alert.setHeaderText("Salary Ex");
        alert.setContentText("App Version 1.0\nJavaFX 8\nH2 Database 1.4.197\nHibernate 5.4.4.Final");
        Stage s = (Stage) alert.getDialogPane().getScene().getWindow();
        s.getIcons().add(new Image("/images/graph.png"));
        alert.showAndWait();
    }

    public void companiesAction(ActionEvent event) {
        mWindowManager.switchScene(WindowManager.SCENES.COMPANY_LIST_SCENE);
    }

    public void menuItemNew(ActionEvent event) {
        tableViewEmployees.getSelectionModel().clearSelection();
        mWindowManager.switchScene(WindowManager.SCENES.EMPLOYEE_EDITOR_SCENE);
    }

    public void areasAction(ActionEvent event) {
        mWindowManager.switchScene(WindowManager.SCENES.AREA_LIST_SCENE);
    }

    public void reportsAction(ActionEvent event) {
        mWindowManager.switchScene(WindowManager.SCENES.REPORT_SCENE);
    }
}
