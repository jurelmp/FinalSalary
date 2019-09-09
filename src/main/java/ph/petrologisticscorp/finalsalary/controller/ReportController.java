package ph.petrologisticscorp.finalsalary.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import ph.petrologisticscorp.finalsalary.Constants;
import ph.petrologisticscorp.finalsalary.Helper;
import ph.petrologisticscorp.finalsalary.ReportManager;
import ph.petrologisticscorp.finalsalary.database.AreaService;
import ph.petrologisticscorp.finalsalary.database.CompanyService;
import ph.petrologisticscorp.finalsalary.gui.modeladapter.ListViewModelAdapter;
import ph.petrologisticscorp.finalsalary.model.Area;
import ph.petrologisticscorp.finalsalary.model.Company;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class ReportController {

    @FXML
    private GridPane reportGridPane;

    @FXML
    private Button btnSummary;
    @FXML
    private Button btnIndividual;
    @FXML
    private Button btnUnusedLeaves;
    @FXML
    private DatePicker datePickerFrom;
    @FXML
    private DatePicker datePickerTo;
    @FXML
    private ComboBox<Company> cmbCompany;
    @FXML
    private ComboBox<Area> cmbArea;
    @FXML
    private ComboBox<Integer> cmbYear;

    @Inject
    private AreaService mAreaService;
    @Inject
    private CompanyService mCompanyService;

    private ObservableList<Company> mCompanyObservableList;
    private ObservableList<Area> mAreaObservableList;

    @Inject
    private ReportManager mReportManager;

    @FXML
    private void initialize() throws ClassNotFoundException, SQLException {
        mCompanyObservableList = FXCollections.observableArrayList(Company.extractor());
        mAreaObservableList = FXCollections.observableArrayList(Area.extractor());
        setupBindings();
        setupListeners();
    }

    private void setupBindings() {
        BooleanBinding datePickerFromValid = Bindings.createBooleanBinding(
                () -> datePickerFrom.getValue() != null, datePickerFrom.valueProperty());
        BooleanBinding datePickerToValid = Bindings.createBooleanBinding(
                () -> datePickerTo.getValue() != null, datePickerTo.valueProperty());
        BooleanBinding cmbCompanyValid = Bindings.createBooleanBinding(
                () -> cmbCompany.getValue() != null, cmbCompany.valueProperty());
        BooleanBinding cmbAreaValid = Bindings.createBooleanBinding(
                () -> cmbArea.getValue() != null, cmbArea.valueProperty());
        BooleanBinding cmbYearValid = Bindings.createBooleanBinding(
                () -> cmbYear.getValue() != null, cmbYear.valueProperty());
        cmbCompany.setCellFactory((ListView<Company> param) -> new ListViewModelAdapter<>());
        cmbArea.setCellFactory((ListView<Area> param) -> new ListViewModelAdapter<>());

        mCompanyObservableList.addAll(mCompanyService.getAll());
        mAreaObservableList.addAll(mAreaService.getAll());
        cmbCompany.setItems(mCompanyObservableList);
        cmbArea.setItems(mAreaObservableList);
        cmbYear.setItems(FXCollections.observableArrayList(Helper.generateYear()));

        btnSummary.disableProperty().bind(datePickerFromValid.not()
                .or(datePickerToValid.not())
                .or(cmbCompanyValid.not())
                .or(cmbAreaValid.not())
                .or(cmbYearValid.not()));
        btnIndividual.disableProperty().bind(datePickerFromValid.not()
                .or(datePickerToValid.not())
                .or(cmbCompanyValid.not())
                .or(cmbAreaValid.not())
                .or(cmbYearValid.not()));
        btnUnusedLeaves.disableProperty().bind(datePickerFromValid.not()
                .or(datePickerToValid.not())
                .or(cmbCompanyValid.not())
                .or(cmbAreaValid.not())
                .or(cmbYearValid.not()));

        datePickerTo.setValue(LocalDate.now());
        datePickerFrom.setValue(datePickerTo.getValue().minusDays(15));
        cmbCompany.getSelectionModel().selectFirst();
        cmbArea.getSelectionModel().selectFirst();
        cmbYear.getSelectionModel().selectFirst();
    }

    private void setupListeners() {
        reportGridPane.sceneProperty().addListener((observableScene, oldScene, newScene) -> {
            if (oldScene == null && newScene != null) {
                newScene.windowProperty().addListener((observableWindow, oldWindow, newWindow) -> {
                    if (oldWindow == null && newWindow != null) {
                        Stage stage = ((Stage) newWindow);
                        stage.setWidth(350.0);
                        stage.setHeight(350.0);
                        stage.setResizable(false);
                    }
                });
            }
        });
    }

    private Map getParameters() {
        HashMap map = new HashMap();
        map.put(Constants.PRESIDENT.getValue(), "JUREL PATOC");
        map.put(Constants.DATE_FROM.getValue(), Helper.toUtilDate(datePickerFrom.getValue()));
        map.put(Constants.DATE_TO.getValue(), Helper.toUtilDate(datePickerTo.getValue()));
        map.put(Constants.COMPANY.getValue(), cmbCompany.getValue());
        map.put(Constants.COMPANY_ID.getValue(), cmbCompany.getValue().getId());
        map.put(Constants.AREA.getValue(), cmbArea.getValue());
        map.put(Constants.AREA_ID.getValue(), cmbArea.getValue().getId());
        map.put(Constants.YEAR.getValue(), cmbYear.getValue());
        map.put(Constants.DATE_NOW.getValue(), new Date());
        return map;
    }

    public void btnSummaryClickAction(ActionEvent event) throws JRException, SQLException, ClassNotFoundException {
        mReportManager.generateReport(ReportManager.REPORTS.SALARIES, getParameters());
    }

    public void btnIndividualClickAction(ActionEvent event) {
        // TODO: Report viewing for individual employee's salary.
    }

    public void btnUnusedLeaveClickAction(ActionEvent event) {
        // TODO: Report viewing for employees unused leaves.
    }
}
