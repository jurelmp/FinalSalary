package ph.petrologisticscorp.finalsalary.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ph.petrologisticscorp.finalsalary.database.PersonService;
import ph.petrologisticscorp.finalsalary.model.Person;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PersonEditController {

    @FXML
    private TextField name;

    @FXML
    private Button save;

    @Inject
    private PersonService personService;

    @Inject
    private PersonListController personListController;

    @FXML
    private void initialize() {
        System.out.println("initialize");
        final Person personSelected = personListController.getPersonSelected();
        name.setText(personSelected.getName());

    }

    public void save(ActionEvent event) {
        final Person personSelected = personListController.getPersonSelected();
        personSelected.setName(name.getText());
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
}
