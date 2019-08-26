package ph.petrologisticscorp.finalsalary.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.io.IOException;

public class WindowManager {

    @Inject
    private FXMLLoader fxmlLoader;

    public enum SCENES {
        PERSON_LIST_SCENE("/fxml/PersonList.fxml"),
        PERSON_EDIT_SCENE("/fxml/PersonEdit.fxml"),
        EMPLOYEE_LIST_SCENE("/fxml/EmployeeList.fxml"),
        EMPLOYEE_EDITOR_SCENE("/fxml/EmployeeEditor.fxml"),
        COMPANY_LIST_SCENE("/fxml/CompanyList.fxml");

        private String sceneName;

        SCENES(String scenePath) {
            this.sceneName = scenePath;
        }

        public String getSceneName() {
            return sceneName;
        }
    }

    public void switchScene(SCENES scene) {
        fxmlLoader.setRoot(null);
        fxmlLoader.setController(null);
        fxmlLoader.setLocation(getClass().getResource(scene.getSceneName()));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Wrong path: " + e.getMessage());
        }
        if (null == root) {
            throw new IllegalStateException("There was likely an error in the controller initialize() method.");
        }
        fxmlLoader.getController();
        Stage stage = new Stage();
        stage.getIcons().add(new Image("/images/graph.png"));
        stage.setTitle("Petrologistics Corporation");
        stage.setScene(new Scene(root, 750, 550));
        stage.show();
    }
}
