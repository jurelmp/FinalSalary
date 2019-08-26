package ph.petrologisticscorp.finalsalary;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Helper {
    public static void disableResize(ReadOnlyObjectProperty<Scene> sceneReadOnlyObjectProperty) {
        sceneReadOnlyObjectProperty.addListener((observableScene, oldScene, newScene) -> {
            if (oldScene == null && newScene != null) {
                newScene.windowProperty().addListener((observableWindow, oldWindow, newWindow) -> {
                    if (oldWindow == null && newWindow != null) {
                        ((Stage) newWindow).setResizable(false);
                    }
                });
            }
        });
    }
}
