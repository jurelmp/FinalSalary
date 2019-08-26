package ph.petrologisticscorp.finalsalary.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import ph.petrologisticscorp.finalsalary.Helper;
import ph.petrologisticscorp.finalsalary.database.CompanyService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CompanyListController {

    @Inject
    private CompanyService mCompanyService;

    @FXML
    private BorderPane companyListPane;

    @FXML
    private void initialize() {
        setupBindings();
        setupListeners();
    }

    private void setupListeners() {
        Helper.disableResize(companyListPane.sceneProperty());
    }

    private void setupBindings() {

    }
}
