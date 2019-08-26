package ph.petrologisticscorp.finalsalary.controller;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.table.TableFilter;
import ph.petrologisticscorp.finalsalary.database.CompanyService;
import ph.petrologisticscorp.finalsalary.model.Company;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class CompanyListController {

    @Inject
    private CompanyService mCompanyService;

    @FXML
    private BorderPane companyListPane;

    @FXML
    private TableView<Company> tableViewCompanies;
    @FXML
    private TableColumn<Company, String> colName;

    private ObservableList<Company> mCompanyObservableList;

    private ContextMenu contextMenu;
    private MenuItem menuItemEdit;
    private MenuItem menuItemAreas;

    @FXML
    private void initialize() {
        mCompanyObservableList = FXCollections.observableArrayList();
        contextMenu = new ContextMenu();
        menuItemEdit = new MenuItem("Edit");
        menuItemAreas = new MenuItem("Area List");
        contextMenu.getItems().add(menuItemEdit);
        contextMenu.getItems().add(menuItemAreas);
        setupBindings();
        setupListeners();
    }

    private void setupListeners() {
        companyListPane.sceneProperty().addListener((observableScene, oldScene, newScene) -> {
            if (oldScene == null && newScene != null) {
                // scene is set for the first time. Now its the time to listen stage changes.
                newScene.windowProperty().addListener((observableWindow, oldWindow, newWindow) -> {
                    if (oldWindow == null && newWindow != null) {
                        Stage s = ((Stage) newWindow);
                        s.setResizable(false);
                        s.setHeight(400.0);
                        s.setWidth(300.0);
                        s.setTitle("Companies");
                    }
                });
            }
        });

        tableViewCompanies.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY) {
                showUpdateDialog();
            }
        });

        tableViewCompanies.setRowFactory(param -> {
            final TableRow<Company> row = new TableRow<>();
            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu)
            );
            return row;
        });

        menuItemEdit.setOnAction(event -> showUpdateDialog());
        menuItemAreas.setOnAction(event -> System.out.println("Menu Item Areas Clicked!"));
    }

    private void showUpdateDialog() {
        Company company = tableViewCompanies.getSelectionModel().getSelectedItem();
        TextInputDialog inputDialog = new TextInputDialog(company.getName());
        inputDialog.setTitle("Update " + company.getName());
        inputDialog.setContentText("Name");
        Stage s = (Stage) inputDialog.getDialogPane().getScene().getWindow();
        s.getIcons().add(new Image("/images/graph.png"));

        Optional<String> result = inputDialog.showAndWait();
        if (result.isPresent()) {
            if (!result.get().equals(company.getName())) {
                company.setName(result.get());
                mCompanyService.update(company);
            }
        }
    }

    private void setupBindings() {
        colName.setCellValueFactory(param -> param.getValue().nameProperty());
        mCompanyObservableList.addAll(mCompanyService.getAll());
        tableViewCompanies.setItems(mCompanyObservableList);
        TableFilter.forTableView(tableViewCompanies).lazy(true).apply();
    }

    public void newEntryAction(ActionEvent event) {
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setTitle("New Entry");
        inputDialog.setContentText("Name");
        Stage s = (Stage) inputDialog.getDialogPane().getScene().getWindow();
        s.getIcons().add(new Image("/images/graph.png"));

        Optional<String> result = inputDialog.showAndWait();
        if (result.isPresent()) {
            Company company = new Company();
            company.setName(result.get());
            mCompanyService.create(company);
            mCompanyObservableList.add(company);
        }
    }
}
