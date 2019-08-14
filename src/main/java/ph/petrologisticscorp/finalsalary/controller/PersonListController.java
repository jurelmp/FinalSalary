package ph.petrologisticscorp.finalsalary.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import ph.petrologisticscorp.finalsalary.database.PersonService;
import ph.petrologisticscorp.finalsalary.domain.company.CompanyModel;
import ph.petrologisticscorp.finalsalary.domain.company.CompanyService;
import ph.petrologisticscorp.finalsalary.gui.WindowManager;
import ph.petrologisticscorp.finalsalary.gui.modeladapter.ListViewModelAdapter;
import ph.petrologisticscorp.finalsalary.model.Person;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PersonListController {
    @FXML
    private ListView<Person> personsListView;

    private ObservableList<Person> personObservableList;

    @Inject
    private PersonService personService;

    @Inject
    private CompanyService companyService;

    @Inject
    WindowManager windowManager;

    @FXML
    private void initialize() {
        System.out.println("initialize");
        personsListView.setCellFactory((ListView<Person> param) -> new ListViewModelAdapter<>());
        setupBindings();
        setupListeners();
    }

    private void setupBindings() {
        System.out.println("setupBindings");
        personObservableList = FXCollections.observableArrayList(Person.extractor());
        personObservableList.addAll(personService.getAll());
        personsListView.setItems(personObservableList);
    }

    private void setupListeners() {
        System.out.println("setupListeners");
        personsListView.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (event.getClickCount() == 2) {
                    windowManager.switchScene(WindowManager.SCENES.PERSON_EDIT_SCENE);
                }
            }
        });
    }

    public void random(ActionEvent event) {
        personObservableList.add(personService.createRandom());
        companyService.save(new CompanyModel("Petrologistics Corp."));
    }

    public Person getPersonSelected() {
        return personsListView.getSelectionModel().getSelectedItem();
    }
}
