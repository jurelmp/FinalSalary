package ph.petrologisticscorp.finalsalary.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.controlsfx.control.table.TableFilter;
import ph.petrologisticscorp.finalsalary.database.AreaService;
import ph.petrologisticscorp.finalsalary.model.Area;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class AreaListController {

    @Inject
    private AreaService mAreaService;

    @FXML
    private BorderPane areaListPane;
    @FXML
    private TableView<Area> tableViewAreas;
    @FXML
    private TableColumn<Area, String> colName;

    private ObservableList<Area> mAreaObservableList;

    @FXML
    private void initialize() {
        mAreaObservableList = FXCollections.observableArrayList();
        setupBindings();
        setupListeners();
    }

    private void setupListeners() {
        areaListPane.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (oldScene == null && newScene != null) {
                // scene is set for the first time. Now its the time to listen stage changes.
                newScene.windowProperty().addListener((observableWindow, oldWindow, newWindow) -> {
                    if (oldWindow == null && newWindow != null) {
                        Stage s = ((Stage) newWindow);
                        s.setResizable(false);
                        s.setHeight(400.0);
                        s.setWidth(300.0);
                        s.setTitle("Areas");
                    }
                });
            }
        });

        tableViewAreas.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY) {
                showUpdateDialog();
            }
        });
    }

    private void showUpdateDialog() {
        Area area = tableViewAreas.getSelectionModel().getSelectedItem();
        TextInputDialog inputDialog = new TextInputDialog(area.getName());
        inputDialog.setTitle(area.getName());
        inputDialog.setContentText("Area");
        Stage s = (Stage) inputDialog.getDialogPane().getScene().getWindow();
        s.getIcons().add(new Image("/images/graph.png"));

        Optional<String> result = inputDialog.showAndWait();
        if (result.isPresent()) {
            if (!result.get().equals(area.getName())) {
                area.setName(result.get());
                mAreaService.update(area);
            }
        }
    }

    private void setupBindings() {
        colName.setCellValueFactory(param -> param.getValue().nameProperty());
        mAreaObservableList.addAll(mAreaService.getAll());
        tableViewAreas.setItems(mAreaObservableList);
        TableFilter.forTableView(tableViewAreas).lazy(true).apply();
    }

    public void newEntryAction(ActionEvent actionEvent) {
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setTitle("New Entry");
        inputDialog.setContentText("Name");
        Stage s = (Stage) inputDialog.getDialogPane().getScene().getWindow();
        s.getIcons().add(new Image("/images/graph.png"));

        Optional<String> result = inputDialog.showAndWait();
        if (result.isPresent() && !result.get().equals("")) {
            Area area = new Area();
            area.setName(result.get());
            mAreaService.create(area);
            mAreaObservableList.add(area);
        }
    }
}
